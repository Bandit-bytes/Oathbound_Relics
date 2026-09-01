package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.world.entity.player.Player;
import net.bandit.oathboundrelics.fabricbridge.events.entity.living.LivingHealEvent;

public final class OathboundRitualEvents {

    private OathboundRitualEvents() {
    }
    public static void onLivingHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof Player player && OathboundUtil.isInSeveranceRitual(player)) {
            event.setAmount(0.0F);
        }
    }
}