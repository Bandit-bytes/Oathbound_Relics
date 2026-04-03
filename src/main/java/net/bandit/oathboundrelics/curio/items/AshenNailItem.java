package net.bandit.oathboundrelics.curio.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AshenNailItem extends OathboundCurioItem {

    public AshenNailItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        tooltip.add(Component.empty());
        addFlavorTooltip(tooltip, "tooltip.oathboundrelics.ashen_nail.flavor");
        tooltip.add(Component.empty());
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.ashen_nail.desc_1");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.ashen_nail.desc_2");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.ashen_nail.desc_3");
    }
}