package net.bandit.oathboundrelics.entity;

import net.bandit.oathboundrelics.util.SlothCombatUtil;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class HeavyCubeProjectileEntity extends ThrowableItemProjectile {

    private static final EntityDataAccessor<Float> BASE_DAMAGE =
            SynchedEntityData.defineId(HeavyCubeProjectileEntity.class, EntityDataSerializers.FLOAT);

    public HeavyCubeProjectileEntity(EntityType<? extends HeavyCubeProjectileEntity> type, Level level) {
        super(type, level);
    }

    public HeavyCubeProjectileEntity(EntityType<? extends HeavyCubeProjectileEntity> type, Level level, LivingEntity thrower) {
        super(type, thrower, level);
    }

    public void setBaseDamage(float damage) {
        this.entityData.set(BASE_DAMAGE, damage);
    }

    public float getBaseDamage() {
        return this.entityData.get(BASE_DAMAGE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BASE_DAMAGE, 0.0F);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.STONE;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!(result.getEntity() instanceof LivingEntity target)) {
            return;
        }

        if (this.getOwner() instanceof LivingEntity owner) {
            boolean hurt = target.hurt(this.damageSources().thrown(this, owner), getBaseDamage());
            if (hurt) {
                SlothCombatUtil.applyHeavyStun(target);
            }
        }

        spawnImpactEffects();
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        spawnBlockImpactEffects(result);

        this.setDeltaMovement(0.0D, 0.0D, 0.0D);
        this.discard();
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (result.getType() == HitResult.Type.MISS) {
            return;
        }

        if (!this.level().isClientSide && this.isAlive()) {
            this.discard();
        }
    }

    private void spawnImpactEffects() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.sendParticles(
                new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState()),
                this.getX(),
                this.getY() + 0.1D,
                this.getZ(),
                16,
                0.25D,
                0.25D,
                0.25D,
                0.08D
        );

        this.level().playSound(
                null,
                this.blockPosition(),
                SoundEvents.MACE_SMASH_GROUND_HEAVY,
                SoundSource.PLAYERS,
                1.0F,
                0.75F
        );
    }

    private void spawnBlockImpactEffects(BlockHitResult result) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.sendParticles(
                new BlockParticleOption(ParticleTypes.BLOCK, this.level().getBlockState(result.getBlockPos())),
                result.getLocation().x,
                result.getLocation().y,
                result.getLocation().z,
                20,
                0.2D,
                0.2D,
                0.2D,
                0.08D
        );

        this.level().playSound(
                null,
                result.getBlockPos(),
                SoundEvents.ANVIL_LAND,
                SoundSource.PLAYERS,
                1.2F,
                0.65F
        );
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("BaseDamage", getBaseDamage());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setBaseDamage(tag.getFloat("BaseDamage"));
    }
}