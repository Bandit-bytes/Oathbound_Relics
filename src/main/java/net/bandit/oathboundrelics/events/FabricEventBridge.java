package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.util.OathboundUtil;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class FabricEventBridge {
    private FabricEventBridge() {
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                CommandEvents.onRegisterCommands(new RegisterCommandsEvent(dispatcher)));

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                PlayerTickEvent.Post event = new PlayerTickEvent.Post(player);
                BrandedTimeTrackerEvents.onPlayerTick(event);
                AredriteArmorEvents.onPlayerTick(event);
                CovetfangEvents.onPlayerTick(event);
                OathboundBearerCurioEvents.onPlayerTick(event);
                OathboundRelicEvents.onPlayerTick(event);
                SlothWeaponEvents.onPlayerTick(event);
                SoulLanternEvents.onPlayerTick(event);
                TitansRemnantEvents.onPlayerTick(event);
                VanitysEdgeEvents.onPlayerTick(event);
            }
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            AttackEntityEvent event = new AttackEntityEvent(player, entity);
            CovetfangEvents.onAttackEntity(event);
            SlothCombatEvents.onAttackEntity(event);
            VanitysEdgeEvents.onAttackEntity(event);
            return event.isCanceled() ? InteractionResult.FAIL : InteractionResult.PASS;
        });

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (!(killedEntity instanceof LivingEntity living)) {
                return;
            }

            DamageSource source = living.getLastDamageSource();

            if (source == null) {
                source = oathbound$createFallbackDeathSource(entity, living);
            }

            LivingDeathEvent event = new LivingDeathEvent(living, source);
            AredriteArmorEvents.onKill(event);
            CovetfangEvents.onKill(event);
            OathboundBearerCurioEvents.onLivingDeath(event);
            OathboundRelicEvents.onKill(event);
            OblivionStoneDropEvents.onLivingDeath(event);
            SoulFractureEvents.onLivingDeath(event);
            VanitysEdgeEvents.onKill(event);
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerEvent.Clone clone = new PlayerEvent.Clone(oldPlayer, newPlayer, !alive);
            SoulFractureEvents.onPlayerClone(clone);
            SoulHarvestEvents.onPlayerClone(clone);
            SoulLanternEvents.onClone(clone);

            PlayerEvent.PlayerRespawnEvent respawn = new PlayerEvent.PlayerRespawnEvent(newPlayer);
            OathboundRelicEvents.onRespawn(respawn);
            SoulFractureEvents.onPlayerRespawn(respawn);
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerEvent.PlayerLoggedInEvent event = new PlayerEvent.PlayerLoggedInEvent(handler.getPlayer());
            SoulFractureEvents.onPlayerLoggedIn(event);
            ServerPlayer player = handler.getPlayer();
            OathboundUtil.giveStarterBookOnce(player);
            SoulHarvestEvents.onPlayerLoggedIn(event);
            StarterOathboundRelicEvents.onPlayerLogin(event);
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
                SoulLanternEvents.onLogout(new PlayerEvent.PlayerLoggedOutEvent(handler.getPlayer())));

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            OathboundRelicEvents.onBreakBlock(new BlockEvent.BreakEvent(world, pos, player));
            return true;
        });
    }

    public static float fireIncomingDamage(LivingEntity entity, DamageSource source, float amount) {
        LivingIncomingDamageEvent event = new LivingIncomingDamageEvent(entity, source, amount);
        OathboundRelicEvents.onIncomingDamage(event);
        VanitysEdgeEvents.onIncomingDamage(event);
        return event.resultAmount();
    }

    public static LivingDamageEvent.Pre fireLivingDamagePre(LivingEntity entity, DamageSource source, float amount) {
        LivingDamageEvent.Pre event = new LivingDamageEvent.Pre(entity, source, amount);
        AredriteArmorEvents.onDamagePre(event);
        CovetfangEvents.onDamagePre(event);
        OathboundBearerCurioEvents.onPlayerDealsDamagePre(event);
        OathboundRelicEvents.onOutgoingDamage(event);
        OathboundRelicEvents.onLowHealthDamage(event);
        SlothCombatEvents.onDamagePre(event);
        TitansRemnantEvents.onDamagePre(event);
        VanitysEdgeEvents.onDamagePre(event);
        return event;
    }

    public static void fireLivingDamagePost(LivingEntity entity, DamageSource source, float amount) {
        LivingDamageEvent.Post event = new LivingDamageEvent.Post(entity, source, amount);
        CovetfangEvents.onDamagePost(event);
        OathboundBearerCurioEvents.onPlayerDamaged(event);
        OathboundBearerCurioEvents.onPlayerDealsDamagePost(event);
        OathboundRelicEvents.onSuccessfulHit(event);
        TitansRemnantEvents.onDamagePost(event);
        VanitysEdgeEvents.onDamagePost(event);
    }

    public static float fireLivingHeal(LivingEntity entity, float amount) {
        LivingHealEvent event = new LivingHealEvent(entity, amount);
        OathboundRitualEvents.onLivingHeal(event);
        VanitysEdgeEvents.onHeal(event);
        return event.getAmount();
    }

    private static DamageSource oathbound$createFallbackDeathSource(Entity killer, LivingEntity victim) {
        if (killer instanceof Player player) {
            return victim.damageSources().playerAttack(player);
        }

        if (killer instanceof Projectile projectile) {
            Entity owner = projectile.getOwner();
            return victim.damageSources().thrown(projectile, owner);
        }

        if (killer instanceof LivingEntity livingKiller) {
            return victim.damageSources().mobAttack(livingKiller);
        }

        return victim.damageSources().generic();
    }
}
