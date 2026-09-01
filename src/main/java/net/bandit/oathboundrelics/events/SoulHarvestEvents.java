package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.util.SoulHarvestUtil;
import net.bandit.oathboundrelics.fabricbridge.events.entity.player.PlayerEvent;

public final class SoulHarvestEvents {

    private SoulHarvestEvents() {}
    public static void onPlayerClone(PlayerEvent.Clone event) {
        int harvested = SoulHarvestUtil.getHarvestedSouls(event.getOriginal());
        SoulHarvestUtil.setHarvestedSouls(event.getEntity(), harvested);
        SoulHarvestUtil.applySoulTax(event.getEntity());
    }
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        SoulHarvestUtil.applySoulTax(event.getEntity());
    }
}