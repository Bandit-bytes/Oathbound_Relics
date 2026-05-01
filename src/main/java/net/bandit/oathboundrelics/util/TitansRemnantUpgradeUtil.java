package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.curio.items.TitansRemnantItem;
import net.bandit.oathboundrelics.items.TitanRemnantStage;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public final class TitansRemnantUpgradeUtil {

    private TitansRemnantUpgradeUtil() {}

    public static InteractionResultHolder<ItemStack> tryUpgrade(Level level, Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        ItemStack remnant = player.getMainHandItem();
        if (!(remnant.getItem() instanceof TitansRemnantItem)) {
            return InteractionResultHolder.pass(remnant);
        }

        TitanRemnantStage current = TitansRemnantUtil.getStage(remnant);
        if (current == TitanRemnantStage.APEX) {
            return InteractionResultHolder.fail(remnant);
        }

        TitanRemnantStage next = current.next();
        ItemStack catalyst = player.getOffhandItem();

        if (!matchesCatalyst(current, catalyst)) {
            return InteractionResultHolder.fail(remnant);
        }

        if (!TitansRemnantUtil.canAdvance(player, next)) {
            return InteractionResultHolder.fail(remnant);
        }

            if (!level.isClientSide()) {
                ItemStack upgradedRemnant = remnant.copy();
                TitansRemnantUtil.setStage(upgradedRemnant, next);

                ItemStack updatedCatalyst = catalyst.copy();
                if (!player.getAbilities().instabuild) {
                    updatedCatalyst.shrink(1);
                }

                player.setItemInHand(InteractionHand.MAIN_HAND, upgradedRemnant);
                player.setItemInHand(InteractionHand.OFF_HAND, updatedCatalyst);
                player.containerMenu.broadcastChanges();

                player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.0F, 1.0F + (next.id() * 0.1F));

        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getMainHandItem());
    }

    private static boolean matchesCatalyst(TitanRemnantStage current, ItemStack catalyst) {
        return switch (current) {
            case DORMANT -> catalyst.is(ItemRegistry.OBLIVION_STONE.get());
            case LATENT -> catalyst.is(ItemRegistry.VOID_ASHES.get());
            case AWAKENED -> catalyst.is(ItemRegistry.MIRRORSTEEL_IDOL.get());
            case ASCENDED -> catalyst.is(Items.NETHER_STAR);
            case TRANSCENDENT -> catalyst.is(ItemRegistry.SOUL_GEM.get());
            case APEX -> false;
        };
    }
}