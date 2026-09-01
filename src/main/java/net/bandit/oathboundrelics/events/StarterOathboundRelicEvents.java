package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.data.PersistentData;
import net.bandit.oathboundrelics.data.PlayerDataStorage;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.bandit.oathboundrelics.fabricbridge.events.entity.player.PlayerEvent;

public final class StarterOathboundRelicEvents {

    private static final String STARTER_TAG = "oathboundrelics_received_starter_relic";

    private StarterOathboundRelicEvents() {
    }
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        player.server.execute(() -> tryGrantStarterRelic(player));
    }

    private static void tryGrantStarterRelic(ServerPlayer player) {
        if (!OathboundConfig.giveStarterOathboundRelic()) {
            return;
        }

        // Migrate the legacy Fabric persistent flag into the player data store.
        CompoundTag legacyData = PersistentData.get(player);
        if (legacyData.getBoolean(STARTER_TAG)) {
            PlayerDataStorage.setStarterRelicReceived(player, true);
            legacyData.remove(STARTER_TAG);
        }

        if (PlayerDataStorage.starterRelicReceived(player)) {
            return;
        }

        if (hasRelicAlready(player)) {
            PlayerDataStorage.setStarterRelicReceived(player, true);
            return;
        }

        ItemStack relic = new ItemStack(ItemRegistry.OATHBOUND_RELIC.get());

        boolean added = player.getInventory().add(relic);
        if (!added) {
            player.drop(relic, false);
        }

        PlayerDataStorage.setStarterRelicReceived(player, true);
    }

    private static boolean hasRelicAlready(ServerPlayer player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ItemRegistry.OATHBOUND_RELIC.get())) {
                return true;
            }
        }

        for (ItemStack stack : player.getInventory().offhand) {
            if (stack.is(ItemRegistry.OATHBOUND_RELIC.get())) {
                return true;
            }
        }

        for (ItemStack stack : player.getInventory().armor) {
            if (stack.is(ItemRegistry.OATHBOUND_RELIC.get())) {
                return true;
            }
        }

        return OathboundUtil.isBranded(player);
    }
}
