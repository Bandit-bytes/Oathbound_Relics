package net.bandit.oathboundrelics.util;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

public final class TooltipAccess {
    private TooltipAccess() {}

    public static boolean hasShiftDown() {
        if (FMLEnvironment.dist != Dist.CLIENT) {
            return false;
        }

        try {
            Class<?> helper = Class.forName("net.bandit.oathboundrelics.client.ClientTooltipHelper");
            return (boolean) helper.getMethod("hasShiftDown").invoke(null);
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    public static Player getClientPlayer() {
        if (FMLEnvironment.dist != Dist.CLIENT) {
            return null;
        }

        try {
            Class<?> helper = Class.forName("net.bandit.oathboundrelics.client.ClientTooltipHelper");
            return (Player) helper.getMethod("getClientPlayer").invoke(null);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }
}