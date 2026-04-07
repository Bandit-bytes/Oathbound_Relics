package net.bandit.oathboundrelics.curio.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class FracturedRelicAshItem extends RelicCurioItem {

    private static final ResourceLocation RING_SLOT_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "ash_ring_slots");

    public FracturedRelicAshItem(Properties properties) {
        super(properties, "tooltip.oathboundrelics.fractured_relic_ash.effect");
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext,
            ResourceLocation id,
            ItemStack stack
    ) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.putAll(super.getAttributeModifiers(slotContext, id, stack));

        ResourceLocation armorId = ResourceLocation.fromNamespaceAndPath(
                OathboundRelicsMod.MOD_ID,
                id.getPath() + "_fractured_relic_ash_armor"
        );

        ResourceLocation kbId = ResourceLocation.fromNamespaceAndPath(
                OathboundRelicsMod.MOD_ID,
                id.getPath() + "_fractured_relic_ash_knockback_resist"
        );

        modifiers.put(
                Attributes.ARMOR,
                new AttributeModifier(
                        armorId,
                        5.0D,
                        AttributeModifier.Operation.ADD_VALUE
                )
        );

        modifiers.put(
                Attributes.KNOCKBACK_RESISTANCE,
                new AttributeModifier(
                        kbId,
                        0.15D,
                        AttributeModifier.Operation.ADD_VALUE
                )
        );
        {
            modifiers.putAll(super.getAttributeModifiers(slotContext, id, stack));

            if (OathboundConfig.FracturedAshRing()
                    && OathboundConfig.FracturedRingExtraRingSlots() > 0) {
                CuriosApi.addSlotModifier(
                        modifiers,
                        "ring",
                        RING_SLOT_MODIFIER_ID,
                        OathboundConfig.FracturedRingExtraRingSlots(),
                        AttributeModifier.Operation.ADD_VALUE
                );
            }

            return modifiers;
        }

    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        addFlavorTooltip(tooltip, "tooltip.oathboundrelics.fractured_relic_ash.flavor");

        tooltip.add(Component.empty());

        super.appendHoverText(stack, context, tooltip, flag);

        addDetailTooltip(tooltip, "tooltip.oathboundrelics.fractured_relic_ash.detail_1");
        addDetailTooltip(tooltip, "tooltip.oathboundrelics.fractured_relic_ash.detail_2");

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.oathboundrelics.fractured_relic_ash.note")
                .withStyle(ChatFormatting.DARK_GRAY));
    }
}