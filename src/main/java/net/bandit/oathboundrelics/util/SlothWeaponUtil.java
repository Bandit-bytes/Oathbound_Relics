package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.world.entity.player.Player;

public final class SlothWeaponUtil {

    private SlothWeaponUtil() {
    }

    public static boolean qualifiesForLethargicFlail(Player player) {
        BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
        return data.qualifies(
                OathboundConfig.slothWeaponMaxBrandedTicks(),
                OathboundConfig.slothWeaponRequiredBrandedPercent()
        );
    }

    public static double getLethargicFlailProgress(Player player) {
        BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
        return data.getProgressRatio(OathboundConfig.slothWeaponMaxBrandedTicks());
    }

    public static boolean canUseLethargicFlail(Player player) {
        if (!OathboundConfig.enableLethargicFlail()) {
            return false;
        }

        // Creative bypass for testing
        if (player.isCreative()) {
            return true;
        }

        return OathboundUtil.isBranded(player) && qualifiesForLethargicFlail(player);
    }

    public static boolean isHoldingLethargicFlail(Player player) {
        return player.getMainHandItem().is(ItemRegistry.LETHARGIC_FLAIL.get())
                || player.getOffhandItem().is(ItemRegistry.LETHARGIC_FLAIL.get());
    }

    public static boolean isHoldingLethargicFlailMainHand(Player player) {
        return player.getMainHandItem().is(ItemRegistry.LETHARGIC_FLAIL.get());
    }
}