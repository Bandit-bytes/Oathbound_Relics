package net.bandit.oathboundrelics.curio.items;

import net.bandit.oathboundrelics.util.OathboundUtil;
import net.bandit.oathboundrelics.util.TooltipAccess;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class BrandkeepersMercyItem extends OathboundCurioItem {

    public BrandkeepersMercyItem(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isEffectActive(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) {
            return false;
        }

        return OathboundUtil.isBranded(player)
                && OathboundUtil.hasNearTotalBrandedAttunement(player);
    }

    private boolean viewerHasAttunement() {
        Player player = TooltipAccess.getClientPlayer();
        return player != null
                && (player.isCreative() || OathboundUtil.hasNearTotalBrandedAttunement(player));
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        super.appendHoverText(stack, context, tooltip, flag);

        tooltip.add(Component.empty());
        addFlavorTooltip(tooltip, "tooltip.oathboundrelics.brandkeepers_mercy.flavor");
        tooltip.add(Component.empty());
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.brandkeepers_mercy.desc_1");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.brandkeepers_mercy.desc_2");
        addRelicEffectTooltip(tooltip, "tooltip.oathboundrelics.brandkeepers_mercy.desc_3");

        if (shouldRevealTooltip() && !viewerHasAttunement()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.oathboundrelics.brandkeepers_mercy.requires_time")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}