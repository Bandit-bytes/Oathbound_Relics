package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.bandit.oathboundrelics.util.SlothWeaponUtil;
import net.minecraft.core.Holder;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.enchanting.EnchantmentLevelSetEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class OathboundRelicEvents {

    private static final String TAG_RELIC_RESTORE_DELAY = OathboundRelicsMod.MOD_ID + "_relic_restore_delay";
    private static final int RELIC_RESTORE_DELAY_TICKS = 20;

    private static final Map<UUID, Long> SHROUD_COOLDOWNS = new HashMap<>();
    private static final Map<UUID, Long> LAST_BREATH_COOLDOWNS = new HashMap<>();
    private static final int OVER_ENCHANT_THRESHOLD = 30;
    private static final int MAX_OVER_ENCHANT_BONUS = 1;
    private static final Map<UUID, Long> BLOOD_TOLL_COOLDOWNS = new HashMap<>();

    private static boolean canAttemptOverEnchant(int enchantingPower) {
        return enchantingPower > OVER_ENCHANT_THRESHOLD;
    }

    private OathboundRelicEvents() {
    }

    private static boolean isOnCooldown(Map<UUID, Long> map, Player player, long gameTime) {
        return map.getOrDefault(player.getUUID(), 0L) > gameTime;
    }

    private static void setCooldown(Map<UUID, Long> map, Player player, long gameTime, long ticks) {
        map.put(player.getUUID(), gameTime + ticks);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (event.getSource().is(DamageTypes.GENERIC_KILL)
                || event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return;
        }

        boolean branded = OathboundUtil.isBranded(player);
        if (branded) {
            refreshBrandedActivity(player, 20 * 5);
        }
        if (branded) {
            if (OathboundConfig.enableFrailty()) {
                event.setAmount((float) (event.getAmount() * OathboundConfig.incomingDamageMultiplier()));
            }

            if (OathboundConfig.enableShatteredPlate()) {
                event.addReductionModifier(
                        DamageContainer.Reduction.ARMOR,
                        (container, reduction) -> (float) (reduction * OathboundConfig.armorEffectiveness())
                );
            }
        }

        if (player.isCreative() || player.isSpectator()) {
            return;
        }

        // Shroud of the Forsaken
        if (OathboundConfig.enableShroudOfTheForsaken()
                && OathboundUtil.hasCurio(player, ItemRegistry.SHROUD_OF_THE_FORSAKEN.get())) {
            long gameTime = player.level().getGameTime();

            if ((player.getHealth() - event.getAmount()) <= OathboundConfig.shroudLowHealthThreshold()
                    && !isOnCooldown(SHROUD_COOLDOWNS, player, gameTime)) {
                event.setAmount((float) (event.getAmount() * OathboundConfig.shroudDamageMultiplier()));
                player.addEffect(new MobEffectInstance(
                        MobEffects.DAMAGE_RESISTANCE,
                        OathboundConfig.shroudResistanceDurationTicks(),
                        OathboundConfig.shroudResistanceAmplifier(),
                        false,
                        true
                ));
                setCooldown(SHROUD_COOLDOWNS, player, gameTime, OathboundConfig.shroudCooldownTicks());
            }
        }

// Thornbound Carapace
        if (OathboundConfig.enableThornboundCarapace()
                && OathboundUtil.hasCurio(player, ItemRegistry.THORNBOUND_CARAPACE.get())
                && event.getSource().getEntity() instanceof LivingEntity attacker
                && !event.getSource().is(DamageTypes.THORNS)) {
            float reflected = (float) (event.getAmount() * OathboundConfig.thornboundCarapaceReflectPercent());
            attacker.hurt(attacker.damageSources().thorns(player), reflected);
        }

// Voidstep Band
        if (OathboundConfig.enableVoidstepBand()
                && OathboundUtil.hasCurio(player, ItemRegistry.VOIDSTEP_BAND.get())
                && event.getSource().is(DamageTypes.FALL)) {
            event.setAmount(0.0F);
        }

// Relic of the Last Breath
        if (OathboundConfig.enableRelicOfTheLastBreath()
                && OathboundUtil.hasCurio(player, ItemRegistry.RELIC_OF_THE_LAST_BREATH.get())) {
            long gameTime = player.level().getGameTime();
            float lethalThreshold = player.getHealth() + player.getAbsorptionAmount();

            if (event.getAmount() >= lethalThreshold
                    && !isOnCooldown(LAST_BREATH_COOLDOWNS, player, gameTime)) {
                event.setAmount(0.0F);
                player.setHealth((float) OathboundConfig.lastBreathPostTriggerHealth());
                player.addEffect(new MobEffectInstance(
                        MobEffects.ABSORPTION,
                        OathboundConfig.lastBreathAbsorptionDurationTicks(),
                        OathboundConfig.lastBreathAbsorptionAmplifier(),
                        false,
                        true
                ));
                player.addEffect(new MobEffectInstance(
                        MobEffects.REGENERATION,
                        OathboundConfig.lastBreathRegenerationDurationTicks(),
                        OathboundConfig.lastBreathRegenerationAmplifier(),
                        false,
                        true
                ));
                setCooldown(LAST_BREATH_COOLDOWNS, player, gameTime, OathboundConfig.lastBreathCooldownTicks());
            }
        }
    }
    @SubscribeEvent
    public static void onOutgoingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (event.getEntity() == player) {
            return;
        }

        boolean branded = OathboundUtil.isBranded(player);
        if (branded) {
            refreshBrandedActivity(player, 20 * 5);
        }
        float damage = event.getContainer().getNewDamage();

        // Bound outgoing relics
        if (branded
                && OathboundConfig.enableAshenNail()
                && OathboundUtil.hasCurio(player, ItemRegistry.ASHEN_NAIL.get())
                && player.isOnFire()) {
            damage *= (float) OathboundConfig.ashenNailBurningDamageMultiplier();
        }

        if (branded
                && OathboundConfig.enableHuntersSigil()
                && OathboundUtil.hasCurio(player, ItemRegistry.HUNTERS_SIGIL.get())
                && event.getEntity() instanceof Mob mob
                && mob.getTarget() == player) {
            damage *= (float) OathboundConfig.huntersSigilDamageMultiplier();
        }

        boolean usingLethargicFlail = SlothWeaponUtil.isHoldingLethargicFlail(player);

        if (usingLethargicFlail && !SlothWeaponUtil.canUseLethargicFlail(player)) {
            event.getContainer().setNewDamage(0.0F);
            return;
        }

        // Free outgoing relics
        if (event.getEntity() instanceof LivingEntity target) {
            if (OathboundConfig.enableExecutionersCoin()
                    && OathboundUtil.hasCurio(player, ItemRegistry.EXECUTIONERS_COIN.get())
                    && target.getHealth() <= (target.getMaxHealth() * OathboundConfig.executionersCoinHealthThresholdPercent())) {
                damage *= (float) OathboundConfig.executionersCoinDamageMultiplier();
            }

            if (OathboundConfig.enableCenserOfAsh()
                    && OathboundUtil.hasCurio(player, ItemRegistry.CENSER_OF_ASH.get())
                    && target.isOnFire()) {
                damage *= (float) OathboundConfig.censerOfAshDamageMultiplier();
            }

            if (OathboundConfig.enableTorchOfGravesong()
                    && OathboundUtil.hasCurio(player, ItemRegistry.TORCH_OF_GRAVESONG.get())
                    && target.getType().is(EntityTypeTags.UNDEAD)) {
                damage *= (float) OathboundConfig.torchOfGravesongUndeadDamageMultiplier();
            }
        }

        event.getContainer().setNewDamage(damage);
    }

    @SubscribeEvent
    public static void onSuccessfulHit(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        if (event.getEntity() == player) {
            return;
        }

        if (!OathboundUtil.isBranded(player)) {
            return;
        }
        refreshBrandedActivity(player, 20 * 5);

        if (!OathboundConfig.enableBloodToll()) {
            return;
        }

        if (event.getNewDamage() <= 0.0F) {
            return;
        }

        long gameTime = player.level().getGameTime();
        if (isOnCooldown(BLOOD_TOLL_COOLDOWNS, player, gameTime)) {
            return;
        }

        float cost = (float) OathboundConfig.bloodTollHealthCost();
        if (cost <= 0.0F) {
            return;
        }

        player.hurt(player.damageSources().generic(), cost);
        setCooldown(BLOOD_TOLL_COOLDOWNS, player, gameTime, OathboundConfig.bloodTollCooldownTicks());
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        boolean branded = OathboundUtil.isBranded(player);
        if (branded) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            data.refreshActivity(20 * 8);
            data.addBrandedProgressTicks(20 * 5);
        }
