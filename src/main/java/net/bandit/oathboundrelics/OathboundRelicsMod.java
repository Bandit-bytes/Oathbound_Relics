package net.bandit.oathboundrelics;

import com.mojang.logging.LogUtils;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(OathboundRelicsMod.MOD_ID)
public final class OathboundRelicsMod {

    public static final String MOD_ID = "oathboundrelics";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OathboundRelicsMod(IEventBus modBus, ModContainer modContainer) {
        ItemRegistry.register(modBus);
        ArmorMaterialRegistry.register(modBus);
        TabRegistry.register(modBus);
        BlockRegistry.register(modBus);
        BlockEntityRegistry.register(modBus);
        EffectRegistry.register(modBus);
        EntityRegistry.register(modBus);
        AttachmentRegistry.register(modBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, OathboundConfig.SPEC);
    }
}