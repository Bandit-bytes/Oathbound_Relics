package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class AredriteArmorEvents {

    private static final ResourceLocation AREDRITE_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "aredrite_speed");

    private static final String TAG_LAST_HORIZON_COOLDOWN = "oathboundrelics.aredrite_last_horizon_cooldown";
    private static final String TAG_LAST_HORIZON_ACTIVE = "oathboundrelics.aredrite_last_horizon_active";

    private static final int LAST_HORIZON_COOLDOWN_TICKS = 20 * 180;
    private static final int LAST_HORIZON_ACTIVE_TICKS = 20 * 10;

    private AredriteArmorEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }

        if (player.tickCount % 10 != 0) {
            return;
        }

        int pieces = getAredritePieceCount(player);
        boolean branded = OathboundUtil.isBranded(player);

        updateLeggingsSpeed(player, branded);

        if (pieces <= 0) {
            return;
        }

        if (isWearingHelmet(player)) {
            applyHelmetEffects(player, pieces, branded);
        }

        if (isWearingChestplate(player)) {
            applyChestplateEffects(player, branded);
        }

        if (pieces >= 2 && branded) {
            applyWitnessedBonus(player);
        }

        if (pieces >= 3 && branded) {
            applyHollowPressure(player);
        }
    }

    @SubscribeEvent
    public static void onDamagePre(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        int pieces = getAredritePieceCount(player);
        if (pieces <= 0) {
            return;
        }

        boolean branded = OathboundUtil.isBranded(player);

        if (isWearingBoots(player) && event.getSource().is(DamageTypes.FALL)) {
            if (player.level().dimension() == Level.END) {
                event.setNewDamage(0.0F);
                return;
            } else {
                event.setNewDamage(event.getNewDamage() * 0.35F);
            }
        }

        if (pieces >= 4 && branded && canTriggerLastHorizon(player, event.getNewDamage())) {
            triggerLastHorizon(player);
            event.setNewDamage(Math.max(0.0F, player.getHealth() - 1.0F));
        }
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        if (!isLastHorizonActive(player)) {
            return;
        }

        player.heal(2.0F);
        player.getFoodData().eat(2, 0.2F);
    }

    private static void applyHelmetEffects(Player player, int pieces, boolean branded) {
        if (player.hasEffect(MobEffects.BLINDNESS)) {
            player.removeEffect(MobEffects.BLINDNESS);
        }

        if (player.hasEffect(MobEffects.DARKNESS)) {
            player.removeEffect(MobEffects.DARKNESS);
        }

        double radius = branded ? 16.0D : 10.0D;
        if (pieces >= 2 && branded) {
            radius = 20.0D;
        }

        List<LivingEntity> targets = player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(radius),
                living -> living.isAlive() && living != player && (living instanceof Enemy || living.isInvisible())
        );

        for (LivingEntity target : targets) {
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60, 0, false, false, true));
        }
    }

    private static void applyChestplateEffects(Player player, boolean branded) {
        double threshold = player.getMaxHealth() * 0.35D;
        if (player.getHealth() > threshold) {
            return;
        }

        int amplifier = branded ? 1 : 0;
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, amplifier, false, false, true));
    }

    private static void applyWitnessedBonus(Player player) {
        List<LivingEntity> hostiles = player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(20.0D),
                living -> living.isAlive() && living != player && living instanceof Enemy
        );

        for (LivingEntity hostile : hostiles) {
            hostile.addEffect(new MobEffectInstance(MobEffects.GLOWING, 80, 0, false, false, true));
        }
    }

    private static void applyHollowPressure(Player player) {
        List<LivingEntity> hostiles = player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(8.0D),
                living -> living.isAlive() && living != player && living instanceof Enemy
        );

        if (hostiles.size() < 3) {
            return;
        }

        for (LivingEntity hostile : hostiles) {
            hostile.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, false, false, true));
            hostile.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, false, false, true));
        }

        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0, false, false, true));
    }

    private static void updateLeggingsSpeed(Player player, boolean branded) {
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            return;
        }

        movementSpeed.removeModifier(AREDRITE_SPEED_ID);

        if (!isWearingLeggings(player)) {
            return;
        }

        double amount = 0.04D;
        if (branded) {
            amount = 0.08D;
        }

        if (player.level().dimension() == Level.END) {
            amount += 0.04D;
        }

        movementSpeed.addTransientModifier(
                new AttributeModifier(
                        AREDRITE_SPEED_ID,
                        amount,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );
    }

    private static boolean canTriggerLastHorizon(Player player, float incomingDamage) {
        if (incomingDamage < player.getHealth()) {
            return false;
        }

        CompoundTag data = player.getPersistentData();
        long cooldownEnd = data.getLong(TAG_LAST_HORIZON_COOLDOWN);
        return player.level().getGameTime() >= cooldownEnd;
    }

    private static void triggerLastHorizon(Player player) {
        long gameTime = player.level().getGameTime();
        CompoundTag data = player.getPersistentData();

        data.putLong(TAG_LAST_HORIZON_COOLDOWN, gameTime + LAST_HORIZON_COOLDOWN_TICKS);
        data.putLong(TAG_LAST_HORIZON_ACTIVE, gameTime + LAST_HORIZON_ACTIVE_TICKS);

        if (player.hasEffect(MobEffects.BLINDNESS)) {
            player.removeEffect(MobEffects.BLINDNESS);
        }

        if (player.hasEffect(MobEffects.DARKNESS)) {
            player.removeEffect(MobEffects.DARKNESS);
        }

        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 1, false, true, true));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 2, false, true, true));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 120, 0, false, true, true));

        List<LivingEntity> hostiles = player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(12.0D),
                living -> living.isAlive() && living != player && living instanceof Enemy
        );

        for (LivingEntity hostile : hostiles) {
            hostile.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0, false, true, true));
            hostile.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1, false, true, true));
            hostile.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1, false, true, true));
        }
    }

    private static boolean isLastHorizonActive(Player player) {
        return player.getPersistentData().getLong(TAG_LAST_HORIZON_ACTIVE) > player.level().getGameTime();
    }

    private static int getAredritePieceCount(Player player) {
        int count = 0;
        if (isWearingHelmet(player)) count++;
        if (isWearingChestplate(player)) count++;
        if (isWearingLeggings(player)) count++;
        if (isWearingBoots(player)) count++;
        return count;
    }

    private static boolean isWearingHelmet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.AREDRITE_HELMET.get());
    }

    private static boolean isWearingChestplate(Player player) {
        return player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.AREDRITE_CHESTPLATE.get());
    }

    private static boolean isWearingLeggings(Player player) {
        return player.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.AREDRITE_LEGGINGS.get());
    }

    private static boolean isWearingBoots(Player player) {
        return player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.AREDRITE_BOOTS.get());
    }
}