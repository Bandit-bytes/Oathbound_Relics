package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.registry.EffectRegistry;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.bandit.oathboundrelics.util.BearerCurioUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;


import java.util.List;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class OathboundBearerCurioEvents {

    private static final int SHORT = 40;
    private static final int MEDIUM = 60;
    private static final int LONG = 200;
    private static final float MAX_PENANCE = 40.0F;

    private OathboundBearerCurioEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        if (player.tickCount % 20 != 0) {
            return;
        }

        tickReliquary(player);
        tickSleeplessEye(player);
        tickCenser(player);
    }

    private static void tickReliquary(Player player) {
        if (!OathboundConfig.enableOathboundReliquary()
                || !BearerCurioUtil.hasEquipped(player, ItemRegistry.OATHBOUND_RELIQUARY.get())) {
            return;
        }

        refreshEffectIfNeeded(
                player,
                MobEffects.ABSORPTION,
                OathboundConfig.oathboundReliquaryAbsorptionDurationTicks(),
                OathboundConfig.oathboundReliquaryAbsorptionAmplifier(),
                80,
                false,
                false,
                true
        );

        if (player.getHealth() <= OathboundConfig.oathboundReliquaryLowHealthThreshold()) {
            refreshEffectIfNeeded(
                    player,
                    MobEffects.REGENERATION,
                    OathboundConfig.oathboundReliquaryRegenerationDurationTicks(),
                    OathboundConfig.oathboundReliquaryRegenerationAmplifier(),
                    20,
                    false,
                    false,
                    true
            );
        }

        if (BearerCurioUtil.countNewBearerCurios(player) >= OathboundConfig.oathboundReliquaryHungerCurioThreshold()) {
            refreshEffectIfNeeded(
                    player,
                    MobEffects.HUNGER,
                    OathboundConfig.oathboundReliquaryHungerDurationTicks(),
                    OathboundConfig.oathboundReliquaryHungerAmplifier(),
                    40,
                    false,
                    false,
                    true
            );
        }
    }

    private static void tickSleeplessEye(Player player) {
        boolean equipped = OathboundConfig.enableEyeOfTheSleeplessWitness()
                && BearerCurioUtil.hasEquipped(player, ItemRegistry.EYE_OF_THE_SLEEPLESS_WITNESS.get());

        if (!equipped) {
            removeInfiniteEffectIfOwned(player, MobEffects.NIGHT_VISION, 0);
            return;
        }

        if (OathboundConfig.eyeOfTheSleeplessWitnessNightVision()) {
            ensureInfiniteEffect(player, MobEffects.NIGHT_VISION, 0, false, false, true);
        } else {
            removeInfiniteEffectIfOwned(player, MobEffects.NIGHT_VISION, 0);
        }

        boolean oakskinActive =
                player.onGround()
                        && !player.isSprinting()
                        && player.getDeltaMovement().horizontalDistanceSqr()
                        < OathboundConfig.eyeOfTheSleeplessWitnessOakskinMovementThreshold();

        if (oakskinActive) {
            refreshEffectIfNeeded(
                    player,
                    MobEffects.DAMAGE_RESISTANCE,
                    OathboundConfig.eyeOfTheSleeplessWitnessOakskinDurationTicks(),
                    OathboundConfig.eyeOfTheSleeplessWitnessOakskinAmplifier(),
                    20,
                    false,
                    false,
                    true
            );
        }

        double radius = oakskinActive
                ? OathboundConfig.eyeOfTheSleeplessWitnessRevealRadiusStill()
                : OathboundConfig.eyeOfTheSleeplessWitnessRevealRadiusMoving();

        List<Monster> monsters = player.level().getEntitiesOfClass(
                Monster.class,
                player.getBoundingBox().inflate(radius),
                monster -> monster.isAlive()
        );

        if (!oakskinActive && !monsters.isEmpty()) {
            refreshEffectIfNeeded(
                    player,
                    MobEffects.MOVEMENT_SPEED,
                    60,
                    0,
                    20,
                    false,
                    false,
                    true
            );
        }
    }

    private static void tickCenser(Player player) {
        if (!OathboundConfig.enableCenserOfHollowPrayer()
                || !BearerCurioUtil.hasEquipped(player, ItemRegistry.CENSER_OF_HOLLOW_PRAYER.get())) {
            return;
        }

        List<Monster> monsters = player.level().getEntitiesOfClass(
                Monster.class,
                player.getBoundingBox().inflate(OathboundConfig.censerOfHollowPrayerRadius()),
                monster -> monster.isAlive()
        );

        for (Monster monster : monsters) {
            refreshEffectIfNeeded(
                    monster,
                    MobEffects.WEAKNESS,
                    OathboundConfig.censerOfHollowPrayerWeaknessDurationTicks(),
                    OathboundConfig.censerOfHollowPrayerWeaknessAmplifier(),
                    20,
                    false,
                    true,
                    true
            );

            refreshEffectIfNeeded(
                    monster,
                    MobEffects.MOVEMENT_SLOWDOWN,
                    OathboundConfig.censerOfHollowPrayerSlownessDurationTicks(),
                    OathboundConfig.censerOfHollowPrayerSlownessAmplifier(),
                    20,
                    false,
                    true,
                    true
            );
        }

        if (!monsters.isEmpty()) {
            refreshEffectIfNeeded(
                    player,
                    MobEffects.REGENERATION,
                    OathboundConfig.censerOfHollowPrayerRegenerationDurationTicks(),
                    OathboundConfig.censerOfHollowPrayerRegenerationAmplifier(),
                    20,
                    false,
                    false,
                    true
            );
        }

        if (monsters.size() >= OathboundConfig.censerOfHollowPrayerCrowdThreshold()) {
            refreshEffectIfNeeded(
                    player,
                    MobEffects.ABSORPTION,
                    OathboundConfig.censerOfHollowPrayerAbsorptionDurationTicks(),
                    OathboundConfig.censerOfHollowPrayerAbsorptionAmplifier(),
                    30,
                    false,
                    false,
                    true
            );
        }
    }

    private static void refreshEffectIfNeeded(
            LivingEntity entity,
            Holder<MobEffect> effect,
            int duration,
            int amplifier,
            int refreshThreshold,
            boolean ambient,
            boolean visible,
            boolean showIcon
    ) {
        MobEffectInstance current = entity.getEffect(effect);

        if (current == null) {
            entity.addEffect(new MobEffectInstance(effect, duration, amplifier, ambient, visible, showIcon));
            return;
        }

        if (current.getAmplifier() > amplifier) {
            return;
        }

        if (current.getAmplifier() < amplifier) {
            entity.addEffect(new MobEffectInstance(effect, duration, amplifier, ambient, visible, showIcon));
            return;
        }

        if (current.getDuration() <= refreshThreshold) {
            entity.addEffect(new MobEffectInstance(effect, duration, amplifier, ambient, visible, showIcon));
        }
    }

    @SubscribeEvent
    public static void onPlayerDamaged(LivingDamageEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        if (!OathboundConfig.enableChainOfThePenitent()
                || !BearerCurioUtil.hasEquipped(player, ItemRegistry.CHAIN_OF_THE_PENITENT.get())) {
            return;
        }

        BearerCurioUtil.addPenance(
                player,
                (float) (event.getNewDamage() * OathboundConfig.chainOfThePenitentPenanceGainMultiplier()),
                (float) OathboundConfig.chainOfThePenitentMaxPenance()
        );
    }

    @SubscribeEvent
    public static void onPlayerDealsDamagePre(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        if (event.getSource().getDirectEntity() != player) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        if (OathboundConfig.enableChainOfThePenitent()
                && BearerCurioUtil.hasEquipped(player, ItemRegistry.CHAIN_OF_THE_PENITENT.get())) {
            float stored = BearerCurioUtil.getPenance(player);

            if (stored > 0.0F) {
                float bonus = Math.min(stored, (float) OathboundConfig.chainOfThePenitentMaxBonusDamage());
                event.setNewDamage(event.getNewDamage() + bonus);

                refreshEffectIfNeeded(
                        target,
                        MobEffects.GLOWING,
                        OathboundConfig.chainOfThePenitentMarkDurationTicks(),
                        0,
                        20,
                        false,
                        true,
                        true
                );

                BearerCurioUtil.clearPenance(player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDealsDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        if (event.getSource().getDirectEntity() != player) {
            return;
        }

        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        if (!OathboundConfig.enableNailOfTheFirstMartyr()
                || !BearerCurioUtil.hasEquipped(player, ItemRegistry.NAIL_OF_THE_FIRST_MARTYR.get())) {
            return;
        }

        if (!target.isAlive()) {
            return;
        }

        if (!BearerCurioUtil.isSameMartyrTarget(player, target.getId())) {
            BearerCurioUtil.setLastMartyrTarget(player, target.getId());
        }

        int currentStacks = 0;
        MobEffectInstance existing = target.getEffect(EffectRegistry.MARTYRS_CLAIM);
        if (existing != null) {
            currentStacks = existing.getAmplifier() + 1;
        }

        int newStacks = Math.min(OathboundConfig.nailOfTheFirstMartyrMaxStacks(), currentStacks + 1);

        target.addEffect(new MobEffectInstance(
                EffectRegistry.MARTYRS_CLAIM,
                OathboundConfig.nailOfTheFirstMartyrClaimDurationTicks(),
                newStacks - 1,
                false,
                true,
                true
        ));

        if (newStacks >= OathboundConfig.nailOfTheFirstMartyrMaxStacks()) {
            target.removeEffect(EffectRegistry.MARTYRS_CLAIM);
            target.addEffect(new MobEffectInstance(
                    EffectRegistry.JUDGED,
                    OathboundConfig.nailOfTheFirstMartyrJudgedDurationTicks(),
                    0,
                    false,
                    true,
                    true
            ));
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        if (!OathboundConfig.enableNailOfTheFirstMartyr()
                || !BearerCurioUtil.hasEquipped(player, ItemRegistry.NAIL_OF_THE_FIRST_MARTYR.get())) {
            return;
        }

        if (!target.hasEffect(EffectRegistry.JUDGED)) {
            return;
        }

        refreshEffectIfNeeded(
                player,
                MobEffects.DIG_SPEED,
                OathboundConfig.nailOfTheFirstMartyrHasteDurationTicks(),
                OathboundConfig.nailOfTheFirstMartyrHasteAmplifier(),
                40,
                false,
                true,
                true
        );

        refreshEffectIfNeeded(
                player,
                MobEffects.ABSORPTION,
                OathboundConfig.nailOfTheFirstMartyrAbsorptionDurationTicks(),
                OathboundConfig.nailOfTheFirstMartyrAbsorptionAmplifier(),
                40,
                false,
                true,
                true
        );
        BearerCurioUtil.clearLastMartyrTarget(player);
    }
    private static void ensureInfiniteEffect(
            LivingEntity entity,
            Holder<MobEffect> effect,
            int amplifier,
            boolean ambient,
            boolean visible,
            boolean showIcon
    ) {
        MobEffectInstance current = entity.getEffect(effect);

        if (current == null
                || !current.isInfiniteDuration()
                || current.getAmplifier() != amplifier
                || current.isAmbient() != ambient
                || current.isVisible() != visible
                || current.showIcon() != showIcon) {
            entity.addEffect(new MobEffectInstance(
                    effect,
                    MobEffectInstance.INFINITE_DURATION,
                    amplifier,
                    ambient,
                    visible,
                    showIcon
            ));
        }
    }

    private static void removeInfiniteEffectIfOwned(
            LivingEntity entity,
            Holder<MobEffect> effect,
            int amplifier
    ) {
        MobEffectInstance current = entity.getEffect(effect);

        if (current != null
                && current.isInfiniteDuration()
                && current.getAmplifier() == amplifier) {
            entity.removeEffect(effect);
        }
    }
}