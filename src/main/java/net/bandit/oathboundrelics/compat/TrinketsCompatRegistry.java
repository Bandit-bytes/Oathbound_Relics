package net.bandit.oathboundrelics.compat;

import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Supplier;

public final class TrinketsCompatRegistry {

    private static final List<Supplier<? extends ItemLike>> TRINKET_ITEMS = List.of(
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
            ItemRegistry.SOUL_LANTERN,
            ItemRegistry.GOLD_RING,
            ItemRegistry.CYAN_RING,
            ItemRegistry.NEBULA_RING
    );

    private TrinketsCompatRegistry() {
    }

    public static void register() {
        for (Supplier<? extends ItemLike> supplier : TRINKET_ITEMS) {
            Item item = supplier.get().asItem();

            if (item instanceof Trinket trinket) {
                TrinketsApi.registerTrinket(item, trinket);
            }
        }
    }
}