package net.bandit.oathboundrelics.curio.items;

import net.bandit.oathboundrelics.config.OathboundConfig;

public class CyanRingItem extends ArmorRingItem {

    public CyanRingItem(Properties properties) {
        super(
                properties,
                "tooltip.oathboundrelics.cyan_ring.flavor",
                "tooltip.oathboundrelics.cyan_ring.desc_1",
                "tooltip.oathboundrelics.cyan_ring.desc_2",
                "tooltip.oathboundrelics.cyan_ring.desc_3"
        );
    }
    @Override
    protected boolean isRingEnabled() {
        return OathboundConfig.enableCyanRing();
    }

    @Override
    protected double getArmorBonus() {
        return OathboundConfig.cyanRingArmorBonus();
    }
}