package net.bandit.oathboundrelics.items;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.entity.HeavyCubeProjectileEntity;
import net.bandit.oathboundrelics.registry.EntityRegistry;
import net.bandit.oathboundrelics.util.SlothWeaponUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
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

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            HeavyCubeProjectileEntity cube = new HeavyCubeProjectileEntity(
                    EntityRegistry.HEAVY_CUBE_PROJECTILE.get(),
                    level,
                    player
            );

            cube.setBaseDamage(
                    (float) player.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE)
                            * (float) OathboundConfig.lethargicFlailCubeDamageMultiplier()
            );
            cube.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.1F, 0.0F);

            level.addFreshEntity(cube);
            player.getCooldowns().addCooldown(this, OathboundConfig.lethargicFlailCubeCooldownTicks());
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
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

        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.passive_header")
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.passive_full_damage")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.passive_laziness")
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

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.ability_header")
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.ability_aoe")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.translatable("tooltip.oathboundrelics.lethargic_flail.ability_cube")
                .withStyle(ChatFormatting.GRAY));
    }

    private static String formatSignedPercent(double value) {
        return Math.round(value * 100.0D) + "%";
    }

    private static String formatPercentFromExhaustionPerSecond(double value) {
        return Math.round((value / 0.05D) * 50.0D) + "%";
    }
}