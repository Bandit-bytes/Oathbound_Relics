package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.blocks.blockentity.RiteOfSeveranceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(net.minecraft.core.registries.BuiltInRegistries.BLOCK_ENTITY_TYPE, OathboundRelicsMod.MOD_ID);

    public static final Supplier<BlockEntityType<RiteOfSeveranceBlockEntity>> RITE_OF_SEVERANCE_BE =
            BLOCK_ENTITIES.register("rite_of_severance", () ->
                    BlockEntityType.Builder.of(
                            RiteOfSeveranceBlockEntity::new,
                            BlockRegistry.RITE_OF_SEVERANCE.get()
                    ).build(null)
            );

    private BlockEntityRegistry() {
    }

    public static void register(IEventBus modBus) {
        BLOCK_ENTITIES.register(modBus);
    }
}