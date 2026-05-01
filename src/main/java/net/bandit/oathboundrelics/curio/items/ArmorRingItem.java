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

public abstract class ArmorRingItem extends RelicCurioItem {

    private final String flavorKey;
    private final String desc1Key;
    private final String desc2Key;
    private final String desc3Key;

    protected ArmorRingItem(
            Properties properties,
            String flavorKey,
            String desc1Key,
            String desc2Key,
            String desc3Key
    ) {
        super(properties.stacksTo(1));
        this.flavorKey = flavorKey;
        this.desc1Key = desc1Key;
        this.desc2Key = desc2Key;
        this.desc3Key = desc3Key;
    }
    protected abstract boolean isRingEnabled();
    protected abstract double getArmorBonus();

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
                id.getPath() + "_armor"
        );

        if (isRingEnabled() && getArmorBonus() > 0.0D) {
            modifiers.put(
                    Attributes.ARMOR,
                    new AttributeModifier(
                            armorId,
                            getArmorBonus(),
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        }

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
        addFlavorTooltip(tooltip, flavorKey);
        tooltip.add(Component.empty());
        addRelicEffectTooltip(tooltip, desc1Key);
        addRelicEffectTooltip(tooltip, desc2Key);
        addDetailTooltip(tooltip, desc3Key);
    }
}