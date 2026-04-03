package net.bandit.oathboundrelics.curio.items;

import net.bandit.oathboundrelics.config.OathboundConfig;

public class GoldRingItem extends ArmorRingItem {

    public GoldRingItem(Properties properties) {
        super(
                properties,
                "tooltip.oathboundrelics.gold_ring.flavor",
                "tooltip.oathboundrelics.gold_ring.desc_1",
                "tooltip.oathboundrelics.gold_ring.desc_2",
                "tooltip.oathboundrelics.gold_ring.desc_3"
        );
    }
    @Override
    protected boolean isRingEnabled() {
        return OathboundConfig.enableGoldRing();
    }

    @Override
    protected double getArmorBonus() {
        return OathboundConfig.goldRingArmorBonus();
    }
}