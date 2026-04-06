package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.items.TitanRemnantStage;
import net.bandit.oathboundrelics.items.TitanRemnantType;
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

        TitanRemnantType type = TitansRemnantUtil.getEquippedType(player);
        if (type == null) {
            return;
        }

        TitanRemnantStage stage = TitansRemnantUtil.getEffectiveEquippedStage(player);

        switch (type) {
            case COLOSSUS_HEART -> handleColossusOutgoingPost(player, target, stage);
            case EMBER_SEED -> handleEmberOutgoingPost(player, target, stage);
            case VOID_PEARL -> handleVoidOutgoingPost(player, target, stage);
            default -> {
            }
        }
    }

    private static void tickColossus(Player player, TitanRemnantStage stage) {
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
        AttributeInstance kb = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        double armorBonus = switch (stage) {
            case DORMANT -> 2.0D;
            case LATENT -> 4.0D;
            case AWAKENED -> 5.0D;
            case ASCENDED -> 6.0D;
            case TRANSCENDENT -> 7.0D;
            case APEX -> 8.0D;
        };

        double kbBonus = switch (stage) {
            case DORMANT -> 0.10D;
            case LATENT -> 0.20D;
            case AWAKENED -> 0.25D;
            case ASCENDED -> 0.30D;
            case TRANSCENDENT -> 0.35D;
            case APEX -> 0.40D;
        };

        double penalty = switch (stage) {
            case DORMANT -> -0.02D;
            case LATENT, AWAKENED -> -0.03D;
            case ASCENDED -> -0.04D;
            case TRANSCENDENT, APEX -> -0.05D;
        };

        if (armor != null) {
            armor.addTransientModifier(new AttributeModifier(COLOSSUS_ARMOR_ID, armorBonus, AttributeModifier.Operation.ADD_VALUE));
        }
        if (kb != null) {
            kb.addTransientModifier(new AttributeModifier(COLOSSUS_KB_ID, kbBonus, AttributeModifier.Operation.ADD_VALUE));
        }
        if (speed != null) {
            speed.addTransientModifier(new AttributeModifier(COLOSSUS_SPEED_ID, penalty, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    private static void tickEmber(Player player, TitanRemnantStage stage) {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0, false, false, true));

        int purgeAmount = stage.atLeast(TitanRemnantStage.LATENT) ? 20 : 10;
        if (player.tickCount % 20 == 0) {
            for (MobEffectInstance instance : new ArrayList<>(player.getActiveEffects())) {
                if (instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                    int newDuration = Math.max(1, instance.getDuration() - purgeAmount);
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

        if (stage.atLeast(TitanRemnantStage.AWAKENED)) {
            int decayInterval = switch (stage) {
                case AWAKENED -> 20;
                case ASCENDED -> 30;
                case TRANSCENDENT -> 40;
                case APEX -> 50;
                default -> 20;
            };
            if (player.tickCount % decayInterval == 0) {
                TitansRemnantUtil.setHeat(player, TitansRemnantUtil.getHeat(player) - 1);
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
                AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speed != null) {
                    speed.addTransientModifier(new AttributeModifier(EMBER_SPEED_ID, speedBonus, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                }
            }

            int scorchThreshold = switch (stage) {
                case AWAKENED -> 8;
                case ASCENDED -> 7;
                case TRANSCENDENT, APEX -> 6;
                default -> 99;
            };

            if (heat >= scorchThreshold && player.tickCount % 10 == 0) {
                double radius = switch (stage) {
                    case AWAKENED -> 4.0D;
                    case ASCENDED -> 4.5D;
                    case TRANSCENDENT -> 5.0D;
                    case APEX -> 6.0D;
                    default -> 4.0D;
                };

                List<LivingEntity> enemies = player.level().getEntitiesOfClass(
                        LivingEntity.class,
                        player.getBoundingBox().inflate(radius),
                        living -> living != player && living.isAlive() && living instanceof Enemy
                );

                for (LivingEntity enemy : enemies) {
                    enemy.igniteForSeconds(stage == TitanRemnantStage.APEX ? 4.0F : 2.0F);
                }
            }

            if (stage == TitanRemnantStage.APEX && heat >= 10) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, false, false, true));
            }
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

            if (stage.atLeast(TitanRemnantStage.AWAKENED) && player.tickCount % 10 == 0) {
                double radius = switch (stage) {
                    case AWAKENED -> 6.0D;
                    case ASCENDED -> 7.0D;
                    case TRANSCENDENT -> 8.0D;
                    case APEX -> 9.0D;
                    default -> 6.0D;
                };

                int slownessAmp = stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 2 : 1;
                int weaknessAmp = stage.atLeast(TitanRemnantStage.ASCENDED) ? 1 : 0;

                List<LivingEntity> enemies = player.level().getEntitiesOfClass(
                        LivingEntity.class,
                        player.getBoundingBox().inflate(radius),
                        living -> living != player && living.isAlive() && living instanceof Enemy
                );

                for (LivingEntity enemy : enemies) {
                    enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, slownessAmp, false, false, true));
                    enemy.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, weaknessAmp, false, false, true));
                    enemy.setDeltaMovement(enemy.getDeltaMovement().x, Math.min(enemy.getDeltaMovement().y - 0.08D, -0.08D), enemy.getDeltaMovement().z);
                    enemy.hurtMarked = true;
                }
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

        boolean wasAirborne = TitansRemnantUtil.wasAirborne(player);
        if (wasAirborne && player.onGround()) {
            float storedFall = TitansRemnantUtil.getStoredAirborneFall(player);

            float requiredFall = switch (stage) {
                case AWAKENED -> 6.0F;
                case ASCENDED -> 5.5F;
                case TRANSCENDENT -> 5.0F;
                case APEX -> 4.0F;
                default -> 999.0F;
            };

            if (stage.atLeast(TitanRemnantStage.AWAKENED) && storedFall >= requiredFall) {
                double radius = switch (stage) {
                    case AWAKENED -> 4.5D;
                    case ASCENDED -> 5.0D;
                    case TRANSCENDENT -> 5.5D;
                    case APEX -> 6.0D;
                    default -> 4.5D;
                };

                float burstDamage = switch (stage) {
                    case AWAKENED -> 0.0F;
                    case ASCENDED -> 1.0F;
                    case TRANSCENDENT -> 2.0F;
                    case APEX -> 3.0F;
                    default -> 0.0F;
                };

                for (LivingEntity enemy : player.level().getEntitiesOfClass(
                        LivingEntity.class,
                        player.getBoundingBox().inflate(radius),
                        living -> living != player && living.isAlive() && living instanceof Enemy
                )) {
                    double dx = enemy.getX() - player.getX();
                    double dz = enemy.getZ() - player.getZ();
                    double knockback = switch (stage) {
                        case AWAKENED -> 1.0D;
                        case ASCENDED -> 1.2D;
                        case TRANSCENDENT -> 1.4D;
                        case APEX -> 1.7D;
                        default -> 1.0D;
                    };
                    enemy.knockback(knockback, dx, dz);
                    enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 2 : 1, false, false, true));
                    if (burstDamage > 0.0F) {
                        enemy.hurt(player.damageSources().playerAttack(player), burstDamage);
                    }
                }
            }

            TitansRemnantUtil.setStoredAirborneFall(player, 0.0F);
        }

        TitansRemnantUtil.setWasAirborne(player, airborne);
    }

    private static void tickNebula(Player player, TitanRemnantStage stage) {
        if (stage.atLeast(TitanRemnantStage.AWAKENED) && player.tickCount % 20 == 0) {
            long gameTime = player.level().getGameTime();
            if (TitansRemnantUtil.getNebulaBlinkCooldown(player) < gameTime) {
                TitansRemnantUtil.setNebulaBlinkCooldown(player, 0L);
            }
        }
    }

    private static void tickVoid(Player player, TitanRemnantStage stage) {
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60, 0, false, false, true));

        int cleanseInterval = stage.atLeast(TitanRemnantStage.LATENT) ? 10 : 20;
        if (player.tickCount % cleanseInterval == 0) {
            for (MobEffectInstance instance : new ArrayList<>(player.getActiveEffects())) {
                if (instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                    player.removeEffect(instance.getEffect());
                }
            }
        }

        if (stage.atLeast(TitanRemnantStage.AWAKENED) && TitansRemnantUtil.isLowLight(player) && player.tickCount % 10 == 0) {
            double radius = switch (stage) {
                case AWAKENED -> 6.0D;
                case ASCENDED -> 7.0D;
                case TRANSCENDENT -> 8.0D;
                case APEX -> 10.0D;
                default -> 6.0D;
            };

            int witherAmp = stage.atLeast(TitanRemnantStage.ASCENDED) ? 1 : 0;
            int weakAmp = stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 1 : 0;

            List<LivingEntity> enemies = player.level().getEntitiesOfClass(
                    LivingEntity.class,
                    player.getBoundingBox().inflate(radius),
                    living -> living != player && living.isAlive() && living instanceof Enemy
            );

            for (LivingEntity enemy : enemies) {
                enemy.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, witherAmp, false, false, true));
                enemy.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, weakAmp, false, false, true));
                enemy.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false, true));
                if (stage == TitanRemnantStage.APEX) {
                    enemy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, false, false, true));
                }
            }

            if (stage == TitanRemnantStage.APEX) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, false, false, true));
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

        float magicMultiplier = switch (stage) {
            case DORMANT, LATENT -> 1.25F;
            case AWAKENED, ASCENDED -> 1.15F;
            case TRANSCENDENT, APEX -> 1.10F;
        };

        if (event.getSource().is(DamageTypes.MAGIC) || event.getSource().is(DamageTypes.INDIRECT_MAGIC)) {
            event.setNewDamage(event.getNewDamage() * magicMultiplier);
        } else if (stage.atLeast(TitanRemnantStage.AWAKENED)) {
            float threshold = stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 3.0F : 4.0F;
            if (event.getNewDamage() >= threshold) {
                TitansRemnantUtil.setImpact(player, TitansRemnantUtil.getImpact(player) + 1);
            }
        }
    }

    private static void handleEmberIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setNewDamage(0.0F);
            return;
        }

        Entity attacker = event.getSource().getEntity();

        if (attacker instanceof LivingEntity living && living.isInWaterOrBubble()) {
            float waterMultiplier = switch (stage) {
                case DORMANT, LATENT -> 1.25F;
                case AWAKENED -> 1.20F;
                case ASCENDED -> 1.15F;
                case TRANSCENDENT, APEX -> 1.10F;
            };
            event.setNewDamage(event.getNewDamage() * waterMultiplier);
        }

        if (attacker instanceof LivingEntity living && event.getSource().getDirectEntity() == attacker) {
            float burnSeconds = switch (stage) {
                case DORMANT -> 2.0F;
                case LATENT, AWAKENED -> 3.0F;
                case ASCENDED, TRANSCENDENT -> 4.0F;
                case APEX -> 5.0F;
            };
            living.igniteForSeconds(burnSeconds);
        }

        if (stage.atLeast(TitanRemnantStage.AWAKENED) && event.getNewDamage() > 0.0F) {
            TitansRemnantUtil.setHeat(player, TitansRemnantUtil.getHeat(player) + 1);
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
                case DORMANT -> 0.75F;
                default -> 0.60F;
            };
            event.setNewDamage(event.getNewDamage() * reduction);
        }

        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            float fireMultiplier = switch (stage) {
                case DORMANT, LATENT -> 1.35F;
                case AWAKENED -> 1.30F;
                case ASCENDED -> 1.25F;
                case TRANSCENDENT -> 1.20F;
                case APEX -> 1.15F;
            };
            event.setNewDamage(event.getNewDamage() * fireMultiplier);
        }
    }

    private static void handleSkybrandIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (event.getSource().is(DamageTypes.FALL)) {
            event.setNewDamage(0.0F);
            return;
        }

        if (event.getSource().getDirectEntity() instanceof Projectile) {
            float negateChance = switch (stage) {
                case DORMANT -> 0.25F;
                case LATENT -> 0.35F;
                case AWAKENED -> 0.40F;
                case ASCENDED -> 0.50F;
                case TRANSCENDENT -> 0.60F;
                case APEX -> 0.75F;
            };
            if (player.getRandom().nextFloat() < negateChance) {
                event.setNewDamage(0.0F);
                return;
            }
        }

        if (event.getSource().is(DamageTypes.WITHER) || event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) {
            float multiplier = switch (stage) {
                case DORMANT, LATENT -> 1.35F;
                case AWAKENED, ASCENDED -> 1.25F;
                case TRANSCENDENT -> 1.15F;
                case APEX -> 1.00F;
            };
            event.setNewDamage(event.getNewDamage() * multiplier);
        }
    }

    private static void handleNebulaIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (event.getSource().is(ENDER_PEARL_DAMAGE)) {
            event.setNewDamage(0.0F);
            return;
        }

        float magicMultiplier = switch (stage) {
            case DORMANT -> 0.65F;
            case LATENT -> 0.55F;
            case AWAKENED -> 0.45F;
            case ASCENDED -> 0.35F;
            case TRANSCENDENT -> 0.30F;
            case APEX -> 0.25F;
        };
        if (event.getSource().is(DamageTypes.MAGIC) || event.getSource().is(DamageTypes.INDIRECT_MAGIC)) {
            event.setNewDamage(event.getNewDamage() * magicMultiplier);
        }

        float waterMultiplier = switch (stage) {
            case DORMANT, LATENT -> 1.50F;
            case AWAKENED -> 1.45F;
            case ASCENDED -> 1.40F;
            case TRANSCENDENT -> 1.35F;
            case APEX -> 1.30F;
        };
        if (player.isInWaterOrBubble()) {
            event.setNewDamage(event.getNewDamage() * waterMultiplier);
        }

        if (stage.atLeast(TitanRemnantStage.AWAKENED)) {
            long gameTime = player.level().getGameTime();
            float blinkChance = switch (stage) {
                case AWAKENED -> 0.15F;
                case ASCENDED -> 0.20F;
                case TRANSCENDENT -> 0.25F;
                case APEX -> 0.30F;
                default -> 0.0F;
            };
            long cooldown = switch (stage) {
                case AWAKENED -> 60L;
                case ASCENDED -> 50L;
                case TRANSCENDENT -> 40L;
                case APEX -> 30L;
                default -> 60L;
            };

            if (TitansRemnantUtil.getNebulaBlinkCooldown(player) <= gameTime
                    && player.getRandom().nextFloat() < blinkChance) {

                Entity attacker = event.getSource().getEntity();
                if (attacker != null) {
                    Vec3 away = player.position().subtract(attacker.position()).normalize().scale(4.0D);
                    player.teleportTo(player.getX() + away.x, player.getY(), player.getZ() + away.z);
                    TitansRemnantUtil.setNebulaEmpowered(player, true);
                    TitansRemnantUtil.setNebulaBlinkCooldown(player, gameTime + cooldown);
                    if (stage.atLeast(TitanRemnantStage.TRANSCENDENT)) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, stage == TitanRemnantStage.APEX ? 1 : 0, false, false, true));
                    }
                    event.setNewDamage(0.0F);
                }
            }
        }
    }

    private static void handleVoidIncoming(Player player, LivingDamageEvent.Pre event, TitanRemnantStage stage) {
        if (event.getSource().is(DamageTypes.DROWN)) {
            event.setNewDamage(0.0F);
            return;
        }

        float chance = switch (stage) {
            case DORMANT -> 0.20F;
            case LATENT -> 0.25F;
            case AWAKENED -> 0.30F;
            case ASCENDED -> 0.35F;
            case TRANSCENDENT -> 0.40F;
            case APEX -> 0.50F;
        };

        if (event.getNewDamage() >= player.getHealth() && player.getRandom().nextFloat() < chance) {
            event.setNewDamage(Math.max(0.0F, player.getHealth() - 1.0F));
        }
    }

    private static void handleOutgoingPre(Player player, LivingEntity target, LivingDamageEvent.Pre event, TitanRemnantType type, TitanRemnantStage stage) {
        switch (type) {
            case COLOSSUS_HEART -> {
                if (stage.atLeast(TitanRemnantStage.AWAKENED)) {
                    int impact = TitansRemnantUtil.getImpact(player);
                    if (impact > 0) {
                        float perStack = switch (stage) {
                            case AWAKENED -> 1.5F;
                            case ASCENDED -> 2.0F;
                            case TRANSCENDENT -> 2.5F;
                            case APEX -> 3.0F;
                            default -> 0.0F;
                        };
                        event.setNewDamage(event.getNewDamage() + (impact * perStack));
                    }
                }
            }
            case EMBER_SEED -> {
                if (stage.atLeast(TitanRemnantStage.AWAKENED)) {
                    int heat = TitansRemnantUtil.getHeat(player);
                    float perHeat = switch (stage) {
                        case AWAKENED -> 0.25F;
                        case ASCENDED -> 0.50F;
                        case TRANSCENDENT -> 0.75F;
                        case APEX -> 1.00F;
                        default -> 0.0F;
                    };
                    event.setNewDamage(event.getNewDamage() + (heat * perHeat));
                }
                if (player.getRemainingFireTicks() > 0) {
                    float igniteSeconds = switch (stage) {
                        case DORMANT -> 3.0F;
                        case LATENT, AWAKENED -> 4.0F;
                        case ASCENDED -> 5.0F;
                        case TRANSCENDENT -> 6.0F;
                        case APEX -> 7.0F;
                    };
                    target.igniteForSeconds(igniteSeconds);
                }
            }
            case TIDE_PEARL -> {
                if (stage.atLeast(TitanRemnantStage.ASCENDED) && player.isInWaterOrBubble()) {
                    float multiplier = switch (stage) {
                        case ASCENDED -> 1.15F;
                        case TRANSCENDENT -> 1.25F;
                        case APEX -> 1.35F;
                        default -> 1.0F;
                    };
                    event.setNewDamage(event.getNewDamage() * multiplier);
                }
            }
            case NEBULA_LENS -> {
                if (stage.atLeast(TitanRemnantStage.AWAKENED) && TitansRemnantUtil.isNebulaEmpowered(player)) {
                    float multiplier = switch (stage) {
                        case AWAKENED -> 2.0F;
                        case ASCENDED -> 2.5F;
                        case TRANSCENDENT -> 3.0F;
                        case APEX -> 3.5F;
                        default -> 1.0F;
                    };
                    event.setNewDamage(event.getNewDamage() * multiplier);
                    TitansRemnantUtil.setNebulaEmpowered(player, false);
                }
            }
            case VOID_PEARL -> {
                if (stage.atLeast(TitanRemnantStage.AWAKENED) && TitansRemnantUtil.isLowLight(player)) {
                    float multiplier = switch (stage) {
                        case AWAKENED -> 1.15F;
                        case ASCENDED -> 1.25F;
                        case TRANSCENDENT -> 1.35F;
                        case APEX -> 1.50F;
                        default -> 1.0F;
                    };
                    event.setNewDamage(event.getNewDamage() * multiplier);
                }
            }
            default -> {
            }
        }
    }

    private static void handleColossusOutgoingPost(Player player, LivingEntity target, TitanRemnantStage stage) {
        if (!stage.atLeast(TitanRemnantStage.AWAKENED)) {
            return;
        }

        int impact = TitansRemnantUtil.getImpact(player);
        if (impact <= 0) {
            return;
        }

        double radius = switch (stage) {
            case AWAKENED -> 2.5D;
            case ASCENDED -> 3.0D;
            case TRANSCENDENT -> 3.5D;
            case APEX -> 4.0D;
            default -> 2.5D;
        };
        double knockback = switch (stage) {
            case AWAKENED -> 0.6D;
            case ASCENDED -> 0.8D;
            case TRANSCENDENT -> 1.0D;
            case APEX -> 1.2D;
            default -> 0.6D;
        };
        float splashPerImpact = switch (stage) {
            case AWAKENED -> 0.75F;
            case ASCENDED -> 1.0F;
            case TRANSCENDENT -> 1.25F;
            case APEX -> 1.50F;
            default -> 0.75F;
        };

        for (LivingEntity enemy : player.level().getEntitiesOfClass(
                LivingEntity.class,
                target.getBoundingBox().inflate(radius),
                living -> living != player && living != target && living.isAlive()
        )) {
            double dx = enemy.getX() - target.getX();
            double dz = enemy.getZ() - target.getZ();
            enemy.knockback(knockback + (impact * 0.10D), dx, dz);
            enemy.hurt(target.damageSources().playerAttack(player), impact * splashPerImpact);
        }

        if (stage == TitanRemnantStage.APEX) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, false, false, true));
        }

        TitansRemnantUtil.setImpact(player, 0);
    }

    private static void handleEmberOutgoingPost(Player player, LivingEntity target, TitanRemnantStage stage) {
        if (stage.atLeast(TitanRemnantStage.AWAKENED)) {
            TitansRemnantUtil.setHeat(player, TitansRemnantUtil.getHeat(player) + 1);
        }

        float igniteSeconds = switch (stage) {
            case DORMANT -> 3.0F;
            case LATENT, AWAKENED -> 4.0F;
            case ASCENDED -> 5.0F;
            case TRANSCENDENT -> 6.0F;
            case APEX -> 7.0F;
        };
        target.igniteForSeconds(igniteSeconds);
    }

    private static void handleVoidOutgoingPost(Player player, LivingEntity target, TitanRemnantStage stage) {
        int amplifier = stage.atLeast(TitanRemnantStage.TRANSCENDENT) ? 1 : 0;
        int duration = switch (stage) {
            case DORMANT -> 60;
            case LATENT -> 80;
            case AWAKENED -> 100;
            case ASCENDED -> 120;
            case TRANSCENDENT -> 140;
            case APEX -> 160;
        };
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, duration, amplifier, false, false, true));
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
