package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class OblivionStoneDropEvents {

    private OblivionStoneDropEvents() {
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();

        if (entity.level().isClientSide()) {
            return;
        }

        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        boolean shouldDrop = entity instanceof WitherBoss
                || entity instanceof Warden
                || entity instanceof EnderDragon;

        if (!shouldDrop) {
            return;
        }

        ItemStack stack = new ItemStack(ItemRegistry.OBLIVION_STONE.get());

        ItemEntity itemEntity = new ItemEntity(
                serverLevel,
                entity.getX(),
                entity.getY() + 0.5D,
                entity.getZ(),
                stack
        );

        serverLevel.addFreshEntity(itemEntity);
    }
}