package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Supplier;

public class TabRegistry {

    private static final List<Supplier<? extends ItemLike>> TAB_ITEMS = List.of(
            ItemRegistry.OATHBOUND_RELIC,
            ItemRegistry.FRACTURED_RELIC_ASH,
            ItemRegistry.ASHEN_NAIL,
            ItemRegistry.BRANDKEEPERS_MERCY,
            ItemRegistry.GRAVEBELL_LOCKET,
            ItemRegistry.HUNTERS_SIGIL,
            ItemRegistry.PILGRIMS_THORN,
            ItemRegistry.SHROUD_OF_THE_FORSAKEN,
            ItemRegistry.VULTURE_CHARM,
            ItemRegistry.HOLLOW_EYE,
            ItemRegistry.CENSER_OF_ASH,
            ItemRegistry.MOURNERS_THREAD,
            ItemRegistry.THORNBOUND_CARAPACE,
            ItemRegistry.VOIDSTEP_BAND,
            ItemRegistry.EXECUTIONERS_COIN,
            ItemRegistry.RELIC_OF_THE_LAST_BREATH,
            ItemRegistry.TORCH_OF_GRAVESONG,
            ItemRegistry.BRANDED_TIME_CHECKER,
            ItemRegistry.OATHBOUND_RELIQUARY,
            ItemRegistry.NAIL_OF_THE_FIRST_MARTYR,
            ItemRegistry.EYE_OF_THE_SLEEPLESS_WITNESS,
            ItemRegistry.CENSER_OF_HOLLOW_PRAYER,
            ItemRegistry.CHAIN_OF_THE_PENITENT,
            ItemRegistry.SOUL_LANTERN,
            ItemRegistry.GOLD_RING,
            ItemRegistry.CYAN_RING,
            ItemRegistry.NEBULA_RING,
            ItemRegistry.TABLET_OF_COVETING,
            ItemRegistry.TABLET_OF_EXALTATION,
            ItemRegistry.TABLET_OF_STILLNESS,
            ItemRegistry.BURDENED_FLAIL_IDOL,
            ItemRegistry.MIRRORSTEEL_IDOL,
            ItemRegistry.HOLLOW_FANG_IDOL,
            ItemRegistry.VOID_ASHES,
            ItemRegistry.COLOSSUS_HEART,
            ItemRegistry.EMBER_SEED,
            ItemRegistry.TIDE_PEARL,
            ItemRegistry.SKYBRAND_FEATHER,
            ItemRegistry.NEBULA_LENS,
            ItemRegistry.VOID_PEARL,
            ItemRegistry.SOUL_GEM,
            ItemRegistry.AREDRITE_GEM,
            ItemRegistry.AREDRITE_HELMET,
            ItemRegistry.AREDRITE_CHESTPLATE,
            ItemRegistry.AREDRITE_LEGGINGS,
            ItemRegistry.AREDRITE_BOOTS,
            BlockRegistry.RITE_OF_SEVERANCE,
            BlockRegistry.AREDRITE_BLOCK,
            BlockRegistry.AREDRITE_ORE,
            ItemRegistry.OBLIVION_STONE,
            ItemRegistry.LETHARGIC_GREATSWORD,
            ItemRegistry.VANITYS_EDGE,
            ItemRegistry.COVETFANG
    );

    public static final FabricRegistrySupplier<CreativeModeTab> OATHBOUND_TAB = register(
            "oathbound_tab",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("itemGroup." + OathboundRelicsMod.MOD_ID + ".oathbound_tab"))
                    .icon(() -> new ItemStack(ItemRegistry.OATHBOUND_RELIC.get()))
                    .displayItems((parameters, output) -> {
                        for (Supplier<? extends ItemLike> entry : TAB_ITEMS) {
                            output.accept(entry.get());
                        }
                    })
                    .build()
    );

    private TabRegistry() {
    }

    public static void register() {
        // Static initializers perform registration on Fabric.
    }

    private static FabricRegistrySupplier<CreativeModeTab> register(String name, Supplier<CreativeModeTab> factory) {
        return new FabricRegistrySupplier<>(Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, name), factory.get()));
    }
}