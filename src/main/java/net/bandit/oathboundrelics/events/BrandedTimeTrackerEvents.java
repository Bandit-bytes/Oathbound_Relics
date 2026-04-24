package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class BrandedTimeTrackerEvents {

    private BrandedTimeTrackerEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());

        boolean branded = OathboundUtil.isBranded(player);
        boolean active = OathboundUtil.isMeaningfullyActive(player);

        data.tick(
                branded,
                active,
                OathboundConfig.slothWeaponMaxBrandedTicks()
        );
    }
}