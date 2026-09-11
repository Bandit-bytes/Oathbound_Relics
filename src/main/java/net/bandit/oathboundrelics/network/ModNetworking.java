package net.bandit.oathboundrelics.network;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(
        modid = OathboundRelicsMod.MOD_ID,
        bus = EventBusSubscriber.Bus.MOD
)
public final class ModNetworking {

    private ModNetworking() {}

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");

        registrar.playToServer(
                SkybrandGlidePayload.TYPE,
                SkybrandGlidePayload.STREAM_CODEC,
                SkybrandGlidePayload::handle
        );
    }
}