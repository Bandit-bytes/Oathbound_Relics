package net.bandit.oathboundrelics.client;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.network.SkybrandGlidePayload;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(
        modid = OathboundRelicsMod.MOD_ID,
        value = net.neoforged.api.distmarker.Dist.CLIENT
)
public final class SkybrandClientInput {

    private static Boolean lastJumpHeld = null;

    private SkybrandClientInput() {}

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            lastJumpHeld = null;
            return;
        }

        boolean jumpHeld = minecraft.options.keyJump.isDown();

        if (lastJumpHeld == null || jumpHeld != lastJumpHeld) {
            lastJumpHeld = jumpHeld;

            PacketDistributor.sendToServer(
                    new SkybrandGlidePayload(jumpHeld)
            );
        }
    }
}