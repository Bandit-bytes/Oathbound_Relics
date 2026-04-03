package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.util.SlothCombatUtil;
import net.bandit.oathboundrelics.util.SlothWeaponUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

import java.util.List;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class SlothCombatEvents {

    private SlothCombatEvents() {
    }

    @SubscribeEvent
    public static void onDamagePre(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (!SlothWeaponUtil.isHoldingLethargicFlailMainHand(player)) {
            return;
        }

        if (!SlothWeaponUtil.canUseLethargicFlail(player)) {
            return;
        }

        if (player.isShiftKeyDown()) {
            return;
        }

        float fullDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        if (event.getNewDamage() < fullDamage) {
            event.setNewDamage(fullDamage);
        }
    }

    @SubscribeEvent
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (!SlothWeaponUtil.isHoldingLethargicFlailMainHand(player)) {
            return;
        }

        if (!SlothWeaponUtil.canUseLethargicFlail(player)) {
            return;
        }

        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        if (player.isShiftKeyDown()) {
            return;
        }

        if (event.getEntity().isAlive()) {
            SlothCombatUtil.addLaziness(player, 1);
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();

        if (!player.isShiftKeyDown()) {
            return;
        }

        if (!SlothWeaponUtil.isHoldingLethargicFlailMainHand(player)) {
            return;
        }

        event.setCanceled(true);

        if (!SlothWeaponUtil.canUseLethargicFlail(player)) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        float aoeDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                * (float) OathboundConfig.lethargicFlailSweepDamageMultiplier();

        List<LivingEntity> targets = player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(OathboundConfig.lethargicFlailSweepRadius()),
                target -> target.isAlive()
                        && target != player
                        && !target.isSpectator()
        );

        for (LivingEntity target : targets) {
            boolean hurt = target.hurt(player.damageSources().playerAttack(player), aoeDamage);
            if (hurt) {
                SlothCombatUtil.applySlothWeakness(target);
            }
        }

        SlothCombatUtil.addLaziness(player, OathboundConfig.lethargicFlailSweepLazinessStacks());
        player.swing(InteractionHand.MAIN_HAND, true);
    }
}