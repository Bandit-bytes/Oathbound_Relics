package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.entity.HeavyCubeProjectileEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, OathboundRelicsMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<HeavyCubeProjectileEntity>> HEAVY_CUBE_PROJECTILE =
            ENTITY_TYPES.register("heavy_cube_projectile", () ->
                    EntityType.Builder.<HeavyCubeProjectileEntity>of(HeavyCubeProjectileEntity::new, MobCategory.MISC)
                            .sized(0.8F, 0.8F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("heavy_cube_projectile")
            );

    private EntityRegistry() {
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}