package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.registry.EffectRegistry;
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
        if (!BearerCurioUtil.hasEquipped(player, ItemRegistry.OATHBOUND_RELIQUARY.get())) {
            return;
        }

        refreshEffectIfNeeded(player, MobEffects.ABSORPTION, 220, 0, 80, false, false, true);

        if (player.getHealth() <= player.getMaxHealth() * 0.5F) {
            refreshEffectIfNeeded(player, MobEffects.REGENERATION, 80, 0, 20, false, false, true);
        }

        if (BearerCurioUtil.countNewBearerCurios(player) >= 3) {
            refreshEffectIfNeeded(player, MobEffects.HUNGER, 120, 0, 40, false, false, true);
        }
    }

    private static void tickSleeplessEye(Player player) {
        boolean equipped = BearerCurioUtil.hasEquipped(player, ItemRegistry.EYE_OF_THE_SLEEPLESS_WITNESS.get());

        if (!equipped) {
            removeInfiniteEffectIfOwned(player, MobEffects.NIGHT_VISION, 0);
            return;
        }

        ensureInfiniteEffect(player, MobEffects.NIGHT_VISION, 0, false, false, true);

        boolean oakskinActive =
                player.onGround()
                        && !player.isSprinting()
                        && player.getDeltaMovement().horizontalDistanceSqr() < 0.02D;

        if (oakskinActive) {
            refreshEffectIfNeeded(player, MobEffects.DAMAGE_RESISTANCE, 80, 0, 20, false, false, true);
        }

        double radius = oakskinActive ? 24.0D : 14.0D;

        List<Monster> monsters = player.level().getEntitiesOfClass(
                Monster.class,
                player.getBoundingBox().inflate(radius),
                monster -> monster.isAlive()
        );

        for (Monster monster : monsters) {
            refreshEffectIfNeeded(monster, MobEffects.GLOWING, 60, 0, 20, false, false, true);
        }
    }

    private static void tickCenser(Player player) {
        if (!BearerCurioUtil.hasEquipped(player, ItemRegistry.CENSER_OF_HOLLOW_PRAYER.get())) {
            return;
        }

        List<Monster> monsters = player.level().getEntitiesOfClass(
                Monster.class,
                player.getBoundingBox().inflate(5.0D),
                monster -> monster.isAlive()
        );

        for (Monster monster : monsters) {
            refreshEffectIfNeeded(monster, MobEffects.WEAKNESS, 80, 0, 20, false, true, true);
            refreshEffectIfNeeded(monster, MobEffects.MOVEMENT_SLOWDOWN, 80, 0, 20, false, true, true);
        }

        if (!monsters.isEmpty()) {
            refreshEffectIfNeeded(player, MobEffects.REGENERATION, 80, 0, 20, false, false, true);
        }

        if (monsters.size() >= 3) {
            refreshEffectIfNeeded(player, MobEffects.ABSORPTION, 100, 1, 30, false, false, true);
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

        if (!BearerCurioUtil.hasEquipped(player, ItemRegistry.CHAIN_OF_THE_PENITENT.get())) {
            return;
        }

        BearerCurioUtil.addPenance(player, event.getNewDamage() * 1.5F, MAX_PENANCE);
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

        if (BearerCurioUtil.hasEquipped(player, ItemRegistry.CHAIN_OF_THE_PENITENT.get())) {
            float stored = BearerCurioUtil.getPenance(player);

            if (stored > 0.0F) {
                float bonus = Math.min(stored, 12.0F);
                event.setNewDamage(event.getNewDamage() + bonus);
                refreshEffectIfNeeded(target, MobEffects.GLOWING, 100, 0, 20, false, true, true);
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

        if (!BearerCurioUtil.hasEquipped(player, ItemRegistry.NAIL_OF_THE_FIRST_MARTYR.get())) {
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

        int newStacks = Math.min(5, currentStacks + 1);

        target.addEffect(new MobEffectInstance(
                EffectRegistry.MARTYRS_CLAIM,
                120,
                newStacks - 1,
                false,
                true,
                true
        ));

        if (newStacks >= 5) {
            target.removeEffect(EffectRegistry.MARTYRS_CLAIM);
            target.addEffect(new MobEffectInstance(
                    EffectRegistry.JUDGED,
                    LONG,
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

        if (!BearerCurioUtil.hasEquipped(player, ItemRegistry.NAIL_OF_THE_FIRST_MARTYR.get())) {
            return;
        }

        if (!target.hasEffect(EffectRegistry.JUDGED)) {
            return;
        }

        refreshEffectIfNeeded(player, MobEffects.DIG_SPEED, LONG, 1, 40, false, true, true);
        refreshEffectIfNeeded(player, MobEffects.ABSORPTION, LONG, 1, 40, false, true, true);
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