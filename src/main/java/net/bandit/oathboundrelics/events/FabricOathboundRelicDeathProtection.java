package net.bandit.oathboundrelics.events;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.data.PersistentData;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

/**
 * Fabric/Trinkets-owned protection for the covenant relic.
 *
 * The equipped stack is removed before death handling so grave/corpse mods never get a
 * chance to capture it. The exact serialized stack is copied to the replacement player
 * and restored after Trinkets and other death handlers have finished rebuilding slots.
 */
public final class FabricOathboundRelicDeathProtection {
    private static final String BACKUP_TAG = "oathboundrelics_protected_relic";
    private static final String SLOT_GROUP_TAG = "oathboundrelics_protected_relic_group";
    private static final String SLOT_NAME_TAG = "oathboundrelics_protected_relic_slot";
    private static final String SLOT_INDEX_TAG = "oathboundrelics_protected_relic_index";
    private static final String RESTORE_DELAY_TAG = "oathboundrelics_protected_relic_restore_delay";
    private static final int RESTORE_DELAY_TICKS = 20;

    private FabricOathboundRelicDeathProtection() {
    }

    public static void protectBeforeDeath(ServerPlayer player) {
        CompoundTag data = PersistentData.get(player);

        // Refresh the backup on every real death. The backup intentionally persists between
        // deaths, so returning early here would let a later grave capture the equipped relic.
        TrinketsApi.getTrinketComponent(player).ifPresent(component -> {
            for (Tuple<SlotReference, ItemStack> pair : component.getAllEquipped()) {
                SlotReference reference = pair.getA();
                ItemStack equipped = pair.getB();
                if (!equipped.is(ItemRegistry.OATHBOUND_RELIC.get())) {
                    continue;
                }

                TrinketInventory inventory = reference.inventory();
                var serialized = equipped.saveOptional(player.registryAccess());
                if (!(serialized instanceof CompoundTag backup)) {
                    OathboundRelicsMod.LOGGER.warn(
                            "Could not serialize Oathbound Relic for {} before death",
                            player.getGameProfile().getName()
                    );
                    return;
                }
                data.put(BACKUP_TAG, backup);
                data.putString(SLOT_GROUP_TAG, inventory.getSlotType().getGroup());
                data.putString(SLOT_NAME_TAG, inventory.getSlotType().getName());
                data.putInt(SLOT_INDEX_TAG, reference.index());
                data.putInt(RESTORE_DELAY_TAG, RESTORE_DELAY_TICKS);

                inventory.setItem(reference.index(), ItemStack.EMPTY);
                inventory.markUpdate();
                component.update();

                OathboundRelicsMod.LOGGER.debug(
                        "Protected Oathbound Relic for {} from Fabric death/grave handling (slot {}/{}[{}])",
                        player.getGameProfile().getName(),
                        inventory.getSlotType().getGroup(),
                        inventory.getSlotType().getName(),
                        reference.index()
                );
                return;
            }
        });
    }

    public static void copyAcrossRespawn(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
        if (alive) {
            return;
        }

        CompoundTag oldData = PersistentData.get(oldPlayer);
        if (!oldData.contains(BACKUP_TAG)) {
            return;
        }

        CompoundTag newData = PersistentData.get(newPlayer);
        newData.put(BACKUP_TAG, oldData.getCompound(BACKUP_TAG).copy());
        newData.putString(SLOT_GROUP_TAG, oldData.getString(SLOT_GROUP_TAG));
        newData.putString(SLOT_NAME_TAG, oldData.getString(SLOT_NAME_TAG));
        newData.putInt(SLOT_INDEX_TAG, oldData.getInt(SLOT_INDEX_TAG));
        newData.putInt(RESTORE_DELAY_TAG, RESTORE_DELAY_TICKS);
    }

    public static void afterRespawn(ServerPlayer player, boolean alive) {
        if (!alive && PersistentData.get(player).contains(BACKUP_TAG)) {
            PersistentData.get(player).putInt(RESTORE_DELAY_TAG, RESTORE_DELAY_TICKS);
        }
    }

