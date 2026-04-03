package net.bandit.oathboundrelics.loot;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.resources.ResourceLocation;

public final class OathboundLootTables {

    private OathboundLootTables() {
    }

    private static ResourceLocation mod(String path) {
        return ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, path);
    }

    private static ResourceLocation mc(String path) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", path);
    }

    public static final ResourceLocation ANCIENT_CITY = mc("chests/ancient_city");
    public static final ResourceLocation STRONGHOLD_LIBRARY = mc("chests/stronghold_library");
    public static final ResourceLocation STRONGHOLD_CORRIDOR = mc("chests/stronghold_corridor");
    public static final ResourceLocation STRONGHOLD_CROSSING = mc("chests/stronghold_crossing");
    public static final ResourceLocation NETHER_BRIDGE = mc("chests/nether_bridge");
    public static final ResourceLocation BASTION_TREASURE = mc("chests/bastion_treasure");
    public static final ResourceLocation WOODLAND_MANSION = mc("chests/woodland_mansion");
    public static final ResourceLocation END_CITY_TREASURE = mc("chests/end_city_treasure");
    public static final ResourceLocation DESERT_PYRAMID = mc("chests/desert_pyramid");
    public static final ResourceLocation TRAIL_RUINS_COMMON = mc("archaeology/trail_ruins_common");
    public static final ResourceLocation TRAIL_RUINS_RARE = mc("archaeology/trail_ruins_rare");
    public static final ResourceLocation TRIAL_REWARD_RARE = mc("chests/trial_chambers/reward_rare");
    public static final ResourceLocation TRIAL_REWARD_OMINOUS_RARE = mc("chests/trial_chambers/reward_ominous_rare");

    public static final ResourceLocation WITHER = mc("entities/wither");
    public static final ResourceLocation WARDEN = mc("entities/warden");
    public static final ResourceLocation ENDER_DRAGON = mc("entities/ender_dragon");
    public static final ResourceLocation SHULKER = mc("entities/shulker");

    public static final ResourceLocation INJECT_ANCIENT_CITY = mod("inject/chests/ancient_city_relics");
    public static final ResourceLocation INJECT_STRONGHOLD_LIBRARY = mod("inject/chests/stronghold_library_relics");
    public static final ResourceLocation INJECT_STRONGHOLD_SUPPORT = mod("inject/chests/stronghold_support_relics");
    public static final ResourceLocation INJECT_NETHER_BRIDGE = mod("inject/chests/nether_bridge_relics");
    public static final ResourceLocation INJECT_BASTION_TREASURE = mod("inject/chests/bastion_treasure_relics");
    public static final ResourceLocation INJECT_WOODLAND_MANSION = mod("inject/chests/woodland_mansion_relics");
    public static final ResourceLocation INJECT_END_CITY = mod("inject/chests/end_city_treasure_relics");
    public static final ResourceLocation INJECT_DESERT_PYRAMID = mod("inject/chests/desert_pyramid_relics");
    public static final ResourceLocation INJECT_TRAIL_RUINS_COMMON = mod("inject/archaeology/trail_ruins_common_relics");
    public static final ResourceLocation INJECT_TRAIL_RUINS_RARE = mod("inject/archaeology/trail_ruins_rare_relics");
    public static final ResourceLocation INJECT_TRIAL_RARE = mod("inject/chests/trial_reward_rare_relics");
    public static final ResourceLocation INJECT_TRIAL_OMINOUS_RARE = mod("inject/chests/trial_reward_ominous_rare_relics");

    public static final ResourceLocation INJECT_WITHER_OBLIVION = mod("inject/entities/wither_oblivion_stone");
    public static final ResourceLocation INJECT_WARDEN_OBLIVION = mod("inject/entities/warden_oblivion_stone");
    public static final ResourceLocation INJECT_DRAGON_OBLIVION = mod("inject/entities/ender_dragon_oblivion_stone");

    public static final ResourceLocation INJECT_SHULKER_VOID_ASHES = mod("inject/entities/shulker_void_ashes");
}