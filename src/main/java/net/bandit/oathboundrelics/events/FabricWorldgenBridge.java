package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * Fabric replacement for the NeoForge biome modifier that injects Aredrite ore into End biomes.
 */
public final class FabricWorldgenBridge {
    private FabricWorldgenBridge() {
    }

    public static void register() {
        ResourceKey<PlacedFeature> aredriteOre = ResourceKey.create(
                Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "aredrite_ore_placed")
        );

        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_END),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                aredriteOre
        );
    }
}
