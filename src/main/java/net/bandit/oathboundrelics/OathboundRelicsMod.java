package net.bandit.oathboundrelics;

import net.bandit.oathboundrelics.compat.TrinketsCompatRegistry;
import net.bandit.oathboundrelics.config.FabricConfigBridge;
import net.bandit.oathboundrelics.events.FabricEventBridge;
import net.bandit.oathboundrelics.events.FabricWorldgenBridge;
import net.bandit.oathboundrelics.registry.*;
import net.fabricmc.api.ModInitializer;

public final class OathboundRelicsMod implements ModInitializer {

    public static final String MOD_ID = "oathboundrelics";

    @Override
    public void onInitialize() {
        FabricConfigBridge.register();

        BlockRegistry.register();
        ItemRegistry.register();
        TrinketsCompatRegistry.register();
        ArmorMaterialRegistry.register();
        TabRegistry.register();
        BlockEntityRegistry.register();
        EffectRegistry.register();
        EntityRegistry.register();
        AttachmentRegistry.register();

        FabricEventBridge.register();
        FabricWorldgenBridge.register();
    }
}
