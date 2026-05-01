package net.bandit.oathboundrelics.client;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.bandit.oathboundrelics.util.TitansRemnantUtil;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class TitansRemnantItemProperties {
    private static final ResourceLocation STAGE =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "stage");

    private TitansRemnantItemProperties() {}

    public static void register() {
        register(ItemRegistry.COLOSSUS_HEART.get());
        register(ItemRegistry.EMBER_SEED.get());
        register(ItemRegistry.TIDE_PEARL.get());
        register(ItemRegistry.SKYBRAND_FEATHER.get());
        register(ItemRegistry.NEBULA_LENS.get());
        register(ItemRegistry.VOID_PEARL.get());
    }

    private static void register(Item item) {
        ItemProperties.register(item, STAGE,
                (stack, level, entity, seed) -> TitansRemnantUtil.getStage(stack).modelValue());
    }
}