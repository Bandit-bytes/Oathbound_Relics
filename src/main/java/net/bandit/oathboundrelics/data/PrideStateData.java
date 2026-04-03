package net.bandit.oathboundrelics.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.List;

public class PrideStateData implements INBTSerializable<CompoundTag> {

    private final List<Long> killStackExpiries = new ArrayList<>();

    public void addKillStack(long expiresAtGameTime) {
        killStackExpiries.add(expiresAtGameTime);
    }

    public int pruneExpiredAndCount(long currentGameTime) {
        killStackExpiries.removeIf(expiry -> expiry <= currentGameTime);
        return killStackExpiries.size();
    }

    public boolean hasActiveStacks(long currentGameTime) {
        return pruneExpiredAndCount(currentGameTime) > 0;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        long[] expiries = new long[killStackExpiries.size()];

        for (int i = 0; i < killStackExpiries.size(); i++) {
            expiries[i] = killStackExpiries.get(i);
        }

        tag.putLongArray("KillStackExpiries", expiries);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        killStackExpiries.clear();

        long[] expiries = tag.getLongArray("KillStackExpiries");
        for (long expiry : expiries) {
            killStackExpiries.add(expiry);
        }
    }
}