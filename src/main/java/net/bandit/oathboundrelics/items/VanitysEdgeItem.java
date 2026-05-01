package net.bandit.oathboundrelics.items;

import net.bandit.oathboundrelics.util.VanitysEdgeUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.List;

public class VanitysEdgeItem extends SwordItem {

    private static final Tier VANITYS_EDGE_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2000,
            1.0F,
            0.0F,
            15,
            () -> Ingredient.EMPTY
    );

    public VanitysEdgeItem(Properties properties) {
        super(
                VANITYS_EDGE_TIER,
                properties.attributes(SwordItem.createAttributes(VANITYS_EDGE_TIER, 49, -2.5F))
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (!VanitysEdgeUtil.canUseVanitysEdge(player)) {
            if (!level.isClientSide) {
                player.sendSystemMessage(
                        Component.translatable("message.oathboundrelics.vanitys_edge.locked")
                                .withStyle(ChatFormatting.RED)
                );
            }
            return InteractionResultHolder.fail(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.requires_brand")
                .withStyle(ChatFormatting.RED));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.requires_time")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.passive_header")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.passive_full_damage")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.passive_high_health_damage")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.passive_high_health_speed")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.passive_nonkill_cost")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.passive_kill_stacks")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.held_header")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.held_allies")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.inventory_header")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.inventory_armor_penalty")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.inventory_mid_health")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.vanitys_edge.inventory_low_health")
                .withStyle(ChatFormatting.GRAY));
    }
}