package net.bandit.oathboundrelics.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class OathboundConfig {

    public static final OathboundConfig CONFIG;
    public static final ModConfigSpec SPEC;

    static {
        Pair<OathboundConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(OathboundConfig::new);
        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }

    // Oathbound Relic curses
    public final ModConfigSpec.BooleanValue enableFrailty;
    public final ModConfigSpec.BooleanValue enableProvocation;
    public final ModConfigSpec.BooleanValue enableShatteredPlate;

    public final ModConfigSpec.BooleanValue enableBloodToll;
    public final ModConfigSpec.DoubleValue bloodTollHealthCost;
    public final ModConfigSpec.IntValue bloodTollCooldownTicks;

    public final ModConfigSpec.BooleanValue enableLivingEmber;
    public final ModConfigSpec.BooleanValue enableSoulFracture;
    public final ModConfigSpec.BooleanValue enableWakefulDoom;
    public final ModConfigSpec.BooleanValue giveStarterOathboundRelic;
    public final ModConfigSpec.DoubleValue incomingDamageMultiplier;
    public final ModConfigSpec.DoubleValue armorEffectiveness;


    public final ModConfigSpec.IntValue minFireTicks;
    public final ModConfigSpec.IntValue neutralAggroInterval;
    public final ModConfigSpec.DoubleValue neutralAggroRange;

    public final ModConfigSpec.IntValue respawnWeaknessDurationTicks;
    public final ModConfigSpec.IntValue respawnWeaknessAmplifier;
    public final ModConfigSpec.IntValue respawnSlownessDurationTicks;
    public final ModConfigSpec.IntValue respawnSlownessAmplifier;

    // Branded blessings
    public final ModConfigSpec.BooleanValue enableLootingBlessing;
    public final ModConfigSpec.BooleanValue enableFortuneBlessing;
    public final ModConfigSpec.BooleanValue enableXpBlessing;
    public final ModConfigSpec.BooleanValue enableEnchantingBlessing;
    public final ModConfigSpec.BooleanValue enableAttackSpeedBlessing;
    public final ModConfigSpec.BooleanValue enableAbsorptionBlessing;

    public final ModConfigSpec.IntValue lootingBonus;
    public final ModConfigSpec.IntValue fortuneBonus;
    public final ModConfigSpec.DoubleValue xpMultiplier;
    public final ModConfigSpec.IntValue enchantingPowerBonus;
    public final ModConfigSpec.DoubleValue attackSpeedBonus;
    public final ModConfigSpec.DoubleValue absorptionThreshold;
    public final ModConfigSpec.DoubleValue absorptionAmount;

    public final ModConfigSpec.DoubleValue soulFractureMaxHealthLossPerGem;
    public final ModConfigSpec.DoubleValue soulGemPickupRadius;

    // Bound curios
    public final ModConfigSpec.BooleanValue enableAshenNail;
    public final ModConfigSpec.DoubleValue ashenNailBurningDamageMultiplier;

    public final ModConfigSpec.BooleanValue enableGravebellLocket;
    public final ModConfigSpec.IntValue gravebellLocketSpeedDurationTicks;
    public final ModConfigSpec.IntValue gravebellLocketSpeedAmplifier;
    public final ModConfigSpec.IntValue gravebellLocketRegenerationDurationTicks;
    public final ModConfigSpec.IntValue gravebellLocketRegenerationAmplifier;

    public final ModConfigSpec.BooleanValue enableHuntersSigil;
    public final ModConfigSpec.DoubleValue huntersSigilDamageMultiplier;

    public final ModConfigSpec.BooleanValue enablePilgrimsThorn;
    public final ModConfigSpec.DoubleValue pilgrimsThornMovementSpeedBonus;

    // Free relics
    public final ModConfigSpec.BooleanValue enableShroudOfTheForsaken;
    public final ModConfigSpec.DoubleValue shroudLowHealthThreshold;
    public final ModConfigSpec.DoubleValue shroudDamageMultiplier;
    public final ModConfigSpec.IntValue shroudResistanceDurationTicks;
    public final ModConfigSpec.IntValue shroudResistanceAmplifier;
    public final ModConfigSpec.IntValue shroudCooldownTicks;

    public final ModConfigSpec.BooleanValue enableVultureCharm;
    public final ModConfigSpec.DoubleValue vultureCharmXpMultiplier;

    public final ModConfigSpec.BooleanValue enableHollowEye;

    public final ModConfigSpec.BooleanValue enableCenserOfAsh;
    public final ModConfigSpec.DoubleValue censerOfAshDamageMultiplier;
    public final ModConfigSpec.DoubleValue censerOfAshGlowRadius;
    public final ModConfigSpec.IntValue censerOfAshGlowDurationTicks;
    public final ModConfigSpec.IntValue censerOfAshScanIntervalTicks;

    public final ModConfigSpec.BooleanValue enableMournersThread;
    public final ModConfigSpec.IntValue mournersThreadFoodRestored;
    public final ModConfigSpec.DoubleValue mournersThreadSaturationRestored;
    public final ModConfigSpec.DoubleValue mournersThreadHealAmount;

    public final ModConfigSpec.BooleanValue enableThornboundCarapace;
    public final ModConfigSpec.DoubleValue thornboundCarapaceReflectPercent;

    public final ModConfigSpec.BooleanValue enableVoidstepBand;

    public final ModConfigSpec.BooleanValue enableExecutionersCoin;
    public final ModConfigSpec.DoubleValue executionersCoinHealthThresholdPercent;
    public final ModConfigSpec.DoubleValue executionersCoinDamageMultiplier;

    public final ModConfigSpec.BooleanValue enableRelicOfTheLastBreath;
    public final ModConfigSpec.DoubleValue lastBreathPostTriggerHealth;
    public final ModConfigSpec.IntValue lastBreathAbsorptionDurationTicks;
    public final ModConfigSpec.IntValue lastBreathAbsorptionAmplifier;
    public final ModConfigSpec.IntValue lastBreathRegenerationDurationTicks;
    public final ModConfigSpec.IntValue lastBreathRegenerationAmplifier;
    public final ModConfigSpec.IntValue lastBreathCooldownTicks;

    public final ModConfigSpec.BooleanValue enableTorchOfGravesong;
    public final ModConfigSpec.DoubleValue torchOfGravesongUndeadDamageMultiplier;
    public final ModConfigSpec.IntValue torchOfGravesongStrengthDurationTicks;
    public final ModConfigSpec.IntValue torchOfGravesongStrengthAmplifier;

    public final ModConfigSpec.LongValue slothWeaponMaxBrandedTicks;
    public final ModConfigSpec.DoubleValue slothWeaponRequiredBrandedPercent;
    public final ModConfigSpec.BooleanValue enableLethargicFlail;
    public final ModConfigSpec.IntValue lethargicFlailInventoryCheckIntervalTicks;
    public final ModConfigSpec.DoubleValue lethargicFlailInventoryMoveSpeedMultiplier;
    public final ModConfigSpec.DoubleValue lethargicFlailInventoryExhaustionPerSecond;

    public final ModConfigSpec.BooleanValue enableBrandkeepersMercy;

    // Revised Hollow Eye
    public final ModConfigSpec.BooleanValue hollowEyeClearBlindness;
    public final ModConfigSpec.BooleanValue hollowEyeClearDarkness;
    public final ModConfigSpec.DoubleValue hollowEyeRevealInvisibleRadius;
    public final ModConfigSpec.IntValue hollowEyeRevealInvisibleDurationTicks;
    public final ModConfigSpec.IntValue hollowEyeRevealInvisibleIntervalTicks;

    // Bearer curios
    public final ModConfigSpec.BooleanValue enableOathboundReliquary;
    public final ModConfigSpec.IntValue oathboundReliquaryBonusCharmSlots;
    public final ModConfigSpec.DoubleValue oathboundReliquaryLowHealthThreshold;
    public final ModConfigSpec.IntValue oathboundReliquaryAbsorptionDurationTicks;
    public final ModConfigSpec.IntValue oathboundReliquaryAbsorptionAmplifier;
    public final ModConfigSpec.IntValue oathboundReliquaryRegenerationDurationTicks;
    public final ModConfigSpec.IntValue oathboundReliquaryRegenerationAmplifier;
    public final ModConfigSpec.IntValue oathboundReliquaryHungerCurioThreshold;
    public final ModConfigSpec.IntValue oathboundReliquaryHungerDurationTicks;
    public final ModConfigSpec.IntValue oathboundReliquaryHungerAmplifier;

    public final ModConfigSpec.BooleanValue enableChainOfThePenitent;
    public final ModConfigSpec.DoubleValue chainOfThePenitentPenanceGainMultiplier;
    public final ModConfigSpec.DoubleValue chainOfThePenitentMaxPenance;
    public final ModConfigSpec.DoubleValue chainOfThePenitentMaxBonusDamage;
    public final ModConfigSpec.IntValue chainOfThePenitentMarkDurationTicks;

    public final ModConfigSpec.BooleanValue enableEyeOfTheSleeplessWitness;
    public final ModConfigSpec.BooleanValue eyeOfTheSleeplessWitnessNightVision;
    public final ModConfigSpec.DoubleValue eyeOfTheSleeplessWitnessRevealRadiusMoving;
    public final ModConfigSpec.DoubleValue eyeOfTheSleeplessWitnessRevealRadiusStill;
    public final ModConfigSpec.IntValue eyeOfTheSleeplessWitnessGlowDurationTicks;
    public final ModConfigSpec.IntValue eyeOfTheSleeplessWitnessOakskinDurationTicks;
    public final ModConfigSpec.IntValue eyeOfTheSleeplessWitnessOakskinAmplifier;
    public final ModConfigSpec.DoubleValue eyeOfTheSleeplessWitnessOakskinMovementThreshold;

    public final ModConfigSpec.BooleanValue enableCenserOfHollowPrayer;
    public final ModConfigSpec.DoubleValue censerOfHollowPrayerRadius;
    public final ModConfigSpec.IntValue censerOfHollowPrayerWeaknessDurationTicks;
    public final ModConfigSpec.IntValue censerOfHollowPrayerWeaknessAmplifier;
    public final ModConfigSpec.IntValue censerOfHollowPrayerSlownessDurationTicks;
    public final ModConfigSpec.IntValue censerOfHollowPrayerSlownessAmplifier;
    public final ModConfigSpec.IntValue censerOfHollowPrayerRegenerationDurationTicks;
    public final ModConfigSpec.IntValue censerOfHollowPrayerRegenerationAmplifier;
    public final ModConfigSpec.IntValue censerOfHollowPrayerCrowdThreshold;
    public final ModConfigSpec.IntValue censerOfHollowPrayerAbsorptionDurationTicks;
    public final ModConfigSpec.IntValue censerOfHollowPrayerAbsorptionAmplifier;

    public final ModConfigSpec.BooleanValue enableNailOfTheFirstMartyr;
    public final ModConfigSpec.IntValue nailOfTheFirstMartyrClaimDurationTicks;
    public final ModConfigSpec.IntValue nailOfTheFirstMartyrMaxStacks;
    public final ModConfigSpec.IntValue nailOfTheFirstMartyrJudgedDurationTicks;
    public final ModConfigSpec.IntValue nailOfTheFirstMartyrHasteDurationTicks;
    public final ModConfigSpec.IntValue nailOfTheFirstMartyrHasteAmplifier;
    public final ModConfigSpec.IntValue nailOfTheFirstMartyrAbsorptionDurationTicks;
    public final ModConfigSpec.IntValue nailOfTheFirstMartyrAbsorptionAmplifier;

    // Basic rings
    public final ModConfigSpec.BooleanValue enableGoldRing;
    public final ModConfigSpec.DoubleValue goldRingArmorBonus;

    public final ModConfigSpec.BooleanValue enableCyanRing;
    public final ModConfigSpec.DoubleValue cyanRingArmorBonus;

    public final ModConfigSpec.BooleanValue enableNebulaRing;
    public final ModConfigSpec.BooleanValue enableFracturedRing;
    public final ModConfigSpec.DoubleValue nebulaRingArmorBonus;
    public final ModConfigSpec.IntValue nebulaRingExtraRingSlots;
    public final ModConfigSpec.IntValue FracturedRingExtraRingSlots;

    // Expanded Sloth weapon
    public final ModConfigSpec.BooleanValue enableCreativeBrandedTimeBypass;
    public final ModConfigSpec.IntValue lethargicFlailLazinessDurationTicks;
    public final ModConfigSpec.IntValue lethargicFlailSweepLazinessStacks;
    public final ModConfigSpec.DoubleValue lethargicFlailSweepDamageMultiplier;
    public final ModConfigSpec.DoubleValue lethargicFlailSweepRadius;
    public final ModConfigSpec.IntValue lethargicFlailSweepEnemyDebuffDurationTicks;
    public final ModConfigSpec.IntValue lethargicFlailCubeCooldownTicks;
    public final ModConfigSpec.IntValue lethargicFlailCubeStunDurationTicks;
    public final ModConfigSpec.DoubleValue lethargicFlailCubeDamageMultiplier;

    // Pride weapon
    public final ModConfigSpec.BooleanValue enableVanitysEdge;
    public final ModConfigSpec.IntValue vanitysEdgeMaintenanceIntervalTicks;
    public final ModConfigSpec.IntValue vanitysEdgeAllyAuraIntervalTicks;

    public final ModConfigSpec.DoubleValue vanitysEdgeHighHealthAttackSpeedThreshold;
    public final ModConfigSpec.DoubleValue vanitysEdgeHighHealthAttackSpeedBonus;
    public final ModConfigSpec.DoubleValue vanitysEdgeHighHealthBonusDamagePerTwoHp;

    public final ModConfigSpec.DoubleValue vanitysEdgeAllySuppressionRadius;
    public final ModConfigSpec.IntValue vanitysEdgeAllySuppressionDurationTicks;

    public final ModConfigSpec.DoubleValue vanitysEdgeNonKillSelfDamage;

    public final ModConfigSpec.IntValue vanitysEdgeKillStackDurationTicks;
    public final ModConfigSpec.DoubleValue vanitysEdgeKillStackAttackSpeedBonus;

    public final ModConfigSpec.DoubleValue vanitysEdgeArmorPenaltyPerTwoArmor;

    public final ModConfigSpec.DoubleValue vanitysEdgeMidHealthUpperThreshold;
    public final ModConfigSpec.DoubleValue vanitysEdgeMidHealthLowerThreshold;
    public final ModConfigSpec.DoubleValue vanitysEdgeMidHealthDamagePenaltyMultiplier;
    public final ModConfigSpec.DoubleValue vanitysEdgeMidHealthAttackSpeedPenaltyMultiplier;

    public final ModConfigSpec.DoubleValue vanitysEdgeLowHealthThreshold;
    public final ModConfigSpec.DoubleValue vanitysEdgeLosingPrideChance;
    public final ModConfigSpec.IntValue vanitysEdgeOutcomeDurationTicks;

    public final ModConfigSpec.DoubleValue vanitysEdgeLosingPrideHealingMultiplier;
    public final ModConfigSpec.IntValue vanitysEdgeBraveryRegenerationAmplifier;

    // Envy weapon
    public final ModConfigSpec.BooleanValue enableCovetfang;
    public final ModConfigSpec.IntValue covetfangMaintenanceIntervalTicks;

    public final ModConfigSpec.IntValue covetfangClaimDurationTicks;
    public final ModConfigSpec.IntValue covetfangClaimCooldownTicks;
    public final ModConfigSpec.DoubleValue covetfangClaimTrackRadius;
    public final ModConfigSpec.IntValue covetfangCovetedGlowDurationTicks;

    public final ModConfigSpec.DoubleValue covetfangBonusDamagePerEnvyScore;
    public final ModConfigSpec.DoubleValue covetfangCovetedTargetExtraDamage;
    public final ModConfigSpec.DoubleValue covetfangBonusDamageCap;

    public final ModConfigSpec.DoubleValue covetfangBaseNonKillSelfDamage;
    public final ModConfigSpec.DoubleValue covetfangHollowComparisonExtraSelfDamage;

    public final ModConfigSpec.IntValue covetfangStolenStrengthDurationTicks;
    public final ModConfigSpec.DoubleValue covetfangStolenVitalityMaxHealthBonus;
    public final ModConfigSpec.DoubleValue covetfangStolenPlatingArmorBonus;
    public final ModConfigSpec.DoubleValue covetfangStolenSwiftnessMoveSpeedBonus;
    public final ModConfigSpec.DoubleValue covetfangStolenFerocityAttackSpeedBonus;

    public final ModConfigSpec.DoubleValue covetfangBeneficialEffectAttackDamagePenalty;

    public final ModConfigSpec.DoubleValue covetfangLowHealthThreshold;
    public final ModConfigSpec.DoubleValue covetfangDesperateWantScanRadius;
    public final ModConfigSpec.DoubleValue covetfangDesperateWantHealOnHit;
    public final ModConfigSpec.DoubleValue covetfangDesperateWantMoveSpeedBonus;

    public final ModConfigSpec.DoubleValue covetfangHopelessComparisonDamagePenalty;
    public final ModConfigSpec.DoubleValue covetfangHopelessComparisonMoveSpeedPenalty;

    public final ModConfigSpec.DoubleValue covetfangCovetedPursuitMoveSpeedBonus;


    private OathboundConfig(ModConfigSpec.Builder builder) {
        builder.push("oathbound_relic");

        giveStarterOathboundRelic = builder
                .comment("If true, players receive an Oathbound Relic the first time they join the world.")
                .define("giveStarterOathboundRelic", true);

        soulFractureMaxHealthLossPerGem = builder
                .comment("Maximum health points lost per unreclaimed Soul Gem. 1.0 = half a heart, 2.0 = one full heart.")
                .defineInRange("soulFractureMaxHealthLossPerGem", 1.0D, 0.0D, 20.0D);

        soulGemPickupRadius = builder
                .comment("Radius in blocks for the owner to consume their Soul Gem.")
                .defineInRange("soulGemPickupRadius", 1.25D, 0.25D, 8.0D);

        enableFrailty = builder
                .comment("If true, Branded players take extra incoming damage.")
                .define("enableFrailty", true);

        incomingDamageMultiplier = builder
                .comment("Incoming damage multiplier. 2.0 = 100% more damage taken.")
                .defineInRange("incomingDamageMultiplier", 2.0D, 0.0D, 100.0D);

        enableProvocation = builder
                .comment("If true, nearby neutral mobs become hostile to Branded players.")
                .define("enableProvocation", true);

        neutralAggroInterval = builder
                .comment("How often, in ticks, nearby neutral mobs are provoked. 20 = once per second.")
                .defineInRange("neutralAggroInterval", 20, 1, 1200);

        neutralAggroRange = builder
                .comment("Range in blocks for provoking nearby neutral mobs.")
                .defineInRange("neutralAggroRange", 16.0D, 1.0D, 128.0D);

        enableShatteredPlate = builder
                .comment("If true, armor becomes less effective for Branded players.")
                .define("enableShatteredPlate", true);

        armorEffectiveness = builder
                .comment("Armor effectiveness multiplier. 0.70 = armor is 30% less effective.")
                .defineInRange("armorEffectiveness", 0.70D, 0.0D, 1.0D);

        enableBloodToll = builder
                .comment("If true, Branded players pay health on their first successful damaging attack after the cooldown expires.")
                .define("enableBloodToll", true);

        bloodTollHealthCost = builder
                .comment("Health points consumed by Blood Toll. 1.0 = half a heart.")
                .defineInRange("bloodTollHealthCost", 1.0D, 0.0D, 20.0D);

        bloodTollCooldownTicks = builder
                .comment("Cooldown in ticks between Blood Toll triggers. 60 ticks = 3 seconds.")
                .defineInRange("bloodTollCooldownTicks", 60, 0, 20 * 60 * 60);
        enableLivingEmber = builder
                .comment("If true, fire lingers longer on Branded players.")
                .define("enableLivingEmber", true);

        minFireTicks = builder
                .comment("Minimum fire ticks maintained while the Branded player is burning. 20 ticks = 1 second.")
                .defineInRange("minFireTicks", 200, 0, 12000);

        enableSoulFracture = builder
                .comment("If true, Branded players suffer a penalty after respawning.")
                .define("enableSoulFracture", true);

        respawnWeaknessDurationTicks = builder
                .comment("Weakness duration after respawn, in ticks.")
                .defineInRange("respawnWeaknessDurationTicks", 20 * 30, 0, 20 * 60 * 60);

        respawnWeaknessAmplifier = builder
                .comment("Weakness amplifier after respawn. 0 = Weakness I.")
                .defineInRange("respawnWeaknessAmplifier", 0, 0, 255);

        respawnSlownessDurationTicks = builder
                .comment("Slowness duration after respawn, in ticks.")
                .defineInRange("respawnSlownessDurationTicks", 20 * 15, 0, 20 * 60 * 60);

        respawnSlownessAmplifier = builder
                .comment("Slowness amplifier after respawn. 0 = Slowness I.")
                .defineInRange("respawnSlownessAmplifier", 0, 0, 255);

        enableWakefulDoom = builder
                .comment("If true, Branded players cannot sleep.")
                .define("enableWakefulDoom", true);

        builder.pop();

        builder.push("blessings");

        enableLootingBlessing = builder
                .comment("If true, the Branded player gains bonus Looting.")
                .define("enableLootingBlessing", true);

        lootingBonus = builder
                .comment("Bonus looting levels. 1 = Looting I equivalent.")
                .defineInRange("lootingBonus", 1, 0, 5);

        enableFortuneBlessing = builder
                .comment("If true, the Branded player gains bonus Fortune.")
                .define("enableFortuneBlessing", true);

        fortuneBonus = builder
                .comment("Bonus fortune levels. 1 = Fortune I equivalent.")
                .defineInRange("fortuneBonus", 1, 0, 5);

        enableXpBlessing = builder
                .comment("If true, mobs drop more XP for the Branded player.")
                .define("enableXpBlessing", true);

        xpMultiplier = builder
                .comment("XP multiplier from kills. 2.0 = double XP.")
                .defineInRange("xpMultiplier", 6.0D, 1.0D, 10.0D);

        enableEnchantingBlessing = builder
                .comment("If true, the Branded player gets bonus enchanting power at nearby tables.")
                .define("enableEnchantingBlessing", true);

        enchantingPowerBonus = builder
                .comment("Bonus enchanting power levels added to nearby tables.")
                .defineInRange("enchantingPowerBonus", 10, 0, 30);

        enableAttackSpeedBlessing = builder
                .comment("If true, the Branded player gains bonus attack speed.")
                .define("enableAttackSpeedBlessing", true);

        attackSpeedBonus = builder
                .comment("Bonus attack speed (additive). 0.5 = noticeably faster.")
                .defineInRange("attackSpeedBonus", 0.5D, 0.0D, 4.0D);

        enableAbsorptionBlessing = builder
                .comment("If true, the Branded player gains absorption when low on health.")
                .define("enableAbsorptionBlessing", true);

        absorptionThreshold = builder
                .comment("Health threshold in health points. 6.0 = 3 hearts.")
                .defineInRange("absorptionThreshold", 8.0D, 1.0D, 20.0D);

        absorptionAmount = builder
                .comment("Absorption amount in health points. 4.0 = 2 hearts.")
                .defineInRange("absorptionAmount", 4.0D, 1.0D, 40.0D);

        builder.pop();

        builder.push("bound_curios");

        builder.push("ashen_nail");
        enableAshenNail = builder
                .comment("If true, Ashen Nail can grant bonus damage while burning.")
                .define("enabled", true);
        ashenNailBurningDamageMultiplier = builder
                .comment("Damage multiplier while the player is burning. 1.35 = 35% more damage.")
                .defineInRange("burningDamageMultiplier", 1.35D, 0.0D, 100.0D);
        builder.pop();

        builder.push("gravebell_locket");
        enableGravebellLocket = builder
                .comment("If true, Gravebell Locket grants buffs on kill.")
                .define("enabled", true);
        gravebellLocketSpeedDurationTicks = builder
                .comment("Speed duration in ticks.")
                .defineInRange("speedDurationTicks", 20 * 8, 0, 20 * 60 * 60);
        gravebellLocketSpeedAmplifier = builder
                .comment("Speed amplifier. 0 = Speed I.")
                .defineInRange("speedAmplifier", 0, 0, 255);
        gravebellLocketRegenerationDurationTicks = builder
                .comment("Regeneration duration in ticks.")
                .defineInRange("regenerationDurationTicks", 20 * 4, 0, 20 * 60 * 60);
        gravebellLocketRegenerationAmplifier = builder
                .comment("Regeneration amplifier. 0 = Regeneration I.")
                .defineInRange("regenerationAmplifier", 0, 0, 255);
        builder.pop();

        builder.push("hunters_sigil");
        enableHuntersSigil = builder
                .comment("If true, Hunter's Sigil grants bonus damage against enemies targeting you.")
                .define("enabled", true);
        huntersSigilDamageMultiplier = builder
                .comment("Damage multiplier when the target is already targeting the player. 1.25 = 25% more damage.")
                .defineInRange("damageMultiplier", 1.25D, 0.0D, 100.0D);
        builder.pop();

        builder.push("pilgrims_thorn");
        enablePilgrimsThorn = builder
                .comment("If true, Pilgrim's Thorn can grant a movement speed bonus.")
                .define("enabled", true);
        pilgrimsThornMovementSpeedBonus = builder
                .comment("Additive movement speed bonus. 0.10 = +10% movement speed.")
                .defineInRange("movementSpeedBonus", 0.10D, 0.0D, 10.0D);
        builder.pop();

        builder.pop();

        builder.push("free_relics");

        builder.push("shroud_of_the_forsaken");
        enableShroudOfTheForsaken = builder
                .comment("If true, Shroud of the Forsaken can trigger when the player is brought low.")
                .define("enabled", true);
        shroudLowHealthThreshold = builder
                .comment("Trigger threshold in health points. 6.0 = 3 hearts.")
                .defineInRange("lowHealthThreshold", 6.0D, 0.0D, 20.0D);
        shroudDamageMultiplier = builder
                .comment("Damage multiplier applied to the triggering hit. 0.60 = take 40% less damage from that hit.")
                .defineInRange("damageMultiplier", 0.60D, 0.0D, 100.0D);
        shroudResistanceDurationTicks = builder
                .comment("Resistance effects duration in ticks.")
                .defineInRange("resistanceDurationTicks", 20 * 5, 0, 20 * 60 * 60);
        shroudResistanceAmplifier = builder
                .comment("Resistance amplifier. 1 = Resistance II.")
                .defineInRange("resistanceAmplifier", 1, 0, 255);
        shroudCooldownTicks = builder
                .comment("Cooldown in ticks.")
                .defineInRange("cooldownTicks", 20 * 60, 0, 20 * 60 * 60);
        builder.pop();

        builder.push("vulture_charm");
        enableVultureCharm = builder
                .comment("If true, Vulture Charm grants bonus XP from kills.")
                .define("enabled", true);
        vultureCharmXpMultiplier = builder
                .comment("XP multiplier from kills. 1.50 = 50% more XP.")
                .defineInRange("xpMultiplier", 1.50D, 1.0D, 100.0D);
        builder.pop();

        builder.push("hollow_eye");
        enableHollowEye = builder
                .comment("If true, Hollow Eye strips away blindness, darkness, and nearby invisibility.")
                .define("enabled", true);

        hollowEyeClearBlindness = builder
                .comment("If true, Hollow Eye removes Blindness from the wearer.")
                .define("clearBlindness", true);

        hollowEyeClearDarkness = builder
                .comment("If true, Hollow Eye removes Darkness from the wearer.")
                .define("clearDarkness", true);

        hollowEyeRevealInvisibleRadius = builder
                .comment("Radius in blocks for revealing invisible nearby entities.")
                .defineInRange("revealInvisibleRadius", 16.0D, 1.0D, 256.0D);

        hollowEyeRevealInvisibleDurationTicks = builder
                .comment("Glowing duration applied to invisible nearby entities.")
                .defineInRange("revealInvisibleDurationTicks", 60, 0, 20 * 60 * 60);

        hollowEyeRevealInvisibleIntervalTicks = builder
                .comment("How often to scan for invisible nearby entities.")
                .defineInRange("revealInvisibleIntervalTicks", 20, 1, 1200);
        builder.pop();

        builder.push("censer_of_ash");
        enableCenserOfAsh = builder
                .comment("If true, Censer of Ash boosts damage against burning enemies and marks them.")
                .define("enabled", true);
        censerOfAshDamageMultiplier = builder
                .comment("Damage multiplier against burning enemies. 1.20 = 20% more damage.")
                .defineInRange("damageMultiplier", 1.20D, 0.0D, 100.0D);
        censerOfAshGlowRadius = builder
                .comment("Radius in blocks for scanning burning entities.")
                .defineInRange("glowRadius", 12.0D, 1.0D, 256.0D);
        censerOfAshGlowDurationTicks = builder
                .comment("Glowing duration applied to burning entities, in ticks.")
                .defineInRange("glowDurationTicks", 40, 0, 20 * 60 * 60);
        censerOfAshScanIntervalTicks = builder
                .comment("How often to scan for burning entities, in ticks.")
                .defineInRange("scanIntervalTicks", 20, 1, 1200);
        builder.pop();

        builder.push("mourners_thread");
        enableMournersThread = builder
                .comment("If true, Mourner's Thread restores hunger and health on kill.")
                .define("enabled", true);
        mournersThreadFoodRestored = builder
                .comment("Food restored on kill.")
                .defineInRange("foodRestored", 4, 0, 20);
        mournersThreadSaturationRestored = builder
                .comment("Saturation restored on kill.")
                .defineInRange("saturationRestored", 0.6D, 0.0D, 20.0D);
        mournersThreadHealAmount = builder
                .comment("Health restored on kill. 2.0 = 1 heart.")
                .defineInRange("healAmount", 2.0D, 0.0D, 40.0D);
        builder.pop();

        builder.push("thornbound_carapace");
        enableThornboundCarapace = builder
                .comment("If true, Thornbound Carapace reflects incoming damage to attackers.")
                .define("enabled", true);
        thornboundCarapaceReflectPercent = builder
                .comment("Percent of incoming damage reflected. 0.20 = 20%.")
                .defineInRange("reflectPercent", 0.20D, 0.0D, 1.0D);
        builder.pop();

        builder.push("voidstep_band");
        enableVoidstepBand = builder
                .comment("If true, Voidstep Band negates fall damage.")
                .define("enabled", true);
        builder.pop();

        builder.push("executioners_coin");
        enableExecutionersCoin = builder
                .comment("If true, Executioner's Coin grants bonus damage against low-health targets.")
                .define("enabled", true);
        executionersCoinHealthThresholdPercent = builder
                .comment("Target health threshold as a percent of max health. 0.30 = 30%.")
                .defineInRange("healthThresholdPercent", 0.30D, 0.0D, 1.0D);
        executionersCoinDamageMultiplier = builder
                .comment("Damage multiplier against low-health targets. 1.30 = 30% more damage.")
                .defineInRange("damageMultiplier", 1.30D, 0.0D, 100.0D);
        builder.pop();

        builder.push("relic_of_the_last_breath");
        enableRelicOfTheLastBreath = builder
                .comment("If true, Relic of the Last Breath can prevent lethal damage.")
                .define("enabled", true);
        lastBreathPostTriggerHealth = builder
                .comment("Health to set after surviving the lethal hit. 1.0 = half a heart.")
                .defineInRange("postTriggerHealth", 1.0D, 0.1D, 20.0D);
        lastBreathAbsorptionDurationTicks = builder
                .comment("Absorption duration in ticks.")
                .defineInRange("absorptionDurationTicks", 20 * 10, 0, 20 * 60 * 60);
        lastBreathAbsorptionAmplifier = builder
                .comment("Absorption amplifier. 1 = Absorption II.")
                .defineInRange("absorptionAmplifier", 1, 0, 255);
        lastBreathRegenerationDurationTicks = builder
                .comment("Regeneration duration in ticks.")
                .defineInRange("regenerationDurationTicks", 20 * 6, 0, 20 * 60 * 60);
        lastBreathRegenerationAmplifier = builder
                .comment("Regeneration amplifier. 1 = Regeneration II.")
                .defineInRange("regenerationAmplifier", 1, 0, 255);
        lastBreathCooldownTicks = builder
                .comment("Cooldown in ticks.")
                .defineInRange("cooldownTicks", 20 * 300, 0, 20 * 60 * 60 * 24);
        builder.pop();

        builder.push("torch_of_gravesong");
        enableTorchOfGravesong = builder
                .comment("If true, Torch of Gravesong deals bonus damage to undead and grants Strength on undead kills.")
                .define("enabled", true);
        torchOfGravesongUndeadDamageMultiplier = builder
                .comment("Damage multiplier against undead. 1.25 = 25% more damage.")
                .defineInRange("undeadDamageMultiplier", 1.25D, 0.0D, 100.0D);
        torchOfGravesongStrengthDurationTicks = builder
                .comment("Strength duration in ticks after killing undead.")
                .defineInRange("strengthDurationTicks", 20 * 6, 0, 20 * 60 * 60);
        torchOfGravesongStrengthAmplifier = builder
                .comment("Strength amplifier. 0 = Strength I.")
                .defineInRange("strengthAmplifier", 0, 0, 255);
        builder.pop();

        builder.pop();

        builder.push("sloth_weapon");

        enableLethargicFlail = builder
                .comment("If true, Sloth: Lethargic Flail is enabled.")
                .define("enabled", true);

        slothWeaponMaxBrandedTicks = builder
                .comment("How many branded ticks are needed to reach 100% progress.")
                .defineInRange("slothWeaponMaxBrandedTicks", 360000L, 1L, Long.MAX_VALUE);

        slothWeaponRequiredBrandedPercent = builder
                .comment("Progress percent required to use Branded weapons. 0.995 = 99.5%")
                .defineInRange("slothWeaponRequiredBrandedPercent", 0.995D, 0.0D, 1.0D);

        lethargicFlailInventoryCheckIntervalTicks = builder
                .comment("How often to scan the player's inventory for the weapon burden effects.")
                .defineInRange("inventoryCheckIntervalTicks", 10, 1, 200);

        lethargicFlailInventoryMoveSpeedMultiplier = builder
                .comment("Movement speed modifier while the weapon exists anywhere in inventory. -0.50 = 50 percent slower.")
                .defineInRange("inventoryMoveSpeedMultiplier", -0.50D, -1.0D, 10.0D);

        lethargicFlailInventoryExhaustionPerSecond = builder
                .comment("Extra exhaustion added per second while the weapon exists anywhere in inventory.")
                .defineInRange("inventoryExhaustionPerSecond", 0.05D, 0.0D, 10.0D);
        enableCreativeBrandedTimeBypass = builder
                .comment("If true, creative players may always use the Lethargic Flail.")
                .define("enableCreativeBrandedTimeBypass", true);

        lethargicFlailLazinessDurationTicks = builder
                .comment("Base duration of Laziness stacks in ticks.")
                .defineInRange("lazinessDurationTicks", 20 * 60, 0, 20 * 60 * 60);

        lethargicFlailSweepLazinessStacks = builder
                .comment("How many Laziness stacks are added when using the sweeping attack.")
                .defineInRange("sweepLazinessStacks", 3, 0, 64);

        lethargicFlailSweepDamageMultiplier = builder
                .comment("Damage multiplier for the sweeping attack. 0.75 = 75% damage.")
                .defineInRange("sweepDamageMultiplier", 0.75D, 0.0D, 100.0D);

        lethargicFlailSweepRadius = builder
                .comment("Radius in blocks for the sweeping attack.")
                .defineInRange("sweepRadius", 10.0D, 1.0D, 128.0D);

        lethargicFlailSweepEnemyDebuffDurationTicks = builder
                .comment("Duration of the enemy debuff applied by the sweeping attack.")
                .defineInRange("sweepEnemyDebuffDurationTicks", 20 * 10, 0, 20 * 60 * 60);

        lethargicFlailCubeCooldownTicks = builder
                .comment("Cooldown in ticks for the thrown cube attack.")
                .defineInRange("cubeCooldownTicks", 20 * 30, 0, 20 * 60 * 60);

        lethargicFlailCubeStunDurationTicks = builder
                .comment("Stun duration in ticks for the thrown cube attack.")
                .defineInRange("cubeStunDurationTicks", 20 * 10, 0, 20 * 60 * 60);

        lethargicFlailCubeDamageMultiplier = builder
                .comment("Damage multiplier for the thrown cube. 0.75 = 75% damage.")
                .defineInRange("cubeDamageMultiplier", 0.75D, 0.0D, 100.0D);

        builder.pop();

        builder.push("pride_weapon");

        enableVanitysEdge = builder
                .comment("If true, Pride: Vanity's Edge is enabled.")
                .define("enabled", true);

        vanitysEdgeMaintenanceIntervalTicks = builder
                .comment("How often, in ticks, Vanity's Edge updates its dynamic state.")
                .defineInRange("maintenanceIntervalTicks", 10, 1, 200);

        vanitysEdgeAllyAuraIntervalTicks = builder
                .comment("How often, in ticks, Vanity's Edge applies its ally suppression aura while held.")
                .defineInRange("allyAuraIntervalTicks", 20, 1, 200);

        vanitysEdgeHighHealthAttackSpeedThreshold = builder
                .comment("Health percentage threshold for the high health attack speed bonus. 0.80 = above 80 percent health.")
                .defineInRange("highHealthAttackSpeedThreshold", 0.80D, 0.0D, 1.0D);

        vanitysEdgeHighHealthAttackSpeedBonus = builder
                .comment("Attack speed bonus while above the high health threshold. 0.10 = +10 percent.")
                .defineInRange("highHealthAttackSpeedBonus", 0.10D, 0.0D, 10.0D);

        vanitysEdgeHighHealthBonusDamagePerTwoHp = builder
                .comment("Bonus damage granted for every 2 HP above half health.")
                .defineInRange("highHealthBonusDamagePerTwoHp", 2.0D, 0.0D, 100.0D);

        vanitysEdgeAllySuppressionRadius = builder
                .comment("Radius in blocks for suppressing allies, companions, and pets while the weapon is held.")
                .defineInRange("allySuppressionRadius", 12.0D, 1.0D, 128.0D);

        vanitysEdgeAllySuppressionDurationTicks = builder
                .comment("Duration of the ally suppression debuff in ticks.")
                .defineInRange("allySuppressionDurationTicks", 40, 0, 20 * 60 * 60);

        vanitysEdgeNonKillSelfDamage = builder
                .comment("Health points lost if a successful hit does not kill the target. 1.0 = half a heart.")
                .defineInRange("nonKillSelfDamage", 1.0D, 0.0D, 20.0D);

        vanitysEdgeKillStackDurationTicks = builder
                .comment("Duration of each kill-based attack speed stack in ticks.")
                .defineInRange("killStackDurationTicks", 20 * 60 * 3, 0, 20 * 60 * 60);

        vanitysEdgeKillStackAttackSpeedBonus = builder
                .comment("Attack speed granted by each kill stack.")
                .defineInRange("killStackAttackSpeedBonus", 0.10D, 0.0D, 10.0D);

        vanitysEdgeArmorPenaltyPerTwoArmor = builder
                .comment("Attack damage lost for every 2 armor points while the weapon exists in inventory.")
                .defineInRange("armorPenaltyPerTwoArmor", 1.0D, 0.0D, 100.0D);

        vanitysEdgeMidHealthUpperThreshold = builder
                .comment("Upper health threshold for the mid-health weakness band. 0.50 = 50 percent.")
                .defineInRange("midHealthUpperThreshold", 0.50D, 0.0D, 1.0D);

        vanitysEdgeMidHealthLowerThreshold = builder
                .comment("Lower health threshold for the mid-health weakness band. 0.30 = 30 percent.")
                .defineInRange("midHealthLowerThreshold", 0.0D, 0.0D, 1.0D);

        vanitysEdgeMidHealthDamagePenaltyMultiplier = builder
                .comment("Damage penalty multiplier applied in the mid-health band. -0.50 = 50 percent less damage.")
                .defineInRange("midHealthDamagePenaltyMultiplier", -0.50D, -1.0D, 10.0D);

        vanitysEdgeMidHealthAttackSpeedPenaltyMultiplier = builder
                .comment("Attack speed penalty multiplier applied in the mid-health band. -0.50 = 50 percent less attack speed.")
                .defineInRange("midHealthAttackSpeedPenaltyMultiplier", -0.50D, -1.0D, 10.0D);

        vanitysEdgeLowHealthThreshold = builder
                .comment("Health percentage threshold for rolling Losing Pride or Bravery. 0.30 = below 30 percent.")
                .defineInRange("lowHealthThreshold", 0.30D, 0.0D, 1.0D);

        vanitysEdgeLosingPrideChance = builder
                .comment("Chance to receive Losing Pride when rolling below the low health threshold. 0.60 = 60 percent.")
                .defineInRange("losingPrideChance", 0.60D, 0.0D, 1.0D);

        vanitysEdgeOutcomeDurationTicks = builder
                .comment("Duration of Losing Pride or Bravery in ticks.")
                .defineInRange("outcomeDurationTicks", 20 * 60 * 10, 0, 20 * 60 * 60 * 24);

        vanitysEdgeLosingPrideHealingMultiplier = builder
                .comment("Incoming healing multiplier while Losing Pride is active. 0.80 = 20 percent less healing.")
                .defineInRange("losingPrideHealingMultiplier", 0.80D, 0.0D, 10.0D);

        vanitysEdgeBraveryRegenerationAmplifier = builder
                .comment("Regeneration amplifier granted by Bravery. 4 = Regeneration V.")
                .defineInRange("braveryRegenerationAmplifier", 4, 0, 255);

        builder.pop();

        builder.push("envy_weapon");

        enableCovetfang = builder
                .comment("If true, Envy: Covetfang is enabled.")
                .define("enabled", true);

        covetfangMaintenanceIntervalTicks = builder
                .comment("How often, in ticks, Covetfang updates its dynamic state.")
                .defineInRange("maintenanceIntervalTicks", 10, 1, 200);

        covetfangClaimDurationTicks = builder
                .comment("How long a claimed Coveted Target lasts, in ticks.")
                .defineInRange("claimDurationTicks", 20 * 20, 1, 20 * 60 * 60);

        covetfangClaimCooldownTicks = builder
                .comment("Cooldown in ticks after claiming a Coveted Target.")
                .defineInRange("claimCooldownTicks", 20 * 8, 0, 20 * 60 * 60);

        covetfangClaimTrackRadius = builder
                .comment("Maximum range in blocks for tracking the claimed Coveted Target.")
                .defineInRange("claimTrackRadius", 48.0D, 1.0D, 256.0D);

        covetfangCovetedGlowDurationTicks = builder
                .comment("Glowing duration applied to the Coveted Target.")
                .defineInRange("covetedGlowDurationTicks", 40, 0, 20 * 60 * 60);

        covetfangBonusDamagePerEnvyScore = builder
                .comment("Bonus damage granted per envy score category matched against the target.")
                .defineInRange("bonusDamagePerEnvyScore", 2.0D, 0.0D, 100.0D);

        covetfangCovetedTargetExtraDamage = builder
                .comment("Extra bonus damage dealt to a claimed Coveted Target if it is genuinely superior to you.")
                .defineInRange("covetedTargetExtraDamage", 2.0D, 0.0D, 100.0D);

        covetfangBonusDamageCap = builder
                .comment("Maximum total bonus damage Covetfang can gain from envy scoring.")
                .defineInRange("bonusDamageCap", 12.0D, 0.0D, 1000.0D);

        covetfangBaseNonKillSelfDamage = builder
                .comment("Health points lost if a successful hit does not kill the target. 1.0 = half a heart.")
                .defineInRange("baseNonKillSelfDamage", 1.0D, 0.0D, 20.0D);

        covetfangHollowComparisonExtraSelfDamage = builder
                .comment("Additional self-damage when striking something beneath you that gives no envy score and is not your claimed target.")
                .defineInRange("hollowComparisonExtraSelfDamage", 1.0D, 0.0D, 20.0D);

        covetfangStolenStrengthDurationTicks = builder
                .comment("Duration in ticks for stolen strengths gained from slaying a claimed target.")
                .defineInRange("stolenStrengthDurationTicks", 20 * 45, 0, 20 * 60 * 60 * 24);

        covetfangStolenVitalityMaxHealthBonus = builder
                .comment("Bonus max health granted when Covetfang steals vitality.")
                .defineInRange("stolenVitalityMaxHealthBonus", 4.0D, 0.0D, 100.0D);

        covetfangStolenPlatingArmorBonus = builder
                .comment("Bonus armor granted when Covetfang steals plating.")
                .defineInRange("stolenPlatingArmorBonus", 4.0D, 0.0D, 100.0D);

        covetfangStolenSwiftnessMoveSpeedBonus = builder
                .comment("Movement speed bonus granted when Covetfang steals swiftness. 0.15 = +15 percent.")
                .defineInRange("stolenSwiftnessMoveSpeedBonus", 0.15D, 0.0D, 10.0D);

        covetfangStolenFerocityAttackSpeedBonus = builder
                .comment("Attack speed bonus granted when Covetfang steals ferocity.")
                .defineInRange("stolenFerocityAttackSpeedBonus", 0.20D, 0.0D, 10.0D);

        covetfangBeneficialEffectAttackDamagePenalty = builder
                .comment("Attack damage lost for each beneficial effect on the wielder while Covetfang exists in inventory.")
                .defineInRange("beneficialEffectAttackDamagePenalty", 1.0D, 0.0D, 100.0D);

        covetfangLowHealthThreshold = builder
                .comment("Health percentage threshold for Covetfang's desperate or hopeless comparison effects. 0.30 = below 30 percent.")
                .defineInRange("lowHealthThreshold", 0.30D, 0.0D, 1.0D);

        covetfangDesperateWantScanRadius = builder
                .comment("Radius in blocks for checking whether something stronger is nearby while low on health.")
                .defineInRange("desperateWantScanRadius", 16.0D, 1.0D, 256.0D);

        covetfangDesperateWantHealOnHit = builder
                .comment("Health restored on successful hit while below the low health threshold and something stronger is nearby. 1.0 = half a heart.")
                .defineInRange("desperateWantHealOnHit", 1.0D, 0.0D, 20.0D);

        covetfangDesperateWantMoveSpeedBonus = builder
                .comment("Movement speed bonus while below the low health threshold and something stronger is nearby. 0.20 = +20 percent.")
                .defineInRange("desperateWantMoveSpeedBonus", 0.20D, 0.0D, 10.0D);

        covetfangHopelessComparisonDamagePenalty = builder
                .comment("Damage penalty while below the low health threshold and nothing worthy of envy is nearby. -0.35 = 35 percent less damage.")
                .defineInRange("hopelessComparisonDamagePenalty", -0.35D, -1.0D, 10.0D);

        covetfangHopelessComparisonMoveSpeedPenalty = builder
                .comment("Movement speed penalty while below the low health threshold and nothing worthy of envy is nearby. -0.20 = 20 percent slower.")
                .defineInRange("hopelessComparisonMoveSpeedPenalty", -0.20D, -1.0D, 10.0D);

        covetfangCovetedPursuitMoveSpeedBonus = builder
                .comment("Movement speed bonus while actively pursuing a claimed Coveted Target. 0.10 = +10 percent.")
                .defineInRange("covetedPursuitMoveSpeedBonus", 0.10D, 0.0D, 10.0D);

        builder.pop();

        builder.push("bearer_curios");

        builder.push("oathbound_reliquary");
        enableOathboundReliquary = builder
                .comment("If true, Oathbound Reliquary is enabled.")
                .define("enabled", true);
        oathboundReliquaryBonusCharmSlots = builder
                .comment("Additional charm slots granted while the Reliquary is worn.")
                .defineInRange("bonusCharmSlots", 2, 0, 16);
        oathboundReliquaryLowHealthThreshold = builder
                .comment("Health threshold in health points for the emergency regeneration effect.")
                .defineInRange("lowHealthThreshold", 10.0D, 0.0D, 40.0D);
        oathboundReliquaryAbsorptionDurationTicks = builder
                .comment("Absorption refresh duration in ticks.")
                .defineInRange("absorptionDurationTicks", 220, 0, 20 * 60 * 60);
        oathboundReliquaryAbsorptionAmplifier = builder
                .comment("Absorption amplifier. 0 = Absorption I.")
                .defineInRange("absorptionAmplifier", 0, 0, 255);
        oathboundReliquaryRegenerationDurationTicks = builder
                .comment("Regeneration duration while below the low health threshold.")
                .defineInRange("regenerationDurationTicks", 80, 0, 20 * 60 * 60);
        oathboundReliquaryRegenerationAmplifier = builder
                .comment("Regeneration amplifier. 0 = Regeneration I.")
                .defineInRange("regenerationAmplifier", 0, 0, 255);
        oathboundReliquaryHungerCurioThreshold = builder
                .comment("How many of the newer bearer curios must be equipped before the Reliquary adds Hunger.")
                .defineInRange("hungerCurioThreshold", 3, 0, 16);
        oathboundReliquaryHungerDurationTicks = builder
                .comment("Hunger duration in ticks while overburdened.")
                .defineInRange("hungerDurationTicks", 120, 0, 20 * 60 * 60);
        oathboundReliquaryHungerAmplifier = builder
                .comment("Hunger amplifier. 0 = Hunger I.")
                .defineInRange("hungerAmplifier", 0, 0, 255);
        builder.pop();
        builder.push("brandkeepers_mercy");
        enableBrandkeepersMercy = builder
                .comment("If true, Brandkeeper's Mercy can suppress the Provocation curse for a nearly fully attuned bearer.")
                .define("enabled", true);
        builder.pop();

        builder.push("chain_of_the_penitent");
        enableChainOfThePenitent = builder
                .comment("If true, Chain of the Penitent stores damage taken as Penance.")
                .define("enabled", true);
        chainOfThePenitentPenanceGainMultiplier = builder
                .comment("How much Penance is gained from damage taken.")
                .defineInRange("penanceGainMultiplier", 1.5D, 0.0D, 100.0D);
        chainOfThePenitentMaxPenance = builder
                .comment("Maximum stored Penance.")
                .defineInRange("maxPenance", 40.0D, 0.0D, 1000.0D);
        chainOfThePenitentMaxBonusDamage = builder
                .comment("Maximum bonus damage added when consuming stored Penance.")
                .defineInRange("maxBonusDamage", 12.0D, 0.0D, 1000.0D);
        chainOfThePenitentMarkDurationTicks = builder
                .comment("Glowing duration applied to the struck target when Penance is consumed.")
                .defineInRange("markDurationTicks", 100, 0, 20 * 60 * 60);
        builder.pop();

        builder.push("eye_of_the_sleepless_witness");
        enableEyeOfTheSleeplessWitness = builder
                .comment("If true, Eye of the Sleepless Witness is enabled.")
                .define("enabled", true);
        eyeOfTheSleeplessWitnessNightVision = builder
                .comment("If true, the bearer receives infinite Night Vision while worn.")
                .define("nightVision", true);
        eyeOfTheSleeplessWitnessRevealRadiusMoving = builder
                .comment("Nearby hostile reveal radius while moving.")
                .defineInRange("revealRadiusMoving", 14.0D, 1.0D, 256.0D);
        eyeOfTheSleeplessWitnessRevealRadiusStill = builder
                .comment("Nearby hostile reveal radius while standing still.")
                .defineInRange("revealRadiusStill", 24.0D, 1.0D, 256.0D);
        eyeOfTheSleeplessWitnessGlowDurationTicks = builder
                .comment("Glowing duration applied to nearby hostiles.")
                .defineInRange("glowDurationTicks", 60, 0, 20 * 60 * 60);
        eyeOfTheSleeplessWitnessOakskinDurationTicks = builder
                .comment("Oakskin Resistance duration in ticks.")
                .defineInRange("oakskinDurationTicks", 80, 0, 20 * 60 * 60);
        eyeOfTheSleeplessWitnessOakskinAmplifier = builder
                .comment("Oakskin Resistance amplifier. 0 = Resistance I.")
                .defineInRange("oakskinAmplifier", 0, 0, 255);
        eyeOfTheSleeplessWitnessOakskinMovementThreshold = builder
                .comment("Maximum horizontal movement squared for Oakskin to count as 'holding your ground'.")
                .defineInRange("oakskinMovementThreshold", 0.02D, 0.0D, 4.0D);
        builder.pop();

        builder.push("censer_of_hollow_prayer");
        enableCenserOfHollowPrayer = builder
                .comment("If true, Censer of Hollow Prayer is enabled.")
                .define("enabled", true);
        censerOfHollowPrayerRadius = builder
                .comment("Effect radius in blocks around the wearer.")
                .defineInRange("radius", 5.0D, 1.0D, 256.0D);
        censerOfHollowPrayerWeaknessDurationTicks = builder
                .comment("Weakness duration applied to nearby monsters.")
                .defineInRange("weaknessDurationTicks", 80, 0, 20 * 60 * 60);
        censerOfHollowPrayerWeaknessAmplifier = builder
                .comment("Weakness amplifier. 0 = Weakness I.")
                .defineInRange("weaknessAmplifier", 0, 0, 255);
        censerOfHollowPrayerSlownessDurationTicks = builder
                .comment("Slowness duration applied to nearby monsters.")
                .defineInRange("slownessDurationTicks", 80, 0, 20 * 60 * 60);
        censerOfHollowPrayerSlownessAmplifier = builder
                .comment("Slowness amplifier. 0 = Slowness I.")
                .defineInRange("slownessAmplifier", 0, 0, 255);
        censerOfHollowPrayerRegenerationDurationTicks = builder
                .comment("Regeneration duration granted while enemies are nearby.")
                .defineInRange("regenerationDurationTicks", 80, 0, 20 * 60 * 60);
        censerOfHollowPrayerRegenerationAmplifier = builder
                .comment("Regeneration amplifier. 0 = Regeneration I.")
                .defineInRange("regenerationAmplifier", 0, 0, 255);
        censerOfHollowPrayerCrowdThreshold = builder
                .comment("How many nearby monsters count as a crowd.")
                .defineInRange("crowdThreshold", 3, 1, 64);
        censerOfHollowPrayerAbsorptionDurationTicks = builder
                .comment("Absorption duration granted in crowds.")
                .defineInRange("absorptionDurationTicks", 100, 0, 20 * 60 * 60);
        censerOfHollowPrayerAbsorptionAmplifier = builder
                .comment("Absorption amplifier in crowds. 1 = Absorption II.")
                .defineInRange("absorptionAmplifier", 1, 0, 255);
        builder.pop();

        builder.push("nail_of_the_first_martyr");
        enableNailOfTheFirstMartyr = builder
                .comment("If true, Nail of the First Martyr is enabled.")
                .define("enabled", true);
        nailOfTheFirstMartyrClaimDurationTicks = builder
                .comment("Martyr's Claim stack duration in ticks.")
                .defineInRange("claimDurationTicks", 120, 0, 20 * 60 * 60);
        nailOfTheFirstMartyrMaxStacks = builder
                .comment("How many Martyr's Claim stacks are needed before the target becomes Judged.")
                .defineInRange("maxStacks", 5, 1, 64);
        nailOfTheFirstMartyrJudgedDurationTicks = builder
                .comment("Judged duration in ticks.")
                .defineInRange("judgedDurationTicks", 200, 0, 20 * 60 * 60);
        nailOfTheFirstMartyrHasteDurationTicks = builder
                .comment("Haste duration granted when slaying a Judged target.")
                .defineInRange("hasteDurationTicks", 200, 0, 20 * 60 * 60);
        nailOfTheFirstMartyrHasteAmplifier = builder
                .comment("Haste amplifier. 1 = Haste II.")
                .defineInRange("hasteAmplifier", 1, 0, 255);
        nailOfTheFirstMartyrAbsorptionDurationTicks = builder
                .comment("Absorption duration granted when slaying a Judged target.")
                .defineInRange("absorptionDurationTicks", 200, 0, 20 * 60 * 60);
        nailOfTheFirstMartyrAbsorptionAmplifier = builder
                .comment("Absorption amplifier. 1 = Absorption II.")
                .defineInRange("absorptionAmplifier", 1, 0, 255);
        builder.pop();

        builder.pop();
        builder.push("basic_rings");

        builder.push("gold_ring");
        enableGoldRing = builder
                .comment("If true, Gold Ring is enabled.")
                .define("enabled", true);
        goldRingArmorBonus = builder
                .comment("Armor bonus granted by Gold Ring.")
                .defineInRange("armorBonus", 1.0D, 0.0D, 100.0D);
        builder.pop();

        builder.push("cyan_ring");
        enableCyanRing = builder
                .comment("If true, Cyan Ring is enabled.")
                .define("enabled", true);
        cyanRingArmorBonus = builder
                .comment("Armor bonus granted by Cyan Ring.")
                .defineInRange("armorBonus", 2.0D, 0.0D, 100.0D);
        builder.pop();

        builder.push("nebula_ring");
        enableNebulaRing = builder
                .comment("If true, Nebula Ring is enabled.")
                .define("enabled", true);
        nebulaRingArmorBonus = builder
                .comment("Armor bonus granted by Nebula Ring.")
                .defineInRange("armorBonus", 3.0D, 0.0D, 100.0D);
        nebulaRingExtraRingSlots = builder
                .comment("Additional ring slots granted by Nebula Ring while worn.")
                .defineInRange("extraRingSlots", 1, 0, 8);
        enableFracturedRing = builder
                .comment("If true, Fractured Ash Ring is enabled.")
                .define("enabled", true);
        FracturedRingExtraRingSlots = builder
                .comment("Additional ring slots granted by Fractured Ash Ring while worn.")
                .defineInRange("extraRingSlots", 1, 0, 8);
        builder.pop();

        builder.pop();
    }

    public static boolean enableFrailty() { return CONFIG.enableFrailty.get(); }
    public static boolean enableProvocation() { return CONFIG.enableProvocation.get(); }
    public static boolean enableShatteredPlate() { return CONFIG.enableShatteredPlate.get(); }
    public static boolean enableBloodToll() { return CONFIG.enableBloodToll.get(); }
    public static boolean enableLivingEmber() { return CONFIG.enableLivingEmber.get(); }
    public static boolean enableSoulFracture() { return CONFIG.enableSoulFracture.get(); }
    public static boolean enableWakefulDoom() { return CONFIG.enableWakefulDoom.get(); }

    public static double incomingDamageMultiplier() { return CONFIG.incomingDamageMultiplier.get(); }
    public static double armorEffectiveness() { return CONFIG.armorEffectiveness.get(); }
    public static double bloodTollHealthCost() { return CONFIG.bloodTollHealthCost.get(); }
    public static int minFireTicks() { return CONFIG.minFireTicks.get(); }
    public static int neutralAggroInterval() { return CONFIG.neutralAggroInterval.get(); }
    public static double neutralAggroRange() { return CONFIG.neutralAggroRange.get(); }
    public static int respawnWeaknessDurationTicks() { return CONFIG.respawnWeaknessDurationTicks.get(); }
    public static int respawnWeaknessAmplifier() { return CONFIG.respawnWeaknessAmplifier.get(); }
    public static int respawnSlownessDurationTicks() { return CONFIG.respawnSlownessDurationTicks.get(); }
    public static int respawnSlownessAmplifier() { return CONFIG.respawnSlownessAmplifier.get(); }

    public static boolean enableLootingBlessing() { return CONFIG.enableLootingBlessing.get(); }
    public static boolean enableFortuneBlessing() { return CONFIG.enableFortuneBlessing.get(); }
    public static boolean enableXpBlessing() { return CONFIG.enableXpBlessing.get(); }
    public static boolean enableEnchantingBlessing() { return CONFIG.enableEnchantingBlessing.get(); }
    public static boolean enableAttackSpeedBlessing() { return CONFIG.enableAttackSpeedBlessing.get(); }
    public static boolean enableAbsorptionBlessing() { return CONFIG.enableAbsorptionBlessing.get(); }
    public static int lootingBonus() { return CONFIG.lootingBonus.get(); }
    public static int fortuneBonus() { return CONFIG.fortuneBonus.get(); }
    public static double xpMultiplier() { return CONFIG.xpMultiplier.get(); }
    public static int enchantingPowerBonus() { return CONFIG.enchantingPowerBonus.get(); }
    public static double attackSpeedBonus() { return CONFIG.attackSpeedBonus.get(); }
    public static double absorptionThreshold() { return CONFIG.absorptionThreshold.get(); }
    public static double absorptionAmount() { return CONFIG.absorptionAmount.get(); }

    public static boolean enableAshenNail() { return CONFIG.enableAshenNail.get(); }
    public static double ashenNailBurningDamageMultiplier() { return CONFIG.ashenNailBurningDamageMultiplier.get(); }

    public static boolean enableGravebellLocket() { return CONFIG.enableGravebellLocket.get(); }
    public static int gravebellLocketSpeedDurationTicks() { return CONFIG.gravebellLocketSpeedDurationTicks.get(); }
    public static int gravebellLocketSpeedAmplifier() { return CONFIG.gravebellLocketSpeedAmplifier.get(); }
    public static int gravebellLocketRegenerationDurationTicks() { return CONFIG.gravebellLocketRegenerationDurationTicks.get(); }
    public static int gravebellLocketRegenerationAmplifier() { return CONFIG.gravebellLocketRegenerationAmplifier.get(); }

    public static boolean enableHuntersSigil() { return CONFIG.enableHuntersSigil.get(); }
    public static double huntersSigilDamageMultiplier() { return CONFIG.huntersSigilDamageMultiplier.get(); }

    public static boolean enablePilgrimsThorn() { return CONFIG.enablePilgrimsThorn.get(); }
    public static double pilgrimsThornMovementSpeedBonus() { return CONFIG.pilgrimsThornMovementSpeedBonus.get(); }

    public static boolean enableShroudOfTheForsaken() { return CONFIG.enableShroudOfTheForsaken.get(); }
    public static double shroudLowHealthThreshold() { return CONFIG.shroudLowHealthThreshold.get(); }
    public static double shroudDamageMultiplier() { return CONFIG.shroudDamageMultiplier.get(); }
    public static int shroudResistanceDurationTicks() { return CONFIG.shroudResistanceDurationTicks.get(); }
    public static int shroudResistanceAmplifier() { return CONFIG.shroudResistanceAmplifier.get(); }
    public static int shroudCooldownTicks() { return CONFIG.shroudCooldownTicks.get(); }

    public static boolean enableVultureCharm() { return CONFIG.enableVultureCharm.get(); }
    public static double vultureCharmXpMultiplier() { return CONFIG.vultureCharmXpMultiplier.get(); }

    public static boolean enableHollowEye() { return CONFIG.enableHollowEye.get(); }

    public static boolean enableCenserOfAsh() { return CONFIG.enableCenserOfAsh.get(); }
    public static double censerOfAshDamageMultiplier() { return CONFIG.censerOfAshDamageMultiplier.get(); }
    public static double censerOfAshGlowRadius() { return CONFIG.censerOfAshGlowRadius.get(); }
    public static int censerOfAshGlowDurationTicks() { return CONFIG.censerOfAshGlowDurationTicks.get(); }
    public static int censerOfAshScanIntervalTicks() { return CONFIG.censerOfAshScanIntervalTicks.get(); }

    public static boolean enableMournersThread() { return CONFIG.enableMournersThread.get(); }
    public static int mournersThreadFoodRestored() { return CONFIG.mournersThreadFoodRestored.get(); }
    public static double mournersThreadSaturationRestored() { return CONFIG.mournersThreadSaturationRestored.get(); }
    public static double mournersThreadHealAmount() { return CONFIG.mournersThreadHealAmount.get(); }

    public static boolean enableThornboundCarapace() { return CONFIG.enableThornboundCarapace.get(); }
    public static double thornboundCarapaceReflectPercent() { return CONFIG.thornboundCarapaceReflectPercent.get(); }

    public static boolean enableVoidstepBand() { return CONFIG.enableVoidstepBand.get(); }

    public static boolean enableExecutionersCoin() { return CONFIG.enableExecutionersCoin.get(); }
    public static double executionersCoinHealthThresholdPercent() { return CONFIG.executionersCoinHealthThresholdPercent.get(); }
    public static double executionersCoinDamageMultiplier() { return CONFIG.executionersCoinDamageMultiplier.get(); }

    public static boolean enableRelicOfTheLastBreath() { return CONFIG.enableRelicOfTheLastBreath.get(); }
    public static double lastBreathPostTriggerHealth() { return CONFIG.lastBreathPostTriggerHealth.get(); }
    public static int lastBreathAbsorptionDurationTicks() { return CONFIG.lastBreathAbsorptionDurationTicks.get(); }
    public static int lastBreathAbsorptionAmplifier() { return CONFIG.lastBreathAbsorptionAmplifier.get(); }
    public static int lastBreathRegenerationDurationTicks() { return CONFIG.lastBreathRegenerationDurationTicks.get(); }
    public static int lastBreathRegenerationAmplifier() { return CONFIG.lastBreathRegenerationAmplifier.get(); }
    public static int lastBreathCooldownTicks() { return CONFIG.lastBreathCooldownTicks.get(); }

    public static boolean enableTorchOfGravesong() { return CONFIG.enableTorchOfGravesong.get(); }
    public static double torchOfGravesongUndeadDamageMultiplier() { return CONFIG.torchOfGravesongUndeadDamageMultiplier.get(); }
    public static int torchOfGravesongStrengthDurationTicks() { return CONFIG.torchOfGravesongStrengthDurationTicks.get(); }
    public static int torchOfGravesongStrengthAmplifier() { return CONFIG.torchOfGravesongStrengthAmplifier.get(); }

    public static boolean enableLethargicFlail() { return CONFIG.enableLethargicFlail.get(); }
    public static int lethargicFlailInventoryCheckIntervalTicks() { return CONFIG.lethargicFlailInventoryCheckIntervalTicks.get(); }
    public static double lethargicFlailInventoryMoveSpeedMultiplier() { return CONFIG.lethargicFlailInventoryMoveSpeedMultiplier.get(); }
    public static double lethargicFlailInventoryExhaustionPerSecond() { return CONFIG.lethargicFlailInventoryExhaustionPerSecond.get(); }
    public static long slothWeaponMaxBrandedTicks() {return CONFIG.slothWeaponMaxBrandedTicks.get();}
    public static double slothWeaponRequiredBrandedPercent() {return CONFIG.slothWeaponRequiredBrandedPercent.get();}
    public static boolean hollowEyeClearBlindness() { return CONFIG.hollowEyeClearBlindness.get(); }
    public static boolean hollowEyeClearDarkness() { return CONFIG.hollowEyeClearDarkness.get(); }
    public static double hollowEyeRevealInvisibleRadius() { return CONFIG.hollowEyeRevealInvisibleRadius.get(); }
    public static int hollowEyeRevealInvisibleDurationTicks() { return CONFIG.hollowEyeRevealInvisibleDurationTicks.get(); }
    public static int hollowEyeRevealInvisibleIntervalTicks() { return CONFIG.hollowEyeRevealInvisibleIntervalTicks.get(); }

    public static boolean enableOathboundReliquary() { return CONFIG.enableOathboundReliquary.get(); }
    public static int oathboundReliquaryBonusCharmSlots() { return CONFIG.oathboundReliquaryBonusCharmSlots.get(); }
    public static double oathboundReliquaryLowHealthThreshold() { return CONFIG.oathboundReliquaryLowHealthThreshold.get(); }
    public static int oathboundReliquaryAbsorptionDurationTicks() { return CONFIG.oathboundReliquaryAbsorptionDurationTicks.get(); }
    public static int oathboundReliquaryAbsorptionAmplifier() { return CONFIG.oathboundReliquaryAbsorptionAmplifier.get(); }
    public static int oathboundReliquaryRegenerationDurationTicks() { return CONFIG.oathboundReliquaryRegenerationDurationTicks.get(); }
    public static int oathboundReliquaryRegenerationAmplifier() { return CONFIG.oathboundReliquaryRegenerationAmplifier.get(); }
    public static int oathboundReliquaryHungerCurioThreshold() { return CONFIG.oathboundReliquaryHungerCurioThreshold.get(); }
    public static int oathboundReliquaryHungerDurationTicks() { return CONFIG.oathboundReliquaryHungerDurationTicks.get(); }
    public static int oathboundReliquaryHungerAmplifier() { return CONFIG.oathboundReliquaryHungerAmplifier.get(); }

    public static boolean enableChainOfThePenitent() { return CONFIG.enableChainOfThePenitent.get(); }
    public static double chainOfThePenitentPenanceGainMultiplier() { return CONFIG.chainOfThePenitentPenanceGainMultiplier.get(); }
    public static double chainOfThePenitentMaxPenance() { return CONFIG.chainOfThePenitentMaxPenance.get(); }
    public static double chainOfThePenitentMaxBonusDamage() { return CONFIG.chainOfThePenitentMaxBonusDamage.get(); }
    public static int chainOfThePenitentMarkDurationTicks() { return CONFIG.chainOfThePenitentMarkDurationTicks.get(); }

    public static boolean enableEyeOfTheSleeplessWitness() { return CONFIG.enableEyeOfTheSleeplessWitness.get(); }
    public static boolean eyeOfTheSleeplessWitnessNightVision() { return CONFIG.eyeOfTheSleeplessWitnessNightVision.get(); }
    public static double eyeOfTheSleeplessWitnessRevealRadiusMoving() { return CONFIG.eyeOfTheSleeplessWitnessRevealRadiusMoving.get(); }
    public static double eyeOfTheSleeplessWitnessRevealRadiusStill() { return CONFIG.eyeOfTheSleeplessWitnessRevealRadiusStill.get(); }
    public static int eyeOfTheSleeplessWitnessGlowDurationTicks() { return CONFIG.eyeOfTheSleeplessWitnessGlowDurationTicks.get(); }
    public static int eyeOfTheSleeplessWitnessOakskinDurationTicks() { return CONFIG.eyeOfTheSleeplessWitnessOakskinDurationTicks.get(); }
    public static int eyeOfTheSleeplessWitnessOakskinAmplifier() { return CONFIG.eyeOfTheSleeplessWitnessOakskinAmplifier.get(); }
    public static double eyeOfTheSleeplessWitnessOakskinMovementThreshold() { return CONFIG.eyeOfTheSleeplessWitnessOakskinMovementThreshold.get(); }

    public static boolean enableCenserOfHollowPrayer() { return CONFIG.enableCenserOfHollowPrayer.get(); }
    public static double censerOfHollowPrayerRadius() { return CONFIG.censerOfHollowPrayerRadius.get(); }
    public static int censerOfHollowPrayerWeaknessDurationTicks() { return CONFIG.censerOfHollowPrayerWeaknessDurationTicks.get(); }
    public static int censerOfHollowPrayerWeaknessAmplifier() { return CONFIG.censerOfHollowPrayerWeaknessAmplifier.get(); }
    public static int censerOfHollowPrayerSlownessDurationTicks() { return CONFIG.censerOfHollowPrayerSlownessDurationTicks.get(); }
    public static int censerOfHollowPrayerSlownessAmplifier() { return CONFIG.censerOfHollowPrayerSlownessAmplifier.get(); }
    public static int censerOfHollowPrayerRegenerationDurationTicks() { return CONFIG.censerOfHollowPrayerRegenerationDurationTicks.get(); }
    public static int censerOfHollowPrayerRegenerationAmplifier() { return CONFIG.censerOfHollowPrayerRegenerationAmplifier.get(); }
    public static int censerOfHollowPrayerCrowdThreshold() { return CONFIG.censerOfHollowPrayerCrowdThreshold.get(); }
    public static int censerOfHollowPrayerAbsorptionDurationTicks() { return CONFIG.censerOfHollowPrayerAbsorptionDurationTicks.get(); }
    public static int censerOfHollowPrayerAbsorptionAmplifier() { return CONFIG.censerOfHollowPrayerAbsorptionAmplifier.get(); }

    public static boolean enableNailOfTheFirstMartyr() { return CONFIG.enableNailOfTheFirstMartyr.get(); }
    public static int nailOfTheFirstMartyrClaimDurationTicks() { return CONFIG.nailOfTheFirstMartyrClaimDurationTicks.get(); }
    public static int nailOfTheFirstMartyrMaxStacks() { return CONFIG.nailOfTheFirstMartyrMaxStacks.get(); }
    public static int nailOfTheFirstMartyrJudgedDurationTicks() { return CONFIG.nailOfTheFirstMartyrJudgedDurationTicks.get(); }
    public static int nailOfTheFirstMartyrHasteDurationTicks() { return CONFIG.nailOfTheFirstMartyrHasteDurationTicks.get(); }
    public static int nailOfTheFirstMartyrHasteAmplifier() { return CONFIG.nailOfTheFirstMartyrHasteAmplifier.get(); }
    public static int nailOfTheFirstMartyrAbsorptionDurationTicks() { return CONFIG.nailOfTheFirstMartyrAbsorptionDurationTicks.get(); }
    public static int nailOfTheFirstMartyrAbsorptionAmplifier() { return CONFIG.nailOfTheFirstMartyrAbsorptionAmplifier.get(); }

    public static boolean enableGoldRing() { return CONFIG.enableGoldRing.get(); }
    public static double goldRingArmorBonus() { return CONFIG.goldRingArmorBonus.get(); }

    public static boolean enableCyanRing() { return CONFIG.enableCyanRing.get(); }
    public static double cyanRingArmorBonus() { return CONFIG.cyanRingArmorBonus.get(); }

    public static boolean enableNebulaRing() { return CONFIG.enableNebulaRing.get(); }
    public static boolean FracturedAshRing() { return CONFIG.enableFracturedRing.get(); }
    public static double nebulaRingArmorBonus() { return CONFIG.nebulaRingArmorBonus.get(); }
    public static int nebulaRingExtraRingSlots() { return CONFIG.nebulaRingExtraRingSlots.get(); }
    public static int FracturedRingExtraRingSlots() { return CONFIG.FracturedRingExtraRingSlots.get(); }

    public static boolean enableCreativeBrandedTimeBypass() { return CONFIG.enableCreativeBrandedTimeBypass.get(); }
    public static int lethargicFlailLazinessDurationTicks() { return CONFIG.lethargicFlailLazinessDurationTicks.get(); }
    public static int lethargicFlailSweepLazinessStacks() { return CONFIG.lethargicFlailSweepLazinessStacks.get(); }
    public static double lethargicFlailSweepDamageMultiplier() { return CONFIG.lethargicFlailSweepDamageMultiplier.get(); }
    public static double lethargicFlailSweepRadius() { return CONFIG.lethargicFlailSweepRadius.get(); }
    public static int lethargicFlailSweepEnemyDebuffDurationTicks() { return CONFIG.lethargicFlailSweepEnemyDebuffDurationTicks.get(); }
    public static int lethargicFlailCubeCooldownTicks() { return CONFIG.lethargicFlailCubeCooldownTicks.get(); }
    public static int lethargicFlailCubeStunDurationTicks() { return CONFIG.lethargicFlailCubeStunDurationTicks.get(); }
    public static double lethargicFlailCubeDamageMultiplier() { return CONFIG.lethargicFlailCubeDamageMultiplier.get(); }

    public static double soulFractureMaxHealthLossPerGem() { return CONFIG.soulFractureMaxHealthLossPerGem.get(); }
    public static double soulGemPickupRadius() { return CONFIG.soulGemPickupRadius.get(); }

    public static boolean enableVanitysEdge() { return CONFIG.enableVanitysEdge.get(); }
    public static int vanitysEdgeMaintenanceIntervalTicks() { return CONFIG.vanitysEdgeMaintenanceIntervalTicks.get(); }
    public static int vanitysEdgeAllyAuraIntervalTicks() { return CONFIG.vanitysEdgeAllyAuraIntervalTicks.get(); }

    public static double vanitysEdgeHighHealthAttackSpeedThreshold() { return CONFIG.vanitysEdgeHighHealthAttackSpeedThreshold.get(); }
    public static double vanitysEdgeHighHealthAttackSpeedBonus() { return CONFIG.vanitysEdgeHighHealthAttackSpeedBonus.get(); }
    public static double vanitysEdgeHighHealthBonusDamagePerTwoHp() { return CONFIG.vanitysEdgeHighHealthBonusDamagePerTwoHp.get(); }

    public static double vanitysEdgeAllySuppressionRadius() { return CONFIG.vanitysEdgeAllySuppressionRadius.get(); }
    public static int vanitysEdgeAllySuppressionDurationTicks() { return CONFIG.vanitysEdgeAllySuppressionDurationTicks.get(); }

    public static double vanitysEdgeNonKillSelfDamage() { return CONFIG.vanitysEdgeNonKillSelfDamage.get(); }

    public static int vanitysEdgeKillStackDurationTicks() { return CONFIG.vanitysEdgeKillStackDurationTicks.get(); }
    public static double vanitysEdgeKillStackAttackSpeedBonus() { return CONFIG.vanitysEdgeKillStackAttackSpeedBonus.get(); }

    public static double vanitysEdgeArmorPenaltyPerTwoArmor() { return CONFIG.vanitysEdgeArmorPenaltyPerTwoArmor.get(); }

    public static double vanitysEdgeMidHealthUpperThreshold() { return CONFIG.vanitysEdgeMidHealthUpperThreshold.get(); }
    public static double vanitysEdgeMidHealthLowerThreshold() { return CONFIG.vanitysEdgeMidHealthLowerThreshold.get(); }
    public static double vanitysEdgeMidHealthDamagePenaltyMultiplier() { return CONFIG.vanitysEdgeMidHealthDamagePenaltyMultiplier.get(); }
    public static double vanitysEdgeMidHealthAttackSpeedPenaltyMultiplier() { return CONFIG.vanitysEdgeMidHealthAttackSpeedPenaltyMultiplier.get(); }

    public static double vanitysEdgeLowHealthThreshold() { return CONFIG.vanitysEdgeLowHealthThreshold.get(); }
    public static double vanitysEdgeLosingPrideChance() { return CONFIG.vanitysEdgeLosingPrideChance.get(); }
    public static int vanitysEdgeOutcomeDurationTicks() { return CONFIG.vanitysEdgeOutcomeDurationTicks.get(); }

    public static double vanitysEdgeLosingPrideHealingMultiplier() { return CONFIG.vanitysEdgeLosingPrideHealingMultiplier.get(); }
    public static int vanitysEdgeBraveryRegenerationAmplifier() { return CONFIG.vanitysEdgeBraveryRegenerationAmplifier.get(); }

    public static boolean enableCovetfang() { return CONFIG.enableCovetfang.get(); }
    public static int covetfangMaintenanceIntervalTicks() { return CONFIG.covetfangMaintenanceIntervalTicks.get(); }

    public static int covetfangClaimDurationTicks() { return CONFIG.covetfangClaimDurationTicks.get(); }
    public static int covetfangClaimCooldownTicks() { return CONFIG.covetfangClaimCooldownTicks.get(); }
    public static double covetfangClaimTrackRadius() { return CONFIG.covetfangClaimTrackRadius.get(); }
    public static int covetfangCovetedGlowDurationTicks() { return CONFIG.covetfangCovetedGlowDurationTicks.get(); }

    public static double covetfangBonusDamagePerEnvyScore() { return CONFIG.covetfangBonusDamagePerEnvyScore.get(); }
    public static double covetfangCovetedTargetExtraDamage() { return CONFIG.covetfangCovetedTargetExtraDamage.get(); }
    public static double covetfangBonusDamageCap() { return CONFIG.covetfangBonusDamageCap.get(); }

    public static double covetfangBaseNonKillSelfDamage() { return CONFIG.covetfangBaseNonKillSelfDamage.get(); }
    public static double covetfangHollowComparisonExtraSelfDamage() { return CONFIG.covetfangHollowComparisonExtraSelfDamage.get(); }

    public static int covetfangStolenStrengthDurationTicks() { return CONFIG.covetfangStolenStrengthDurationTicks.get(); }
    public static double covetfangStolenVitalityMaxHealthBonus() { return CONFIG.covetfangStolenVitalityMaxHealthBonus.get(); }
    public static double covetfangStolenPlatingArmorBonus() { return CONFIG.covetfangStolenPlatingArmorBonus.get(); }
    public static double covetfangStolenSwiftnessMoveSpeedBonus() { return CONFIG.covetfangStolenSwiftnessMoveSpeedBonus.get(); }
    public static double covetfangStolenFerocityAttackSpeedBonus() { return CONFIG.covetfangStolenFerocityAttackSpeedBonus.get(); }

    public static double covetfangBeneficialEffectAttackDamagePenalty() { return CONFIG.covetfangBeneficialEffectAttackDamagePenalty.get(); }

    public static double covetfangLowHealthThreshold() { return CONFIG.covetfangLowHealthThreshold.get(); }
    public static double covetfangDesperateWantScanRadius() { return CONFIG.covetfangDesperateWantScanRadius.get(); }
    public static double covetfangDesperateWantHealOnHit() { return CONFIG.covetfangDesperateWantHealOnHit.get(); }
    public static double covetfangDesperateWantMoveSpeedBonus() { return CONFIG.covetfangDesperateWantMoveSpeedBonus.get(); }

    public static double covetfangHopelessComparisonDamagePenalty() { return CONFIG.covetfangHopelessComparisonDamagePenalty.get(); }
    public static double covetfangHopelessComparisonMoveSpeedPenalty() { return CONFIG.covetfangHopelessComparisonMoveSpeedPenalty.get(); }

    public static double covetfangCovetedPursuitMoveSpeedBonus() { return CONFIG.covetfangCovetedPursuitMoveSpeedBonus.get(); }
    public static boolean giveStarterOathboundRelic() { return CONFIG.giveStarterOathboundRelic.get(); }
    public static boolean enableBrandkeepersMercy() { return CONFIG.enableBrandkeepersMercy.get(); }
    public static int bloodTollCooldownTicks() { return CONFIG.bloodTollCooldownTicks.get(); }
}