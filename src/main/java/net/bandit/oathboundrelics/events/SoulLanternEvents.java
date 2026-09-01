package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.util.SoulLanternLightUtil;
import net.bandit.oathboundrelics.fabricbridge.events.entity.player.PlayerEvent;
import net.bandit.oathboundrelics.fabricbridge.events.tick.PlayerTickEvent;

public final class SoulLanternEvents {

    private SoulLanternEvents() {}
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        SoulLanternLightUtil.tick(event.getEntity());
    }
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        SoulLanternLightUtil.clearTrackedLight(event.getEntity());
    }
    public static void onClone(PlayerEvent.Clone event) {
        SoulLanternLightUtil.clearTrackedLight(event.getOriginal());
    }
}