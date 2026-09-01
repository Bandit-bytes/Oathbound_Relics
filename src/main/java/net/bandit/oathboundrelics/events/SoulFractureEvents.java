package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.data.PlayerDataStorage;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.entity.SoulGemEntity;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.registry.EntityRegistry;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.bandit.oathboundrelics.fabricbridge.events.entity.living.LivingDeathEvent;
import net.bandit.oathboundrelics.fabricbridge.events.entity.player.PlayerEvent;
import net.bandit.oathboundrelics.fabricbridge.events.tick.PlayerTickEvent;

public final class SoulFractureEvents {

    private static final ResourceLocation SOUL_FRACTURE_HEALTH_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "soul_fracture_health_penalty");

    private SoulFractureEvents() {
    }
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        if (!OathboundConfig.enableSoulFracture()) {
            return;
        }

        if (!OathboundUtil.isBranded(player)) {
            return;
        }


        int current = PlayerDataStorage.soulFractureCount(player);
        PlayerDataStorage.setSoulFractureCount(player, current + 1);

        if (player.level() instanceof ServerLevel serverLevel) {
            SoulGemEntity gem = new SoulGemEntity(EntityRegistry.SOUL_GEM.get(), serverLevel);
            gem.setOwnerUUID(player.getUUID());
            gem.moveTo(player.getX(), player.getY() + 1.15D, player.getZ(), 0.0F, 0.0F);
            serverLevel.addFreshEntity(gem);
        }
    }
    public static void onPlayerClone(PlayerEvent.Clone event) {
        applySoulFracturePenalty(event.getEntity());
    }
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        applySoulFracturePenalty(event.getEntity());
    }
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        applySoulFracturePenalty(event.getEntity());
    }
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        if (player.tickCount % 10 != 0) {
            return;
        }

        applySoulFracturePenalty(player);
    }

    public static void applySoulFracturePenalty(Player player) {
        if (player.level().isClientSide()) {
            return;
        }

        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            return;
        }

        maxHealth.removeModifier(SOUL_FRACTURE_HEALTH_ID);

        if (!OathboundConfig.enableSoulFracture()) {
            return;
        }

        int soulCount = PlayerDataStorage.soulFractureCount(player);
        if (soulCount <= 0) {
            return;
        }

        double perGemLoss = OathboundConfig.soulFractureMaxHealthLossPerGem();
        double requestedLoss = soulCount * perGemLoss;
        double cappedLoss = Math.min(
                requestedLoss,
                Math.max(0.0D, player.getAttributeBaseValue(Attributes.MAX_HEALTH) - 1.0D)
        );

        if (cappedLoss > 0.0D) {
            maxHealth.addTransientModifier(new AttributeModifier(
                    SOUL_FRACTURE_HEALTH_ID,
                    -cappedLoss,
                    AttributeModifier.Operation.ADD_VALUE
            ));

            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }
    }
}
