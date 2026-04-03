package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.entity.HeavyCubeProjectileEntity;
import net.bandit.oathboundrelics.entity.SoulGemEntity;
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
    public static final DeferredHolder<EntityType<?>, EntityType<SoulGemEntity>> SOUL_GEM =
            ENTITY_TYPES.register("soul_gem", () ->
                    EntityType.Builder.<SoulGemEntity>of(SoulGemEntity::new, MobCategory.MISC)
                            .sized(0.35F, 0.35F)
                            .clientTrackingRange(8)
                            .updateInterval(10)
                            .build(OathboundRelicsMod.MOD_ID + ":soul_gem")
            );

    private EntityRegistry() {
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}