package net.bandit.oathboundrelics.items;

import net.bandit.oathboundrelics.util.SoulHarvestUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SoulGemItem extends Item {

    public SoulGemItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.pass(stack);
        }

        if (!SoulHarvestUtil.canRestore(player)) {
            if (!level.isClientSide()) {
                player.displayClientMessage(
                        Component.translatable("message.oathboundrelics.soul_gem.no_debt")
                                .withStyle(ChatFormatting.GRAY),
                        true
                );
                level.playSound(
                        null,
                        player.blockPosition(),
                        SoundEvents.AMETHYST_BLOCK_RESONATE,
                        SoundSource.PLAYERS,
                        0.8F,
                        0.8F
                );
            }
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide()) {
            SoulHarvestUtil.restoreSoul(player);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            player.displayClientMessage(
                    Component.translatable("message.oathboundrelics.soul_gem.restored")
                            .withStyle(ChatFormatting.AQUA),
                    true
            );
            level.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.ALLAY_ITEM_GIVEN,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.1F
            );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}