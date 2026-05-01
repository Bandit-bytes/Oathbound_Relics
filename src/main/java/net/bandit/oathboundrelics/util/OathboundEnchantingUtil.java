package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.ArrayList;
import java.util.List;

public final class OathboundEnchantingUtil {

    private static final int OVER_ENCHANT_THRESHOLD = 30;
    private static final int HARD_CAP = 40;

    private OathboundEnchantingUtil() {
    }

    public static List<EnchantmentInstance> applySingleOvercapRoll(
            List<EnchantmentInstance> original,
            int slot,
            int enchantingPower,
            int enchantmentSeed
    ) {
        if (!OathboundConfig.enableEnchantingBlessing()) {
            return original;
        }

        if (original == null || original.isEmpty()) {
            return original;
        }

        int effectivePower = Math.min(HARD_CAP, enchantingPower);
        if (effectivePower <= OVER_ENCHANT_THRESHOLD) {
            return original;
        }

        List<EnchantmentInstance> modified = new ArrayList<>(original.size());

        for (int index = 0; index < original.size(); index++) {
            EnchantmentInstance inst = original.get(index);

            int newLevel = maybeOvercap(
                    inst.enchantment,
                    inst.level,
                    effectivePower,
                    buildSeed(inst.enchantment, slot, effectivePower, enchantmentSeed, index)
            );

            modified.add(new EnchantmentInstance(inst.enchantment, newLevel));
        }

        return modified;
    }

    private static int maybeOvercap(
            Holder<Enchantment> enchantment,
            int rolledLevel,
            int enchantingPower,
            long seed
    ) {
        int vanillaMax = enchantment.value().getMaxLevel();

        if (rolledLevel < vanillaMax) {
            return rolledLevel;
        }

        RandomSource random = RandomSource.create(seed);

        if (random.nextFloat() < getOverEnchantChance(enchantingPower)) {
            return vanillaMax + 1;
        }

        return vanillaMax;
    }

    private static float getOverEnchantChance(int enchantingPower) {
        return Math.min(0.90F, (Math.min(HARD_CAP, enchantingPower) - OVER_ENCHANT_THRESHOLD) * 0.10F);
    }

    private static long buildSeed(
            Holder<Enchantment> enchantment,
            int slot,
            int enchantingPower,
            int enchantmentSeed,
            int index
    ) {
        int enchantHash = enchantment.unwrapKey()
                .map(key -> key.location().hashCode())
                .orElse(0);

        long seed = 1469598103934665603L;
        seed = seed * 31L + enchantmentSeed;
        seed = seed * 31L + slot;
        seed = seed * 31L + enchantingPower;
        seed = seed * 31L + index;
        seed = seed * 31L + enchantHash;
        return seed;
    }
}