// onKill
        if (branded
                && OathboundConfig.enableGravebellLocket()
                && OathboundUtil.hasCurio(player, ItemRegistry.GRAVEBELL_LOCKET.get())) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED,
                    OathboundConfig.gravebellLocketSpeedDurationTicks(),
                    OathboundConfig.gravebellLocketSpeedAmplifier(),
                    false,
                    true
            ));
            player.addEffect(new MobEffectInstance(
                    MobEffects.REGENERATION,
                    OathboundConfig.gravebellLocketRegenerationDurationTicks(),
                    OathboundConfig.gravebellLocketRegenerationAmplifier(),
                    false,
                    true
            ));
        }

        if (OathboundConfig.enableMournersThread()
                && OathboundUtil.hasCurio(player, ItemRegistry.MOURNERS_THREAD.get())) {
            player.getFoodData().eat(
                    OathboundConfig.mournersThreadFoodRestored(),
                    (float) OathboundConfig.mournersThreadSaturationRestored()
            );
            player.heal((float) OathboundConfig.mournersThreadHealAmount());
        }

        if (OathboundConfig.enableTorchOfGravesong()
                && OathboundUtil.hasCurio(player, ItemRegistry.TORCH_OF_GRAVESONG.get())
                && event.getEntity() instanceof LivingEntity dead
                && dead.getType().is(EntityTypeTags.UNDEAD)) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_BOOST,
                    OathboundConfig.torchOfGravesongStrengthDurationTicks(),
                    OathboundConfig.torchOfGravesongStrengthAmplifier(),
                    false,
                    true
            ));
        }
    }


    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        boolean branded = OathboundUtil.isBranded(player);

        BrandedTimeData brandedTimeData = player.getData(AttachmentRegistry.BRANDED_TIME.get());

        boolean activeThisCheck = false;
        if (branded && !player.isCreative() && !player.isSpectator()) {
            activeThisCheck =
                    isMeaningfullyMoving(player)
                            || player.swinging
                            || player.isUsingItem();
        }

        if (player.tickCount % 20 == 0) {
            brandedTimeData.tick(
                    branded,
                    activeThisCheck,
                    OathboundConfig.slothWeaponMaxBrandedTicks()
            );
        }

        if (!player.isCreative() && !player.isSpectator()) {
            if (branded
                    && OathboundConfig.enableLivingEmber()
                    && player.isOnFire()
                    && player.getRemainingFireTicks() < OathboundConfig.minFireTicks()) {
                player.setRemainingFireTicks(OathboundConfig.minFireTicks());
            }

            if (branded
                    && OathboundConfig.enableProvocation()
                    && player.tickCount % OathboundConfig.neutralAggroInterval() == 0
                    && !OathboundUtil.hasActiveBrandkeepersMercy(player)) {
                provokeNearbyNeutralMobs(player);
            }

            // onPlayerTick
            if (OathboundConfig.enableHollowEye()
                    && OathboundUtil.hasCurio(player, ItemRegistry.HOLLOW_EYE.get())) {

                if (OathboundConfig.hollowEyeClearBlindness() && player.hasEffect(MobEffects.BLINDNESS)) {
                    player.removeEffect(MobEffects.BLINDNESS);
                }

                if (OathboundConfig.hollowEyeClearDarkness() && player.hasEffect(MobEffects.DARKNESS)) {
                    player.removeEffect(MobEffects.DARKNESS);
                }

                if (player.tickCount % OathboundConfig.hollowEyeRevealInvisibleIntervalTicks() == 0) {
                    AABB area = player.getBoundingBox().inflate(OathboundConfig.hollowEyeRevealInvisibleRadius());

                    for (LivingEntity living : player.level().getEntitiesOfClass(
                            LivingEntity.class,
                            area,
                            entity -> entity.isAlive() && entity != player
                    )) {
                        if (living.isInvisible()) {
                            living.addEffect(new MobEffectInstance(
                                    MobEffects.GLOWING,
                                    OathboundConfig.hollowEyeRevealInvisibleDurationTicks(),
                                    0,
                                    false,
                                    false
                            ));
                        }
                    }
                }
            }

            if (OathboundConfig.enableCenserOfAsh()
                    && OathboundUtil.hasCurio(player, ItemRegistry.CENSER_OF_ASH.get())
                    && player.tickCount % OathboundConfig.censerOfAshScanIntervalTicks() == 0) {
                AABB area = player.getBoundingBox().inflate(OathboundConfig.censerOfAshGlowRadius());

                for (LivingEntity mob : player.level().getEntitiesOfClass(LivingEntity.class, area, LivingEntity::isAlive)) {
                    if (mob != player && mob.isOnFire()) {
                        mob.addEffect(new MobEffectInstance(
                                MobEffects.GLOWING,
                                OathboundConfig.censerOfAshGlowDurationTicks(),
                                0,
                                false,
                                false
                        ));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSleepAttempt(CanPlayerSleepEvent event) {
        if (!OathboundConfig.enableWakefulDoom()) {
            return;
        }

        if (!OathboundUtil.isBranded(event.getEntity())) {
            return;
        }

        event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
    }

    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (!OathboundUtil.isBranded(player)) {
            return;
        }

        BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
        data.refreshActivity(20 * 4);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void preserveOathboundRelicBeforeDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        CuriosApi.getCuriosInventory(player).ifPresent(curiosInventory -> {
            var matches = curiosInventory.findCurios(ItemRegistry.OATHBOUND_RELIC.get());
            if (matches.isEmpty()) {
                return;
            }

            var result = matches.getFirst();
            var handlerOpt = curiosInventory.getStacksHandler(result.slotContext().identifier());
            if (handlerOpt.isEmpty()) {
                return;
            }

            var stacks = handlerOpt.get().getStacks();
            int index = result.slotContext().index();
            if (index < 0 || index >= stacks.getSlots()) {
                return;
            }

            ItemStack equipped = stacks.getStackInSlot(index);
            if (!equipped.is(ItemRegistry.OATHBOUND_RELIC.get())) {
                return;
            }

            // Store the exact stack, including all components, in player-owned data that
            // NeoForge copies through the death clone. Then remove it from Curios before
            // grave mods snapshot external inventories.
            var preserved = player.getData(AttachmentRegistry.PRESERVED_OATHBOUND_RELIC.get());
            preserved.setStackInSlot(0, equipped.copy());
            stacks.setStackInSlot(index, ItemStack.EMPTY);
            player.containerMenu.broadcastChanges();

            OathboundRelicsMod.LOGGER.debug(
                    "Protected Oathbound Relic for {} from death/grave handling (slot {}[{}])",
                    player.getGameProfile().getName(),
                    result.slotContext().identifier(),
                    index
            );
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void restoreOathboundRelicIfDeathCanceled(LivingDeathEvent event) {
        if (!event.isCanceled() || !(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        player.getPersistentData().remove(TAG_RELIC_RESTORE_DELAY);
        restorePreservedOathboundRelic(player);
    }

    private static boolean hasOathboundRelicInVanillaInventory(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ItemRegistry.OATHBOUND_RELIC.get())) {
                return true;
            }
        }
        for (ItemStack stack : player.getInventory().offhand) {
            if (stack.is(ItemRegistry.OATHBOUND_RELIC.get())) {
                return true;
            }
        }
        return false;
    }

    private static void restorePreservedOathboundRelic(Player player) {
        var preservedHandler = player.getData(AttachmentRegistry.PRESERVED_OATHBOUND_RELIC.get());
        ItemStack preserved = preservedHandler.getStackInSlot(0);
        if (preserved.isEmpty() || !preserved.is(ItemRegistry.OATHBOUND_RELIC.get())) {
            return;
        }

        // If the real relic is already present, leave the hidden backup intact.
        // Grave mods such as YIGD can overwrite Curios/accessory layouts later when
        // a grave is claimed, so clearing the backup immediately after respawn is unsafe.
        if (OathboundUtil.isBranded(player) || hasOathboundRelicInVanillaInventory(player)) {
            return;
        }

        CuriosApi.getCuriosInventory(player).ifPresent(curiosInventory -> {
            // The Oathbound Relic is a ring. Prefer ring slots so it returns exactly where
            // players expect it, while still allowing another valid Curios slot as a
            // fallback if a pack changes its slot layout.
            var ringHandler = curiosInventory.getStacksHandler("ring");
            if (ringHandler.isPresent()) {
                var stacks = ringHandler.get().getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    if (stacks.getStackInSlot(i).isEmpty() && stacks.isItemValid(i, preserved)) {
                        curiosInventory.setEquippedCurio("ring", i, preserved.copy());
                        player.containerMenu.broadcastChanges();
                        OathboundRelicsMod.LOGGER.debug(
                                "Restored protected Oathbound Relic to {} after respawn",
                                player.getGameProfile().getName()
                        );
                        return;
                    }
                }
            }

            for (var entry : curiosInventory.getCurios().entrySet()) {
                if (entry.getKey().equals("ring")) {
                    continue;
                }
                var stacks = entry.getValue().getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    if (stacks.getStackInSlot(i).isEmpty() && stacks.isItemValid(i, preserved)) {
                        curiosInventory.setEquippedCurio(entry.getKey(), i, preserved.copy());
                        player.containerMenu.broadcastChanges();
                        OathboundRelicsMod.LOGGER.debug(
                                "Restored protected Oathbound Relic to fallback Curios slot {}[{}] for {}",
                                entry.getKey(),
                                i,
                                player.getGameProfile().getName()
                        );
                        return;
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void retryPreservedRelicRestore(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }

        ItemStack preserved = player
                .getData(AttachmentRegistry.PRESERVED_OATHBOUND_RELIC.get())
                .getStackInSlot(0);
        if (preserved.isEmpty()) {
            return;
        }

        // Do not write into Curios during PlayerRespawnEvent. Curios and grave mods
        // can still perform their own clone/sync work immediately afterward, which can
        // overwrite the slot client-side. Wait one full second so that initialization
        // settles, then restore through ICuriosItemHandler#setEquippedCurio so Curios
        // owns the mutation and sends its normal synchronization packet.
        int delay = player.getPersistentData().getInt(TAG_RELIC_RESTORE_DELAY);
        if (delay > 0) {
            player.getPersistentData().putInt(TAG_RELIC_RESTORE_DELAY, delay - 1);
            return;
        }

        restorePreservedOathboundRelic(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();

        ItemStack preserved = player
                .getData(AttachmentRegistry.PRESERVED_OATHBOUND_RELIC.get())
                .getStackInSlot(0);
        if (!preserved.isEmpty()) {
            player.getPersistentData().putInt(TAG_RELIC_RESTORE_DELAY, RELIC_RESTORE_DELAY_TICKS);
        }

        // Soul Fracture should still apply to a bearer whose relic is temporarily held
        // in the protected attachment during the post-respawn Curios synchronization
        // window. Do not require the Curios slot to already be restored here.
        boolean oathboundBearer = OathboundUtil.isBranded(player) || !preserved.isEmpty();
        if (!OathboundConfig.enableSoulFracture() || !oathboundBearer) {
            return;
        }

        int weaknessDuration = OathboundConfig.respawnWeaknessDurationTicks();
        int slownessDuration = OathboundConfig.respawnSlownessDurationTicks();

        if (weaknessDuration > 0) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.WEAKNESS,
                    weaknessDuration,
                    OathboundConfig.respawnWeaknessAmplifier()
            ));
        }

        if (slownessDuration > 0) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    slownessDuration,
                    OathboundConfig.respawnSlownessAmplifier()
            ));
        }
    }

    @SubscribeEvent
    public static void onXpDrop(LivingExperienceDropEvent event) {
        Player player = event.getAttackingPlayer();
        if (player == null) {
            return;
        }

        if (OathboundConfig.enableXpBlessing() && OathboundUtil.isBranded(player)) {
            int bonus = (int) (event.getDroppedExperience() * (OathboundConfig.xpMultiplier() - 1.0D));
            event.setDroppedExperience(event.getDroppedExperience() + bonus);
        }

        // onXpDrop
        if (OathboundConfig.enableVultureCharm()
                && OathboundUtil.hasCurio(player, ItemRegistry.VULTURE_CHARM.get())) {
            int bonus = (int) (event.getDroppedExperience() * (OathboundConfig.vultureCharmXpMultiplier() - 1.0D));
            event.setDroppedExperience(event.getDroppedExperience() + bonus);
        }
    }
    @SubscribeEvent
    public static void onDropRules(DropRulesEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        event.addOverride(
                stack -> stack.is(ItemRegistry.OATHBOUND_RELIC.get()),
                ICurio.DropRule.ALWAYS_KEEP
        );
    }


    @SubscribeEvent
    public static void onEnchantingPower(EnchantmentLevelSetEvent event) {
        if (!OathboundConfig.enableEnchantingBlessing()) {
            return;
        }

        boolean brandedNearby = event.getLevel()
                .getEntitiesOfClass(Player.class, new AABB(event.getPos()).inflate(5))
                .stream()
                .anyMatch(OathboundUtil::isBranded);

        if (brandedNearby) {
            // Add the Oathbound blessing to whatever level the enchanting system/modpack
            // has already calculated. Do not impose a vanilla-style hard cap here:
            // mods such as Apothic Enchanting legitimately produce levels above 40.
            int boosted = event.getEnchantLevel() + OathboundConfig.enchantingPowerBonus();
            event.setEnchantLevel(boosted);
        }
    }

    private static float getOverEnchantChance(int enchantingPower) {
        return Math.min(0.90F, (enchantingPower - OVER_ENCHANT_THRESHOLD) * 0.10F);
    }

    private static void refreshBrandedActivity(Player player, int ticks) {
        BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
        data.refreshActivity(ticks);
    }

    private static boolean isMeaningfullyMoving(Player player) {
        double horizontalSpeedSqr =
                player.getDeltaMovement().x * player.getDeltaMovement().x
                        + player.getDeltaMovement().z * player.getDeltaMovement().z;

        return horizontalSpeedSqr > 0.0025D || player.isSprinting() || !player.onGround();
    }

    private static int applyOneLevelOvercapIfEligible(
            Holder<Enchantment> enchantment,
            int rolledLevel,
            int enchantingPower,
            RandomSource random
    ) {
        int vanillaMax = enchantment.value().getMaxLevel();
        if (!canAttemptOverEnchant(enchantingPower)) {
            return Math.min(rolledLevel, vanillaMax);
        }

        if (rolledLevel < vanillaMax) {
            return rolledLevel;
        }

        if (random.nextFloat() < getOverEnchantChance(enchantingPower)) {
            return vanillaMax + MAX_OVER_ENCHANT_BONUS;
        }

        return vanillaMax;
    }

    @SubscribeEvent
    public static void onLowHealthDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (event.getSource().is(DamageTypes.GENERIC_KILL)
                || event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return;
        }

        if (!OathboundConfig.enableAbsorptionBlessing() || !OathboundUtil.isBranded(player)) {
            return;
        }

        float threshold = (float) OathboundConfig.absorptionThreshold();
        if (player.getHealth() <= threshold && player.getAbsorptionAmount() <= 0) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.ABSORPTION,
                    200,
                    (int) (OathboundConfig.absorptionAmount() / 4) - 1,
                    false,
                    true
            ));
        }
    }

    private static void provokeNearbyNeutralMobs(Player player) {
        AABB area = player.getBoundingBox().inflate(OathboundConfig.neutralAggroRange());

        for (Mob mob : player.level().getEntitiesOfClass(Mob.class, area, Mob::isAlive)) {
            if (!(mob instanceof NeutralMob neutralMob)) {
                continue;
            }

            if (mob.getTarget() == player) {
                continue;
            }

            mob.setTarget(player);
            neutralMob.setPersistentAngerTarget(player.getUUID());
            neutralMob.startPersistentAngerTimer();
        }
    }
}