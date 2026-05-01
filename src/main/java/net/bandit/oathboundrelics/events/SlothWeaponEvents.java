package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.util.SlothWeaponUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class SlothWeaponEvents {

    private static final ResourceLocation LETHARGIC_FLAIL_BURDEN_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "lethargic_flail_burden");

    private SlothWeaponEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        int interval = OathboundConfig.lethargicFlailInventoryCheckIntervalTicks();
        if (player.tickCount % interval != 0) {
            return;
        }

        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            return;
        }

        if (player.isCreative() || player.isSpectator() || !OathboundConfig.enableLethargicFlail()) {
            movementSpeed.removeModifier(LETHARGIC_FLAIL_BURDEN_ID);
            return;
        }

        boolean holdingWeapon = SlothWeaponUtil.isHoldingLethargicFlail(player);

        if (holdingWeapon) {
            if (!movementSpeed.hasModifier(LETHARGIC_FLAIL_BURDEN_ID)) {
                movementSpeed.addTransientModifier(new AttributeModifier(
                        LETHARGIC_FLAIL_BURDEN_ID,
                        OathboundConfig.lethargicFlailInventoryMoveSpeedMultiplier(),
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ));
            }

            double perInterval = OathboundConfig.lethargicFlailInventoryExhaustionPerSecond() * (interval / 20.0D);
            player.causeFoodExhaustion((float) perInterval);
        } else {
            movementSpeed.removeModifier(LETHARGIC_FLAIL_BURDEN_ID);
        }
    }
}