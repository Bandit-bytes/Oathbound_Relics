package top.theillusivec4.curios.api;

import net.minecraft.world.entity.LivingEntity;

public record SlotContext(String identifier, LivingEntity entity, int index) {
}
