package net.bandit.oathboundrelics.fabricbridge.trinkets;

import net.minecraft.world.entity.LivingEntity;

public record SlotContext(String identifier, LivingEntity entity, int index) {
}
