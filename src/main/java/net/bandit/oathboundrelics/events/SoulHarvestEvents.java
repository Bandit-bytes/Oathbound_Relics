package net.bandit.oathboundrelics.event;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.util.SoulHarvestUtil;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class SoulHarvestEvents {

    private SoulHarvestEvents() {}

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        int harvested = SoulHarvestUtil.getHarvestedSouls(event.getOriginal());
        SoulHarvestUtil.setHarvestedSouls(event.getEntity(), harvested);
        SoulHarvestUtil.applySoulTax(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        SoulHarvestUtil.applySoulTax(event.getEntity());
    }
}