package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.blocks.RiteOfSeveranceBlock;
import net.bandit.oathboundrelics.blocks.SoulLanternBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(OathboundRelicsMod.MOD_ID);

    public static final DeferredBlock<Block> AREDRITE_ORE = registerBlock("aredrite_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(3, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
                            .strength(4.0F)
                            .requiresCorrectToolForDrops()
            ));

    public static final DeferredBlock<Block> AREDRITE_BLOCK = registerBlock("aredrite_block",
            () -> new Block(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)
                            .strength(4.0F)
                            .requiresCorrectToolForDrops()
            ));

    public static final DeferredBlock<Block> SOUL_LANTERN_BLOCK = registerBlockWithoutItem(
            "soul_lantern",
            () -> new SoulLanternBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_LANTERN)
                            .strength(3.5F)
                            .lightLevel(state -> 15)
                            .noOcclusion()
            )
    );

    public static final DeferredBlock<Block> RITE_OF_SEVERANCE = registerBlock("rite_of_severance",
            () -> new RiteOfSeveranceBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.CRYING_OBSIDIAN)
                            .strength(5.0F, 1200.0F)
                            .lightLevel(state -> 10)
                            .noOcclusion()
            ));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}