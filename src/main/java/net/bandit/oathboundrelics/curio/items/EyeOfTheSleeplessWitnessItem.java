package net.bandit.oathboundrelics.curio.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EyeOfTheSleeplessWitnessItem extends OathboundCurioItem {

    public EyeOfTheSleeplessWitnessItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        tooltip.add(Component.empty());

        addFlavorTooltip(tooltip, "tooltip.oathboundrelics.eye_of_the_sleepless_witness.flavor");

        tooltip.add(Component.empty());

        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.eye_of_the_sleepless_witness.desc_1");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.eye_of_the_sleepless_witness.desc_2");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.eye_of_the_sleepless_witness.desc_3");
    }
}