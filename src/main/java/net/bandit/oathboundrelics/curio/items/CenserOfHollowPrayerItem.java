package net.bandit.oathboundrelics.curio.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CenserOfHollowPrayerItem extends OathboundCurioItem {

    public CenserOfHollowPrayerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        tooltip.add(Component.empty());

        addFlavorTooltip(tooltip, "tooltip.oathboundrelics.censer_of_hollow_prayer.flavor");

        tooltip.add(Component.empty());

        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.censer_of_hollow_prayer.desc_1");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.censer_of_hollow_prayer.desc_2");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.censer_of_hollow_prayer.desc_3");
    }
}