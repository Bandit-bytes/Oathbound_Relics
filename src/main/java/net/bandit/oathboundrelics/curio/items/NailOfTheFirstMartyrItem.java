package net.bandit.oathboundrelics.curio.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class NailOfTheFirstMartyrItem extends OathboundCurioItem {

    public NailOfTheFirstMartyrItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        tooltip.add(Component.empty());

        addFlavorTooltip(tooltip, "tooltip.oathboundrelics.nail_of_the_first_martyr.flavor");

        tooltip.add(Component.empty());

        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.nail_of_the_first_martyr.desc_1");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.nail_of_the_first_martyr.desc_2");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.nail_of_the_first_martyr.desc_3");
    }
}