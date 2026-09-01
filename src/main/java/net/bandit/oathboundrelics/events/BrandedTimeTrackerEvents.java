package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.data.PlayerDataStorage;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.world.entity.player.Player;
import net.bandit.oathboundrelics.fabricbridge.events.tick.PlayerTickEvent;

public final class BrandedTimeTrackerEvents {

    private BrandedTimeTrackerEvents() {
    }
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        BrandedTimeData data = PlayerDataStorage.brandedTime(player);

        boolean branded = OathboundUtil.isBranded(player);
        boolean active = OathboundUtil.isMeaningfullyActive(player);

        data.tick(
                branded,
                active,
                OathboundConfig.slothWeaponMaxBrandedTicks()
        );
    }
}
