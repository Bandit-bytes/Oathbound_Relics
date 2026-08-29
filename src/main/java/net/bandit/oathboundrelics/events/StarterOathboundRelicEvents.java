package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class StarterOathboundRelicEvents {

    private static final String STARTER_TAG = "oathboundrelics_received_starter_relic";

    private StarterOathboundRelicEvents() {
    }

    @SubscribeEvent
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

        CompoundTag data = persistedData(player);
        if (data.getBoolean(STARTER_TAG)) {
            return;
        }

        if (hasRelicAlready(player)) {
            data.putBoolean(STARTER_TAG, true);
            return;
        }

        ItemStack relic = new ItemStack(ItemRegistry.OATHBOUND_RELIC.get());

        boolean added = player.getInventory().add(relic);
        if (!added) {
            player.drop(relic, false);
        }

        data.putBoolean(STARTER_TAG, true);
    }

    /**
     * Returns the player's {@code PlayerPersisted} compound, creating and attaching it if needed.
     *
     * <p>The starter flag has to live in this sub-compound rather than at the root of
     * {@link ServerPlayer#getPersistentData()}. {@code ServerPlayer#restoreFrom} copies only
     * {@link Player#PERSISTED_NBT_TAG} onto the new player entity, so a root-level flag is
     * discarded every time the player respawns, and the next login grants another relic.
     */
    private static CompoundTag persistedData(ServerPlayer player) {
        CompoundTag root = player.getPersistentData();
        CompoundTag persisted = root.getCompound(Player.PERSISTED_NBT_TAG);

        // Carry over the pre-fix flag so existing players are not handed one extra relic.
        if (root.getBoolean(STARTER_TAG)) {
            persisted.putBoolean(STARTER_TAG, true);
            root.remove(STARTER_TAG);
        }

        root.put(Player.PERSISTED_NBT_TAG, persisted);
        return persisted;
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