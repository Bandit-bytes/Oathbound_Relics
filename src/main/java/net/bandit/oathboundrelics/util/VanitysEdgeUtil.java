package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.registry.EffectRegistry;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class VanitysEdgeUtil {

    private VanitysEdgeUtil() {
    }

    public static boolean qualifiesForVanitysEdge(Player player) {
        BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
        return data.qualifies(
                OathboundConfig.slothWeaponMaxBrandedTicks(),
                OathboundConfig.slothWeaponRequiredBrandedPercent()
        );
    }

    public static boolean canUseVanitysEdge(Player player) {
        if (!OathboundConfig.enableVanitysEdge()) {
            return false;
        }

        if (player.isCreative() && OathboundConfig.enableCreativeBrandedTimeBypass()) {
            return true;
        }

        return OathboundUtil.isBranded(player) && qualifiesForVanitysEdge(player);
    }

    public static boolean isHoldingVanitysEdge(Player player) {
        return player.getMainHandItem().is(ItemRegistry.VANITYS_EDGE.get())
                || player.getOffhandItem().is(ItemRegistry.VANITYS_EDGE.get());
    }

    public static boolean isHoldingVanitysEdgeMainHand(Player player) {
        return player.getMainHandItem().is(ItemRegistry.VANITYS_EDGE.get());
    }

    public static boolean hasVanitysEdge(Player player) {
        if (isHoldingVanitysEdge(player)) {
            return true;
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ItemRegistry.VANITYS_EDGE.get())) {
                return true;
            }
        }

        return false;
    }

    public static double getHighHealthDamageBonus(Player player) {
        double halfHealth = player.getMaxHealth() * 0.5D;
        double excess = Math.max(0.0D, player.getHealth() - halfHealth);
        return Math.floor(excess / 2.0D) * OathboundConfig.vanitysEdgeHighHealthBonusDamagePerTwoHp();
    }

    public static boolean shouldGrantHighHealthAttackSpeed(Player player) {
        return player.getHealth() > player.getMaxHealth() * OathboundConfig.vanitysEdgeHighHealthAttackSpeedThreshold();
    }

    public static double getArmorAttackPenalty(Player player) {
        return Math.floor(player.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR) / 2.0D)
                * OathboundConfig.vanitysEdgeArmorPenaltyPerTwoArmor();
    }

    public static boolean isInMidHealthPenaltyBand(Player player) {
        double percent = player.getHealth() / Math.max(1.0D, player.getMaxHealth());
        return percent < OathboundConfig.vanitysEdgeMidHealthUpperThreshold()
                && percent > OathboundConfig.vanitysEdgeMidHealthLowerThreshold();
    }

    public static boolean isBelowLowHealthThreshold(Player player) {
        double percent = player.getHealth() / Math.max(1.0D, player.getMaxHealth());
        return percent < OathboundConfig.vanitysEdgeLowHealthThreshold();
    }

    public static boolean hasPrideOutcome(Player player) {
        return player.hasEffect(EffectRegistry.LOSING_PRIDE)
                || player.hasEffect(EffectRegistry.BRAVERY);
    }

    public static void applyLosingPride(Player player) {
        player.removeEffect(EffectRegistry.BRAVERY);
        player.addEffect(new MobEffectInstance(
                EffectRegistry.LOSING_PRIDE,
                OathboundConfig.vanitysEdgeOutcomeDurationTicks(),
                0,
                false,
                true,
                true
        ));
        player.addEffect(new MobEffectInstance(
                MobEffects.BLINDNESS,
                OathboundConfig.vanitysEdgeOutcomeDurationTicks(),
                0,
                false,
                true,
                true
        ));
    }

    public static void applyBravery(Player player) {
        player.removeEffect(EffectRegistry.LOSING_PRIDE);
        player.addEffect(new MobEffectInstance(
                EffectRegistry.BRAVERY,
                OathboundConfig.vanitysEdgeOutcomeDurationTicks(),
                0,
                false,
                true,
                true
        ));
        player.addEffect(new MobEffectInstance(
                MobEffects.REGENERATION,
                OathboundConfig.vanitysEdgeOutcomeDurationTicks(),
                OathboundConfig.vanitysEdgeBraveryRegenerationAmplifier(),
                false,
                true,
                true
        ));
    }

    public static boolean isAlly(Player player, LivingEntity target) {
        if (target == player) {
            return false;
        }

        if (target instanceof OwnableEntity ownable) {
            LivingEntity owner = ownable.getOwner();
            if (owner != null && owner.getUUID().equals(player.getUUID())) {
                return true;
            }
        }

        return player.isAlliedTo(target) || target.isAlliedTo(player);
    }
}