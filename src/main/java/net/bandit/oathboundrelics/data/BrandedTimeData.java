package net.bandit.oathboundrelics.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class BrandedTimeData implements INBTSerializable<CompoundTag> {

    private long brandedProgressTicks;

    public long getBrandedProgressTicks() {
        return brandedProgressTicks;
    }

    public void setBrandedProgressTicks(long ticks) {
        this.brandedProgressTicks = Math.max(0L, ticks);
    }

    public void addBrandedProgressTicks(long amount) {
        this.brandedProgressTicks = Math.max(0L, this.brandedProgressTicks + amount);
    }

    public void tick(boolean branded, long maxTicks) {
        if (branded && brandedProgressTicks < maxTicks) {
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
            return;
        }

        if (compoundTag.contains("BrandedWorldTicks")) {
            brandedProgressTicks = Math.max(0L, compoundTag.getLong("BrandedWorldTicks"));
            return;
        }

        brandedProgressTicks = 0L;
    }
}