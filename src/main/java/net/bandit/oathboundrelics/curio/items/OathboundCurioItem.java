package net.bandit.oathboundrelics.curio.items;

import net.bandit.oathboundrelics.util.OathboundUtil;
import net.bandit.oathboundrelics.util.TooltipAccess;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class OathboundCurioItem extends Item implements ICurioItem {

    public OathboundCurioItem(Properties properties) {
        super(properties);
    }

    protected boolean isEffectActive(SlotContext slotContext, ItemStack stack) {
        return slotContext.entity() instanceof Player player && OathboundUtil.isBranded(player);
    }

    protected boolean shouldRevealTooltip() {
        Player player = TooltipAccess.getClientPlayer();
        return player != null && (player.isCreative() || OathboundUtil.isBranded(player));
    }

    protected void addRelicEffectTooltip(List<Component> tooltip, String translationKey, Object... args) {
        MutableComponent line = Component.translatable(translationKey, args);

        if (shouldRevealTooltip()) {
            tooltip.add(line.withStyle(ChatFormatting.GOLD));
        } else {
            tooltip.add(line.withStyle(ChatFormatting.RED, ChatFormatting.ITALIC, ChatFormatting.OBFUSCATED));
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    protected void addFlavorTooltip(List<Component> tooltip, String translationKey) {
        tooltip.add(Component.translatable(translationKey)
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.requires_oathbound_relic")
                .withStyle(ChatFormatting.DARK_PURPLE));

        if (shouldRevealTooltip()) {
            tooltip.add(Component.translatable("tooltip.oathboundrelics.dormant_without_brand")
                    .withStyle(ChatFormatting.DARK_GRAY));
        } else {
            tooltip.add(Component.translatable("tooltip.oathboundrelics.forbidden_text")
                    .withStyle(ChatFormatting.RED, ChatFormatting.ITALIC, ChatFormatting.OBFUSCATED));
        }
    }
}