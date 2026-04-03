package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.data.EnvyStateData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.util.CovetfangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class CovetfangEvents {

    private static final ResourceLocation STOLEN_VITALITY_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "covetfang_stolen_vitality");
    private static final ResourceLocation STOLEN_PLATING_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "covetfang_stolen_plating");
    private static final ResourceLocation STOLEN_SWIFTNESS_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "covetfang_stolen_swiftness");
    private static final ResourceLocation STOLEN_FEROCITY_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "covetfang_stolen_ferocity");
    private static final ResourceLocation BLESSING_PENALTY_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "covetfang_blessing_penalty");
    private static final ResourceLocation DESPERATE_WANT_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "covetfang_desperate_want_speed");
    private static final ResourceLocation HOPELESS_DAMAGE_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "covetfang_hopeless_damage");
    private static final ResourceLocation HOPELESS_MOVE_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "covetfang_hopeless_move_speed");
    private static final ResourceLocation COVETED_PURSUIT_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "covetfang_coveted_pursuit_speed");

    private CovetfangEvents() {
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (!CovetfangUtil.isEnabled()) {
            return;
        }

        Player player = event.getEntity();

        if (!CovetfangUtil.isHoldingCovetfangMainHand(player)) {
            return;
        }

        if (CovetfangUtil.canUseCovetfang(player)) {
            return;
        }

        event.setCanceled(true);

        if (!player.level().isClientSide()) {
            player.sendSystemMessage(
                    Component.translatable("message.oathboundrelics.covetfang.locked")
                            .withStyle(ChatFormatting.RED)
            );
        }
    }

    @SubscribeEvent
    public static void onDamagePre(LivingDamageEvent.Pre event) {
        if (!CovetfangUtil.isEnabled()) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (event.getSource().getDirectEntity() != player) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        if (!CovetfangUtil.isHoldingCovetfangMainHand(player)) {
            return;
        }

        if (!CovetfangUtil.canUseCovetfang(player)) {
            return;
        }

        EnvyStateData state = player.getData(AttachmentRegistry.ENVY_STATE.get());
        boolean isCoveted = state.isCovetedTarget(target.getUUID(), player.level().getGameTime());

        float currentAttackDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float desiredMinimumDamage = (float) (
                currentAttackDamage + CovetfangUtil.getDamageBonus(player, target, isCoveted)
        );

        if (event.getNewDamage() < desiredMinimumDamage) {
            event.setNewDamage(desiredMinimumDamage);
        }
    }

    @SubscribeEvent
    public static void onDamagePost(LivingDamageEvent.Post event) {
        if (!CovetfangUtil.isEnabled()) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (event.getSource().getDirectEntity() != player) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        if (!CovetfangUtil.isHoldingCovetfangMainHand(player)) {
            return;
        }

        if (!CovetfangUtil.canUseCovetfang(player)) {
            return;
        }

        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        EnvyStateData state = player.getData(AttachmentRegistry.ENVY_STATE.get());
        long gameTime = player.level().getGameTime();

        boolean wasCoveted = state.isCovetedTarget(target.getUUID(), gameTime);
        int envyScore = CovetfangUtil.getEnvyScore(player, target);

        if (!state.hasCovetedTarget(gameTime)) {
            state.setCovetedTarget(target.getUUID(), gameTime + CovetfangUtil.claimDurationTicks());
        }

        target.addEffect(new MobEffectInstance(
                MobEffects.GLOWING,
                CovetfangUtil.covetedGlowDurationTicks(),
                0,
                false,
                true,
                true
        ));

        float selfDamage = 0.0F;

        if (target.isAlive()) {
            selfDamage += CovetfangUtil.baseNonKillSelfDamage();
        }

        if (envyScore <= 0 && !wasCoveted) {
            selfDamage += CovetfangUtil.hollowComparisonExtraSelfDamage();
        }

        if (selfDamage > 0.0F) {
            player.setHealth(Math.max(0.0F, player.getHealth() - selfDamage));
        }

        if (CovetfangUtil.isBelowLowHealthThreshold(player) && CovetfangUtil.hasDesperateWantTarget(player, state)) {
            player.heal(CovetfangUtil.desperateWantHealOnHit());
        }
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!CovetfangUtil.isEnabled()) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (event.getSource().getDirectEntity() != player) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        if (!CovetfangUtil.isHoldingCovetfangMainHand(player)) {
            return;
        }

        if (!CovetfangUtil.canUseCovetfang(player)) {
            return;
        }

        EnvyStateData state = player.getData(AttachmentRegistry.ENVY_STATE.get());
        long gameTime = player.level().getGameTime();

        if (state.isCovetedTarget(target.getUUID(), gameTime)) {
            CovetfangUtil.grantStolenStrengths(player, target, state);
            state.clearCovetedTarget();
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        if (player.tickCount % CovetfangUtil.maintenanceIntervalTicks() != 0) {
            return;
        }

        EnvyStateData state = player.getData(AttachmentRegistry.ENVY_STATE.get());
        long gameTime = player.level().getGameTime();

        boolean hasAnywhere = CovetfangUtil.isEnabled() && CovetfangUtil.hasCovetfang(player);
        boolean hasActiveState = state.hasAnyActiveBuffs(gameTime);

        updateDynamicModifiers(player, state, hasAnywhere);

        if (!CovetfangUtil.isEnabled()) {
            return;
        }

        if (!hasAnywhere && !hasActiveState) {
            return;
        }

        LivingEntity covetedTarget = CovetfangUtil.findCovetedTarget(player, state, CovetfangUtil.claimTrackRadius());
        if (covetedTarget != null) {
            covetedTarget.addEffect(new MobEffectInstance(
                    MobEffects.GLOWING,
                    CovetfangUtil.covetedGlowDurationTicks(),
                    0,
                    false,
                    true,
                    true
            ));
        }

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
    }

    private static void updateDynamicModifiers(Player player, EnvyStateData state, boolean hasAnywhere) {
        AttributeInstance attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance attackSpeed = player.getAttribute(Attributes.ATTACK_SPEED);
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);

        if (attackDamage == null || attackSpeed == null || movementSpeed == null || armor == null || maxHealth == null) {
            return;
        }

        clearModifier(maxHealth, STOLEN_VITALITY_ID);
        clearModifier(armor, STOLEN_PLATING_ID);
        clearModifier(movementSpeed, STOLEN_SWIFTNESS_ID);
        clearModifier(attackSpeed, STOLEN_FEROCITY_ID);
        clearModifier(attackDamage, BLESSING_PENALTY_ID);
        clearModifier(movementSpeed, DESPERATE_WANT_SPEED_ID);
        clearModifier(attackDamage, HOPELESS_DAMAGE_ID);
        clearModifier(movementSpeed, HOPELESS_MOVE_SPEED_ID);
        clearModifier(movementSpeed, COVETED_PURSUIT_SPEED_ID);

        if (!CovetfangUtil.isEnabled()) {
            return;
        }

        long gameTime = player.level().getGameTime();

        if (state.hasStolenVitality(gameTime)) {
            applyModifier(
                    maxHealth,
                    STOLEN_VITALITY_ID,
                    CovetfangUtil.stolenVitalityMaxHealthBonus(),
                    AttributeModifier.Operation.ADD_VALUE
            );
        }

        if (state.hasStolenPlating(gameTime)) {
            applyModifier(
                    armor,
                    STOLEN_PLATING_ID,
                    CovetfangUtil.stolenPlatingArmorBonus(),
                    AttributeModifier.Operation.ADD_VALUE
            );
        }

        if (state.hasStolenSwiftness(gameTime)) {
            applyModifier(
                    movementSpeed,
                    STOLEN_SWIFTNESS_ID,
                    CovetfangUtil.stolenSwiftnessMoveSpeedBonus(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
        }

        if (state.hasStolenFerocity(gameTime)) {
            applyModifier(
                    attackSpeed,
                    STOLEN_FEROCITY_ID,
                    CovetfangUtil.stolenFerocityAttackSpeedBonus(),
                    AttributeModifier.Operation.ADD_VALUE
            );
        }

        if (!hasAnywhere) {
            return;
        }

        int beneficialEffects = CovetfangUtil.countBeneficialEffects(player);
        if (beneficialEffects > 0) {
            applyModifier(
                    attackDamage,
                    BLESSING_PENALTY_ID,
                    -(beneficialEffects * CovetfangUtil.beneficialEffectAttackDamagePenalty()),
                    AttributeModifier.Operation.ADD_VALUE
            );
        }

        LivingEntity covetedTarget = CovetfangUtil.findCovetedTarget(player, state, CovetfangUtil.claimTrackRadius());
        if (covetedTarget != null
                && CovetfangUtil.isHoldingCovetfangMainHand(player)
                && CovetfangUtil.canUseCovetfang(player)) {
            applyModifier(
                    movementSpeed,
                    COVETED_PURSUIT_SPEED_ID,
                    CovetfangUtil.covetedPursuitMoveSpeedBonus(),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
        }

        if (CovetfangUtil.isBelowLowHealthThreshold(player)) {
            if (CovetfangUtil.hasDesperateWantTarget(player, state)) {
                applyModifier(
                        movementSpeed,
                        DESPERATE_WANT_SPEED_ID,
                        CovetfangUtil.desperateWantMoveSpeedBonus(),
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                );
            } else {
                applyModifier(
                        attackDamage,
                        HOPELESS_DAMAGE_ID,
                        CovetfangUtil.hopelessComparisonDamagePenalty(),
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                );
                applyModifier(
                        movementSpeed,
                        HOPELESS_MOVE_SPEED_ID,
                        CovetfangUtil.hopelessComparisonMoveSpeedPenalty(),
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                );
            }
        }
    }

    private static void clearModifier(AttributeInstance attribute, ResourceLocation id) {
        attribute.removeModifier(id);
    }

    private static void applyModifier(AttributeInstance attribute, ResourceLocation id, double amount, AttributeModifier.Operation operation) {
        attribute.addTransientModifier(new AttributeModifier(id, amount, operation));
    }
}