package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.items.TitanRemnantType;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.bandit.oathboundrelics.util.TitansRemnantUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class TitansRemnantEvents {
    private static final ResourceLocation COLOSSUS_ARMOR_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "colossus_armor");
    private static final ResourceLocation COLOSSUS_KB_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "colossus_knockback_resist");
    private static final ResourceLocation COLOSSUS_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "colossus_speed_penalty");
    private static final ResourceLocation EMBER_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "ember_speed_bonus");
    private static final ResourceKey<DamageType> ENDER_PEARL_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.withDefaultNamespace("ender_pearl"));

    private TitansRemnantEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        clearDynamicModifiers(player);

        TitanRemnantType type = TitansRemnantUtil.getEquippedType(player);
        if (type == null) {
            TitansRemnantUtil.setWasAirborne(player, false);
            TitansRemnantUtil.setStoredAirborneFall(player, 0.0F);
            return;
        }

        boolean branded = OathboundUtil.isBranded(player);

        switch (type) {
            case COLOSSUS_HEART -> tickColossus(player, branded);
            case EMBER_SEED -> tickEmber(player, branded);
            case TIDE_PEARL -> tickTide(player, branded);
            case SKYBRAND_FEATHER -> tickSkybrand(player, branded);
            case NEBULA_LENS -> tickNebula(player, branded);
            case VOID_PEARL -> tickVoid(player, branded);
        }
    }

    @SubscribeEvent
    public static void onDamagePre(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        TitanRemnantType type = TitansRemnantUtil.getEquippedType(player);
        if (type == null) {
            return;
        }

        boolean branded = OathboundUtil.isBranded(player);

        switch (type) {
            case COLOSSUS_HEART -> handleColossusIncoming(player, event, branded);
            case EMBER_SEED -> handleEmberIncoming(player, event, branded);
            case TIDE_PEARL -> handleTideIncoming(player, event, branded);
            case SKYBRAND_FEATHER -> handleSkybrandIncoming(player, event, branded);
            case NEBULA_LENS -> handleNebulaIncoming(player, event, branded);
            case VOID_PEARL -> handleVoidIncoming(player, event, branded);
        }

        if (event.getSource().getEntity() instanceof Player attacker
                && event.getSource().getDirectEntity() == attacker
                && event.getEntity() instanceof LivingEntity target) {

            TitanRemnantType attackerType = TitansRemnantUtil.getEquippedType(attacker);
            if (attackerType != null) {
                boolean attackerBranded = OathboundUtil.isBranded(attacker);
                handleOutgoingPre(attacker, target, event, attackerType, attackerBranded);
            }
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

        if (!(event.getEntity() instanceof LivingEntity target)) {
            return;
        }

        TitanRemnantType type = TitansRemnantUtil.getEquippedType(player);
        if (type == null) {
            return;
        }

        boolean branded = OathboundUtil.isBranded(player);

        switch (type) {
            case COLOSSUS_HEART -> handleColossusOutgoingPost(player, target);
            case EMBER_SEED -> handleEmberOutgoingPost(player, target, branded);
            case VOID_PEARL -> handleVoidOutgoingPost(player, target, branded);
            default -> {
            }
        }
    }

    private static void tickColossus(Player player, boolean branded) {
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
        AttributeInstance kb = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (armor != null) {
            armor.addTransientModifier(new AttributeModifier(
                    COLOSSUS_ARMOR_ID,
                    4.0D,
                    AttributeModifier.Operation.ADD_VALUE
            ));
        }

        if (kb != null) {
            kb.addTransientModifier(new AttributeModifier(
                    COLOSSUS_KB_ID,
                    0.25D,
                    AttributeModifier.Operation.ADD_VALUE
            ));
        }

        if (speed != null) {
            double penalty = -0.03D;
            if (branded && TitansRemnantUtil.getImpact(player) >= 5) {
                penalty -= 0.05D;
            }

            speed.addTransientModifier(new AttributeModifier(
                    COLOSSUS_SPEED_ID,
                    penalty,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        }
    }

    private static void tickEmber(Player player, boolean branded) {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0, false, false, true));

        if (player.tickCount % 20 == 0) {
            for (MobEffectInstance instance : new ArrayList<>(player.getActiveEffects())) {
                if (instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                    int newDuration = Math.max(1, instance.getDuration() - 20);
                    player.removeEffect(instance.getEffect());
                    player.addEffect(new MobEffectInstance(
                            instance.getEffect(),
                            newDuration,
                            instance.getAmplifier(),
                            instance.isAmbient(),
                            instance.isVisible(),
                            instance.showIcon()
                    ));
                }
            }
        }

        if (branded) {
            if (player.tickCount % 20 == 0) {
                TitansRemnantUtil.setHeat(player, TitansRemnantUtil.getHeat(player) - 1);
            }

            int heat = TitansRemnantUtil.getHeat(player);

            if (heat >= 3) {
                AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speed != null) {
                    speed.addTransientModifier(new AttributeModifier(
                            EMBER_SPEED_ID,
                            0.05D,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    ));
                }
            }

            if (heat >= 8 && player.tickCount % 10 == 0) {
                List<LivingEntity> enemies = player.level().getEntitiesOfClass(
                        LivingEntity.class,
                        player.getBoundingBox().inflate(4.0D),
                        living -> living != player && living.isAlive() && living instanceof Enemy
                );

                for (LivingEntity enemy : enemies) {
                    enemy.igniteForSeconds(2.0F);
                }
            }
        }
    }

    private static void tickTide(Player player, boolean branded) {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60, 0, false, false, true));

        if (player.isInWaterOrBubble()) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 60, 0, false, false, true));

            if (branded && player.tickCount % 10 == 0) {
                List<LivingEntity> enemies = player.level().getEntitiesOfClass(
                        LivingEntity.class,
                        player.getBoundingBox().inflate(6.0D),
                        living -> living != player && living.isAlive() && living instanceof Enemy
                );

                for (LivingEntity enemy : enemies) {
                    enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1, false, false, true));
                    enemy.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0, false, false, true));
                    enemy.setDeltaMovement(
                            enemy.getDeltaMovement().x,
                            Math.min(enemy.getDeltaMovement().y - 0.08D, -0.08D),
                            enemy.getDeltaMovement().z
                    );
                    enemy.hurtMarked = true;
                }
            }
        }
    }

    private static void tickSkybrand(Player player, boolean branded) {
        boolean airborne = !player.onGround();

        if (airborne) {
            TitansRemnantUtil.setStoredAirborneFall(
                    player,
                    Math.max(TitansRemnantUtil.getStoredAirborneFall(player), player.fallDistance)
            );
        }

        if (branded && airborne) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 0, false, false, true));
        }

        boolean wasAirborne = TitansRemnantUtil.wasAirborne(player);
        if (wasAirborne && player.onGround()) {
            float storedFall = TitansRemnantUtil.getStoredAirborneFall(player);

            if (branded && storedFall >= 6.0F) {
                List<LivingEntity> enemies = player.level().getEntitiesOfClass(
                        LivingEntity.class,
                        player.getBoundingBox().inflate(4.5D),
                        living -> living != player && living.isAlive() && living instanceof Enemy
                );

                for (LivingEntity enemy : enemies) {
                    double dx = enemy.getX() - player.getX();
                    double dz = enemy.getZ() - player.getZ();
                    enemy.knockback(1.2D, dx, dz);
                    enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1, false, false, true));
                }
            }

            TitansRemnantUtil.setStoredAirborneFall(player, 0.0F);
        }

        TitansRemnantUtil.setWasAirborne(player, airborne);
    }

    private static void tickNebula(Player player, boolean branded) {
        if (branded && player.tickCount % 20 == 0) {
            long gameTime = player.level().getGameTime();
            if (TitansRemnantUtil.getNebulaBlinkCooldown(player) < gameTime) {
                TitansRemnantUtil.setNebulaBlinkCooldown(player, 0L);
            }
        }
    }

    private static void tickVoid(Player player, boolean branded) {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60, 0, false, false, true));

        if (player.tickCount % 10 == 0) {
            for (MobEffectInstance instance : new ArrayList<>(player.getActiveEffects())) {
                if (instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                    player.removeEffect(instance.getEffect());
                }
            }
        }

        if (branded && TitansRemnantUtil.isLowLight(player) && player.tickCount % 10 == 0) {
            List<LivingEntity> enemies = player.level().getEntitiesOfClass(
                    LivingEntity.class,
                    player.getBoundingBox().inflate(6.0D),
                    living -> living != player && living.isAlive() && living instanceof Enemy
            );

            for (LivingEntity enemy : enemies) {
                enemy.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 0, false, false, true));
                enemy.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0, false, false, true));
                enemy.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false, true));
            }
        }
    }

    private static void handleColossusIncoming(Player player, LivingDamageEvent.Pre event, boolean branded) {
        if (event.getSource().is(DamageTypes.CACTUS)
                || event.getSource().is(DamageTypes.STALAGMITE)
                || event.getSource().is(DamageTypes.SWEET_BERRY_BUSH)
                || event.getSource().is(DamageTypes.IN_WALL)) {
            event.setNewDamage(0.0F);
            return;
        }

        if (event.getSource().is(DamageTypes.MAGIC) || event.getSource().is(DamageTypes.INDIRECT_MAGIC)) {
            event.setNewDamage(event.getNewDamage() * 1.25F);
        } else if (branded && event.getNewDamage() >= 4.0F) {
            TitansRemnantUtil.setImpact(player, TitansRemnantUtil.getImpact(player) + 1);
        }
    }

    private static void handleEmberIncoming(Player player, LivingDamageEvent.Pre event, boolean branded) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setNewDamage(0.0F);
            return;
        }

        Entity attacker = event.getSource().getEntity();

        if (attacker instanceof LivingEntity living && living.isInWaterOrBubble()) {
            event.setNewDamage(event.getNewDamage() * 1.25F);
        }

        if (attacker instanceof LivingEntity living && event.getSource().getDirectEntity() == attacker) {
            living.igniteForSeconds(3.0F);
        }

        if (branded && event.getNewDamage() > 0.0F) {
            TitansRemnantUtil.setHeat(player, TitansRemnantUtil.getHeat(player) + 1);
        }
    }

    private static void handleTideIncoming(Player player, LivingDamageEvent.Pre event, boolean branded) {
        if (event.getSource().is(DamageTypes.DROWN)) {
            event.setNewDamage(0.0F);
            return;
        }

        Entity attacker = event.getSource().getEntity();

        if (attacker instanceof WaterAnimal) {
            event.setNewDamage(event.getNewDamage() * 0.60F);
        }

        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setNewDamage(event.getNewDamage() * 1.35F);
        }
    }

    private static void handleSkybrandIncoming(Player player, LivingDamageEvent.Pre event, boolean branded) {
        if (event.getSource().is(DamageTypes.FALL)) {
            event.setNewDamage(0.0F);
            return;
        }

        if (event.getSource().getDirectEntity() instanceof Projectile && player.getRandom().nextFloat() < 0.40F) {
            event.setNewDamage(0.0F);
            return;
        }

        if (event.getSource().is(DamageTypes.WITHER) || event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) {
            event.setNewDamage(event.getNewDamage() * 1.35F);
        }
    }

    private static void handleNebulaIncoming(Player player, LivingDamageEvent.Pre event, boolean branded) {
        if (event.getSource().is(ENDER_PEARL_DAMAGE)) {
            event.setNewDamage(0.0F);
            return;
        }

        if (event.getSource().is(DamageTypes.MAGIC) || event.getSource().is(DamageTypes.INDIRECT_MAGIC)) {
            event.setNewDamage(event.getNewDamage() * 0.35F);
        }

        if (player.isInWaterOrBubble()) {
            event.setNewDamage(event.getNewDamage() * 1.50F);
        }

        long gameTime = player.level().getGameTime();
        if (branded
                && TitansRemnantUtil.getNebulaBlinkCooldown(player) <= gameTime
                && player.getRandom().nextFloat() < 0.15F) {

            Entity attacker = event.getSource().getEntity();
            if (attacker != null) {
                Vec3 away = player.position().subtract(attacker.position()).normalize().scale(4.0D);
                player.teleportTo(player.getX() + away.x, player.getY(), player.getZ() + away.z);
                TitansRemnantUtil.setNebulaEmpowered(player, true);
                TitansRemnantUtil.setNebulaBlinkCooldown(player, gameTime + 60L);
                event.setNewDamage(0.0F);
            }
        }
    }

    private static void handleVoidIncoming(Player player, LivingDamageEvent.Pre event, boolean branded) {
        if (event.getSource().is(DamageTypes.DROWN)) {
            event.setNewDamage(0.0F);
            return;
        }

        float chance = branded ? 0.35F : 0.30F;
        if (event.getNewDamage() >= player.getHealth() && player.getRandom().nextFloat() < chance) {
            event.setNewDamage(Math.max(0.0F, player.getHealth() - 1.0F));
        }
    }

    private static void handleOutgoingPre(Player player, LivingEntity target, LivingDamageEvent.Pre event, TitanRemnantType type, boolean branded) {
        switch (type) {
            case COLOSSUS_HEART -> {
                int impact = TitansRemnantUtil.getImpact(player);
                if (impact > 0) {
                    event.setNewDamage(event.getNewDamage() + (impact * 2.0F));
                }
            }
            case EMBER_SEED -> {
                int heat = TitansRemnantUtil.getHeat(player);
                if (branded && heat > 0) {
                    event.setNewDamage(event.getNewDamage() + (heat * 0.5F));
                }
                if (player.getRemainingFireTicks() > 0) {
                    target.igniteForSeconds(4.0F);
                }
            }
            case NEBULA_LENS -> {
                if (TitansRemnantUtil.isNebulaEmpowered(player)) {
                    event.setNewDamage(event.getNewDamage() * 2.5F);
                    TitansRemnantUtil.setNebulaEmpowered(player, false);
                }
            }
            case VOID_PEARL -> {
                if (branded && TitansRemnantUtil.isLowLight(player)) {
                    event.setNewDamage(event.getNewDamage() * 1.25F);
                }
            }
            default -> {
            }
        }
    }

    private static void handleColossusOutgoingPost(Player player, LivingEntity target) {
        int impact = TitansRemnantUtil.getImpact(player);
        if (impact <= 0) {
            return;
        }

        List<LivingEntity> enemies = player.level().getEntitiesOfClass(
                LivingEntity.class,
                target.getBoundingBox().inflate(3.5D),
                living -> living != player && living != target && living.isAlive()
        );

        for (LivingEntity enemy : enemies) {
            double dx = enemy.getX() - target.getX();
            double dz = enemy.getZ() - target.getZ();
            enemy.knockback(0.8D + (impact * 0.15D), dx, dz);
            enemy.hurt(target.damageSources().playerAttack(player), impact);
        }

        TitansRemnantUtil.setImpact(player, 0);
    }

    private static void handleEmberOutgoingPost(Player player, LivingEntity target, boolean branded) {
        if (branded) {
            TitansRemnantUtil.setHeat(player, TitansRemnantUtil.getHeat(player) + 1);
        }
        target.igniteForSeconds(4.0F);
    }

    private static void handleVoidOutgoingPost(Player player, LivingEntity target, boolean branded) {
        int amplifier = branded ? 1 : 0;
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 80, amplifier, false, false, true));
    }

    private static void clearDynamicModifiers(Player player) {
        clearModifier(player, Attributes.ARMOR, COLOSSUS_ARMOR_ID);
        clearModifier(player, Attributes.KNOCKBACK_RESISTANCE, COLOSSUS_KB_ID);
        clearModifier(player, Attributes.MOVEMENT_SPEED, COLOSSUS_SPEED_ID);
        clearModifier(player, Attributes.MOVEMENT_SPEED, EMBER_SPEED_ID);
    }

    private static void clearModifier(Player player, net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute, ResourceLocation id) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance != null) {
            instance.removeModifier(id);
        }
    }
}