    public static void tick(ServerPlayer player) {
        CompoundTag data = PersistentData.get(player);
        if (!data.contains(BACKUP_TAG)) {
            return;
        }

        // Creative mode is allowed to intentionally manipulate the bound slot.
        if (player.isCreative()) {
            return;
        }

        int delay = data.getInt(RESTORE_DELAY_TAG);
        if (delay > 0) {
            data.putInt(RESTORE_DELAY_TAG, delay - 1);
            return;
        }

        if (hasRelic(player)) {
            return;
        }

        ItemStack protectedRelic = ItemStack.parseOptional(player.registryAccess(), data.getCompound(BACKUP_TAG));
        if (protectedRelic.isEmpty()) {
            OathboundRelicsMod.LOGGER.warn("Could not deserialize protected Oathbound Relic for {}", player.getGameProfile().getName());
            return;
        }

        boolean restored = TrinketsApi.getTrinketComponent(player)
                .map(component -> restoreToTrinkets(component, data, protectedRelic))
                .orElse(false);

        if (!restored) {
            return;
        }

        // Keep the serialized covenant backup so a grave mod that rewrites Trinkets later
        // cannot permanently remove the relic. Rite of Severance clears this backup.
        data.putInt(RESTORE_DELAY_TAG, RESTORE_DELAY_TICKS);
        OathboundRelicsMod.LOGGER.debug(
                "Restored protected Oathbound Relic to {} after Fabric respawn/inventory sync",
                player.getGameProfile().getName()
        );
    }

    public static boolean hasProtectedRelic(net.minecraft.world.entity.player.Player player) {
        return player != null && PersistentData.get(player).contains(BACKUP_TAG);
    }

    public static void clearBackup(ServerPlayer player) {
        clearBackup((net.minecraft.world.entity.player.Player) player);
    }

    public static void clearBackup(net.minecraft.world.entity.player.Player player) {
        CompoundTag data = PersistentData.get(player);
        data.remove(BACKUP_TAG);
        data.remove(SLOT_GROUP_TAG);
        data.remove(SLOT_NAME_TAG);
        data.remove(SLOT_INDEX_TAG);
        data.remove(RESTORE_DELAY_TAG);
    }

    private static boolean hasRelic(ServerPlayer player) {
        if (TrinketsApi.getTrinketComponent(player)
                .map(component -> component.isEquipped(ItemRegistry.OATHBOUND_RELIC.get()))
                .orElse(false)) {
            return true;
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ItemRegistry.OATHBOUND_RELIC.get())) return true;
        }
        for (ItemStack stack : player.getInventory().offhand) {
            if (stack.is(ItemRegistry.OATHBOUND_RELIC.get())) return true;
        }
        for (ItemStack stack : player.getInventory().armor) {
            if (stack.is(ItemRegistry.OATHBOUND_RELIC.get())) return true;
        }
        return false;
    }

    private static boolean restoreToTrinkets(TrinketComponent component, CompoundTag data, ItemStack relic) {
        String groupName = data.getString(SLOT_GROUP_TAG);
        String slotName = data.getString(SLOT_NAME_TAG);
        int preferredIndex = data.getInt(SLOT_INDEX_TAG);

        Map<String, Map<String, TrinketInventory>> groups = component.getInventory();
        Map<String, TrinketInventory> group = groups.get(groupName);
        if (group != null) {
            TrinketInventory inventory = group.get(slotName);
            if (inventory != null && restoreIntoInventory(component, inventory, preferredIndex, relic)) {
                return true;
            }
        }

        // Slot layouts can be changed by modpacks. Fall back to any Trinkets slot named ring.
        for (Map<String, TrinketInventory> slots : groups.values()) {
            for (TrinketInventory inventory : slots.values()) {
                if ("ring".equals(inventory.getSlotType().getName())
                        && restoreIntoInventory(component, inventory, 0, relic)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean restoreIntoInventory(
            TrinketComponent component,
            TrinketInventory inventory,
            int preferredIndex,
            ItemStack relic
    ) {
        if (preferredIndex >= 0
                && preferredIndex < inventory.getContainerSize()
                && inventory.getItem(preferredIndex).isEmpty()) {
            inventory.setItem(preferredIndex, relic.copy());
            inventory.markUpdate();
            component.update();
            return true;
        }

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (inventory.getItem(i).isEmpty()) {
                inventory.setItem(i, relic.copy());
                inventory.markUpdate();
                component.update();
                return true;
            }
        }
        return false;
    }
}
