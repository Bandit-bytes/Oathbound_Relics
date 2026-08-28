package net.bandit.oathboundrelics.config;

public final class OathboundConfig {

    private OathboundConfig() {
    }

    public static void initDefaults() {
        FabricConfigBridge.defineDefault("oathbound_relic.enableFrailty", true);
        FabricConfigBridge.defineDefault("oathbound_relic.enableProvocation", true);
        FabricConfigBridge.defineDefault("oathbound_relic.enableShatteredPlate", true);
        FabricConfigBridge.defineDefault("oathbound_relic.enableBloodToll", true);
        FabricConfigBridge.defineDefault("oathbound_relic.enableLivingEmber", true);
        FabricConfigBridge.defineDefault("oathbound_relic.enableSoulFracture", true);
        FabricConfigBridge.defineDefault("oathbound_relic.enableWakefulDoom", true);
        FabricConfigBridge.defineDefault("oathbound_relic.incomingDamageMultiplier", 2.0D);
        FabricConfigBridge.defineDefault("oathbound_relic.armorEffectiveness", 0.70D);
        FabricConfigBridge.defineDefault("oathbound_relic.bloodTollHealthCost", 1.0D);
        FabricConfigBridge.defineDefault("oathbound_relic.minFireTicks", 200);
        FabricConfigBridge.defineDefault("oathbound_relic.neutralAggroInterval", 20);
        FabricConfigBridge.defineDefault("oathbound_relic.neutralAggroRange", 16.0D);
        FabricConfigBridge.defineDefault("oathbound_relic.respawnWeaknessDurationTicks", 20 * 30);
        FabricConfigBridge.defineDefault("oathbound_relic.respawnWeaknessAmplifier", 0);
        FabricConfigBridge.defineDefault("oathbound_relic.respawnSlownessDurationTicks", 20 * 15);
        FabricConfigBridge.defineDefault("oathbound_relic.respawnSlownessAmplifier", 0);
        FabricConfigBridge.defineDefault("blessings.customAttributes", "");
        FabricConfigBridge.defineDefault("blessings.enableLootingBlessing", true);
        FabricConfigBridge.defineDefault("blessings.enableFortuneBlessing", true);
        FabricConfigBridge.defineDefault("blessings.enableXpBlessing", true);
        FabricConfigBridge.defineDefault("blessings.enableEnchantingBlessing", true);
        FabricConfigBridge.defineDefault("blessings.enableAttackSpeedBlessing", true);
        FabricConfigBridge.defineDefault("blessings.enableAbsorptionBlessing", true);
        FabricConfigBridge.defineDefault("blessings.lootingBonus", 1);
        FabricConfigBridge.defineDefault("blessings.fortuneBonus", 1);
        FabricConfigBridge.defineDefault("blessings.xpMultiplier", 6.0D);
        FabricConfigBridge.defineDefault("blessings.enchantingPowerBonus", 10);
        FabricConfigBridge.defineDefault("blessings.attackSpeedBonus", 0.5D);
        FabricConfigBridge.defineDefault("blessings.absorptionThreshold", 8.0D);
        FabricConfigBridge.defineDefault("blessings.absorptionAmount", 4.0D);
        FabricConfigBridge.defineDefault("bound_curios.ashen_nail.enabled", true);
        FabricConfigBridge.defineDefault("bound_curios.ashen_nail.burningDamageMultiplier", 1.35D);
        FabricConfigBridge.defineDefault("bound_curios.gravebell_locket.enabled", true);
        FabricConfigBridge.defineDefault("bound_curios.gravebell_locket.speedDurationTicks", 20 * 8);
        FabricConfigBridge.defineDefault("bound_curios.gravebell_locket.speedAmplifier", 0);
        FabricConfigBridge.defineDefault("bound_curios.gravebell_locket.regenerationDurationTicks", 20 * 4);
        FabricConfigBridge.defineDefault("bound_curios.gravebell_locket.regenerationAmplifier", 0);
        FabricConfigBridge.defineDefault("bound_curios.hunters_sigil.enabled", true);
        FabricConfigBridge.defineDefault("bound_curios.hunters_sigil.damageMultiplier", 1.25D);
        FabricConfigBridge.defineDefault("bound_curios.pilgrims_thorn.enabled", true);
        FabricConfigBridge.defineDefault("bound_curios.pilgrims_thorn.movementSpeedBonus", 0.10D);
        FabricConfigBridge.defineDefault("free_relics.shroud_of_the_forsaken.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.shroud_of_the_forsaken.lowHealthThreshold", 6.0D);
        FabricConfigBridge.defineDefault("free_relics.shroud_of_the_forsaken.damageMultiplier", 0.60D);
        FabricConfigBridge.defineDefault("free_relics.shroud_of_the_forsaken.resistanceDurationTicks", 20 * 5);
        FabricConfigBridge.defineDefault("free_relics.shroud_of_the_forsaken.resistanceAmplifier", 1);
        FabricConfigBridge.defineDefault("free_relics.shroud_of_the_forsaken.cooldownTicks", 20 * 60);
        FabricConfigBridge.defineDefault("free_relics.vulture_charm.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.vulture_charm.xpMultiplier", 1.50D);
        FabricConfigBridge.defineDefault("free_relics.hollow_eye.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.censer_of_ash.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.censer_of_ash.damageMultiplier", 1.20D);
        FabricConfigBridge.defineDefault("free_relics.censer_of_ash.glowRadius", 12.0D);
        FabricConfigBridge.defineDefault("free_relics.censer_of_ash.glowDurationTicks", 40);
        FabricConfigBridge.defineDefault("free_relics.censer_of_ash.scanIntervalTicks", 20);
        FabricConfigBridge.defineDefault("free_relics.mourners_thread.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.mourners_thread.foodRestored", 4);
        FabricConfigBridge.defineDefault("free_relics.mourners_thread.saturationRestored", 0.6D);
        FabricConfigBridge.defineDefault("free_relics.mourners_thread.healAmount", 2.0D);
        FabricConfigBridge.defineDefault("free_relics.thornbound_carapace.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.thornbound_carapace.reflectPercent", 0.20D);
        FabricConfigBridge.defineDefault("free_relics.voidstep_band.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.executioners_coin.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.executioners_coin.healthThresholdPercent", 0.30D);
        FabricConfigBridge.defineDefault("free_relics.executioners_coin.damageMultiplier", 1.30D);
        FabricConfigBridge.defineDefault("free_relics.relic_of_the_last_breath.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.relic_of_the_last_breath.postTriggerHealth", 1.0D);
        FabricConfigBridge.defineDefault("free_relics.relic_of_the_last_breath.absorptionDurationTicks", 20 * 10);
        FabricConfigBridge.defineDefault("free_relics.relic_of_the_last_breath.absorptionAmplifier", 1);
        FabricConfigBridge.defineDefault("free_relics.relic_of_the_last_breath.regenerationDurationTicks", 20 * 6);
        FabricConfigBridge.defineDefault("free_relics.relic_of_the_last_breath.regenerationAmplifier", 1);
        FabricConfigBridge.defineDefault("free_relics.relic_of_the_last_breath.cooldownTicks", 20 * 300);
        FabricConfigBridge.defineDefault("free_relics.torch_of_gravesong.enabled", true);
        FabricConfigBridge.defineDefault("free_relics.torch_of_gravesong.undeadDamageMultiplier", 1.25D);
        FabricConfigBridge.defineDefault("free_relics.torch_of_gravesong.strengthDurationTicks", 20 * 6);
        FabricConfigBridge.defineDefault("free_relics.torch_of_gravesong.strengthAmplifier", 0);
        FabricConfigBridge.defineDefault("sloth_weapon.enabled", true);
        FabricConfigBridge.defineDefault("sloth_weapon.inventoryCheckIntervalTicks", 10);
        FabricConfigBridge.defineDefault("sloth_weapon.inventoryMoveSpeedMultiplier", -0.50D);
        FabricConfigBridge.defineDefault("sloth_weapon.inventoryExhaustionPerSecond", 0.05D);
        FabricConfigBridge.defineDefault("sloth_weapon.slothWeaponMaxBrandedTicks", 360000L);
        FabricConfigBridge.defineDefault("sloth_weapon.slothWeaponRequiredBrandedPercent", 0.995D);
        FabricConfigBridge.defineDefault("free_relics.hollow_eye.clearBlindness", true);
        FabricConfigBridge.defineDefault("free_relics.hollow_eye.clearDarkness", true);
        FabricConfigBridge.defineDefault("free_relics.hollow_eye.revealInvisibleRadius", 16.0D);
        FabricConfigBridge.defineDefault("free_relics.hollow_eye.revealInvisibleDurationTicks", 60);
        FabricConfigBridge.defineDefault("free_relics.hollow_eye.revealInvisibleIntervalTicks", 20);
        FabricConfigBridge.defineDefault("wrath_weapon.enabled", true);
        FabricConfigBridge.defineDefault("wrath_weapon.maxGrudgeStacks", 5);
        FabricConfigBridge.defineDefault("wrath_weapon.damageBonusPerStack", 0.08D);
        FabricConfigBridge.defineDefault("wrath_weapon.stackDecayTicks", 20 * 10);
        FabricConfigBridge.defineDefault("wrath_weapon.releaseRadius", 6.0D);
        FabricConfigBridge.defineDefault("wrath_weapon.releaseBaseDamageMultiplier", 0.45D);
        FabricConfigBridge.defineDefault("wrath_weapon.releaseDamagePerStack", 0.17D);
        FabricConfigBridge.defineDefault("wrath_weapon.releaseKnockback", 1.2D);
        FabricConfigBridge.defineDefault("wrath_weapon.weaknessDurationTicks", 20 * 8);
        FabricConfigBridge.defineDefault("wrath_weapon.releaseCooldownTicks", 20 * 12);
        FabricConfigBridge.defineDefault("wrath_weapon.failureSelfDamagePerStack", 1.0D);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.enabled", true);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.bonusCharmSlots", 2);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.lowHealthThreshold", 10.0D);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.absorptionDurationTicks", 220);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.absorptionAmplifier", 0);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.regenerationDurationTicks", 80);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.regenerationAmplifier", 0);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.hungerCurioThreshold", 3);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.hungerDurationTicks", 120);
        FabricConfigBridge.defineDefault("bearer_curios.oathbound_reliquary.hungerAmplifier", 0);
        FabricConfigBridge.defineDefault("bearer_curios.chain_of_the_penitent.enabled", true);
        FabricConfigBridge.defineDefault("bearer_curios.chain_of_the_penitent.penanceGainMultiplier", 1.5D);
        FabricConfigBridge.defineDefault("bearer_curios.chain_of_the_penitent.maxPenance", 40.0D);
        FabricConfigBridge.defineDefault("bearer_curios.chain_of_the_penitent.maxBonusDamage", 12.0D);
        FabricConfigBridge.defineDefault("bearer_curios.chain_of_the_penitent.markDurationTicks", 100);
        FabricConfigBridge.defineDefault("bearer_curios.eye_of_the_sleepless_witness.enabled", true);
        FabricConfigBridge.defineDefault("bearer_curios.eye_of_the_sleepless_witness.nightVision", true);
        FabricConfigBridge.defineDefault("bearer_curios.eye_of_the_sleepless_witness.revealRadiusMoving", 14.0D);
        FabricConfigBridge.defineDefault("bearer_curios.eye_of_the_sleepless_witness.revealRadiusStill", 24.0D);
        FabricConfigBridge.defineDefault("bearer_curios.eye_of_the_sleepless_witness.glowDurationTicks", 60);
        FabricConfigBridge.defineDefault("bearer_curios.eye_of_the_sleepless_witness.oakskinDurationTicks", 80);
        FabricConfigBridge.defineDefault("bearer_curios.eye_of_the_sleepless_witness.oakskinAmplifier", 0);
        FabricConfigBridge.defineDefault("bearer_curios.eye_of_the_sleepless_witness.oakskinMovementThreshold", 0.02D);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.enabled", true);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.radius", 5.0D);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.weaknessDurationTicks", 80);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.weaknessAmplifier", 0);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.slownessDurationTicks", 80);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.slownessAmplifier", 0);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.regenerationDurationTicks", 80);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.regenerationAmplifier", 0);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.crowdThreshold", 3);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.absorptionDurationTicks", 100);
        FabricConfigBridge.defineDefault("bearer_curios.censer_of_hollow_prayer.absorptionAmplifier", 1);
        FabricConfigBridge.defineDefault("bearer_curios.nail_of_the_first_martyr.enabled", true);
        FabricConfigBridge.defineDefault("bearer_curios.nail_of_the_first_martyr.claimDurationTicks", 120);
        FabricConfigBridge.defineDefault("bearer_curios.nail_of_the_first_martyr.maxStacks", 5);
        FabricConfigBridge.defineDefault("bearer_curios.nail_of_the_first_martyr.judgedDurationTicks", 200);
        FabricConfigBridge.defineDefault("bearer_curios.nail_of_the_first_martyr.hasteDurationTicks", 200);
        FabricConfigBridge.defineDefault("bearer_curios.nail_of_the_first_martyr.hasteAmplifier", 1);
        FabricConfigBridge.defineDefault("bearer_curios.nail_of_the_first_martyr.absorptionDurationTicks", 200);
        FabricConfigBridge.defineDefault("bearer_curios.nail_of_the_first_martyr.absorptionAmplifier", 1);
        FabricConfigBridge.defineDefault("basic_rings.gold_ring.enabled", true);
        FabricConfigBridge.defineDefault("basic_rings.gold_ring.armorBonus", 1.0D);
        FabricConfigBridge.defineDefault("basic_rings.cyan_ring.enabled", true);
        FabricConfigBridge.defineDefault("basic_rings.cyan_ring.armorBonus", 2.0D);
        FabricConfigBridge.defineDefault("basic_rings.nebula_ring.enabled", true);
        FabricConfigBridge.defineDefault("basic_rings.fractured_ring.enabled", true);
        FabricConfigBridge.defineDefault("basic_rings.nebula_ring.armorBonus", 3.0D);
        FabricConfigBridge.defineDefault("basic_rings.nebula_ring.extraRingSlots", 1);
        FabricConfigBridge.defineDefault("basic_rings.fractured_ring.extraRingSlots", 1);
        FabricConfigBridge.defineDefault("sloth_weapon.enableCreativeBrandedTimeBypass", true);
        FabricConfigBridge.defineDefault("sloth_weapon.lazinessDurationTicks", 20 * 60);
        FabricConfigBridge.defineDefault("sloth_weapon.sweepLazinessStacks", 3);
        FabricConfigBridge.defineDefault("sloth_weapon.sweepDamageMultiplier", 0.75D);
        FabricConfigBridge.defineDefault("sloth_weapon.sweepRadius", 10.0D);
        FabricConfigBridge.defineDefault("sloth_weapon.sweepEnemyDebuffDurationTicks", 20 * 10);
        FabricConfigBridge.defineDefault("sloth_weapon.cubeCooldownTicks", 20 * 30);
        FabricConfigBridge.defineDefault("sloth_weapon.cubeStunDurationTicks", 20 * 10);
        FabricConfigBridge.defineDefault("sloth_weapon.cubeDamageMultiplier", 0.75D);
        FabricConfigBridge.defineDefault("oathbound_relic.soulFractureMaxHealthLossPerGem", 1.0D);
        FabricConfigBridge.defineDefault("oathbound_relic.soulGemPickupRadius", 1.25D);
        FabricConfigBridge.defineDefault("pride_weapon.enabled", true);
        FabricConfigBridge.defineDefault("pride_weapon.maintenanceIntervalTicks", 10);
        FabricConfigBridge.defineDefault("pride_weapon.allyAuraIntervalTicks", 20);
        FabricConfigBridge.defineDefault("pride_weapon.highHealthAttackSpeedThreshold", 0.80D);
        FabricConfigBridge.defineDefault("pride_weapon.highHealthAttackSpeedBonus", 0.10D);
        FabricConfigBridge.defineDefault("pride_weapon.highHealthBonusDamagePerTwoHp", 2.0D);
        FabricConfigBridge.defineDefault("pride_weapon.allySuppressionRadius", 12.0D);
        FabricConfigBridge.defineDefault("pride_weapon.allySuppressionDurationTicks", 40);
        FabricConfigBridge.defineDefault("pride_weapon.nonKillSelfDamage", 1.0D);
        FabricConfigBridge.defineDefault("pride_weapon.killStackDurationTicks", 20 * 60 * 3);
        FabricConfigBridge.defineDefault("pride_weapon.killStackAttackSpeedBonus", 0.10D);
        FabricConfigBridge.defineDefault("pride_weapon.armorPenaltyPerTwoArmor", 1.0D);
        FabricConfigBridge.defineDefault("pride_weapon.midHealthUpperThreshold", 0.50D);
        FabricConfigBridge.defineDefault("pride_weapon.midHealthLowerThreshold", 0.0D);
        FabricConfigBridge.defineDefault("pride_weapon.midHealthDamagePenaltyMultiplier", -0.50D);
        FabricConfigBridge.defineDefault("pride_weapon.midHealthAttackSpeedPenaltyMultiplier", -0.50D);
        FabricConfigBridge.defineDefault("pride_weapon.lowHealthThreshold", 0.30D);
        FabricConfigBridge.defineDefault("pride_weapon.losingPrideChance", 0.60D);
        FabricConfigBridge.defineDefault("pride_weapon.outcomeDurationTicks", 20 * 60 * 10);
        FabricConfigBridge.defineDefault("pride_weapon.losingPrideHealingMultiplier", 0.80D);
        FabricConfigBridge.defineDefault("pride_weapon.braveryRegenerationAmplifier", 4);
        FabricConfigBridge.defineDefault("envy_weapon.enabled", true);
        FabricConfigBridge.defineDefault("envy_weapon.maintenanceIntervalTicks", 10);
        FabricConfigBridge.defineDefault("envy_weapon.claimDurationTicks", 20 * 20);
        FabricConfigBridge.defineDefault("envy_weapon.claimCooldownTicks", 20 * 8);
        FabricConfigBridge.defineDefault("envy_weapon.claimTrackRadius", 48.0D);
        FabricConfigBridge.defineDefault("envy_weapon.covetedGlowDurationTicks", 40);
        FabricConfigBridge.defineDefault("envy_weapon.bonusDamagePerEnvyScore", 2.0D);
        FabricConfigBridge.defineDefault("envy_weapon.covetedTargetExtraDamage", 2.0D);
        FabricConfigBridge.defineDefault("envy_weapon.bonusDamageCap", 12.0D);
        FabricConfigBridge.defineDefault("envy_weapon.baseNonKillSelfDamage", 1.0D);
        FabricConfigBridge.defineDefault("envy_weapon.hollowComparisonExtraSelfDamage", 1.0D);
        FabricConfigBridge.defineDefault("envy_weapon.stolenStrengthDurationTicks", 20 * 45);
        FabricConfigBridge.defineDefault("envy_weapon.stolenVitalityMaxHealthBonus", 4.0D);
        FabricConfigBridge.defineDefault("envy_weapon.stolenPlatingArmorBonus", 4.0D);
        FabricConfigBridge.defineDefault("envy_weapon.stolenSwiftnessMoveSpeedBonus", 0.15D);
        FabricConfigBridge.defineDefault("envy_weapon.stolenFerocityAttackSpeedBonus", 0.20D);
        FabricConfigBridge.defineDefault("envy_weapon.beneficialEffectAttackDamagePenalty", 1.0D);
        FabricConfigBridge.defineDefault("envy_weapon.lowHealthThreshold", 0.30D);
        FabricConfigBridge.defineDefault("envy_weapon.desperateWantScanRadius", 16.0D);
        FabricConfigBridge.defineDefault("envy_weapon.desperateWantHealOnHit", 1.0D);
        FabricConfigBridge.defineDefault("envy_weapon.desperateWantMoveSpeedBonus", 0.20D);
        FabricConfigBridge.defineDefault("envy_weapon.hopelessComparisonDamagePenalty", -0.35D);
        FabricConfigBridge.defineDefault("envy_weapon.hopelessComparisonMoveSpeedPenalty", -0.20D);
        FabricConfigBridge.defineDefault("envy_weapon.covetedPursuitMoveSpeedBonus", 0.10D);
        FabricConfigBridge.defineDefault("oathbound_relic.giveStarterOathboundRelic", true);
        FabricConfigBridge.defineDefault("bearer_curios.brandkeepers_mercy.enabled", true);
        FabricConfigBridge.defineDefault("oathbound_relic.bloodTollCooldownTicks", 60);
    }

    private static boolean B(String key, boolean defaultValue) {
        return FabricConfigBridge.getBoolean(key, defaultValue);
    }

    private static int I(String key, int defaultValue) {
        return FabricConfigBridge.getInt(key, defaultValue);
    }

    private static long L(String key, long defaultValue) {
        return FabricConfigBridge.getLong(key, defaultValue);
    }

    private static double D(String key, double defaultValue) {
        return FabricConfigBridge.getDouble(key, defaultValue);
    }

    public static boolean enableFrailty() { return B("oathbound_relic.enableFrailty", true); }
    public static boolean enableProvocation() { return B("oathbound_relic.enableProvocation", true); }
    public static boolean enableShatteredPlate() { return B("oathbound_relic.enableShatteredPlate", true); }
    public static boolean enableBloodToll() { return B("oathbound_relic.enableBloodToll", true); }
    public static boolean enableLivingEmber() { return B("oathbound_relic.enableLivingEmber", true); }
    public static boolean enableSoulFracture() { return B("oathbound_relic.enableSoulFracture", true); }
    public static boolean enableWakefulDoom() { return B("oathbound_relic.enableWakefulDoom", true); }
    public static double incomingDamageMultiplier() { return D("oathbound_relic.incomingDamageMultiplier", 2.0D); }
    public static double armorEffectiveness() { return D("oathbound_relic.armorEffectiveness", 0.70D); }
    public static double bloodTollHealthCost() { return D("oathbound_relic.bloodTollHealthCost", 1.0D); }
    public static int minFireTicks() { return I("oathbound_relic.minFireTicks", 200); }
    public static int neutralAggroInterval() { return I("oathbound_relic.neutralAggroInterval", 20); }
    public static double neutralAggroRange() { return D("oathbound_relic.neutralAggroRange", 16.0D); }
    public static int respawnWeaknessDurationTicks() { return I("oathbound_relic.respawnWeaknessDurationTicks", 20 * 30); }
    public static int respawnWeaknessAmplifier() { return I("oathbound_relic.respawnWeaknessAmplifier", 0); }
    public static int respawnSlownessDurationTicks() { return I("oathbound_relic.respawnSlownessDurationTicks", 20 * 15); }
    public static int respawnSlownessAmplifier() { return I("oathbound_relic.respawnSlownessAmplifier", 0); }
    public static java.util.List<String> oathboundRelicCustomAttributes() { return FabricConfigBridge.getStringList("blessings.customAttributes"); }
    public static boolean enableLootingBlessing() { return B("blessings.enableLootingBlessing", true); }
    public static boolean enableFortuneBlessing() { return B("blessings.enableFortuneBlessing", true); }
    public static boolean enableXpBlessing() { return B("blessings.enableXpBlessing", true); }
    public static boolean enableEnchantingBlessing() { return B("blessings.enableEnchantingBlessing", true); }
    public static boolean enableAttackSpeedBlessing() { return B("blessings.enableAttackSpeedBlessing", true); }
    public static boolean enableAbsorptionBlessing() { return B("blessings.enableAbsorptionBlessing", true); }
    public static int lootingBonus() { return I("blessings.lootingBonus", 1); }
    public static int fortuneBonus() { return I("blessings.fortuneBonus", 1); }
    public static double xpMultiplier() { return D("blessings.xpMultiplier", 6.0D); }
    public static int enchantingPowerBonus() { return I("blessings.enchantingPowerBonus", 10); }
    public static double attackSpeedBonus() { return D("blessings.attackSpeedBonus", 0.5D); }
    public static double absorptionThreshold() { return D("blessings.absorptionThreshold", 8.0D); }
    public static double absorptionAmount() { return D("blessings.absorptionAmount", 4.0D); }
    public static boolean enableAshenNail() { return B("bound_curios.ashen_nail.enabled", true); }
    public static double ashenNailBurningDamageMultiplier() { return D("bound_curios.ashen_nail.burningDamageMultiplier", 1.35D); }
    public static boolean enableGravebellLocket() { return B("bound_curios.gravebell_locket.enabled", true); }
    public static int gravebellLocketSpeedDurationTicks() { return I("bound_curios.gravebell_locket.speedDurationTicks", 20 * 8); }
    public static int gravebellLocketSpeedAmplifier() { return I("bound_curios.gravebell_locket.speedAmplifier", 0); }
    public static int gravebellLocketRegenerationDurationTicks() { return I("bound_curios.gravebell_locket.regenerationDurationTicks", 20 * 4); }
    public static int gravebellLocketRegenerationAmplifier() { return I("bound_curios.gravebell_locket.regenerationAmplifier", 0); }
    public static boolean enableHuntersSigil() { return B("bound_curios.hunters_sigil.enabled", true); }
    public static double huntersSigilDamageMultiplier() { return D("bound_curios.hunters_sigil.damageMultiplier", 1.25D); }
    public static boolean enablePilgrimsThorn() { return B("bound_curios.pilgrims_thorn.enabled", true); }
    public static double pilgrimsThornMovementSpeedBonus() { return D("bound_curios.pilgrims_thorn.movementSpeedBonus", 0.10D); }
    public static boolean enableShroudOfTheForsaken() { return B("free_relics.shroud_of_the_forsaken.enabled", true); }
    public static double shroudLowHealthThreshold() { return D("free_relics.shroud_of_the_forsaken.lowHealthThreshold", 6.0D); }
    public static double shroudDamageMultiplier() { return D("free_relics.shroud_of_the_forsaken.damageMultiplier", 0.60D); }
    public static int shroudResistanceDurationTicks() { return I("free_relics.shroud_of_the_forsaken.resistanceDurationTicks", 20 * 5); }
    public static int shroudResistanceAmplifier() { return I("free_relics.shroud_of_the_forsaken.resistanceAmplifier", 1); }
    public static int shroudCooldownTicks() { return I("free_relics.shroud_of_the_forsaken.cooldownTicks", 20 * 60); }
    public static boolean enableVultureCharm() { return B("free_relics.vulture_charm.enabled", true); }
    public static double vultureCharmXpMultiplier() { return D("free_relics.vulture_charm.xpMultiplier", 1.50D); }
    public static boolean enableHollowEye() { return B("free_relics.hollow_eye.enabled", true); }
    public static boolean enableCenserOfAsh() { return B("free_relics.censer_of_ash.enabled", true); }
    public static double censerOfAshDamageMultiplier() { return D("free_relics.censer_of_ash.damageMultiplier", 1.20D); }
    public static double censerOfAshGlowRadius() { return D("free_relics.censer_of_ash.glowRadius", 12.0D); }
    public static int censerOfAshGlowDurationTicks() { return I("free_relics.censer_of_ash.glowDurationTicks", 40); }
    public static int censerOfAshScanIntervalTicks() { return I("free_relics.censer_of_ash.scanIntervalTicks", 20); }
    public static boolean enableMournersThread() { return B("free_relics.mourners_thread.enabled", true); }
    public static int mournersThreadFoodRestored() { return I("free_relics.mourners_thread.foodRestored", 4); }
    public static double mournersThreadSaturationRestored() { return D("free_relics.mourners_thread.saturationRestored", 0.6D); }
    public static double mournersThreadHealAmount() { return D("free_relics.mourners_thread.healAmount", 2.0D); }
    public static boolean enableThornboundCarapace() { return B("free_relics.thornbound_carapace.enabled", true); }
    public static double thornboundCarapaceReflectPercent() { return D("free_relics.thornbound_carapace.reflectPercent", 0.20D); }
    public static boolean enableVoidstepBand() { return B("free_relics.voidstep_band.enabled", true); }
    public static boolean enableExecutionersCoin() { return B("free_relics.executioners_coin.enabled", true); }
    public static double executionersCoinHealthThresholdPercent() { return D("free_relics.executioners_coin.healthThresholdPercent", 0.30D); }
    public static double executionersCoinDamageMultiplier() { return D("free_relics.executioners_coin.damageMultiplier", 1.30D); }
    public static boolean enableRelicOfTheLastBreath() { return B("free_relics.relic_of_the_last_breath.enabled", true); }
    public static double lastBreathPostTriggerHealth() { return D("free_relics.relic_of_the_last_breath.postTriggerHealth", 1.0D); }
    public static int lastBreathAbsorptionDurationTicks() { return I("free_relics.relic_of_the_last_breath.absorptionDurationTicks", 20 * 10); }
    public static int lastBreathAbsorptionAmplifier() { return I("free_relics.relic_of_the_last_breath.absorptionAmplifier", 1); }
    public static int lastBreathRegenerationDurationTicks() { return I("free_relics.relic_of_the_last_breath.regenerationDurationTicks", 20 * 6); }
    public static int lastBreathRegenerationAmplifier() { return I("free_relics.relic_of_the_last_breath.regenerationAmplifier", 1); }
    public static int lastBreathCooldownTicks() { return I("free_relics.relic_of_the_last_breath.cooldownTicks", 20 * 300); }
    public static boolean enableTorchOfGravesong() { return B("free_relics.torch_of_gravesong.enabled", true); }
    public static double torchOfGravesongUndeadDamageMultiplier() { return D("free_relics.torch_of_gravesong.undeadDamageMultiplier", 1.25D); }
    public static int torchOfGravesongStrengthDurationTicks() { return I("free_relics.torch_of_gravesong.strengthDurationTicks", 20 * 6); }
    public static int torchOfGravesongStrengthAmplifier() { return I("free_relics.torch_of_gravesong.strengthAmplifier", 0); }
    public static boolean enableLethargicFlail() { return B("sloth_weapon.enabled", true); }
    public static int lethargicFlailInventoryCheckIntervalTicks() { return I("sloth_weapon.inventoryCheckIntervalTicks", 10); }
    public static double lethargicFlailInventoryMoveSpeedMultiplier() { return D("sloth_weapon.inventoryMoveSpeedMultiplier", -0.50D); }
    public static double lethargicFlailInventoryExhaustionPerSecond() { return D("sloth_weapon.inventoryExhaustionPerSecond", 0.05D); }
    public static long slothWeaponMaxBrandedTicks() { return L("sloth_weapon.slothWeaponMaxBrandedTicks", 360000L); }
    public static double slothWeaponRequiredBrandedPercent() { return D("sloth_weapon.slothWeaponRequiredBrandedPercent", 0.995D); }
    public static boolean hollowEyeClearBlindness() { return B("free_relics.hollow_eye.clearBlindness", true); }
    public static boolean hollowEyeClearDarkness() { return B("free_relics.hollow_eye.clearDarkness", true); }
    public static double hollowEyeRevealInvisibleRadius() { return D("free_relics.hollow_eye.revealInvisibleRadius", 16.0D); }
    public static int hollowEyeRevealInvisibleDurationTicks() { return I("free_relics.hollow_eye.revealInvisibleDurationTicks", 60); }
    public static int hollowEyeRevealInvisibleIntervalTicks() { return I("free_relics.hollow_eye.revealInvisibleIntervalTicks", 20); }
    public static boolean enableRuinwake() { return B("wrath_weapon.enabled", true); }
    public static int ruinwakeMaxGrudgeStacks() { return I("wrath_weapon.maxGrudgeStacks", 5); }
    public static double ruinwakeDamageBonusPerStack() { return D("wrath_weapon.damageBonusPerStack", 0.08D); }
    public static int ruinwakeStackDecayTicks() { return I("wrath_weapon.stackDecayTicks", 20 * 10); }
    public static double ruinwakeReleaseRadius() { return D("wrath_weapon.releaseRadius", 6.0D); }
    public static double ruinwakeReleaseBaseDamageMultiplier() { return D("wrath_weapon.releaseBaseDamageMultiplier", 0.45D); }
    public static double ruinwakeReleaseDamagePerStack() { return D("wrath_weapon.releaseDamagePerStack", 0.17D); }
    public static double ruinwakeReleaseKnockback() { return D("wrath_weapon.releaseKnockback", 1.2D); }
    public static int ruinwakeWeaknessDurationTicks() { return I("wrath_weapon.weaknessDurationTicks", 20 * 8); }
    public static int ruinwakeReleaseCooldownTicks() { return I("wrath_weapon.releaseCooldownTicks", 20 * 12); }
    public static double ruinwakeFailureSelfDamagePerStack() { return D("wrath_weapon.failureSelfDamagePerStack", 1.0D); }
    public static boolean enableOathboundReliquary() { return B("bearer_curios.oathbound_reliquary.enabled", true); }
    public static int oathboundReliquaryBonusCharmSlots() { return I("bearer_curios.oathbound_reliquary.bonusCharmSlots", 2); }
    public static double oathboundReliquaryLowHealthThreshold() { return D("bearer_curios.oathbound_reliquary.lowHealthThreshold", 10.0D); }
    public static int oathboundReliquaryAbsorptionDurationTicks() { return I("bearer_curios.oathbound_reliquary.absorptionDurationTicks", 220); }
    public static int oathboundReliquaryAbsorptionAmplifier() { return I("bearer_curios.oathbound_reliquary.absorptionAmplifier", 0); }
    public static int oathboundReliquaryRegenerationDurationTicks() { return I("bearer_curios.oathbound_reliquary.regenerationDurationTicks", 80); }
    public static int oathboundReliquaryRegenerationAmplifier() { return I("bearer_curios.oathbound_reliquary.regenerationAmplifier", 0); }
    public static int oathboundReliquaryHungerCurioThreshold() { return I("bearer_curios.oathbound_reliquary.hungerCurioThreshold", 3); }
    public static int oathboundReliquaryHungerDurationTicks() { return I("bearer_curios.oathbound_reliquary.hungerDurationTicks", 120); }
    public static int oathboundReliquaryHungerAmplifier() { return I("bearer_curios.oathbound_reliquary.hungerAmplifier", 0); }
    public static boolean enableChainOfThePenitent() { return B("bearer_curios.chain_of_the_penitent.enabled", true); }
    public static double chainOfThePenitentPenanceGainMultiplier() { return D("bearer_curios.chain_of_the_penitent.penanceGainMultiplier", 1.5D); }
    public static double chainOfThePenitentMaxPenance() { return D("bearer_curios.chain_of_the_penitent.maxPenance", 40.0D); }
    public static double chainOfThePenitentMaxBonusDamage() { return D("bearer_curios.chain_of_the_penitent.maxBonusDamage", 12.0D); }
    public static int chainOfThePenitentMarkDurationTicks() { return I("bearer_curios.chain_of_the_penitent.markDurationTicks", 100); }
    public static boolean enableEyeOfTheSleeplessWitness() { return B("bearer_curios.eye_of_the_sleepless_witness.enabled", true); }
    public static boolean eyeOfTheSleeplessWitnessNightVision() { return B("bearer_curios.eye_of_the_sleepless_witness.nightVision", true); }
    public static double eyeOfTheSleeplessWitnessRevealRadiusMoving() { return D("bearer_curios.eye_of_the_sleepless_witness.revealRadiusMoving", 14.0D); }
    public static double eyeOfTheSleeplessWitnessRevealRadiusStill() { return D("bearer_curios.eye_of_the_sleepless_witness.revealRadiusStill", 24.0D); }
    public static int eyeOfTheSleeplessWitnessGlowDurationTicks() { return I("bearer_curios.eye_of_the_sleepless_witness.glowDurationTicks", 60); }
    public static int eyeOfTheSleeplessWitnessOakskinDurationTicks() { return I("bearer_curios.eye_of_the_sleepless_witness.oakskinDurationTicks", 80); }
    public static int eyeOfTheSleeplessWitnessOakskinAmplifier() { return I("bearer_curios.eye_of_the_sleepless_witness.oakskinAmplifier", 0); }
    public static double eyeOfTheSleeplessWitnessOakskinMovementThreshold() { return D("bearer_curios.eye_of_the_sleepless_witness.oakskinMovementThreshold", 0.02D); }
    public static boolean enableCenserOfHollowPrayer() { return B("bearer_curios.censer_of_hollow_prayer.enabled", true); }
    public static double censerOfHollowPrayerRadius() { return D("bearer_curios.censer_of_hollow_prayer.radius", 5.0D); }
    public static int censerOfHollowPrayerWeaknessDurationTicks() { return I("bearer_curios.censer_of_hollow_prayer.weaknessDurationTicks", 80); }
    public static int censerOfHollowPrayerWeaknessAmplifier() { return I("bearer_curios.censer_of_hollow_prayer.weaknessAmplifier", 0); }
    public static int censerOfHollowPrayerSlownessDurationTicks() { return I("bearer_curios.censer_of_hollow_prayer.slownessDurationTicks", 80); }
    public static int censerOfHollowPrayerSlownessAmplifier() { return I("bearer_curios.censer_of_hollow_prayer.slownessAmplifier", 0); }
    public static int censerOfHollowPrayerRegenerationDurationTicks() { return I("bearer_curios.censer_of_hollow_prayer.regenerationDurationTicks", 80); }
    public static int censerOfHollowPrayerRegenerationAmplifier() { return I("bearer_curios.censer_of_hollow_prayer.regenerationAmplifier", 0); }
    public static int censerOfHollowPrayerCrowdThreshold() { return I("bearer_curios.censer_of_hollow_prayer.crowdThreshold", 3); }
    public static int censerOfHollowPrayerAbsorptionDurationTicks() { return I("bearer_curios.censer_of_hollow_prayer.absorptionDurationTicks", 100); }
    public static int censerOfHollowPrayerAbsorptionAmplifier() { return I("bearer_curios.censer_of_hollow_prayer.absorptionAmplifier", 1); }
    public static boolean enableNailOfTheFirstMartyr() { return B("bearer_curios.nail_of_the_first_martyr.enabled", true); }
    public static int nailOfTheFirstMartyrClaimDurationTicks() { return I("bearer_curios.nail_of_the_first_martyr.claimDurationTicks", 120); }
    public static int nailOfTheFirstMartyrMaxStacks() { return I("bearer_curios.nail_of_the_first_martyr.maxStacks", 5); }
    public static int nailOfTheFirstMartyrJudgedDurationTicks() { return I("bearer_curios.nail_of_the_first_martyr.judgedDurationTicks", 200); }
    public static int nailOfTheFirstMartyrHasteDurationTicks() { return I("bearer_curios.nail_of_the_first_martyr.hasteDurationTicks", 200); }
    public static int nailOfTheFirstMartyrHasteAmplifier() { return I("bearer_curios.nail_of_the_first_martyr.hasteAmplifier", 1); }
    public static int nailOfTheFirstMartyrAbsorptionDurationTicks() { return I("bearer_curios.nail_of_the_first_martyr.absorptionDurationTicks", 200); }
    public static int nailOfTheFirstMartyrAbsorptionAmplifier() { return I("bearer_curios.nail_of_the_first_martyr.absorptionAmplifier", 1); }
    public static boolean enableGoldRing() { return B("basic_rings.gold_ring.enabled", true); }
    public static double goldRingArmorBonus() { return D("basic_rings.gold_ring.armorBonus", 1.0D); }
    public static boolean enableCyanRing() { return B("basic_rings.cyan_ring.enabled", true); }
    public static double cyanRingArmorBonus() { return D("basic_rings.cyan_ring.armorBonus", 2.0D); }
    public static boolean enableNebulaRing() { return B("basic_rings.nebula_ring.enabled", true); }
    public static boolean FracturedAshRing() { return B("basic_rings.fractured_ring.enabled", true); }
    public static double nebulaRingArmorBonus() { return D("basic_rings.nebula_ring.armorBonus", 3.0D); }
    public static int nebulaRingExtraRingSlots() { return I("basic_rings.nebula_ring.extraRingSlots", 1); }
    public static int FracturedRingExtraRingSlots() { return I("basic_rings.fractured_ring.extraRingSlots", 1); }
    public static boolean enableCreativeBrandedTimeBypass() { return B("sloth_weapon.enableCreativeBrandedTimeBypass", true); }
    public static int lethargicFlailLazinessDurationTicks() { return I("sloth_weapon.lazinessDurationTicks", 20 * 60); }
    public static int lethargicFlailSweepLazinessStacks() { return I("sloth_weapon.sweepLazinessStacks", 3); }
    public static double lethargicFlailSweepDamageMultiplier() { return D("sloth_weapon.sweepDamageMultiplier", 0.75D); }
    public static double lethargicFlailSweepRadius() { return D("sloth_weapon.sweepRadius", 10.0D); }
    public static int lethargicFlailSweepEnemyDebuffDurationTicks() { return I("sloth_weapon.sweepEnemyDebuffDurationTicks", 20 * 10); }
    public static int lethargicFlailCubeCooldownTicks() { return I("sloth_weapon.cubeCooldownTicks", 20 * 30); }
    public static int lethargicFlailCubeStunDurationTicks() { return I("sloth_weapon.cubeStunDurationTicks", 20 * 10); }
    public static double lethargicFlailCubeDamageMultiplier() { return D("sloth_weapon.cubeDamageMultiplier", 0.75D); }
    public static double soulFractureMaxHealthLossPerGem() { return D("oathbound_relic.soulFractureMaxHealthLossPerGem", 1.0D); }
    public static double soulGemPickupRadius() { return D("oathbound_relic.soulGemPickupRadius", 1.25D); }
    public static boolean enableVanitysEdge() { return B("pride_weapon.enabled", true); }
    public static int vanitysEdgeMaintenanceIntervalTicks() { return I("pride_weapon.maintenanceIntervalTicks", 10); }
    public static int vanitysEdgeAllyAuraIntervalTicks() { return I("pride_weapon.allyAuraIntervalTicks", 20); }
    public static double vanitysEdgeHighHealthAttackSpeedThreshold() { return D("pride_weapon.highHealthAttackSpeedThreshold", 0.80D); }
    public static double vanitysEdgeHighHealthAttackSpeedBonus() { return D("pride_weapon.highHealthAttackSpeedBonus", 0.10D); }
    public static double vanitysEdgeHighHealthBonusDamagePerTwoHp() { return D("pride_weapon.highHealthBonusDamagePerTwoHp", 2.0D); }
    public static double vanitysEdgeAllySuppressionRadius() { return D("pride_weapon.allySuppressionRadius", 12.0D); }
    public static int vanitysEdgeAllySuppressionDurationTicks() { return I("pride_weapon.allySuppressionDurationTicks", 40); }
    public static double vanitysEdgeNonKillSelfDamage() { return D("pride_weapon.nonKillSelfDamage", 1.0D); }
    public static int vanitysEdgeKillStackDurationTicks() { return I("pride_weapon.killStackDurationTicks", 20 * 60 * 3); }
    public static double vanitysEdgeKillStackAttackSpeedBonus() { return D("pride_weapon.killStackAttackSpeedBonus", 0.10D); }
    public static double vanitysEdgeArmorPenaltyPerTwoArmor() { return D("pride_weapon.armorPenaltyPerTwoArmor", 1.0D); }
    public static double vanitysEdgeMidHealthUpperThreshold() { return D("pride_weapon.midHealthUpperThreshold", 0.50D); }
    public static double vanitysEdgeMidHealthLowerThreshold() { return D("pride_weapon.midHealthLowerThreshold", 0.0D); }
    public static double vanitysEdgeMidHealthDamagePenaltyMultiplier() { return D("pride_weapon.midHealthDamagePenaltyMultiplier", -0.50D); }
    public static double vanitysEdgeMidHealthAttackSpeedPenaltyMultiplier() { return D("pride_weapon.midHealthAttackSpeedPenaltyMultiplier", -0.50D); }
    public static double vanitysEdgeLowHealthThreshold() { return D("pride_weapon.lowHealthThreshold", 0.30D); }
    public static double vanitysEdgeLosingPrideChance() { return D("pride_weapon.losingPrideChance", 0.60D); }
    public static int vanitysEdgeOutcomeDurationTicks() { return I("pride_weapon.outcomeDurationTicks", 20 * 60 * 10); }
    public static double vanitysEdgeLosingPrideHealingMultiplier() { return D("pride_weapon.losingPrideHealingMultiplier", 0.80D); }
    public static int vanitysEdgeBraveryRegenerationAmplifier() { return I("pride_weapon.braveryRegenerationAmplifier", 4); }
    public static boolean enableCovetfang() { return B("envy_weapon.enabled", true); }
    public static int covetfangMaintenanceIntervalTicks() { return I("envy_weapon.maintenanceIntervalTicks", 10); }
    public static int covetfangClaimDurationTicks() { return I("envy_weapon.claimDurationTicks", 20 * 20); }
    public static int covetfangClaimCooldownTicks() { return I("envy_weapon.claimCooldownTicks", 20 * 8); }
    public static double covetfangClaimTrackRadius() { return D("envy_weapon.claimTrackRadius", 48.0D); }
    public static int covetfangCovetedGlowDurationTicks() { return I("envy_weapon.covetedGlowDurationTicks", 40); }
    public static double covetfangBonusDamagePerEnvyScore() { return D("envy_weapon.bonusDamagePerEnvyScore", 2.0D); }
    public static double covetfangCovetedTargetExtraDamage() { return D("envy_weapon.covetedTargetExtraDamage", 2.0D); }
    public static double covetfangBonusDamageCap() { return D("envy_weapon.bonusDamageCap", 12.0D); }
    public static double covetfangBaseNonKillSelfDamage() { return D("envy_weapon.baseNonKillSelfDamage", 1.0D); }
    public static double covetfangHollowComparisonExtraSelfDamage() { return D("envy_weapon.hollowComparisonExtraSelfDamage", 1.0D); }
    public static int covetfangStolenStrengthDurationTicks() { return I("envy_weapon.stolenStrengthDurationTicks", 20 * 45); }
    public static double covetfangStolenVitalityMaxHealthBonus() { return D("envy_weapon.stolenVitalityMaxHealthBonus", 4.0D); }
    public static double covetfangStolenPlatingArmorBonus() { return D("envy_weapon.stolenPlatingArmorBonus", 4.0D); }
    public static double covetfangStolenSwiftnessMoveSpeedBonus() { return D("envy_weapon.stolenSwiftnessMoveSpeedBonus", 0.15D); }
    public static double covetfangStolenFerocityAttackSpeedBonus() { return D("envy_weapon.stolenFerocityAttackSpeedBonus", 0.20D); }
    public static double covetfangBeneficialEffectAttackDamagePenalty() { return D("envy_weapon.beneficialEffectAttackDamagePenalty", 1.0D); }
    public static double covetfangLowHealthThreshold() { return D("envy_weapon.lowHealthThreshold", 0.30D); }
    public static double covetfangDesperateWantScanRadius() { return D("envy_weapon.desperateWantScanRadius", 16.0D); }
    public static double covetfangDesperateWantHealOnHit() { return D("envy_weapon.desperateWantHealOnHit", 1.0D); }
    public static double covetfangDesperateWantMoveSpeedBonus() { return D("envy_weapon.desperateWantMoveSpeedBonus", 0.20D); }
    public static double covetfangHopelessComparisonDamagePenalty() { return D("envy_weapon.hopelessComparisonDamagePenalty", -0.35D); }
    public static double covetfangHopelessComparisonMoveSpeedPenalty() { return D("envy_weapon.hopelessComparisonMoveSpeedPenalty", -0.20D); }
    public static double covetfangCovetedPursuitMoveSpeedBonus() { return D("envy_weapon.covetedPursuitMoveSpeedBonus", 0.10D); }
    public static boolean giveStarterOathboundRelic() { return B("oathbound_relic.giveStarterOathboundRelic", true); }
    public static boolean enableBrandkeepersMercy() { return B("bearer_curios.brandkeepers_mercy.enabled", true); }
    public static int bloodTollCooldownTicks() { return I("oathbound_relic.bloodTollCooldownTicks", 60); }
}
