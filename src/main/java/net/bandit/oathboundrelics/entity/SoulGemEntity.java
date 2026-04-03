package net.bandit.oathboundrelics.entity;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.events.SoulFractureEvents;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;

public class SoulGemEntity extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID =
            SynchedEntityData.defineId(SoulGemEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private static final String OWNER_UUID_TAG = "OwnerUUID";

    private static final int PARTICLE_INTERVAL_TICKS = 6;
    private static final int PICKUP_CHECK_INTERVAL_TICKS = 2;

    private static final double PARTICLE_BASE_Y_OFFSET = 1.05D;

    public SoulGemEntity(EntityType<? extends SoulGemEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public void setOwnerUUID(UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public boolean isOwnedBy(ServerPlayer player) {
        UUID owner = getOwnerUUID();
        return owner != null && owner.equals(player.getUUID());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(OWNER_UUID, Optional.empty());
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            return;
        }

        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        UUID ownerUuid = getOwnerUUID();
        if (ownerUuid == null) {
            if (this.tickCount > 20) {
                this.discard();
            }
            return;
        }

        ServerPlayer owner = serverLevel.getServer().getPlayerList().getPlayer(ownerUuid);
        if (owner == null || !owner.isAlive() || owner.level() != this.level()) {
            return;
        }

        if (this.tickCount % PARTICLE_INTERVAL_TICKS == 0) {
            spawnOwnerOnlyParticles(serverLevel, owner);
        }

        if (this.tickCount % PICKUP_CHECK_INTERVAL_TICKS == 0) {
            tryConsume(owner);
        }
    }

    private void spawnOwnerOnlyParticles(ServerLevel serverLevel, ServerPlayer owner) {
        double age = this.tickCount;
        double bob = Math.sin(age * 0.08D) * 0.18D;
        double particleY = this.getY() + PARTICLE_BASE_Y_OFFSET + bob;

        serverLevel.sendParticles(
                owner,
                ParticleTypes.ENCHANT,
                false,
                this.getX(),
                particleY,
                this.getZ(),
                6,
                0.24D,
                0.16D,
                0.24D,
                0.01D
        );

        serverLevel.sendParticles(
                owner,
                ParticleTypes.END_ROD,
                false,
                this.getX(),
                particleY + 0.04D,
                this.getZ(),
                2,
                0.10D,
                0.10D,
                0.10D,
                0.0D
        );
    }

    private void tryConsume(ServerPlayer owner) {
        double pickupRadius = OathboundConfig.soulGemPickupRadius();
        double pickupRadiusSq = pickupRadius * pickupRadius;

        double ownerX = owner.getX();
        double ownerY = owner.getY() + (owner.getBbHeight() * 0.5D);
        double ownerZ = owner.getZ();

        double gemX = this.getX();
        double gemY = this.getY() + PARTICLE_BASE_Y_OFFSET;
        double gemZ = this.getZ();

        double dx = ownerX - gemX;
        double dy = ownerY - gemY;
        double dz = ownerZ - gemZ;

        if ((dx * dx) + (dy * dy) + (dz * dz) > pickupRadiusSq) {
            return;
        }

        int current = owner.getData(AttachmentRegistry.SOUL_FRACTURE_COUNT.get());
        if (current > 0) {
            owner.setData(AttachmentRegistry.SOUL_FRACTURE_COUNT.get(), current - 1);
        }

        SoulFractureEvents.applySoulFracturePenalty(owner);

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    owner,
                    ParticleTypes.END_ROD,
                    false,
                    this.getX(),
                    this.getY() + PARTICLE_BASE_Y_OFFSET,
                    this.getZ(),
                    10,
                    0.28D,
                    0.22D,
                    0.28D,
                    0.03D
            );
        }

        this.level().playSound(
                null,
                this.blockPosition(),
                SoundEvents.AMETHYST_BLOCK_CHIME,
                SoundSource.PLAYERS,
                0.9F,
                1.35F
        );

        this.discard();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.hasUUID(OWNER_UUID_TAG)) {
            setOwnerUUID(tag.getUUID(OWNER_UUID_TAG));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        UUID owner = getOwnerUUID();
        if (owner != null) {
            tag.putUUID(OWNER_UUID_TAG, owner);
        }
    }
}