package net.bandit.oathboundrelics.client;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.client.renderer.HeavyCubeProjectileRenderer;
import net.bandit.oathboundrelics.client.renderer.SoulGemRenderer;
import net.bandit.oathboundrelics.registry.EntityRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(
        modid = OathboundRelicsMod.MOD_ID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class ClientEvents {

    private ClientEvents() {
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                EntityRegistry.HEAVY_CUBE_PROJECTILE.get(),
                HeavyCubeProjectileRenderer::new
        );
        event.registerEntityRenderer(
                EntityRegistry.SOUL_GEM.get(),
                SoulGemRenderer::new
        );
    }
}