package net.bandit.oathboundrelics.curio.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class OathboundReliquaryItem extends RelicCurioItem {

    private static final ResourceLocation CHARM_SLOT_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "oathbound_reliquary_charm_slots");

    public OathboundReliquaryItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext,
            ResourceLocation id,
            ItemStack stack
    ) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        if (OathboundConfig.enableOathboundReliquary()
                && OathboundConfig.oathboundReliquaryBonusCharmSlots() > 0) {
            CuriosApi.addSlotModifier(
                    modifiers,
                    "charm",
                    CHARM_SLOT_MODIFIER_ID,
                    OathboundConfig.oathboundReliquaryBonusCharmSlots(),
                    AttributeModifier.Operation.ADD_VALUE
            );
        }

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.oathboundrelics.oathbound_reliquary.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.empty());
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.oathbound_reliquary.desc_1");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.oathbound_reliquary.desc_2");
        addDetailTooltip(tooltip, "tooltip.oathboundrelics.oathbound_reliquary.desc_3");
    }
}