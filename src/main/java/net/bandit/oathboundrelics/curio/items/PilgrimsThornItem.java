package net.bandit.oathboundrelics.curio.items;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class PilgrimsThornItem extends OathboundCurioItem {

    public PilgrimsThornItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext,
            ResourceLocation id,
            ItemStack stack
    ) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.putAll(super.getAttributeModifiers(slotContext, id, stack));

        if (!isEffectActive(slotContext, stack)) {
            return modifiers;
        }

        ResourceLocation moveSpeedId = ResourceLocation.fromNamespaceAndPath(
                OathboundRelicsMod.MOD_ID,
                id.getPath() + "_move_speed"
        );

        modifiers.put(
                Attributes.MOVEMENT_SPEED,
                new AttributeModifier(
                        moveSpeedId,
                        0.10D,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );

        return modifiers;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        tooltip.add(Component.empty());
        addFlavorTooltip(tooltip, "tooltip.oathboundrelics.pilgrims_thorn.flavor");
        tooltip.add(Component.empty());
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.pilgrims_thorn.desc_1");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.pilgrims_thorn.desc_2");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.pilgrims_thorn.desc_3");
    }
}