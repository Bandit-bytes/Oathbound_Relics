package net.bandit.oathboundrelics.items;

import net.bandit.oathboundrelics.util.OathboundUtil;
import net.bandit.oathboundrelics.util.SoulHarvestUtil;
import net.bandit.oathboundrelics.util.TooltipAccess;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class SoulLanternItem extends BlockItem implements ICurioItem {

    public SoulLanternItem(Block block, Properties properties) {
        super(block, properties.stacksTo(1));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return "belt".equals(slotContext.identifier());
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.pass(stack);
        }

        // Let block placement happen when the player is targeting a block.
        // This use() method is mainly for your soul-harvest behavior.
        if (!OathboundUtil.isBranded(player)) {
            if (!level.isClientSide()) {
                player.displayClientMessage(
                        Component.translatable("message.oathboundrelics.soul_lantern.requires_oathbound")
                                .withStyle(ChatFormatting.DARK_PURPLE),
                        true
                );
                level.playSound(null, player.blockPosition(), SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 0.8F, 0.8F);
            }
            return InteractionResultHolder.fail(stack);
        }

        if (!SoulHarvestUtil.canHarvest(player)) {
            if (!level.isClientSide()) {
                player.displayClientMessage(
                        Component.translatable("message.oathboundrelics.soul_lantern.too_weak")
                                .withStyle(ChatFormatting.RED),
                        true
                );
                level.playSound(null, player.blockPosition(), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 0.7F, 0.8F);
            }
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide()) {
            SoulHarvestUtil.harvestSoul(player);
            player.getCooldowns().addCooldown(this, 20);
            player.awardStat(Stats.ITEM_USED.get(this));

            player.displayClientMessage(
                    Component.translatable("message.oathboundrelics.soul_lantern.harvested")
                            .withStyle(ChatFormatting.GOLD),
                    true
            );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.soul_lantern.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.empty());

        if (!TooltipAccess.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.oathboundrelics.soul_lantern.hold_shift")
                    .withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(Component.translatable("tooltip.oathboundrelics.soul_lantern.slot")
                    .withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("tooltip.oathboundrelics.soul_lantern.placeable")
                    .withStyle(ChatFormatting.GRAY));
            return;
        }

        tooltip.add(Component.translatable("tooltip.oathboundrelics.soul_lantern.line_1")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable(
                "tooltip.oathboundrelics.soul_lantern.line_2",
                SoulHarvestUtil.getCostDisplay()
        ).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(
                "tooltip.oathboundrelics.soul_lantern.line_3",
                SoulHarvestUtil.getMinimumRemainingDisplay()
        ).withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.soul_lantern.line_4")
                .withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.soul_lantern.placeable_desc")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.oathboundrelics.soul_lantern.release_shift")
                .withStyle(ChatFormatting.DARK_GRAY));
    }
}