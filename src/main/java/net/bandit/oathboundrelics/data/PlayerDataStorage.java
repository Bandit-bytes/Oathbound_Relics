package net.bandit.oathboundrelics.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Fabric-side replacement for the NeoForge player attachments used by the original mod.
 *
 * Data is cached during play, then serialized into the entity persistent data tag that is
 * supplied by EntityPersistentDataMixin.
 */
public final class PlayerDataStorage {
    private static final String PLAYER_DATA_KEY = "OathboundRelicsPlayerData";

    private static final Map<UUID, PlayerOathData> CLIENT_OR_FALLBACK = new HashMap<>();

    private PlayerDataStorage() {
    }

    public static PlayerOathData get(Player player) {
        return CLIENT_OR_FALLBACK.computeIfAbsent(player.getUUID(), uuid -> {
            PlayerOathData data = new PlayerOathData();
            CompoundTag persistent = PersistentData.get(player);
            if (persistent.contains(PLAYER_DATA_KEY)) {
                data.load(persistent.getCompound(PLAYER_DATA_KEY));
            }
            return data;
        });
    }

    public static void invalidate(Player player) {
        CLIENT_OR_FALLBACK.remove(player.getUUID());
    }

    public static void saveToPersistent(Player player) {
        PersistentData.get(player).put(PLAYER_DATA_KEY, get(player).save());
    }

    public static void copy(Player original, Player replacement, boolean keepEverything) {
        PlayerOathData oldData = get(original);
        PlayerOathData newData = get(replacement);
        if (keepEverything) {
            newData.load(oldData.save());
        } else {
            // Soul fracture and branded progress were copy-on-death in the NeoForge attachment setup.
            newData.brandedTime.deserializeNBT(replacement.registryAccess(), oldData.brandedTime.serializeNBT(original.registryAccess()));
            newData.soulFractureCount = oldData.soulFractureCount;
        }
        saveToPersistent(replacement);
    }

    public static BrandedTimeData brandedTime(Player player) {
        return get(player).brandedTime;
    }

    public static void setBrandedTime(Player player, BrandedTimeData data) {
        get(player).brandedTime = data;
        saveToPersistent(player);
    }

    public static int soulFractureCount(Player player) {
        return get(player).soulFractureCount;
    }

    public static void setSoulFractureCount(Player player, int value) {
        get(player).soulFractureCount = Math.max(0, value);
        saveToPersistent(player);
    }

    public static PrideStateData prideState(Player player) {
        return get(player).prideState;
    }

    public static void setPrideState(Player player, PrideStateData data) {
        get(player).prideState = data;
        saveToPersistent(player);
    }

    public static EnvyStateData envyState(Player player) {
        return get(player).envyState;
    }

    public static void setEnvyState(Player player, EnvyStateData data) {
        get(player).envyState = data;
        saveToPersistent(player);
    }

    public static final class PlayerOathData {
        private BrandedTimeData brandedTime = new BrandedTimeData();
        private int soulFractureCount;
        private PrideStateData prideState = new PrideStateData();
        private EnvyStateData envyState = new EnvyStateData();

        public CompoundTag save() {
            CompoundTag tag = new CompoundTag();
            tag.put("BrandedTime", brandedTime.serializeNBT(null));
            tag.putInt("SoulFractureCount", soulFractureCount);
            tag.put("PrideState", prideState.serializeNBT(null));
            tag.put("EnvyState", envyState.serializeNBT(null));
            return tag;
        }

        public void load(CompoundTag tag) {
            brandedTime.deserializeNBT(null, tag.getCompound("BrandedTime"));
            soulFractureCount = tag.getInt("SoulFractureCount");
            prideState.deserializeNBT(null, tag.getCompound("PrideState"));
            envyState.deserializeNBT(null, tag.getCompound("EnvyState"));
        }
    }
}
