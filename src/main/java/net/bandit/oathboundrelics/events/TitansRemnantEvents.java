package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.items.TitanRemnantStage;
import net.bandit.oathboundrelics.items.TitanRemnantType;
import net.bandit.oathboundrelics.util.TitansRemnantUtil;
import net.minecraft.core.Holder;
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
import net.minecraft.world.entity.ai.attributes.Attribute;
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
    private static final ResourceLocation COLOSSUS_ATTACK_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "colossus_tremor_attack");

    private static final ResourceLocation EMBER_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "ember_speed_bonus");
    private static final ResourceLocation EMBER_ATTACK_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "ember_attack_speed_bonus");

    private static final ResourceLocation VOID_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "void_speed_bonus");
    private static final ResourceLocation VOID_ATTACK_SPEED_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "void_attack_speed_bonus");

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

        TitanRemnantStage stage = TitansRemnantUtil.getEffectiveEquippedStage(player);

        switch (type) {
            case COLOSSUS_HEART -> tickColossus(player, stage);
            case EMBER_SEED -> tickEmber(player, stage);
            case TIDE_PEARL -> tickTide(player, stage);
            case SKYBRAND_FEATHER -> tickSkybrand(player, stage);
            case NEBULA_LENS -> tickNebula(player, stage);
            case VOID_PEARL -> tickVoid(player, stage);
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

        TitanRemnantStage stage = TitansRemnantUtil.getEffectiveEquippedStage(player);

        switch (type) {
            case COLOSSUS_HEART -> handleColossusIncoming(player, event, stage);
            case EMBER_SEED -> handleEmberIncoming(player, event, stage);
            case TIDE_PEARL -> handleTideIncoming(player, event, stage);
            case SKYBRAND_FEATHER -> handleSkybrandIncoming(player, event, stage);
            case NEBULA_LENS -> handleNebulaIncoming(player, event, stage);
            case VOID_PEARL -> handleVoidIncoming(player, event, stage);
        }

        if (event.getSource().getEntity() instanceof Player attacker
                && event.getSource().getDirectEntity() == attacker
                && event.getEntity() instanceof LivingEntity target) {

            TitanRemnantType attackerType = TitansRemnantUtil.getEquippedType(attacker);
            if (attackerType != null) {
                TitanRemnantStage attackerStage = TitansRemnantUtil.getEffectiveEquippedStage(attacker);
                handleOutgoingPre(attacker, target, event, attackerType, attackerStage);
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

        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        TitanRemnantType type = TitansRemnantUtil.getEquippedType(player);
        if (type == null) {
            return;
        }

        TitanRemnantStage stage = TitansRemnantUtil.getEffectiveEquippedStage(player);

        switch (type) {
            case COLOSSUS_HEART -> handleColossusOutgoingPost(player, target, stage);
            case EMBER_SEED -> handleEmberOutgoingPost(player, target, stage);
            case TIDE_PEARL -> handleTideOutgoingPost(player, target, stage);
            case SKYBRAND_FEATHER -> handleSkybrandOutgoingPost(player, target, stage);
            case NEBULA_LENS -> handleNebulaOutgoingPost(player, target, stage);
            case VOID_PEARL -> handleVoidOutgoingPost(player, target, stage);
        }
    }

    private static void tickColossus(Player player, TitanRemnantStage stage) {
        long gameTime = player.level().getGameTime();

        if (player.tickCount % 20 == 0 && TitansRemnantUtil.shouldDecayTremor(player, gameTime)) {
            TitansRemnantUtil.setTremor(player, TitansRemnantUtil.getTremor(player) - 1);
            if (TitansRemnantUtil.getTremor(player) > 0) {
                TitansRemnantUtil.refreshTremorDecay(player, gameTime, 40L);
            }
        }

        int tremor = TitansRemnantUtil.getTremor(player);

        double armorBonus = switch (stage) {
            case DORMANT -> 2.0D;
            case LATENT -> 4.0D;
            case AWAKENED -> 6.0D;
            case ASCENDED -> 7.0D;
            case TRANSCENDENT -> 8.0D;
            case APEX -> 10.0D;
        };

        double kbBonus = switch (stage) {
            case DORMANT -> 0.10D;
            case LATENT -> 0.25D;
            case AWAKENED, ASCENDED, TRANSCENDENT, APEX -> 1.00D;
        };

        double basePenalty = switch (stage) {
            case DORMANT -> -0.05D;
            case LATENT -> -0.06D;
            case AWAKENED -> -0.05D;
            case ASCENDED -> -0.03D;
            case TRANSCENDENT, APEX -> 0.00D;
        };

        double tremorAttackBonus = switch (stage) {
            case DORMANT -> 0.0D;
            case LATENT -> tremor * 0.50D;
            case AWAKENED -> tremor * 0.75D;
            case ASCENDED -> tremor * 1.00D;
            case TRANSCENDENT -> tremor * 1.25D;
            case APEX -> tremor * 1.50D;
        };

        double tremorPenaltyRelief = switch (stage) {
            case DORMANT -> 0.0D;
            case LATENT -> tremor * 0.004D;
            case AWAKENED -> tremor * 0.008D;
            case ASCENDED -> tremor * 0.010D;
            case TRANSCENDENT -> tremor * 0.012D;
            case APEX -> tremor * 0.015D;
        };

        double finalSpeed = basePenalty + tremorPenaltyRelief;
        if (stage.atLeast(TitanRemnantStage.AWAKENED) && tremor >= 8) {
            finalSpeed = Math.max(finalSpeed, 0.05D);
        }

        addModifier(player, Attributes.ARMOR, COLOSSUS_ARMOR_ID, armorBonus, AttributeModifier.Operation.ADD_VALUE);
        addModifier(player, Attributes.KNOCKBACK_RESISTANCE, COLOSSUS_KB_ID, kbBonus, AttributeModifier.Operation.ADD_VALUE);

        if (finalSpeed != 0.0D) {
            addModifier(player, Attributes.MOVEMENT_SPEED, COLOSSUS_SPEED_ID, finalSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }

        if (tremorAttackBonus > 0.0D) {
            addModifier(player, Attributes.ATTACK_DAMAGE, COLOSSUS_ATTACK_ID, tremorAttackBonus, AttributeModifier.Operation.ADD_VALUE);
        }
    }

    private static void tickEmber(Player player, TitanRemnantStage stage) {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0, false, false, true));

        boolean touchedHarmful = false;

        if (player.tickCount % 20 == 0) {
            for (MobEffectInstance instance : new ArrayList<>(player.getActiveEffects())) {
                if (instance.getEffect().value().getCategory() != MobEffectCategory.HARMFUL) {
                    continue;
                }

                touchedHarmful = true;

                if (stage.atLeast(TitanRemnantStage.TRANSCENDENT)) {
                    player.removeEffect(instance.getEffect());
                    continue;
                }

                int reduction = switch (stage) {
                    case DORMANT -> 0;
                    case LATENT -> 20;
                    case AWAKENED -> 40;
                    case ASCENDED -> 60;
                    default -> 0;
                };

                if (reduction > 0) {
                    int newDuration = Math.max(1, instance.getDuration() - reduction);
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

        if (touchedHarmful && stage.atLeast(TitanRemnantStage.AWAKENED)) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, 0, false, false, true));
        }

        boolean wetPenalty = switch (stage) {
            case DORMANT, LATENT, AWAKENED -> TitansRemnantUtil.isInWaterOrRain(player);
            case ASCENDED -> player.isInWaterOrBubble();
            case TRANSCENDENT, APEX -> false;
        };

        if (wetPenalty) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 40, 0, false, false, true));
        }

        if (stage.atLeast(TitanRemnantStage.AWAKENED)) {
            int decayInterval = switch (stage) {
                case AWAKENED -> 30;
                case ASCENDED -> 35;
                case TRANSCENDENT -> 40;
                case APEX -> 50;
                default -> 30;
            };

            if (player.tickCount % decayInterval == 0) {
                TitansRemnantUtil.setHeat(player, TitansRemnantUtil.getHeat(player) - 1);
            }
        }

        int heat = TitansRemnantUtil.getHeat(player);

        if (heat >= 3) {
            double speedBonus = switch (stage) {
                case AWAKENED -> 0.05D;
                case ASCENDED -> 0.08D;
                case TRANSCENDENT -> 0.10D;
                case APEX -> 0.12D;
                default -> 0.0D;
            };
            double attackSpeedBonus = switch (stage) {
                case AWAKENED -> 0.05D;
                case ASCENDED -> 0.08D;
                case TRANSCENDENT -> 0.10D;
                case APEX -> 0.15D;
                default -> 0.0D;
            };

            if (speedBonus > 0.0D) {
                addModifier(player, Attributes.MOVEMENT_SPEED, EMBER_SPEED_ID, speedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                addModifier(player, Attributes.ATTACK_SPEED, EMBER_ATTACK_SPEED_ID, attackSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            }
        }

        if (stage == TitanRemnantStage.APEX && heat >= 8) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, false, false, true));
        }
    }

    private static void tickTide(Player player, TitanRemnantStage stage) {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60, 0, false, false, true));

        if (player.isInWaterOrBubble()) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 60, 0, false, false, true));

            if (stage == TitanRemnantStage.APEX) {
                player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 60, 0, false, false, true));
            }
        }

        if (stage.atLeast(TitanRemnantStage.ASCENDED) && TitansRemnantUtil.isInWaterOrRain(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0, false, false, true));
        }

        if (stage.atLeast(TitanRemnantStage.AWAKENED) && player.isInWaterOrBubble() && player.tickCount % 10 == 0) {
            double radius = switch (stage) {
                case AWAKENED -> 6.0D;
                case ASCENDED -> 7.0D;
                case TRANSCENDENT -> 8.0D;
                case APEX -> 9.0D;
                default -> 6.0D;
            };

            List<LivingEntity> enemies = getNearbyEnemies(player, radius);
            for (LivingEntity enemy : enemies) {
                enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 2 : 1, false, false, true));
                enemy.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, stage.atLeast(TitanRemnantStage.ASCENDED) ? 1 : 0, false, false, true));
                enemy.setDeltaMovement(enemy.getDeltaMovement().x, Math.min(enemy.getDeltaMovement().y - 0.08D, -0.08D), enemy.getDeltaMovement().z);
                enemy.hurtMarked = true;
            }
        }

        if (stage.atLeast(TitanRemnantStage.TRANSCENDENT) && player.tickCount % 20 == 0) {
            for (LivingEntity enemy : getNearbyEnemies(player, 6.0D)) {
                enemy.hurt(player.damageSources().drown(), 1.0F);
                enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, false, false, true));
            }
        }
    }

    private static void tickSkybrand(Player player, TitanRemnantStage stage) {
        boolean airborne = !player.onGround();

        if (airborne) {
            TitansRemnantUtil.setStoredAirborneFall(player, Math.max(TitansRemnantUtil.getStoredAirborneFall(player), player.fallDistance));
        }

        if (stage.atLeast(TitanRemnantStage.AWAKENED) && airborne) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 0, false, false, true));
        }

        if (stage.atLeast(TitanRemnantStage.LATENT) && airborne) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 1 : 0, false, false, true));
        }

        long gameTime = player.level().getGameTime();
        if (stage.atLeast(TitanRemnantStage.TRANSCENDENT)
                && !player.onGround()
                && player.isShiftKeyDown()
                && TitansRemnantUtil.getSkybrandAscentCooldown(player) <= gameTime) {

            player.setDeltaMovement(player.getDeltaMovement().x, 1.0D, player.getDeltaMovement().z);
            player.hurtMarked = true;
            cleanseHarmfulEffects(player);
            TitansRemnantUtil.setSkybrandAscentCooldown(player, gameTime + 100L);
        }

        boolean wasAirborne = TitansRemnantUtil.wasAirborne(player);
        if (wasAirborne && player.onGround()) {
            float storedFall = TitansRemnantUtil.getStoredAirborneFall(player);

            float requiredFall = switch (stage) {
                case AWAKENED -> 5.0F;
                case ASCENDED -> 4.0F;
                case TRANSCENDENT -> 4.0F;
                case APEX -> 3.0F;
                default -> 999.0F;
            };

            if (stage.atLeast(TitanRemnantStage.AWAKENED) && storedFall >= requiredFall) {
                double radius = switch (stage) {
                    case AWAKENED -> 4.0D;
                    case ASCENDED -> 4.5D;
                    case TRANSCENDENT -> 5.0D;
                    case APEX -> 6.0D;
                    default -> 4.0D;
                };

                float burstDamage = switch (stage) {
                    case AWAKENED -> 2.0F;
                    case ASCENDED -> 3.0F;
                    case TRANSCENDENT -> 4.0F;
                    case APEX -> 6.0F;
                    default -> 2.0F;
                };

                for (LivingEntity enemy : getNearbyEnemies(player, radius)) {
                    knockbackFrom(enemy, player, switch (stage) {
                        case AWAKENED -> 1.0D;
                        case ASCENDED -> 1.2D;
                        case TRANSCENDENT -> 1.5D;
                        case APEX -> 2.0D;
                        default -> 1.0D;
                    });
                    enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 1 : 0, false, false, true));
                    enemy.hurt(player.damageSources().playerAttack(player), burstDamage);
                }
            }

            TitansRemnantUtil.setStoredAirborneFall(player, 0.0F);
        }

        TitansRemnantUtil.setWasAirborne(player, airborne);
    }

    private static void tickNebula(Player player, TitanRemnantStage stage) {
        long gameTime = player.level().getGameTime();

        if (TitansRemnantUtil.isInWaterOrRain(player)
                && stage.atLeast(TitanRemnantStage.LATENT)
                && TitansRemnantUtil.getNebulaUnstableReactionCooldown(player) <= gameTime) {

            randomShortTeleport(player, 5.0D);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 0, false, false, true));
            TitansRemnantUtil.setNebulaEmpowered(player, true);
            TitansRemnantUtil.setNebulaTeleportWindowAt(player, gameTime + 60L);
            TitansRemnantUtil.setNebulaUnstableReactionCooldown(player, gameTime + 80L);

            if (!stage.atLeast(TitanRemnantStage.ASCENDED)) {
                player.hurt(player.damageSources().magic(), 1.0F);
            }
        }
    }

    private static void tickVoid(Player player, TitanRemnantStage stage) {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60, 0, false, false, true));

        if (player.tickCount % 20 == 0) {
            switch (stage) {
                case DORMANT -> reduceHarmfulEffects(player, 10);
                case LATENT -> reduceHarmfulEffects(player, 20);
                case AWAKENED, ASCENDED, TRANSCENDENT, APEX -> cleanseHarmfulEffects(player);
            }
        }

        boolean empoweredByDarkness = TitansRemnantUtil.isLowLight(player) || TitansRemnantUtil.isInOpenNight(player);

        if (stage.atLeast(TitanRemnantStage.ASCENDED) && empoweredByDarkness) {
            double speedBonus = switch (stage) {
                case ASCENDED -> 0.08D;
                case TRANSCENDENT -> 0.12D;
                case APEX -> 0.15D;
                default -> 0.0D;
            };

            double attackSpeedBonus = switch (stage) {
                case ASCENDED -> 0.10D;
                case TRANSCENDENT -> 0.15D;
                case APEX -> 0.20D;
                default -> 0.0D;
            };

            addModifier(player, Attributes.MOVEMENT_SPEED, VOID_SPEED_ID, speedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            addModifier(player, Attributes.ATTACK_SPEED, VOID_ATTACK_SPEED_ID, attackSpeedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }

        if (stage.atLeast(TitanRemnantStage.AWAKENED) && TitansRemnantUtil.isLowLight(player) && player.tickCount % 10 == 0) {
            double radius = switch (stage) {
                case AWAKENED -> 6.0D;
                case ASCENDED -> 7.0D;
                case TRANSCENDENT -> 8.0D;
                case APEX -> 10.0D;
                default -> 6.0D;
            };

            for (LivingEntity enemy : getNearbyEnemies(player, radius)) {
                enemy.addEffect(new MobEffectInstance(MobEffects.WITHER, 50, stage.atLeast(TitanRemnantStage.ASCENDED) ? 1 : 0, false, false, true));
                enemy.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 1 : 0, false, false, true));

                if (stage == TitanRemnantStage.APEX) {
                    enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 0, false, false, true));
                }

                if (stage.atLeast(TitanRemnantStage.TRANSCENDENT)) {
                    stripBeneficialEffects(enemy);
                }
            }
        }
    }

    private static void handleColossusIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (event.getSource().is(DamageTypes.CACTUS)
                || event.getSource().is(DamageTypes.SWEET_BERRY_BUSH)
                || event.getSource().is(DamageTypes.IN_WALL)
                || (stage.atLeast(TitanRemnantStage.LATENT) && event.getSource().is(DamageTypes.STALAGMITE))) {
            event.setNewDamage(0.0F);
            return;
        }

        if (stage.atLeast(TitanRemnantStage.LATENT)
                && event.getSource().getDirectEntity() instanceof Projectile
                && event.getNewDamage() <= 3.0F) {
            event.setNewDamage(0.0F);
            return;
        }

        if (isMagicDamage(event)) {
            float multiplier = switch (stage) {
                case DORMANT, LATENT -> 1.25F;
                case AWAKENED, ASCENDED -> 1.15F;
                case TRANSCENDENT -> 1.05F;
                case APEX -> 0.70F;
            };
            event.setNewDamage(event.getNewDamage() * multiplier);
            return;
        }

        long gameTime = player.level().getGameTime();
        TitansRemnantUtil.addTremor(player, stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 2 : 1);
        TitansRemnantUtil.refreshTremorDecay(player, gameTime, 200L);

        if (stage.atLeast(TitanRemnantStage.AWAKENED) && event.getNewDamage() >= (player.getMaxHealth() * 0.15F)) {
            for (LivingEntity enemy : getNearbyEnemies(player, 4.0D)) {
                knockbackFrom(enemy, player, 1.2D);
                enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1, false, false, true));
            }
        }

        if (stage.atLeast(TitanRemnantStage.ASCENDED)) {
            float gain = event.getNewDamage() * 0.30F;
            player.setAbsorptionAmount(Math.min(player.getAbsorptionAmount() + gain, 8.0F));
        }
    }

    private static void handleEmberIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setNewDamage(0.0F);
            return;
        }

        if (stage.atLeast(TitanRemnantStage.ASCENDED)
                && event.getSource().getDirectEntity() instanceof Projectile
                && player.getRandom().nextFloat() < 0.30F) {
            event.setNewDamage(0.0F);
            return;
        }

        if (TitansRemnantUtil.isInWaterOrRain(player) && !stage.atLeast(TitanRemnantStage.TRANSCENDENT)) {
            float waterMultiplier = switch (stage) {
                case DORMANT, LATENT -> 1.35F;
                case AWAKENED -> 1.25F;
                case ASCENDED -> 1.10F;
                default -> 1.0F;
            };
            event.setNewDamage(event.getNewDamage() * waterMultiplier);
        }

        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof LivingEntity living && event.getSource().getDirectEntity() == attacker) {
            float burnSeconds = switch (stage) {
                case DORMANT -> 3.0F;
                case LATENT -> 4.0F;
                case AWAKENED -> 5.0F;
                case ASCENDED -> 6.0F;
                case TRANSCENDENT, APEX -> 7.0F;
            };
            living.igniteForSeconds(burnSeconds);
        }

        if (event.getNewDamage() > 0.0F && stage.atLeast(TitanRemnantStage.AWAKENED)) {
            TitansRemnantUtil.addHeat(player, 1);
        }

        if (stage.atLeast(TitanRemnantStage.AWAKENED)
                && player.getHealth() - event.getNewDamage() <= (player.getMaxHealth() * 0.30F)
                && TitansRemnantUtil.getEmberCauterizeCooldown(player) <= player.level().getGameTime()) {

            TitansRemnantUtil.setEmberCauterizeCooldown(player, player.level().getGameTime() + (20L * 180L));

            for (LivingEntity enemy : getNearbyEnemies(player, 4.5D)) {
                knockbackFrom(enemy, player, 1.0D);
                enemy.igniteForSeconds(4.0F);
            }

            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 0, false, false, true));
        }
    }

    private static void handleTideIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (stage.atLeast(TitanRemnantStage.LATENT) && event.getSource().is(DamageTypes.DROWN)) {
            event.setNewDamage(0.0F);
            return;
        }

        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof WaterAnimal) {
            float reduction = switch (stage) {
                case DORMANT -> 0.80F;
                case LATENT -> 0.60F;
                case AWAKENED, ASCENDED, TRANSCENDENT, APEX -> 0.50F;
            };
            event.setNewDamage(event.getNewDamage() * reduction);
        }

        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            float multiplier = switch (stage) {
                case DORMANT, LATENT -> 2.00F;
                case AWAKENED -> 1.75F;
                case ASCENDED -> 1.50F;
                case TRANSCENDENT, APEX -> 1.00F;
            };
            event.setNewDamage(event.getNewDamage() * multiplier);
        }

        if (stage.atLeast(TitanRemnantStage.ASCENDED)
                && event.getNewDamage() >= (player.getMaxHealth() * 0.20F)
                && TitansRemnantUtil.getTideWardCooldown(player) <= player.level().getGameTime()) {

            TitansRemnantUtil.setTideWardCooldown(player, player.level().getGameTime() + (20L * 30L));
            event.setNewDamage(event.getNewDamage() * 0.25F);

            for (LivingEntity enemy : getNearbyEnemies(player, 4.0D)) {
                knockbackFrom(enemy, player, 0.9D);
                enemy.hurt(player.damageSources().playerAttack(player), 2.0F);
            }
        }
    }

    private static void handleSkybrandIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (event.getSource().is(DamageTypes.FALL)) {
            event.setNewDamage(0.0F);
            return;
        }

        boolean projectileLike = event.getSource().getDirectEntity() instanceof Projectile
                || (stage.atLeast(TitanRemnantStage.ASCENDED)
                && (event.getSource().is(DamageTypes.INDIRECT_MAGIC) || event.getSource().is(DamageTypes.MAGIC)));

        if (projectileLike) {
            float chance = switch (stage) {
                case DORMANT -> 0.10F;
                case LATENT -> 0.15F;
                case AWAKENED -> 0.25F;
                case ASCENDED -> 0.35F;
                case TRANSCENDENT -> 0.50F;
                case APEX -> 1.00F;
            };

            if (player.getRandom().nextFloat() < chance) {
                event.setNewDamage(0.0F);
                return;
            }
        }

        if (event.getSource().is(DamageTypes.WITHER) || event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) {
            float multiplier = switch (stage) {
                case DORMANT, LATENT -> 2.00F;
                case AWAKENED, ASCENDED -> 1.75F;
                case TRANSCENDENT -> 1.50F;
                case APEX -> 1.25F;
            };
            event.setNewDamage(event.getNewDamage() * multiplier);
        }

        if (stage == TitanRemnantStage.APEX
                && !player.onGround()
                && event.getSource().getDirectEntity() instanceof LivingEntity attacker
                && !(event.getSource().getDirectEntity() instanceof Projectile)
                && player.getRandom().nextFloat() < 0.50F) {

            event.setNewDamage(0.0F);
            attacker.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, false, false, true));
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, false, false, true));
        }
    }

    private static void handleNebulaIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (event.getSource().is(ENDER_PEARL_DAMAGE)) {
            event.setNewDamage(0.0F);
            return;
        }

        if (isMagicDamage(event)) {
            float multiplier = switch (stage) {
                case DORMANT -> 0.65F;
                case LATENT -> 0.35F;
                case AWAKENED -> 0.20F;
                case ASCENDED -> 0.10F;
                case TRANSCENDENT -> 0.05F;
                case APEX -> 0.00F;
            };
            event.setNewDamage(event.getNewDamage() * multiplier);
            return;
        }

        if (TitansRemnantUtil.isInWaterOrRain(player)) {
            float multiplier = switch (stage) {
                case DORMANT -> 1.50F;
                case LATENT -> 1.45F;
                case AWAKENED -> 1.35F;
                case ASCENDED -> 1.25F;
                case TRANSCENDENT -> 1.15F;
                case APEX -> 1.00F;
            };
            event.setNewDamage(event.getNewDamage() * multiplier);
        }

        if (stage.atLeast(TitanRemnantStage.LATENT)) {
            float evadeChance = switch (stage) {
                case LATENT -> 0.10F;
                case AWAKENED -> 0.20F;
                case ASCENDED -> 0.25F;
                case TRANSCENDENT -> 0.30F;
                case APEX -> 0.35F;
                default -> 0.0F;
            };

            long gameTime = player.level().getGameTime();
            if (TitansRemnantUtil.getNebulaBlinkCooldown(player) <= gameTime
                    && player.getRandom().nextFloat() < evadeChance) {

                Entity attacker = event.getSource().getEntity();
                if (attacker != null) {
                    Vec3 away = player.position().subtract(attacker.position()).normalize().scale(4.0D);
                    player.teleportTo(player.getX() + away.x, player.getY(), player.getZ() + away.z);
                } else {
                    randomShortTeleport(player, 4.0D);
                }

                TitansRemnantUtil.setNebulaEmpowered(player, true);
                TitansRemnantUtil.setNebulaTeleportWindowAt(player, gameTime + 60L);

                long cooldown = switch (stage) {
                    case LATENT -> 70L;
                    case AWAKENED -> 60L;
                    case ASCENDED -> 50L;
                    case TRANSCENDENT -> 40L;
                    case APEX -> 30L;
                    default -> 70L;
                };
                TitansRemnantUtil.setNebulaBlinkCooldown(player, gameTime + cooldown);

                if (stage.atLeast(TitanRemnantStage.TRANSCENDENT)) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, 1, false, false, true));
                }

                event.setNewDamage(0.0F);
            }
        }
    }

    private static void handleVoidIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (event.getSource().is(DamageTypes.DROWN)) {
            event.setNewDamage(0.0F);
            return;
        }

        Entity attacker = event.getSource().getEntity();
        if (stage.atLeast(TitanRemnantStage.ASCENDED)
                && attacker instanceof LivingEntity living
                && living.hasEffect(MobEffects.WITHER)) {
            event.setNewDamage(event.getNewDamage() * 0.80F);
        }

        if (stage == TitanRemnantStage.APEX && TitansRemnantUtil.isLowLight(player)) {
            event.setNewDamage(event.getNewDamage() * 0.75F);
        }

        if (event.getNewDamage() < player.getHealth()) {
            return;
        }

        long gameTime = player.level().getGameTime();

        switch (stage) {
            case DORMANT -> {
                if (player.getRandom().nextFloat() < 0.20F) {
                    event.setNewDamage(Math.max(0.0F, player.getHealth() - 1.0F));
                }
            }
            case LATENT -> {
                if (player.getRandom().nextFloat() < 0.25F) {
                    event.setNewDamage(Math.max(0.0F, player.getHealth() - 1.0F));
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 100, 0, false, false, true));
                }
            }
            case AWAKENED, ASCENDED, TRANSCENDENT, APEX -> {
                long cooldown = switch (stage) {
                    case AWAKENED, ASCENDED -> 20L * 600L;
                    case TRANSCENDENT -> 20L * 480L;
                    case APEX -> 20L * 300L;
                    default -> 20L * 600L;
                };

                if (TitansRemnantUtil.getVoidDeathCooldown(player) <= gameTime) {
                    TitansRemnantUtil.setVoidDeathCooldown(player, gameTime + cooldown);
                    event.setNewDamage(0.0F);

                    if (stage.atLeast(TitanRemnantStage.TRANSCENDENT)) {
                        player.setHealth(player.getMaxHealth());
                    } else {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 1, false, false, true));
                        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 1, false, false, true));
                    }

                    for (LivingEntity enemy : getNearbyEnemies(player, stage == TitanRemnantStage.APEX ? 8.0D : 6.0D)) {
                        knockbackFrom(enemy, player, stage == TitanRemnantStage.APEX ? 1.5D : 1.0D);
                        enemy.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1, false, false, true));
                        enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false, false, true));
                    }
                }
            }
        }
    }

    private static void handleOutgoingPre(Player player, LivingEntity target, LivingDamageEvent.Pre event, TitanRemnantType type, TitanRemnantStage stage) {
        switch (type) {
            case COLOSSUS_HEART -> {
                // Tremor damage is mostly handled through attribute modifiers now.
                if (stage == TitanRemnantStage.APEX && TitansRemnantUtil.getTremor(player) >= 10) {
                    event.setNewDamage(event.getNewDamage() * 1.20F);
                }
            }
            case EMBER_SEED -> {
                int heat = TitansRemnantUtil.getHeat(player);
                if (heat > 0 && stage.atLeast(TitanRemnantStage.AWAKENED)) {
                    float perHeat = switch (stage) {
                        case AWAKENED -> 0.25F;
                        case ASCENDED -> 0.50F;
                        case TRANSCENDENT -> 0.75F;
                        case APEX -> 1.00F;
                        default -> 0.0F;
                    };
                    event.setNewDamage(event.getNewDamage() + (heat * perHeat));
                }

                if (target.isOnFire() && stage.atLeast(TitanRemnantStage.ASCENDED)) {
                    event.setNewDamage(event.getNewDamage() * 1.10F);
                }
            }
            case TIDE_PEARL -> {
                if (TitansRemnantUtil.isInWaterOrRain(player) && stage.atLeast(TitanRemnantStage.LATENT)) {
                    float multiplier = switch (stage) {
                        case LATENT -> 1.20F;
                        case AWAKENED -> 1.25F;
                        case ASCENDED -> 1.30F;
                        case TRANSCENDENT -> 1.35F;
                        case APEX -> 1.45F;
                        default -> 1.0F;
                    };
                    event.setNewDamage(event.getNewDamage() * multiplier);
                }

                if (stage == TitanRemnantStage.APEX
                        && target.getHealth() <= (target.getMaxHealth() * 0.15F)
                        && (target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || target.hasEffect(MobEffects.WEAKNESS))) {
                    event.setNewDamage(event.getNewDamage() + 1000.0F);
                }
            }
            case SKYBRAND_FEATHER -> {
                if (!player.onGround() && stage.atLeast(TitanRemnantStage.LATENT)) {
                    float airborneMultiplier = switch (stage) {
                        case LATENT -> 1.20F;
                        case AWAKENED -> 1.25F;
                        case ASCENDED -> 1.35F;
                        case TRANSCENDENT -> 1.45F;
                        case APEX -> 1.60F;
                        default -> 1.0F;
                    };
                    event.setNewDamage(event.getNewDamage() * airborneMultiplier);
                }

                if (stage.atLeast(TitanRemnantStage.AWAKENED) && player.fallDistance > 5.0F) {
                    float plungeMultiplier = 1.0F + Math.min(player.fallDistance, 15.0F) * 0.03F;
                    event.setNewDamage(event.getNewDamage() * plungeMultiplier);
                }
            }
            case NEBULA_LENS -> {
                if (stage.atLeast(TitanRemnantStage.AWAKENED)
                        && (TitansRemnantUtil.isNebulaEmpowered(player)
                        || TitansRemnantUtil.isNebulaTeleportWindowActive(player, player.level().getGameTime()))) {

                    float multiplier = switch (stage) {
                        case AWAKENED -> 1.50F;
                        case ASCENDED -> 1.75F;
                        case TRANSCENDENT -> 2.00F;
                        case APEX -> 2.25F;
                        default -> 1.0F;
                    };
                    event.setNewDamage(event.getNewDamage() * multiplier);
                }
            }
            case VOID_PEARL -> {
                boolean empoweredByDarkness = TitansRemnantUtil.isLowLight(player) || TitansRemnantUtil.isInOpenNight(player);

                if (stage.atLeast(TitanRemnantStage.AWAKENED) && empoweredByDarkness) {
                    float multiplier = switch (stage) {
                        case AWAKENED -> 1.15F;
                        case ASCENDED -> 1.25F;
                        case TRANSCENDENT -> 1.35F;
                        case APEX -> 1.50F;
                        default -> 1.0F;
                    };
                    event.setNewDamage(event.getNewDamage() * multiplier);
                }

                if (stage.atLeast(TitanRemnantStage.ASCENDED) && target.hasEffect(MobEffects.WITHER)) {
                    event.setNewDamage(event.getNewDamage() * 1.15F);
                }

                if (stage.atLeast(TitanRemnantStage.TRANSCENDENT)
                        && target.hasEffect(MobEffects.WITHER)
                        && target.getHealth() <= (target.getMaxHealth() * 0.15F)) {
                    event.setNewDamage(event.getNewDamage() + 1000.0F);
                }
            }
        }
    }

    private static void handleColossusOutgoingPost(Player player, LivingEntity target, TitanRemnantStage stage) {
        if (!stage.atLeast(TitanRemnantStage.AWAKENED)) {
            return;
        }

        int tremor = TitansRemnantUtil.getTremor(player);
        if (tremor < 8) {
            return;
        }

        double radius = switch (stage) {
            case AWAKENED -> 3.0D;
            case ASCENDED -> 3.5D;
            case TRANSCENDENT -> 4.0D;
            case APEX -> 5.0D;
            default -> 3.0D;
        };

        float splashDamage = switch (stage) {
            case AWAKENED -> 3.0F;
            case ASCENDED -> 4.0F;
            case TRANSCENDENT -> 5.0F;
            case APEX -> 7.0F;
            default -> 3.0F;
        };

        for (LivingEntity enemy : target.level().getEntitiesOfClass(
                LivingEntity.class,
                target.getBoundingBox().inflate(radius),
                living -> living != player && living != target && living.isAlive()
        )) {
            knockbackFrom(enemy, target, 1.2D + (tremor * 0.05D));
            enemy.hurt(target.damageSources().playerAttack(player), splashDamage);
        }

        TitansRemnantUtil.setTremor(player, 0);
    }

    private static void handleEmberOutgoingPost(Player player, LivingEntity target, TitanRemnantStage stage) {
        target.igniteForSeconds(switch (stage) {
            case DORMANT -> 3.0F;
            case LATENT -> 4.0F;
            case AWAKENED -> 5.0F;
            case ASCENDED -> 6.0F;
            case TRANSCENDENT, APEX -> 7.0F;
        });

        if (stage.atLeast(TitanRemnantStage.AWAKENED)) {
            TitansRemnantUtil.addHeat(player, 1);
        }

        int heat = TitansRemnantUtil.getHeat(player);
        if (heat >= 10 && stage.atLeast(TitanRemnantStage.LATENT)) {
            for (LivingEntity enemy : target.level().getEntitiesOfClass(
                    LivingEntity.class,
                    target.getBoundingBox().inflate(4.0D),
                    living -> living != player && living.isAlive()
            )) {
                enemy.igniteForSeconds(stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 6.0F : 4.0F);
                enemy.hurt(player.damageSources().inFire(), stage.atLeast(TitanRemnantStage.APEX) ? 6.0F : 3.0F);
            }

            TitansRemnantUtil.setHeat(player, Math.max(0, heat - 5));
        }

        if (stage.atLeast(TitanRemnantStage.ASCENDED) && target.isOnFire()) {
            player.heal(1.0F);
        }
    }

    private static void handleTideOutgoingPost(Player player, LivingEntity target, TitanRemnantStage stage) {
        if (!stage.atLeast(TitanRemnantStage.AWAKENED)) {
            return;
        }

        float chance = switch (stage) {
            case AWAKENED -> 0.25F;
            case ASCENDED -> 0.30F;
            case TRANSCENDENT -> 0.35F;
            case APEX -> 0.40F;
            default -> 0.0F;
        };

        if (player.getRandom().nextFloat() < chance) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 2 : 1, false, false, true));
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, stage.atLeast(TitanRemnantStage.ASCENDED) ? 1 : 0, false, false, true));
        }

        if (stage.atLeast(TitanRemnantStage.TRANSCENDENT) && target.getArmorValue() >= 10) {
            target.setDeltaMovement(target.getDeltaMovement().x, 0.8D, target.getDeltaMovement().z);
            target.hurtMarked = true;
        }
    }

    private static void handleSkybrandOutgoingPost(Player player, LivingEntity target, TitanRemnantStage stage) {
        if (!stage.atLeast(TitanRemnantStage.AWAKENED) || player.fallDistance <= 5.0F) {
            return;
        }

        double radius = switch (stage) {
            case AWAKENED -> 3.0D;
            case ASCENDED -> 3.5D;
            case TRANSCENDENT -> 4.0D;
            case APEX -> 5.0D;
            default -> 3.0D;
        };

        float splashDamage = switch (stage) {
            case AWAKENED -> 2.0F;
            case ASCENDED -> 3.0F;
            case TRANSCENDENT -> 4.0F;
            case APEX -> 6.0F;
            default -> 2.0F;
        };

        for (LivingEntity enemy : target.level().getEntitiesOfClass(
                LivingEntity.class,
                target.getBoundingBox().inflate(radius),
                living -> living != player && living != target && living.isAlive()
        )) {
            knockbackFrom(enemy, target, 0.8D);
            enemy.hurt(target.damageSources().playerAttack(player), splashDamage);
        }

        if (stage.atLeast(TitanRemnantStage.ASCENDED) && !target.isAlive()) {
            player.setDeltaMovement(player.getDeltaMovement().x, 0.8D, player.getDeltaMovement().z);
            player.hurtMarked = true;
        }
    }

    private static void handleNebulaOutgoingPost(Player player, LivingEntity target, TitanRemnantStage stage) {
        if (stage.atLeast(TitanRemnantStage.AWAKENED)
                && (TitansRemnantUtil.isNebulaEmpowered(player)
                || TitansRemnantUtil.isNebulaTeleportWindowActive(player, player.level().getGameTime()))) {

            TitansRemnantUtil.setNebulaEmpowered(player, false);
            TitansRemnantUtil.setNebulaTeleportWindowAt(player, 0L);

            if (stage.atLeast(TitanRemnantStage.ASCENDED)) {
                for (LivingEntity enemy : target.level().getEntitiesOfClass(
                        LivingEntity.class,
                        target.getBoundingBox().inflate(3.0D),
                        living -> living != player && living.isAlive()
                )) {
                    knockbackFrom(enemy, target, 0.9D);
                    enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0, false, false, true));
                    enemy.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, false, false, true));
                }
            }
        }
    }

    private static void handleVoidOutgoingPost(Player player, LivingEntity target, TitanRemnantStage stage) {
        int amplifier = switch (stage) {
            case DORMANT, LATENT -> 0;
            case AWAKENED, ASCENDED -> 1;
            case TRANSCENDENT, APEX -> 2;
        };

        int duration = switch (stage) {
            case DORMANT -> 60;
            case LATENT -> 100;
            case AWAKENED -> 140;
            case ASCENDED -> 180;
            case TRANSCENDENT -> 220;
            case APEX -> 260;
        };

        target.addEffect(new MobEffectInstance(MobEffects.WITHER, duration, amplifier, false, false, true));

        if (stage.atLeast(TitanRemnantStage.AWAKENED) && target.hasEffect(MobEffects.WITHER)) {
            player.heal(stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 1.0F : 0.5F);
        }
    }

    private static boolean isMagicDamage(LivingDamageEvent.Pre event) {
        return event.getSource().is(DamageTypes.MAGIC) || event.getSource().is(DamageTypes.INDIRECT_MAGIC);
    }

    private static List<LivingEntity> getNearbyEnemies(Player player, double radius) {
        return player.level().getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(radius),
                living -> living != player && living.isAlive() && living instanceof Enemy
        );
    }

    private static void knockbackFrom(LivingEntity target, Entity center, double strength) {
        double dx = target.getX() - center.getX();
        double dz = target.getZ() - center.getZ();

        if ((dx * dx + dz * dz) < 0.0001D) {
            dx = (target.getRandom().nextDouble() - 0.5D) * 0.1D;
            dz = (target.getRandom().nextDouble() - 0.5D) * 0.1D;
        }

        target.knockback(strength, dx, dz);
    }

    private static void randomShortTeleport(Player player, double radius) {
        double x = player.getX() + ((player.getRandom().nextDouble() * 2.0D - 1.0D) * radius);
        double y = player.getY();
        double z = player.getZ() + ((player.getRandom().nextDouble() * 2.0D - 1.0D) * radius);
        player.teleportTo(x, y, z);
    }

    private static void reduceHarmfulEffects(Player player, int amount) {
        for (MobEffectInstance instance : new ArrayList<>(player.getActiveEffects())) {
            if (instance.getEffect().value().getCategory() != MobEffectCategory.HARMFUL) {
                continue;
            }

            int newDuration = Math.max(1, instance.getDuration() - amount);
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

    private static void cleanseHarmfulEffects(Player player) {
        for (MobEffectInstance instance : new ArrayList<>(player.getActiveEffects())) {
            if (instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                player.removeEffect(instance.getEffect());
            }
        }
    }

    private static void stripBeneficialEffects(LivingEntity entity) {
        for (MobEffectInstance instance : new ArrayList<>(entity.getActiveEffects())) {
            if (instance.getEffect().value().getCategory() == MobEffectCategory.BENEFICIAL) {
                entity.removeEffect(instance.getEffect());
            }
        }
    }

    private static void addModifier(
            Player player,
            Holder<Attribute> attribute,
            ResourceLocation id,
            double amount,
            AttributeModifier.Operation operation
    ) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance != null) {
            instance.addTransientModifier(new AttributeModifier(id, amount, operation));
        }
    }

    private static void clearDynamicModifiers(Player player) {
        clearModifier(player, Attributes.ARMOR, COLOSSUS_ARMOR_ID);
        clearModifier(player, Attributes.KNOCKBACK_RESISTANCE, COLOSSUS_KB_ID);
        clearModifier(player, Attributes.MOVEMENT_SPEED, COLOSSUS_SPEED_ID);
        clearModifier(player, Attributes.ATTACK_DAMAGE, COLOSSUS_ATTACK_ID);

        clearModifier(player, Attributes.MOVEMENT_SPEED, EMBER_SPEED_ID);
        clearModifier(player, Attributes.ATTACK_SPEED, EMBER_ATTACK_SPEED_ID);

        clearModifier(player, Attributes.MOVEMENT_SPEED, VOID_SPEED_ID);
        clearModifier(player, Attributes.ATTACK_SPEED, VOID_ATTACK_SPEED_ID);
    }

    private static void clearModifier(
            Player player,
            Holder<Attribute> attribute,
            ResourceLocation id
    ) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance != null) {
            instance.removeModifier(id);
        }
    }
}