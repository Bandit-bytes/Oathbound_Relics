package net.bandit.oathboundrelics.items;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.util.SlothWeaponUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.List;

public class LethargicFlailItem extends SwordItem {

    private static final Tier LETHARGIC_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            1000,
            1.0F,
            0.0F,
            15,
            () -> Ingredient.EMPTY
    );

    public LethargicFlailItem(Properties properties) {
        super(
                LETHARGIC_TIER,
                properties.attributes(SwordItem.createAttributes(LETHARGIC_TIER, 99, -2.0F))
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, net.minecraft.world.entity.player.Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (!SlothWeaponUtil.canUseLethargicFlail(player)) {
            if (!level.isClientSide) {
                player.sendSystemMessage(
                        Component.translatable("message.oathboundrelics.lethargic_flail.locked")
                                .withStyle(ChatFormatting.RED)
                );
            }
            return InteractionResultHolder.fail(stack);
        }

        // Right-click cube special comes next.
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.requires_brand")
                .withStyle(ChatFormatting.RED));

        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.requires_time")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.held_effects_header")
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(Component.translatable(
                "tooltip.oathboundrelics.lethargic_flail.held_slow",
                formatSignedPercent(OathboundConfig.lethargicFlailInventoryMoveSpeedMultiplier())
        ).withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.translatable(
                "tooltip.oathboundrelics.lethargic_flail.held_hunger",
                formatPercentFromExhaustionPerSecond(OathboundConfig.lethargicFlailInventoryExhaustionPerSecond())
        ).withStyle(ChatFormatting.GRAY));
    }

    private static String formatSignedPercent(double value) {
        return Math.round(value * 100.0D) + "%";
    }

    private static String formatPercentFromExhaustionPerSecond(double value) {
        // purely display wording for your chosen default
        // 0.05 exhaustion per second is being used as the "50% faster" design target
        return Math.round((value / 0.05D) * 50.0D) + "%";
    }
}