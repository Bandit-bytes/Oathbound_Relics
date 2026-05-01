package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public class ArmorMaterialRegistry {

    public static final Holder<ArmorMaterial> AREDRITE = register(
            "aredrite",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 5);
                        map.put(ArmorItem.Type.LEGGINGS, 8);
                        map.put(ArmorItem.Type.CHESTPLATE, 12);
                        map.put(ArmorItem.Type.HELMET, 5);
                        map.put(ArmorItem.Type.BODY, 7);
                    }),
                    22,
                    SoundEvents.ARMOR_EQUIP_NETHERITE,
                    () -> Ingredient.of(ItemRegistry.AREDRITE_GEM.get()),
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "aredrite")
                            )
                    ),
                    4.0F,
                    0.15F
            )
    );

    private ArmorMaterialRegistry() {
    }

    public static void register() {
        // Static initializers perform registration on Fabric.
    }

    private static Holder<ArmorMaterial> register(String name, java.util.function.Supplier<ArmorMaterial> factory) {
        ArmorMaterial material = Registry.register(BuiltInRegistries.ARMOR_MATERIAL, ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, name), factory.get());
        return BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(material);
    }
}
