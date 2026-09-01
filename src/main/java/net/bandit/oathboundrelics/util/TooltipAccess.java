package net.bandit.oathboundrelics.util;

import net.minecraft.world.entity.player.Player;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public final class TooltipAccess {
    private TooltipAccess() {}

    public static boolean hasShiftDown() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
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
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
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