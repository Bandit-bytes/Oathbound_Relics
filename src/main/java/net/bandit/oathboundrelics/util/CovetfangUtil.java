package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.data.EnvyStateData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public final class CovetfangUtil {

    private CovetfangUtil() {
    }

    public static boolean isEnabled() {
        return OathboundConfig.enableCovetfang();
    }

    public static int maintenanceIntervalTicks() {
        return OathboundConfig.covetfangMaintenanceIntervalTicks();
    }

    public static int claimDurationTicks() {
        return OathboundConfig.covetfangClaimDurationTicks();
    }

    public static int claimCooldownTicks() {
        return OathboundConfig.covetfangClaimCooldownTicks();
    }

    public static double claimTrackRadius() {
        return OathboundConfig.covetfangClaimTrackRadius();
    }

    public static int covetedGlowDurationTicks() {
        return OathboundConfig.covetfangCovetedGlowDurationTicks();
    }

    public static double bonusDamagePerEnvyScore() {
        return OathboundConfig.covetfangBonusDamagePerEnvyScore();
    }

    public static double covetedTargetExtraDamage() {
        return OathboundConfig.covetfangCovetedTargetExtraDamage();
    }

    public static double bonusDamageCap() {
        return OathboundConfig.covetfangBonusDamageCap();
    }

    public static float baseNonKillSelfDamage() {
        return (float) OathboundConfig.covetfangBaseNonKillSelfDamage();
    }

    public static float hollowComparisonExtraSelfDamage() {
        return (float) OathboundConfig.covetfangHollowComparisonExtraSelfDamage();
    }

    public static int stolenStrengthDurationTicks() {
        return OathboundConfig.covetfangStolenStrengthDurationTicks();
    }

    public static double stolenVitalityMaxHealthBonus() {
        return OathboundConfig.covetfangStolenVitalityMaxHealthBonus();
    }

    public static double stolenPlatingArmorBonus() {
        return OathboundConfig.covetfangStolenPlatingArmorBonus();
    }

    public static double stolenSwiftnessMoveSpeedBonus() {
        return OathboundConfig.covetfangStolenSwiftnessMoveSpeedBonus();
    }

    public static double stolenFerocityAttackSpeedBonus() {
        return OathboundConfig.covetfangStolenFerocityAttackSpeedBonus();
    }

    public static double beneficialEffectAttackDamagePenalty() {
        return OathboundConfig.covetfangBeneficialEffectAttackDamagePenalty();
    }

    public static double lowHealthThreshold() {
        return OathboundConfig.covetfangLowHealthThreshold();
    }

    public static double desperateWantScanRadius() {
        return OathboundConfig.covetfangDesperateWantScanRadius();
    }

    public static float desperateWantHealOnHit() {
        return (float) OathboundConfig.covetfangDesperateWantHealOnHit();
    }

    public static double desperateWantMoveSpeedBonus() {
        return OathboundConfig.covetfangDesperateWantMoveSpeedBonus();
    }

    public static double hopelessComparisonDamagePenalty() {
        return OathboundConfig.covetfangHopelessComparisonDamagePenalty();
    }

    public static double hopelessComparisonMoveSpeedPenalty() {
        return OathboundConfig.covetfangHopelessComparisonMoveSpeedPenalty();
    }

    public static double covetedPursuitMoveSpeedBonus() {
        return OathboundConfig.covetfangCovetedPursuitMoveSpeedBonus();
    }

    public static boolean qualifiesForCovetfang(Player player) {
        BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
        return data.qualifies(
                OathboundConfig.slothWeaponMaxBrandedTicks(),
                OathboundConfig.slothWeaponRequiredBrandedPercent()
        );
    }

    public static boolean canUseCovetfang(Player player) {
        if (!isEnabled()) {
            return false;
        }

        if (player.isCreative() && OathboundConfig.enableCreativeBrandedTimeBypass()) {
            return true;
        }

        return OathboundUtil.isBranded(player) && qualifiesForCovetfang(player);
    }

    public static boolean isHoldingCovetfang(Player player) {
        return player.getMainHandItem().is(ItemRegistry.COVETFANG.get())
                || player.getOffhandItem().is(ItemRegistry.COVETFANG.get());
    }

    public static boolean isHoldingCovetfangMainHand(Player player) {
        return player.getMainHandItem().is(ItemRegistry.COVETFANG.get());
    }

    public static boolean hasCovetfang(Player player) {
        if (isHoldingCovetfang(player)) {
            return true;
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ItemRegistry.COVETFANG.get())) {
                return true;
            }
        }

        return false;
    }

    public static int countBeneficialEffects(LivingEntity entity) {
        int count = 0;

        for (MobEffectInstance instance : entity.getActiveEffects()) {
            if (instance.getEffect().value().getCategory() == MobEffectCategory.BENEFICIAL) {
                count++;
            }
        }

        return count;
    }

    public static int getEnvyScore(Player player, LivingEntity target) {
        int score = 0;

        if (target.getHealth() > player.getHealth()) {
            score++;
        }

        if (target.getMaxHealth() > player.getMaxHealth()) {
            score++;
        }

        if (target.getAttributeValue(Attributes.ARMOR) > player.getAttributeValue(Attributes.ARMOR)) {
            score++;
        }

        if (target.getAttributeValue(Attributes.MOVEMENT_SPEED) > player.getAttributeValue(Attributes.MOVEMENT_SPEED)) {
            score++;
        }

        if (countBeneficialEffects(target) > countBeneficialEffects(player)) {
            score++;
        }

        return score;
    }

    public static double getDamageBonus(Player player, LivingEntity target, boolean isCovetedTarget) {
        int envyScore = getEnvyScore(player, target);
        double bonus = envyScore * bonusDamagePerEnvyScore();

        if (isCovetedTarget && envyScore > 0) {
            bonus += covetedTargetExtraDamage();
        }

        return Math.min(bonusDamageCap(), bonus);
    }

    public static boolean isBelowLowHealthThreshold(Player player) {
        return player.getHealth() / Math.max(1.0D, player.getMaxHealth()) < lowHealthThreshold();
    }

    public static boolean hasStrongerTargetNearby(Player player) {
        return !player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(desperateWantScanRadius()),
                living -> living.isAlive()
                        && living != player
                        && !isAlly(player, living)
                        && getEnvyScore(player, living) > 0
        ).isEmpty();
    }

    public static LivingEntity findCovetedTarget(Player player, EnvyStateData state, double radius) {
        long gameTime = player.level().getGameTime();
        UUID uuid = state.getCovetedTargetUuid(gameTime);

        if (uuid == null) {
            return null;
        }

        return player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(radius),
                living -> living.isAlive() && living.getUUID().equals(uuid)
        ).stream().findFirst().orElse(null);
    }

    public static boolean hasDesperateWantTarget(Player player, EnvyStateData state) {
        return findCovetedTarget(player, state, desperateWantScanRadius()) != null
                || hasStrongerTargetNearby(player);
    }

    public static void grantStolenStrengths(Player player, LivingEntity target, EnvyStateData state) {
        long expiry = player.level().getGameTime() + stolenStrengthDurationTicks();

        if (target.getHealth() > player.getHealth() || target.getMaxHealth() > player.getMaxHealth()) {
            state.extendStolenVitality(expiry);
        }

        if (target.getAttributeValue(Attributes.ARMOR) > player.getAttributeValue(Attributes.ARMOR)) {
            state.extendStolenPlating(expiry);
        }

        if (target.getAttributeValue(Attributes.MOVEMENT_SPEED) > player.getAttributeValue(Attributes.MOVEMENT_SPEED)) {
            state.extendStolenSwiftness(expiry);
        }

        if (countBeneficialEffects(target) > countBeneficialEffects(player)) {
            state.extendStolenFerocity(expiry);
        }
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