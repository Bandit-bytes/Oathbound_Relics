package net.bandit.oathboundrelics.loot;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Map;

public final class FabricLootInjection {

    private FabricLootInjection() {
    }

    private static final Map<ResourceLocation, ResourceLocation> INJECTIONS = Map.ofEntries(
            Map.entry(OathboundLootTables.ANCIENT_CITY, OathboundLootTables.INJECT_ANCIENT_CITY),
            Map.entry(OathboundLootTables.STRONGHOLD_LIBRARY, OathboundLootTables.INJECT_STRONGHOLD_LIBRARY),
            Map.entry(OathboundLootTables.STRONGHOLD_CORRIDOR, OathboundLootTables.INJECT_STRONGHOLD_SUPPORT),
            Map.entry(OathboundLootTables.STRONGHOLD_CROSSING, OathboundLootTables.INJECT_STRONGHOLD_SUPPORT),
            Map.entry(OathboundLootTables.NETHER_BRIDGE, OathboundLootTables.INJECT_NETHER_BRIDGE),
            Map.entry(OathboundLootTables.BASTION_TREASURE, OathboundLootTables.INJECT_BASTION_TREASURE),
            Map.entry(OathboundLootTables.WOODLAND_MANSION, OathboundLootTables.INJECT_WOODLAND_MANSION),
            Map.entry(OathboundLootTables.END_CITY_TREASURE, OathboundLootTables.INJECT_END_CITY),
            Map.entry(OathboundLootTables.DESERT_PYRAMID, OathboundLootTables.INJECT_DESERT_PYRAMID),
            Map.entry(OathboundLootTables.JUNGLE_TEMPLE, OathboundLootTables.INJECT_JUNGLE_TEMPLE),
            Map.entry(OathboundLootTables.TRAIL_RUINS_COMMON, OathboundLootTables.INJECT_TRAIL_RUINS_COMMON),
            Map.entry(OathboundLootTables.TRAIL_RUINS_RARE, OathboundLootTables.INJECT_TRAIL_RUINS_RARE),
            Map.entry(OathboundLootTables.TRIAL_REWARD_RARE, OathboundLootTables.INJECT_TRIAL_RARE),
            Map.entry(OathboundLootTables.TRIAL_REWARD_OMINOUS_RARE, OathboundLootTables.INJECT_TRIAL_OMINOUS_RARE),
            Map.entry(OathboundLootTables.WITHER, OathboundLootTables.INJECT_WITHER_OBLIVION),
            Map.entry(OathboundLootTables.WARDEN, OathboundLootTables.INJECT_WARDEN_OBLIVION),
            Map.entry(OathboundLootTables.ENDER_DRAGON, OathboundLootTables.INJECT_DRAGON_OBLIVION),
            Map.entry(OathboundLootTables.ELDER_GUARDIAN, OathboundLootTables.INJECT_ELDER_GUARDIAN_SPECIAL),
            Map.entry(OathboundLootTables.PHANTOM, OathboundLootTables.INJECT_PHANTOM_SKYBRAND),
            Map.entry(OathboundLootTables.SHULKER, OathboundLootTables.INJECT_SHULKER_VOID_ASHES)
    );

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin()) {
                return;
            }

            ResourceLocation injectedTable = INJECTIONS.get(key.location());
            if (injectedTable == null) {
                return;
            }

            tableBuilder.withPool(
                    LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(NestedLootTable.lootTableReference(lootTableKey(injectedTable)))
            );
        });
    }

    private static ResourceKey<LootTable> lootTableKey(ResourceLocation id) {
        return ResourceKey.create(Registries.LOOT_TABLE, id);
    }
}