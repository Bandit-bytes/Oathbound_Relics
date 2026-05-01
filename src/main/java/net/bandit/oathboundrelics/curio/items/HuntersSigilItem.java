package net.bandit.oathboundrelics.curio.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class HuntersSigilItem extends OathboundCurioItem {

    public HuntersSigilItem(Properties properties) {
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
        addFlavorTooltip(tooltip, "tooltip.oathboundrelics.hunters_sigil.flavor");
        tooltip.add(Component.empty());
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.hunters_sigil.desc_1");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.hunters_sigil.desc_2");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.hunters_sigil.desc_3");
    }
}