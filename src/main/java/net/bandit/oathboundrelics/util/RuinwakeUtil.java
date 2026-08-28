package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.data.PersistentData;
import net.bandit.oathboundrelics.data.PlayerDataStorage;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class RuinwakeUtil {

    private static final String GRUDGE_KEY = "oathboundrelics_ruinwake_grudge";
    private static final String LAST_COMBAT_KEY = "oathboundrelics_ruinwake_last_combat";
    private static final String LAST_DECAY_KEY = "oathboundrelics_ruinwake_last_decay";
    private static final String RELEASING_KEY = "oathboundrelics_ruinwake_releasing";

    private RuinwakeUtil() {
    }

    public static boolean qualifiesForRuinwake(Player player) {
        BrandedTimeData data = PlayerDataStorage.brandedTime(player);
        return data.qualifies(
                OathboundConfig.slothWeaponMaxBrandedTicks(),
                OathboundConfig.slothWeaponRequiredBrandedPercent()
        );
    }

    public static boolean canUseRuinwake(Player player) {
        if (!OathboundConfig.enableRuinwake()) {
            return false;
        }

        if (player.isCreative() && OathboundConfig.enableCreativeBrandedTimeBypass()) {
            return true;
        }

        return OathboundUtil.isBranded(player) && qualifiesForRuinwake(player);
    }

    public static boolean isHoldingRuinwake(Player player) {
        return player.getMainHandItem().is(ItemRegistry.RUINWAKE.get())
                || player.getOffhandItem().is(ItemRegistry.RUINWAKE.get());
    }

    public static boolean isHoldingRuinwakeMainHand(Player player) {
        return player.getMainHandItem().is(ItemRegistry.RUINWAKE.get());
    }

    public static boolean hasRuinwake(Player player) {
        if (isHoldingRuinwake(player)) {
            return true;
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ItemRegistry.RUINWAKE.get())) {
                return true;
            }
        }

        return false;
    }

    public static int getGrudge(Player player) {
        return Math.clamp(
                PersistentData.get(player).getInt(GRUDGE_KEY),
                0,
                OathboundConfig.ruinwakeMaxGrudgeStacks()
        );
    }

    public static void addGrudge(Player player, int amount) {
        if (amount <= 0) {
            return;
        }

        int next = Math.min(
                OathboundConfig.ruinwakeMaxGrudgeStacks(),
                getGrudge(player) + amount
        );
        PersistentData.get(player).putInt(GRUDGE_KEY, next);
        markCombat(player);
    }

    public static int consumeGrudge(Player player) {
        int current = getGrudge(player);
        PersistentData.get(player).putInt(GRUDGE_KEY, 0);
        return current;
    }

    public static void clearGrudge(Player player) {
        PersistentData.get(player).putInt(GRUDGE_KEY, 0);
    }

    public static void markCombat(Player player) {
        long now = player.level().getGameTime();
        PersistentData.get(player).putLong(LAST_COMBAT_KEY, now);
        PersistentData.get(player).putLong(LAST_DECAY_KEY, now);
    }

    public static void decayGrudge(Player player) {
        int stacks = getGrudge(player);
        if (stacks <= 0) {
            return;
        }

        long now = player.level().getGameTime();
        long lastCombat = PersistentData.get(player).getLong(LAST_COMBAT_KEY);
        long lastDecay = PersistentData.get(player).getLong(LAST_DECAY_KEY);
        int delay = OathboundConfig.ruinwakeStackDecayTicks();

        if (now - lastCombat < delay || now - lastDecay < delay) {
            return;
        }

        PersistentData.get(player).putInt(GRUDGE_KEY, stacks - 1);
        PersistentData.get(player).putLong(LAST_DECAY_KEY, now);
    }

    public static boolean isReleasing(Player player) {
        return PersistentData.get(player).getBoolean(RELEASING_KEY);
    }

    public static void setReleasing(Player player, boolean releasing) {
        PersistentData.get(player).putBoolean(RELEASING_KEY, releasing);
    }

    public static boolean isAlly(Player player, LivingEntity target) {
        if (target == player) {
            return true;
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
