package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.blocks.blockentity.RiteOfSeveranceBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class BlockEntityRegistry {

    public static final FabricRegistrySupplier<BlockEntityType<RiteOfSeveranceBlockEntity>> RITE_OF_SEVERANCE_BE =
            new FabricRegistrySupplier<>(Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "rite_of_severance"),
                    BlockEntityType.Builder.of(
                            RiteOfSeveranceBlockEntity::new,
                            BlockRegistry.RITE_OF_SEVERANCE.get()
                    ).build(null)
            ));

    private BlockEntityRegistry() {
    }

    public static void register() {
        // Static initializers perform registration on Fabric.
    }
}
