package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

public final class OathboundUtil {

    private OathboundUtil() {
    }

    public static boolean isBranded(Player player) {
        return hasCurio(player, ItemRegistry.OATHBOUND_RELIC.get());
    }

    public static boolean hasCurio(Player player, Item item) {
        if (player == null || player.isSpectator()) {
            return false;
        }

        return CuriosApi.getCuriosInventory(player)
                .map(curiosInventory -> !curiosInventory.findCurios(item).isEmpty())
                .orElse(false);
    }
}