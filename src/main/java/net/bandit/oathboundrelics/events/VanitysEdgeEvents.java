package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.PrideStateData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.registry.EffectRegistry;
import net.bandit.oathboundrelics.util.VanitysEdgeUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class VanitysEdgeEvents {

    private static final ResourceLocation STACK_ATTACK_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "vanitys_edge_stack_attack_speed");

    private static final ResourceLocation HIGH_HEALTH_ATTACK_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "vanitys_edge_high_health_attack_speed");

    private static final ResourceLocation INVENTORY_ARMOR_DAMAGE_PENALTY_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "vanitys_edge_inventory_armor_damage_penalty");

    private static final ResourceLocation MID_HEALTH_DAMAGE_PENALTY_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "vanitys_edge_mid_health_damage_penalty");

    private static final ResourceLocation MID_HEALTH_ATTACK_SPEED_PENALTY_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "vanitys_edge_mid_health_attack_speed_penalty");

    private VanitysEdgeEvents() {
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();

        if (!VanitysEdgeUtil.isHoldingVanitysEdgeMainHand(player)) {
            return;
        }

        if (VanitysEdgeUtil.canUseVanitysEdge(player)) {
            return;
        }

        event.setCanceled(true);

        if (!player.level().isClientSide()) {
            player.sendSystemMessage(
                    Component.translatable("message.oathboundrelics.vanitys_edge.locked")
                            .withStyle(ChatFormatting.RED)
            );
        }
    }

    @SubscribeEvent
    public static void onDamagePre(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (event.getSource().getDirectEntity() != player) {
            return;
        }

        if (!VanitysEdgeUtil.isHoldingVanitysEdgeMainHand(player)) {
            return;
        }

        if (!VanitysEdgeUtil.canUseVanitysEdge(player)) {
            return;
        }

        float currentAttackDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float desiredMinimumDamage = (float) (currentAttackDamage + VanitysEdgeUtil.getHighHealthDamageBonus(player));

        if (event.getNewDamage() < desiredMinimumDamage) {
            event.setNewDamage(desiredMinimumDamage);
        }
    }

    @SubscribeEvent
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (event.getSource().getDirectEntity() != player) {
            return;
        }

        if (!VanitysEdgeUtil.isHoldingVanitysEdgeMainHand(player)) {
            return;
        }

        if (!VanitysEdgeUtil.canUseVanitysEdge(player)) {
            return;
        }

        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        if (event.getEntity().isAlive()) {
            float selfDamage = (float) OathboundConfig.vanitysEdgeNonKillSelfDamage();
            player.setHealth(Math.max(0.0F, player.getHealth() - selfDamage));
        }
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (event.getSource().getDirectEntity() != player) {
            return;
        }

        if (!VanitysEdgeUtil.isHoldingVanitysEdgeMainHand(player)) {
            return;
        }

        if (!VanitysEdgeUtil.canUseVanitysEdge(player)) {
            return;
        }

        PrideStateData state = player.getData(AttachmentRegistry.PRIDE_STATE.get());
        state.addKillStack(player.level().getGameTime() + OathboundConfig.vanitysEdgeKillStackDurationTicks());
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(EffectRegistry.BRAVERY)) {
            event.setAmount(event.getAmount() * 0.50F);
        }
    }

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(EffectRegistry.LOSING_PRIDE)) {
            event.setAmount((float) (event.getAmount() * OathboundConfig.vanitysEdgeLosingPrideHealingMultiplier()));
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        if (player.tickCount % OathboundConfig.vanitysEdgeMaintenanceIntervalTicks() != 0) {
            return;
        }

        boolean hasAnywhere = VanitysEdgeUtil.hasVanitysEdge(player);
        PrideStateData state = player.getData(AttachmentRegistry.PRIDE_STATE.get());
        boolean hasActiveStacks = state.hasActiveStacks(player.level().getGameTime());

        if (!hasAnywhere && !hasActiveStacks) {
            updateDynamicModifiers(player, state, false, false);
            return;
        }

        boolean heldMainHand = VanitysEdgeUtil.isHoldingVanitysEdgeMainHand(player);
        boolean canUse = heldMainHand && VanitysEdgeUtil.canUseVanitysEdge(player);

        updateDynamicModifiers(player, state, hasAnywhere, canUse);

        if (hasAnywhere && VanitysEdgeUtil.isBelowLowHealthThreshold(player) && !VanitysEdgeUtil.hasPrideOutcome(player)) {
            rollLowHealthOutcome(player);
        }

        if (canUse && player.tickCount % OathboundConfig.vanitysEdgeAllyAuraIntervalTicks() == 0) {
            applyAllySuppressionAura(player);
        }
    }

    private static void updateDynamicModifiers(Player player, PrideStateData state, boolean hasAnywhere, boolean canUseHeldMainHand) {
        AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);

        if (attackDamage == null || attackSpeed == null) {
            return;
        }

        clearModifier(attackDamage, INVENTORY_ARMOR_DAMAGE_PENALTY_ID);
        clearModifier(attackDamage, MID_HEALTH_DAMAGE_PENALTY_ID);

        clearModifier(attackSpeed, STACK_ATTACK_SPEED_ID);
        clearModifier(attackSpeed, HIGH_HEALTH_ATTACK_SPEED_ID);
        clearModifier(attackSpeed, MID_HEALTH_ATTACK_SPEED_PENALTY_ID);

        int activeStacks = state.pruneExpiredAndCount(player.level().getGameTime());
        if (activeStacks > 0) {
            applyModifier(
                    attackSpeed,
                    STACK_ATTACK_SPEED_ID,
                    activeStacks * OathboundConfig.vanitysEdgeKillStackAttackSpeedBonus(),
                    AttributeModifier.Operation.ADD_VALUE
            );
        }

        if (canUseHeldMainHand && VanitysEdgeUtil.shouldGrantHighHealthAttackSpeed(player)) {
            applyModifier(
                    attackSpeed,
                    HIGH_HEALTH_ATTACK_SPEED_ID,
                    OathboundConfig.vanitysEdgeHighHealthAttackSpeedBonus(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
        }

        if (!hasAnywhere) {
            return;
        }

        double armorPenalty = VanitysEdgeUtil.getArmorAttackPenalty(player);
        if (armorPenalty > 0.0D) {
            applyModifier(
                    attackDamage,
                    INVENTORY_ARMOR_DAMAGE_PENALTY_ID,
                    -armorPenalty,
                    AttributeModifier.Operation.ADD_VALUE
            );
        }

        if (VanitysEdgeUtil.isInMidHealthPenaltyBand(player)) {
            applyModifier(
                    attackDamage,
                    MID_HEALTH_DAMAGE_PENALTY_ID,
                    OathboundConfig.vanitysEdgeMidHealthDamagePenaltyMultiplier(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );

            applyModifier(
                    attackSpeed,
                    MID_HEALTH_ATTACK_SPEED_PENALTY_ID,
                    OathboundConfig.vanitysEdgeMidHealthAttackSpeedPenaltyMultiplier(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
        }
    }

    private static void applyAllySuppressionAura(Player player) {
        List<LivingEntity> nearby = player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(OathboundConfig.vanitysEdgeAllySuppressionRadius()),
                living -> living.isAlive()
                        && !(living instanceof Enemy)
                        && VanitysEdgeUtil.isAlly(player, living)
        );

        for (LivingEntity ally : nearby) {
            ally.addEffect(new MobEffectInstance(
                    EffectRegistry.PRIDE_SUPPRESSION,
                    OathboundConfig.vanitysEdgeAllySuppressionDurationTicks(),
                    0,
                    false,
                    true,
                    true
            ));
        }
    }

    private static void rollLowHealthOutcome(Player player) {
        if (player.getRandom().nextDouble() < OathboundConfig.vanitysEdgeLosingPrideChance()) {
            VanitysEdgeUtil.applyLosingPride(player);
        } else {
            VanitysEdgeUtil.applyBravery(player);
        }
    }

    private static void clearModifier(AttributeInstance attribute, ResourceLocation id) {
        attribute.removeModifier(id);
    }

    private static void applyModifier(AttributeInstance attribute, ResourceLocation id, double amount, AttributeModifier.Operation operation) {
        attribute.addTransientModifier(new AttributeModifier(id, amount, operation));
    }
}