package net.bandit.oathboundrelics.blocks.blockentity;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.registry.BlockEntityRegistry;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.UUID;

public class RiteOfSeveranceBlockEntity extends BlockEntity {

    private static final int TOTAL_RITUAL_TICKS = 20 * 60;
    private static final int PULSE_INTERVAL = 40;
    private static final double MAX_DISTANCE_SQR = 7.0D * 7.0D;

    private static final ResourceLocation RITUAL_ARMOR_PENALTY_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "rite_of_severance_armor_penalty");

    private boolean active = false;
    private UUID activePlayerUuid;
    private int ritualTicks = 0;

    public RiteOfSeveranceBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.RITE_OF_SEVERANCE_BE.get(), pos, blockState);
    }

    public InteractionResult onUsed(net.minecraft.world.entity.player.Player player) {
        if (level == null) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.CONSUME;
        }

        if (active) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.oathboundrelics.severance.already_active").withStyle(ChatFormatting.RED),
                    true
            );
            return InteractionResult.CONSUME;
        }

        if (!OathboundUtil.isBranded(serverPlayer)) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.oathboundrelics.severance.need_relic").withStyle(ChatFormatting.RED),
                    true
            );
            return InteractionResult.CONSUME;
        }

        if (!OathboundUtil.mayStartSeverance(level)) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.oathboundrelics.severance.night_or_end").withStyle(ChatFormatting.RED),
                    true
            );
            return InteractionResult.CONSUME;
        }

        if (!OathboundUtil.hasSeveranceMaterials(serverPlayer)) {
            serverPlayer.displayClientMessage(
                    Component.translatable("message.oathboundrelics.severance.need_materials").withStyle(ChatFormatting.RED),
                    true
            );
            return InteractionResult.CONSUME;
        }

        OathboundUtil.consumeSeveranceMaterials(serverPlayer);
        OathboundUtil.setSeveranceActive(serverPlayer, true);

        this.active = true;
        this.activePlayerUuid = serverPlayer.getUUID();
        this.ritualTicks = 0;

        setChanged();

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(
                    null,
                    worldPosition,
                    SoundEvents.BEACON_ACTIVATE,
                    SoundSource.BLOCKS,
                    1.2F,
                    0.7F
            );

            serverLevel.sendParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    worldPosition.getX() + 0.5D,
                    worldPosition.getY() + 1.0D,
                    worldPosition.getZ() + 0.5D,
                    20,
                    0.35D,
                    0.15D,
                    0.35D,
                    0.01D
            );
        }

        serverPlayer.displayClientMessage(
                Component.translatable("message.oathboundrelics.severance.started").withStyle(ChatFormatting.DARK_RED),
                false
        );

        return InteractionResult.CONSUME;
    }

    public static void tick(net.minecraft.world.level.Level level, BlockPos pos, BlockState state, RiteOfSeveranceBlockEntity altar) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!altar.active || altar.activePlayerUuid == null) {
            return;
        }

        ServerPlayer player = serverLevel.getServer().getPlayerList().getPlayer(altar.activePlayerUuid);

        if (player == null || !player.isAlive() || player.isRemoved()) {
            altar.fail(serverLevel, null, "message.oathboundrelics.severance.failed_lost");
            return;
        }

        if (player.level() != level) {
            altar.fail(serverLevel, player, "message.oathboundrelics.severance.failed_dimension");
            return;
        }

        if (!OathboundUtil.isBranded(player)) {
            altar.fail(serverLevel, player, "message.oathboundrelics.severance.failed_relic_missing");
            return;
        }

        double centerX = pos.getX() + 0.5D;
        double centerY = pos.getY() + 0.5D;
        double centerZ = pos.getZ() + 0.5D;

        if (player.distanceToSqr(centerX, centerY, centerZ) > MAX_DISTANCE_SQR) {
            altar.fail(serverLevel, player, "message.oathboundrelics.severance.failed_too_far");
            return;
        }

        altar.ritualTicks++;

        if (altar.ritualTicks % 10 == 0) {
            serverLevel.sendParticles(
                    ParticleTypes.SMOKE,
                    centerX,
                    pos.getY() + 1.1D,
                    centerZ,
                    3,
                    0.3D,
                    0.05D,
                    0.3D,
                    0.002D
            );
        }

        if (altar.ritualTicks % PULSE_INTERVAL == 0) {
            altar.performPulse(serverLevel, player);
        }

        if (altar.ritualTicks >= TOTAL_RITUAL_TICKS) {
            altar.complete(serverLevel, player);
        }
    }

    private void performPulse(ServerLevel level, ServerPlayer player) {
        int pulseIndex = (ritualTicks / PULSE_INTERVAL) % 6;

        level.playSound(
                null,
                worldPosition,
                SoundEvents.RESPAWN_ANCHOR_CHARGE,
                SoundSource.BLOCKS,
                1.0F,
                0.85F + level.random.nextFloat() * 0.3F
        );

        level.sendParticles(
                ParticleTypes.SOUL,
                player.getX(),
                player.getY() + 1.0D,
                player.getZ(),
                10,
                0.25D,
                0.4D,
                0.25D,
                0.01D
        );

        level.sendParticles(
                ParticleTypes.ASH,
                worldPosition.getX() + 0.5D,
                worldPosition.getY() + 1.0D,
                worldPosition.getZ() + 0.5D,
                14,
                0.35D,
                0.15D,
                0.35D,
                0.01D
        );

        player.hurt(player.damageSources().magic(), 2.0F);

        switch (pulseIndex) {
            case 0 -> {
                player.setRemainingFireTicks(Math.max(player.getRemainingFireTicks(), 60));
                player.displayClientMessage(
                        Component.translatable("message.oathboundrelics.severance.pulse_ember").withStyle(ChatFormatting.RED),
                        true
                );
            }
            case 1 -> {
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1, true, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, true, true, true));
                player.displayClientMessage(
                        Component.translatable("message.oathboundrelics.severance.pulse_fracture").withStyle(ChatFormatting.GRAY),
                        true
                );
            }
            case 2 -> {
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80, 0, true, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 80, 1, true, true, true));
                player.displayClientMessage(
                        Component.translatable("message.oathboundrelics.severance.pulse_doom").withStyle(ChatFormatting.DARK_PURPLE),
                        true
                );
            }
            case 3 -> {
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 80, 1, true, true, true));
                provokeNearby(level, player);
                player.displayClientMessage(
                        Component.translatable("message.oathboundrelics.severance.pulse_provocation").withStyle(ChatFormatting.GOLD),
                        true
                );
            }
            case 4 -> {
                applyArmorPenalty(player);
                player.displayClientMessage(
                        Component.translatable("message.oathboundrelics.severance.pulse_plate").withStyle(ChatFormatting.RED),
                        true
                );
            }
            case 5 -> {
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 2, true, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 1, true, true, true));
                player.displayClientMessage(
                        Component.translatable("message.oathboundrelics.severance.pulse_oppression").withStyle(ChatFormatting.DARK_RED),
                        true
                );
            }
        }
    }

    private void provokeNearby(ServerLevel level, ServerPlayer player) {
        AABB box = new AABB(worldPosition).inflate(12.0D);

        for (Mob mob : level.getEntitiesOfClass(Mob.class, box)) {
            if (!mob.isAlive()) {
                continue;
            }

            if (mob instanceof NeutralMob || mob instanceof Monster) {
                mob.setTarget(player);
            }
        }
    }

    private void applyArmorPenalty(ServerPlayer player) {
        var armorAttr = player.getAttribute(Attributes.ARMOR);
        if (armorAttr == null) {
            return;
        }

        armorAttr.addOrUpdateTransientModifier(new AttributeModifier(
                RITUAL_ARMOR_PENALTY_ID,
                -0.35D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ));
    }

    private void clearArmorPenalty(ServerPlayer player) {
        var armorAttr = player.getAttribute(Attributes.ARMOR);
        if (armorAttr != null) {
            armorAttr.removeModifier(RITUAL_ARMOR_PENALTY_ID);
        }
    }

    private void clearRitualEffects(ServerPlayer player) {
        player.removeEffect(MobEffects.WEAKNESS);
        player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
        player.removeEffect(MobEffects.DARKNESS);
        player.removeEffect(MobEffects.DIG_SLOWDOWN);
        player.removeEffect(MobEffects.HUNGER);
    }

    private void complete(ServerLevel level, ServerPlayer player) {
        clearArmorPenalty(player);

        OathboundUtil.setSeveranceUnequipAllowed(player, true);
        boolean removed = OathboundUtil.removeOathboundRelic(player);
        OathboundUtil.clearOathboundState(player);

        if (removed) {
            OathboundUtil.giveFracturedAsh(player);
        }

        OathboundUtil.awardSeveranceAdvancement(player);
        clearArmorPenalty(player);
        clearRitualEffects(player);

        level.playSound(
                null,
                worldPosition,
                SoundEvents.LIGHTNING_BOLT_IMPACT,
                SoundSource.BLOCKS,
                1.25F,
                1.2F
        );

        level.sendParticles(
                ParticleTypes.SOUL_FIRE_FLAME,
                worldPosition.getX() + 0.5D,
                worldPosition.getY() + 1.15D,
                worldPosition.getZ() + 0.5D,
                40,
                0.45D,
                0.25D,
                0.45D,
                0.03D
        );

        level.sendParticles(
                ParticleTypes.ASH,
                worldPosition.getX() + 0.5D,
                worldPosition.getY() + 1.0D,
                worldPosition.getZ() + 0.5D,
                60,
                0.6D,
                0.35D,
                0.6D,
                0.02D
        );

        player.displayClientMessage(
                Component.translatable("message.oathboundrelics.severance.success").withStyle(ChatFormatting.GOLD),
                false
        );

        reset();
    }

    private void fail(ServerLevel level, ServerPlayer player, String messageKey) {
        if (player != null) {
            clearArmorPenalty(player);
            OathboundUtil.clearRitualFlags(player);
            clearArmorPenalty(player);
            clearRitualEffects(player);

            player.displayClientMessage(
                    Component.translatable(messageKey).withStyle(ChatFormatting.RED),
                    false
            );
        }

        level.playSound(
                null,
                worldPosition,
                SoundEvents.BEACON_DEACTIVATE,
                SoundSource.BLOCKS,
                1.0F,
                0.7F
        );

        level.sendParticles(
                ParticleTypes.SMOKE,
                worldPosition.getX() + 0.5D,
                worldPosition.getY() + 1.0D,
                worldPosition.getZ() + 0.5D,
                20,
                0.35D,
                0.2D,
                0.35D,
                0.01D
        );

        reset();
    }

    private void reset() {
        this.active = false;
        this.activePlayerUuid = null;
        this.ritualTicks = 0;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putBoolean("Active", active);
        if (activePlayerUuid != null) {
            tag.putUUID("ActivePlayer", activePlayerUuid);
        }
        tag.putInt("RitualTicks", ritualTicks);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        this.active = tag.getBoolean("Active");
        this.activePlayerUuid = tag.hasUUID("ActivePlayer") ? tag.getUUID("ActivePlayer") : null;
        this.ritualTicks = tag.getInt("RitualTicks");
    }
}