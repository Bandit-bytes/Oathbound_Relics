package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
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

        CompoundTag data = player.getPersistentData();
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