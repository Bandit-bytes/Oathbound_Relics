package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.entity.HeavyCubeProjectileEntity;
import net.bandit.oathboundrelics.entity.SoulGemEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public final class EntityRegistry {

    public static final FabricRegistrySupplier<EntityType<HeavyCubeProjectileEntity>> HEAVY_CUBE_PROJECTILE =
            register("heavy_cube_projectile", () ->
                    EntityType.Builder.<HeavyCubeProjectileEntity>of(HeavyCubeProjectileEntity::new, MobCategory.MISC)
                            .sized(0.8F, 0.8F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("heavy_cube_projectile")
            );

    public static final FabricRegistrySupplier<EntityType<SoulGemEntity>> SOUL_GEM =
            register("soul_gem", () ->
                    EntityType.Builder.<SoulGemEntity>of(SoulGemEntity::new, MobCategory.MISC)
                            .sized(0.35F, 0.35F)
                            .clientTrackingRange(8)
                            .updateInterval(10)
                            .build(OathboundRelicsMod.MOD_ID + ":soul_gem")
            );

    private EntityRegistry() {
    }

    public static void register() {
        // Static initializers perform registration on Fabric.
    }

    private static <T extends EntityType<?>> FabricRegistrySupplier<T> register(String name, Supplier<T> factory) {
        return new FabricRegistrySupplier<>(Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, name), factory.get()));
    }
}
