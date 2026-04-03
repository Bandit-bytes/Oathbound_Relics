package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.registry.EffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public final class SlothCombatUtil {

    private static final int LAZINESS_DURATION = 20 * 60;
    private static final int ENEMY_SLOTH_DURATION = 20 * 10;
    private static final int STUN_DURATION = 20 * 10;

    private SlothCombatUtil() {
    }

    public static void addLaziness(LivingEntity entity, int addedStacks) {
        int currentStacks = 0;

        MobEffectInstance existing = entity.getEffect(EffectRegistry.LAZINESS);
        if (existing != null) {
            currentStacks = existing.getAmplifier() + 1;
        }

        int newStacks = Math.max(1, currentStacks + addedStacks);

        entity.addEffect(new MobEffectInstance(
                EffectRegistry.LAZINESS,
                OathboundConfig.lethargicFlailLazinessDurationTicks(),
                newStacks - 1,
                false,
                true,
                true
        ));
    }

    public static void applySlothWeakness(LivingEntity target) {
        target.addEffect(new MobEffectInstance(
                EffectRegistry.SLOTH_WEAKNESS,
                OathboundConfig.lethargicFlailSweepEnemyDebuffDurationTicks(),
                0,
                false,
                true,
                true
        ));
    }

    public static void applyHeavyStun(LivingEntity target) {
        target.addEffect(new MobEffectInstance(
                EffectRegistry.HEAVY_STUN,
                OathboundConfig.lethargicFlailCubeStunDurationTicks(),
                0,
                false,
                true,
                true
        ));
    }
}