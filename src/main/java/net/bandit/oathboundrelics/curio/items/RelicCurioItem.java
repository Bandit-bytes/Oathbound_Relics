package net.bandit.oathboundrelics.curio.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class RelicCurioItem extends Item implements ICurioItem {

    private final String effectKey;

    public RelicCurioItem(Properties properties) {
        super(properties);
        this.effectKey = null;
    }

    public RelicCurioItem(Properties properties, String effectKey) {
        super(properties);
        this.effectKey = effectKey;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    protected void addRelicEffectTooltip(List<Component> tooltip, String translationKey, Object... args) {
        MutableComponent line = Component.translatable(translationKey, args);
        tooltip.add(line.withStyle(ChatFormatting.GOLD));
    }

    protected void addFlavorTooltip(List<Component> tooltip, String translationKey) {
        tooltip.add(Component.translatable(translationKey)
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
    }

    protected void addDetailTooltip(List<Component> tooltip, String translationKey, Object... args) {
        MutableComponent line = Component.translatable(translationKey, args);
        tooltip.add(line.withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            Item.TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        if (effectKey != null && !effectKey.isBlank()) {
            tooltip.add(Component.translatable(effectKey).withStyle(ChatFormatting.GOLD));
        }
    }
}