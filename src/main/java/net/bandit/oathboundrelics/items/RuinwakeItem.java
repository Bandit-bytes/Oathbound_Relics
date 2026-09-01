package net.bandit.oathboundrelics.items;

import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.util.RuinwakeUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.bandit.oathboundrelics.fabricbridge.item.FabricSimpleTier;

import java.util.List;

public class RuinwakeItem extends SwordItem {

    private static final Tier RUINWAKE_TIER = new FabricSimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2400,
            1.0F,
            0.0F,
            15,
            () -> Ingredient.EMPTY
    );

    public RuinwakeItem(Properties properties) {
        super(
                RUINWAKE_TIER,
                properties.attributes(SwordItem.createAttributes(RUINWAKE_TIER, 55, -2.7F))
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (!RuinwakeUtil.canUseRuinwake(player)) {
            if (!level.isClientSide) {
                player.sendSystemMessage(
                        Component.translatable("message.oathboundrelics.ruinwake.locked")
                                .withStyle(ChatFormatting.RED)
                );
            }
            return InteractionResultHolder.fail(stack);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        int grudge = RuinwakeUtil.getGrudge(player);
        if (grudge <= 0) {
            if (!level.isClientSide) {
                player.sendSystemMessage(
                        Component.translatable("message.oathboundrelics.ruinwake.no_grudge")
                                .withStyle(ChatFormatting.DARK_AQUA)
                );
            }
            return InteractionResultHolder.fail(stack);
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }

        double radius = OathboundConfig.ruinwakeReleaseRadius();
        double multiplier = OathboundConfig.ruinwakeReleaseBaseDamageMultiplier()
                + grudge * OathboundConfig.ruinwakeReleaseDamagePerStack();
        float damage = (float) (player.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE)
                * multiplier);

        AABB area = player.getBoundingBox().inflate(radius);
        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                area,
                target -> target.isAlive() && !RuinwakeUtil.isAlly(player, target)
        );

        int kills = 0;
        RuinwakeUtil.setReleasing(player, true);
        try {
            for (LivingEntity target : targets) {
                if (!target.hurt(player.damageSources().playerAttack(player), damage)) {
                    continue;
                }

                target.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS,
                        OathboundConfig.ruinwakeWeaknessDurationTicks(),
                        1,
                        false,
                        true,
                        true
                ));
                target.knockback(
                        OathboundConfig.ruinwakeReleaseKnockback(),
                        player.getX() - target.getX(),
                        player.getZ() - target.getZ()
                );

                serverLevel.sendParticles(
                        ParticleTypes.SOUL_FIRE_FLAME,
                        target.getX(),
                        target.getY(0.5D),
                        target.getZ(),
                        12 + grudge * 3,
                        0.35D,
                        0.45D,
                        0.35D,
                        0.03D
                );
                serverLevel.sendParticles(
                        ParticleTypes.PORTAL,
                        target.getX(),
                        target.getY(0.5D),
                        target.getZ(),
                        8 + grudge * 2,
                        0.45D,
                        0.35D,
                        0.45D,
                        0.08D
                );

                if (!target.isAlive()) {
                    kills++;
                }
            }
        } finally {
            RuinwakeUtil.setReleasing(player, false);
        }

        RuinwakeUtil.consumeGrudge(player);
        player.getCooldowns().addCooldown(this, OathboundConfig.ruinwakeReleaseCooldownTicks());

        serverLevel.playSound(
                null,
                player.blockPosition(),
                SoundEvents.WITHER_SHOOT,
                SoundSource.PLAYERS,
                1.0F,
                0.7F + grudge * 0.05F
        );
        serverLevel.sendParticles(
                ParticleTypes.SOUL_FIRE_FLAME,
                player.getX(),
                player.getY(0.5D),
                player.getZ(),
                30 + grudge * 8,
                radius * 0.45D,
                0.35D,
                radius * 0.45D,
                0.04D
        );

        if (kills == 0) {
            float cost = (float) (grudge * OathboundConfig.ruinwakeFailureSelfDamagePerStack());
            player.setHealth(Math.max(1.0F, player.getHealth() - cost));
        }

        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.ruinwake.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.oathboundrelics.ruinwake.requires_brand")
                .withStyle(ChatFormatting.RED));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.ruinwake.requires_time")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.oathboundrelics.ruinwake.passive_header")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable(
                "tooltip.oathboundrelics.ruinwake.passive_grudge",
                OathboundConfig.ruinwakeMaxGrudgeStacks()
        ).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(
                "tooltip.oathboundrelics.ruinwake.passive_damage",
                Math.round(OathboundConfig.ruinwakeDamageBonusPerStack() * 100.0D)
        ).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.ruinwake.passive_decay")
                .withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.oathboundrelics.ruinwake.ability_header")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.ruinwake.ability_release")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.ruinwake.ability_failure")
                .withStyle(ChatFormatting.DARK_RED));
    }
}
