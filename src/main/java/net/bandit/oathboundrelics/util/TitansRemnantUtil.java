package net.bandit.oathboundrelics.util;


import net.bandit.oathboundrelics.curio.items.TitansRemnantItem;
import net.bandit.oathboundrelics.items.TitanRemnantType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

public final class TitansRemnantUtil {
    public static final String IMPACT_TAG = "oathboundrelics_titans_remnant_impact";
    public static final String HEAT_TAG = "oathboundrelics_titans_remnant_heat";
    public static final String NEBULA_EMPOWERED_TAG = "oathboundrelics_titans_remnant_nebula_empowered";
    public static final String WAS_AIRBORNE_TAG = "oathboundrelics_titans_remnant_was_airborne";
    public static final String AIRBORNE_FALL_TAG = "oathboundrelics_titans_remnant_airborne_fall";
    public static final String NEBULA_BLINK_CD_TAG = "oathboundrelics_titans_remnant_nebula_blink_cd";
    private static final String TITANS_REMNANT_SLOT = "titans_remnant";

    private TitansRemnantUtil() {
    }

    public static ItemStack getEquippedTitansRemnant(Player player) {
        return CuriosApi.getCuriosHelper()
                .findFirstCurio(player, stack -> stack.getItem() instanceof TitansRemnantItem)
                .filter(slotResult -> TITANS_REMNANT_SLOT.equals(slotResult.slotContext().identifier()))
                .map(slotResult -> slotResult.stack())
                .orElse(ItemStack.EMPTY);
    }

    public static boolean hasEquipped(Player player) {
        ItemStack stack = getEquippedTitansRemnant(player);
        return stack.getItem() instanceof TitansRemnantItem;
    }

    public static TitanRemnantType getEquippedType(Player player) {
        ItemStack stack = getEquippedTitansRemnant(player);
        if (stack.getItem() instanceof TitansRemnantItem item) {
            return item.getType();
        }
        return null;
    }

    public static boolean isEquipped(Player player, TitanRemnantType type) {
        return getEquippedType(player) == type;
    }

    public static int getImpact(Player player) {
        return player.getPersistentData().getInt(IMPACT_TAG);
    }

    public static void setImpact(Player player, int value) {
        player.getPersistentData().putInt(IMPACT_TAG, Math.max(0, Math.min(5, value)));
    }

    public static int getHeat(Player player) {
        return player.getPersistentData().getInt(HEAT_TAG);
    }

    public static void setHeat(Player player, int value) {
        player.getPersistentData().putInt(HEAT_TAG, Math.max(0, Math.min(10, value)));
    }

    public static boolean isNebulaEmpowered(Player player) {
        return player.getPersistentData().getBoolean(NEBULA_EMPOWERED_TAG);
    }

    public static void setNebulaEmpowered(Player player, boolean value) {
        player.getPersistentData().putBoolean(NEBULA_EMPOWERED_TAG, value);
    }

    public static boolean wasAirborne(Player player) {
        return player.getPersistentData().getBoolean(WAS_AIRBORNE_TAG);
    }

    public static void setWasAirborne(Player player, boolean value) {
        player.getPersistentData().putBoolean(WAS_AIRBORNE_TAG, value);
    }

    public static float getStoredAirborneFall(Player player) {
        return player.getPersistentData().getFloat(AIRBORNE_FALL_TAG);
    }

    public static void setStoredAirborneFall(Player player, float value) {
        player.getPersistentData().putFloat(AIRBORNE_FALL_TAG, value);
    }

    public static long getNebulaBlinkCooldown(Player player) {
        return player.getPersistentData().getLong(NEBULA_BLINK_CD_TAG);
    }

    public static void setNebulaBlinkCooldown(Player player, long value) {
        player.getPersistentData().putLong(NEBULA_BLINK_CD_TAG, value);
    }

    public static boolean isLowLight(Player player) {
        return player.level().getMaxLocalRawBrightness(player.blockPosition()) <= 7;
    }
}