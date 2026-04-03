package net.bandit.oathboundrelics.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class BrandedTimeData implements INBTSerializable<CompoundTag> {

    private long totalWorldTicks;
    private long brandedWorldTicks;

    public long getTotalWorldTicks() {
        return totalWorldTicks;
    }

    public long getBrandedWorldTicks() {
        return brandedWorldTicks;
    }

    public void setTotalWorldTicks(long totalWorldTicks) {
        this.totalWorldTicks = Math.max(0L, totalWorldTicks);
        if (this.brandedWorldTicks > this.totalWorldTicks) {
            this.brandedWorldTicks = this.totalWorldTicks;
        }
    }

    public void setBrandedWorldTicks(long brandedWorldTicks) {
        this.brandedWorldTicks = Math.max(0L, Math.min(brandedWorldTicks, this.totalWorldTicks));
    }

    public void setTicks(long totalWorldTicks, long brandedWorldTicks) {
        this.totalWorldTicks = Math.max(0L, totalWorldTicks);
        this.brandedWorldTicks = Math.max(0L, Math.min(brandedWorldTicks, this.totalWorldTicks));
    }

    public void addTotalWorldTicks(long amount) {
        setTotalWorldTicks(this.totalWorldTicks + amount);
    }

    public void addBrandedWorldTicks(long amount) {
        setBrandedWorldTicks(this.brandedWorldTicks + amount);
    }

    public void tick(boolean branded) {
        totalWorldTicks++;
        if (branded) {
            brandedWorldTicks++;
        }
    }

    public double getBrandedRatio() {
        if (totalWorldTicks <= 0L) {
            return 0.0D;
        }
        return (double) brandedWorldTicks / (double) totalWorldTicks;
    }

    public boolean qualifies(long minimumTotalTicks, double requiredRatio) {
        return totalWorldTicks >= minimumTotalTicks && getBrandedRatio() >= requiredRatio;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putLong("TotalWorldTicks", totalWorldTicks);
        tag.putLong("BrandedWorldTicks", brandedWorldTicks);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        totalWorldTicks = compoundTag.getLong("TotalWorldTicks");
        brandedWorldTicks = compoundTag.getLong("BrandedWorldTicks");
    }
}