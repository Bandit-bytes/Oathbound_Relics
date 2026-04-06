package net.bandit.oathboundrelics.client;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.client.renderer.HeavyCubeProjectileRenderer;
import net.bandit.oathboundrelics.client.renderer.SoulGemRenderer;
import net.bandit.oathboundrelics.client.renderer.SoulLanternCurioRenderer;
import net.bandit.oathboundrelics.registry.EntityRegistry;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(
        modid = OathboundRelicsMod.MOD_ID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class ClientEvents {

    private ClientEvents() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CuriosRendererRegistry.register(ItemRegistry.SOUL_LANTERN.get(), SoulLanternCurioRenderer::new);
        });
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