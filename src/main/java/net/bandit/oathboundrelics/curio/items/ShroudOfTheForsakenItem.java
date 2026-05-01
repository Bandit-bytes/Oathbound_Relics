package net.bandit.oathboundrelics.curio.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ShroudOfTheForsakenItem extends RelicCurioItem {

    public ShroudOfTheForsakenItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        tooltip.add(Component.empty());
        addFlavorTooltip(tooltip, "tooltip.oathboundrelics.shroud_of_the_forsaken.flavor");
        tooltip.add(Component.empty());
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.shroud_of_the_forsaken.desc_1");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.shroud_of_the_forsaken.desc_2");
        addDetailTooltip(tooltip, "tooltip.oathboundrelics.shroud_of_the_forsaken.desc_3");
    }
}