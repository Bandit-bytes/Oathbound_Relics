package net.bandit.oathboundrelics.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SoulGemItem extends Item {

    public SoulGemItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}