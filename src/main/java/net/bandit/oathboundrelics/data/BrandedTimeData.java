package net.bandit.oathboundrelics.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class BrandedTimeData implements INBTSerializable<CompoundTag> {

    private static final int MAX_ACTIVITY_TICKS = 20 * 10; // 10 seconds

    private long brandedProgressTicks;
    private int recentActivityTicks;

    public long getBrandedProgressTicks() {
        return brandedProgressTicks;
    }

    public void setBrandedProgressTicks(long ticks) {
        this.brandedProgressTicks = Math.max(0L, ticks);
    }

    public void addBrandedProgressTicks(long amount) {
        this.brandedProgressTicks = Math.max(0L, this.brandedProgressTicks + amount);
    }

    public int getRecentActivityTicks() {
        return recentActivityTicks;
    }

    public boolean hasRecentActivity() {
        return recentActivityTicks > 0;
    }

    public void refreshActivity(int ticks) {
        this.recentActivityTicks = Math.max(this.recentActivityTicks, Math.min(MAX_ACTIVITY_TICKS, ticks));
    }

    public void tick(boolean branded, boolean active, long maxTicks) {
        if (active) {
            refreshActivity(20 * 3); // stay "active" briefly after recent play
        } else if (recentActivityTicks > 0) {
            recentActivityTicks--;
        }

        if (branded && recentActivityTicks > 0 && brandedProgressTicks < maxTicks) {
            brandedProgressTicks++;
        }
    }

    public double getProgressRatio(long maxTicks) {
        if (maxTicks <= 0L) {
            return 0.0D;
        }

        return Math.min(1.0D, (double) brandedProgressTicks / (double) maxTicks);
    }

    public boolean qualifies(long maxTicks, double requiredRatio) {
        return getProgressRatio(maxTicks) >= requiredRatio;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putLong("BrandedProgressTicks", brandedProgressTicks);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        if (compoundTag.contains("BrandedProgressTicks")) {
            brandedProgressTicks = Math.max(0L, compoundTag.getLong("BrandedProgressTicks"));
            recentActivityTicks = 0;
            return;
        }

        if (compoundTag.contains("BrandedWorldTicks")) {
            brandedProgressTicks = Math.max(0L, compoundTag.getLong("BrandedWorldTicks"));
            recentActivityTicks = 0;
            return;
        }

        brandedProgressTicks = 0L;
        recentActivityTicks = 0;
    }
}