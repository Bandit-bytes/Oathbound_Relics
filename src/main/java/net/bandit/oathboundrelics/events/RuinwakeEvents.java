package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.util.RuinwakeUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.bandit.oathboundrelics.fabricbridge.events.entity.living.LivingDamageEvent;
import net.bandit.oathboundrelics.fabricbridge.events.tick.PlayerTickEvent;

public final class RuinwakeEvents {

    private RuinwakeEvents() {
    }
    public static void onDamagePre(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (event.getSource().getDirectEntity() != player
                || !(event.getEntity() instanceof LivingEntity)
                || !RuinwakeUtil.isHoldingRuinwakeMainHand(player)
                || !RuinwakeUtil.canUseRuinwake(player)
                || RuinwakeUtil.isReleasing(player)) {
            return;
        }

        int grudge = RuinwakeUtil.getGrudge(player);
        if (grudge <= 0) {
            return;
        }

        float multiplier = (float) (1.0D + grudge * OathboundConfig.ruinwakeDamageBonusPerStack());
        event.setNewDamage(event.getNewDamage() * multiplier);
    }
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        if (event.getSource().getEntity() instanceof Player attacker
                && event.getSource().getDirectEntity() == attacker
                && RuinwakeUtil.isHoldingRuinwakeMainHand(attacker)
                && RuinwakeUtil.canUseRuinwake(attacker)
                && !RuinwakeUtil.isReleasing(attacker)) {
            RuinwakeUtil.addGrudge(attacker, 1);
        }

        if (event.getEntity() instanceof Player victim
                && RuinwakeUtil.isHoldingRuinwakeMainHand(victim)
                && RuinwakeUtil.canUseRuinwake(victim)
                && !RuinwakeUtil.isReleasing(victim)) {
            RuinwakeUtil.addGrudge(victim, 1);
        }
    }
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide() || player.tickCount % 20 != 0) {
            return;
        }

        if (!RuinwakeUtil.hasRuinwake(player)) {
            RuinwakeUtil.clearGrudge(player);
            return;
        }

        RuinwakeUtil.decayGrudge(player);
    }
}
