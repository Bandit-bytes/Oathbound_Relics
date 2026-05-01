package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.data.PersistentData;

import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public final class BearerCurioUtil {

    public static final String TAG_ROOT = "oathboundrelics_curios";
    public static final String TAG_PENANCE = "Penance";
    public static final String TAG_LAST_MARTYR_TARGET = "LastMartyrTarget";

    private BearerCurioUtil() {
    }

    public static boolean hasEquipped(Player player, Item item) {
        return CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.isEquipped(item))
                .orElse(false);
    }

    public static int countNewBearerCurios(Player player) {
        int count = 0;

        if (hasEquipped(player, ItemRegistry.OATHBOUND_RELIQUARY.get())) count++;
        if (hasEquipped(player, ItemRegistry.CHAIN_OF_THE_PENITENT.get())) count++;
        if (hasEquipped(player, ItemRegistry.EYE_OF_THE_SLEEPLESS_WITNESS.get())) count++;
        if (hasEquipped(player, ItemRegistry.CENSER_OF_HOLLOW_PRAYER.get())) count++;
        if (hasEquipped(player, ItemRegistry.NAIL_OF_THE_FIRST_MARTYR.get())) count++;

        return count;
    }

    public static float getPenance(Player player) {
        return PersistentData.get(player)
                .getCompound(TAG_ROOT)
                .getFloat(TAG_PENANCE);
    }

    public static void setPenance(Player player, float value) {
        var root = PersistentData.get(player).getCompound(TAG_ROOT);
        root.putFloat(TAG_PENANCE, Math.max(0.0F, value));
        PersistentData.get(player).put(TAG_ROOT, root);
    }

    public static void addPenance(Player player, float amount, float max) {
        setPenance(player, Math.min(max, getPenance(player) + amount));
    }

    public static void clearPenance(Player player) {
        setPenance(player, 0.0F);
    }

    public static boolean isSameMartyrTarget(Player player, int entityId) {
        return PersistentData.get(player)
                .getCompound(TAG_ROOT)
                .getInt(TAG_LAST_MARTYR_TARGET) == entityId;
    }

    public static void setLastMartyrTarget(Player player, int entityId) {
        var root = PersistentData.get(player).getCompound(TAG_ROOT);
        root.putInt(TAG_LAST_MARTYR_TARGET, entityId);
        PersistentData.get(player).put(TAG_ROOT, root);
    }

    public static void clearLastMartyrTarget(Player player) {
        var root = PersistentData.get(player).getCompound(TAG_ROOT);
        root.remove(TAG_LAST_MARTYR_TARGET);
        PersistentData.get(player).put(TAG_ROOT, root);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath("oathboundrelics", path);
    }
}
