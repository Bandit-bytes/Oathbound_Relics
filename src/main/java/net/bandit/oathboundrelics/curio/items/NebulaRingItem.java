package net.bandit.oathboundrelics.curio.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class NebulaRingItem extends ArmorRingItem {

    private static final ResourceLocation RING_SLOT_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "nebula_ring_slots");

    public NebulaRingItem(Properties properties) {
        super(
                properties,
                "tooltip.oathboundrelics.nebula_ring.flavor",
                "tooltip.oathboundrelics.nebula_ring.desc_1",
                "tooltip.oathboundrelics.nebula_ring.desc_2",
                "tooltip.oathboundrelics.nebula_ring.desc_3"
        );
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext,
            ResourceLocation id,
            ItemStack stack
    ) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.putAll(super.getAttributeModifiers(slotContext, id, stack));

        if (OathboundConfig.enableNebulaRing()
                && OathboundConfig.nebulaRingExtraRingSlots() > 0) {
            CuriosApi.addSlotModifier(
                    modifiers,
                    "ring",
                    RING_SLOT_MODIFIER_ID,
                    OathboundConfig.nebulaRingExtraRingSlots(),
                    AttributeModifier.Operation.ADD_VALUE
            );
        }

        return modifiers;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof net.minecraft.world.entity.player.Player player)) {
            return true;
        }

        return CuriosApi.getCuriosInventory(player)
                .map(handler -> !handler.isEquipped(stack1 -> stack1.is(this)))
                .orElse(true);
    }
    @Override
    protected boolean isRingEnabled() {
        return OathboundConfig.enableNebulaRing();
    }

    @Override
    protected double getArmorBonus() {
        return OathboundConfig.nebulaRingArmorBonus();
    }
}