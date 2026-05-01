package net.bandit.oathboundrelics.client;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.bandit.oathboundrelics.client.renderer.HeavyCubeProjectileRenderer;
import net.bandit.oathboundrelics.client.renderer.SoulGemRenderer;
import net.bandit.oathboundrelics.client.renderer.SoulLanternTrinketRenderer;
import net.bandit.oathboundrelics.registry.BlockRegistry;
import net.bandit.oathboundrelics.registry.EntityRegistry;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

public class OathboundRelicsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SOUL_LANTERN_BLOCK.get(), RenderType.cutout());
        TrinketRendererRegistry.registerRenderer(ItemRegistry.SOUL_LANTERN.get(), new SoulLanternTrinketRenderer());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.RITE_OF_SEVERANCE.get(), RenderType.cutout());
        EntityRendererRegistry.register(EntityRegistry.HEAVY_CUBE_PROJECTILE.get(), HeavyCubeProjectileRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.SOUL_GEM.get(), SoulGemRenderer::new);
        TitansRemnantItemProperties.register();
    }
}
