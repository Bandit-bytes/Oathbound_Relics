package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.registry.ItemRegistry;
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

        if (!OathboundConfig.giveStarterOathboundRelic()) {
            return;
        }

        CompoundTag data = player.getPersistentData();
        if (data.getBoolean(STARTER_TAG)) {
            return;
        }

        ItemStack relic = new ItemStack(ItemRegistry.OATHBOUND_RELIC.get());

        boolean added = player.getInventory().add(relic);
        if (!added) {
            player.drop(relic, false);
        }

        data.putBoolean(STARTER_TAG, true);
    }
}