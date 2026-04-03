package net.bandit.oathboundrelics.items;

import net.bandit.oathboundrelics.data.EnvyStateData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.util.CovetfangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.List;

public class CovetfangItem extends SwordItem {

    private static final Tier COVETFANG_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            1800,
            1.0F,
            0.0F,
            15,
            () -> Ingredient.EMPTY
    );

    public CovetfangItem(Properties properties) {
        super(
                COVETFANG_TIER,
                properties.attributes(SwordItem.createAttributes(COVETFANG_TIER, 41, -2.2F))
        );
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
        if (usedHand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (target == player || CovetfangUtil.isAlly(player, target)) {
            return InteractionResult.PASS;
        }

        if (!CovetfangUtil.canUseCovetfang(player)) {
            if (!player.level().isClientSide()) {
                player.sendSystemMessage(
                        Component.translatable("message.oathboundrelics.covetfang.locked")
                                .withStyle(ChatFormatting.RED)
                );
            }
            return InteractionResult.FAIL;
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResult.FAIL;
        }

        if (!player.level().isClientSide()) {
            EnvyStateData state = player.getData(AttachmentRegistry.ENVY_STATE.get());
            state.setCovetedTarget(
                    target.getUUID(),
                    player.level().getGameTime() + CovetfangUtil.claimDurationTicks()
            );

            target.addEffect(new MobEffectInstance(
                    MobEffects.GLOWING,
                    CovetfangUtil.covetedGlowDurationTicks(),
                    0,
                    false,
                    true,
                    true
            ));

            player.getCooldowns().addCooldown(this, CovetfangUtil.claimCooldownTicks());
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (!CovetfangUtil.canUseCovetfang(player)) {
            if (!level.isClientSide) {
                player.sendSystemMessage(
                        Component.translatable("message.oathboundrelics.covetfang.locked")
                                .withStyle(ChatFormatting.RED)
                );
            }
            return InteractionResultHolder.fail(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.requires_brand")
                .withStyle(ChatFormatting.RED));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.requires_time")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.passive_header")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.passive_full_damage")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.passive_superiority")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.passive_claim")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.passive_steal")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.passive_cost")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.inventory_header")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.inventory_blessings")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.covetfang.inventory_low_health")
                .withStyle(ChatFormatting.GRAY));
    }
}