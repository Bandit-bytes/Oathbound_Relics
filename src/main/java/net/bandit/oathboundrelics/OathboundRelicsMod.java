package net.bandit.oathboundrelics;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.registry.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(OathboundRelicsMod.MOD_ID)
public final class OathboundRelicsMod {

    public static final String MOD_ID = "oathboundrelics";

    public OathboundRelicsMod(IEventBus modBus, ModContainer modContainer) {
        ItemRegistry.register(modBus);
        ArmorMaterialRegistry.register(modBus);
        TabRegistry.register(modBus);
        BlockRegistry.register(modBus);
        EffectRegistry.register(modBus);
        EntityRegistry.register(modBus);
        AttachmentRegistry.register(modBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, OathboundConfig.SPEC);
    }
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SOUL_LANTERN_BLOCK.get(), RenderType.cutout());
        });
    }
}