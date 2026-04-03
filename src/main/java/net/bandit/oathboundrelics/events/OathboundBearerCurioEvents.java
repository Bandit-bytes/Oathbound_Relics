package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.registry.EffectRegistry;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.bandit.oathboundrelics.util.BearerCurioUtil;
import net.minecraft.server.level.ServerLevel;
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

        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, SHORT, 0, false, false, true));

        if (player.getHealth() <= player.getMaxHealth() * 0.5F) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, SHORT, 0, false, false, true));
        }

        if (BearerCurioUtil.countNewBearerCurios(player) >= 3) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, MEDIUM, 0, false, false, true));
        }
    }

    private static void tickSleeplessEye(Player player) {
        if (!BearerCurioUtil.hasEquipped(player, ItemRegistry.EYE_OF_THE_SLEEPLESS_WITNESS.get())) {
            return;
        }

        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false, true));

        double radius = player.getDeltaMovement().horizontalDistanceSqr() < 0.001D ? 24.0D : 12.0D;

        List<Monster> monsters = player.level().getEntitiesOfClass(
                Monster.class,
                player.getBoundingBox().inflate(radius),
                monster -> monster.isAlive()
        );

        for (Monster monster : monsters) {
            boolean debuffed =
                    monster.hasEffect(MobEffects.WEAKNESS)
                            || monster.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)
                            || monster.hasEffect(EffectRegistry.MARTYRS_CLAIM)
                            || monster.hasEffect(EffectRegistry.JUDGED);

            if (debuffed) {
                monster.addEffect(new MobEffectInstance(MobEffects.GLOWING, MEDIUM, 0, false, false, true));
            }
        }

        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, SHORT, 0, false, false, true));
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
            monster.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, MEDIUM, 0, false, true, true));
            monster.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, MEDIUM, 0, false, true, true));
        }

        if (!monsters.isEmpty()) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, MEDIUM, 0, false, false, true));
        }

        if (monsters.size() >= 3) {
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, MEDIUM, 1, false, false, true));
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
                target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, true, true));
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
        var existing = target.getEffect(EffectRegistry.MARTYRS_CLAIM);
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

        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, LONG, 1, false, true, true));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, LONG, 1, false, true, true));
        BearerCurioUtil.clearLastMartyrTarget(player);
    }
}