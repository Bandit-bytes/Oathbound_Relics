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
    public final ModConfigSpec.BooleanValue enableOppression;
    public final ModConfigSpec.BooleanValue enableLivingEmber;
    public final ModConfigSpec.BooleanValue enableSoulFracture;
    public final ModConfigSpec.BooleanValue enableWakefulDoom;

    public final ModConfigSpec.DoubleValue incomingDamageMultiplier;
    public final ModConfigSpec.DoubleValue armorEffectiveness;
    public final ModConfigSpec.DoubleValue outgoingDamageMultiplier;
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
    public final ModConfigSpec.IntValue hollowEyeNightVisionDurationTicks;
    public final ModConfigSpec.DoubleValue hollowEyeRevealRadius;
    public final ModConfigSpec.IntValue hollowEyeRevealDurationTicks;
    public final ModConfigSpec.IntValue hollowEyeRevealIntervalTicks;

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


    private OathboundConfig(ModConfigSpec.Builder builder) {
        builder.push("oathbound_relic");

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

        enableOppression = builder
                .comment("If true, Branded players deal reduced outgoing damage.")
                .define("enableOppression", true);

        outgoingDamageMultiplier = builder
                .comment("Outgoing damage multiplier. 0.50 = 50% less damage dealt.")
                .defineInRange("outgoingDamageMultiplier", 0.50D, 0.0D, 100.0D);

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
                .defineInRange("xpMultiplier", 2.0D, 1.0D, 10.0D);

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
                .comment("If true, Hollow Eye grants night vision and reveals nearby monsters.")
                .define("enabled", true);
        hollowEyeNightVisionDurationTicks = builder
                .comment("Night Vision refresh duration in ticks.")
                .defineInRange("nightVisionDurationTicks", 220, 0, 20 * 60 * 60);
        hollowEyeRevealRadius = builder
                .comment("Reveal radius in blocks.")
                .defineInRange("revealRadius", 24.0D, 1.0D, 256.0D);
        hollowEyeRevealDurationTicks = builder
                .comment("Glowing duration applied to nearby monsters, in ticks.")
                .defineInRange("revealDurationTicks", 40, 0, 20 * 60 * 60);
        hollowEyeRevealIntervalTicks = builder
                .comment("How often to reveal nearby monsters, in ticks.")
                .defineInRange("revealIntervalTicks", 20, 1, 1200);
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
                .defineInRange("slothWeaponMaxBrandedTicks", 7200000L, 1L, Long.MAX_VALUE);

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

        builder.pop();
    }

    public static boolean enableFrailty() { return CONFIG.enableFrailty.get(); }
    public static boolean enableProvocation() { return CONFIG.enableProvocation.get(); }
    public static boolean enableShatteredPlate() { return CONFIG.enableShatteredPlate.get(); }
    public static boolean enableOppression() { return CONFIG.enableOppression.get(); }
    public static boolean enableLivingEmber() { return CONFIG.enableLivingEmber.get(); }
    public static boolean enableSoulFracture() { return CONFIG.enableSoulFracture.get(); }
    public static boolean enableWakefulDoom() { return CONFIG.enableWakefulDoom.get(); }

    public static double incomingDamageMultiplier() { return CONFIG.incomingDamageMultiplier.get(); }
    public static double armorEffectiveness() { return CONFIG.armorEffectiveness.get(); }
    public static double outgoingDamageMultiplier() { return CONFIG.outgoingDamageMultiplier.get(); }
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
    public static int hollowEyeNightVisionDurationTicks() { return CONFIG.hollowEyeNightVisionDurationTicks.get(); }
    public static double hollowEyeRevealRadius() { return CONFIG.hollowEyeRevealRadius.get(); }
    public static int hollowEyeRevealDurationTicks() { return CONFIG.hollowEyeRevealDurationTicks.get(); }
    public static int hollowEyeRevealIntervalTicks() { return CONFIG.hollowEyeRevealIntervalTicks.get(); }

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
}