package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class TabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OathboundRelicsMod.MOD_ID);

    private static final List<Supplier<? extends Item>> TAB_ITEMS = List.of(
            ItemRegistry.OATHBOUND_RELIC,
            ItemRegistry.ASHEN_NAIL,
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
            ItemRegistry.LETHARGIC_GREATSWORD
    );

    public static final Supplier<CreativeModeTab> OATHBOUND_TAB = CREATIVE_MODE_TABS.register(
            "oathbound_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + OathboundRelicsMod.MOD_ID + ".oathbound_tab"))
                    .icon(() -> new ItemStack(ItemRegistry.OATHBOUND_RELIC.get()))
                    .displayItems((parameters, output) -> {
                        for (Supplier<? extends Item> item : TAB_ITEMS) {
                            output.accept(item.get());
                        }
                    })
                    .build()
    );

    private TabRegistry() {
    }

    public static void register(IEventBus modBus) {
        CREATIVE_MODE_TABS.register(modBus);
    }
}