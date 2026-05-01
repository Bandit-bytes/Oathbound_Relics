package net.bandit.oathboundrelics.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

/**
 * Fabric replacement for NeoForge's Entity#getPersistentData().
 *
 * Backed by an Entity mixin so the tag is serialized with the entity/player NBT.
 */
public final class PersistentData {
    private PersistentData() {
    }

    public static CompoundTag get(Entity entity) {
        return ((OathboundPersistentDataHolder) entity).oathbound$getPersistentData();
    }
}
