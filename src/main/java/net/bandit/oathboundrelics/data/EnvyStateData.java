package net.bandit.oathboundrelics.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.UUID;

public class EnvyStateData implements INBTSerializable<CompoundTag> {

    private UUID covetedTargetUuid;
    private long covetedTargetExpiry;

    private long stolenVitalityExpiry;
    private long stolenPlatingExpiry;
    private long stolenSwiftnessExpiry;
    private long stolenFerocityExpiry;

    public void setCovetedTarget(UUID uuid, long expiry) {
        this.covetedTargetUuid = uuid;
        this.covetedTargetExpiry = expiry;
    }

    public void clearCovetedTarget() {
        this.covetedTargetUuid = null;
        this.covetedTargetExpiry = 0L;
    }

    public boolean hasCovetedTarget(long gameTime) {
        if (covetedTargetUuid == null) {
            return false;
        }

        if (covetedTargetExpiry <= gameTime) {
            clearCovetedTarget();
            return false;
        }

        return true;
    }

    public UUID getCovetedTargetUuid(long gameTime) {
        return hasCovetedTarget(gameTime) ? covetedTargetUuid : null;
    }

    public boolean isCovetedTarget(UUID uuid, long gameTime) {
        return uuid != null && hasCovetedTarget(gameTime) && uuid.equals(covetedTargetUuid);
    }

    public void extendStolenVitality(long expiry) {
        this.stolenVitalityExpiry = Math.max(this.stolenVitalityExpiry, expiry);
    }

    public void extendStolenPlating(long expiry) {
        this.stolenPlatingExpiry = Math.max(this.stolenPlatingExpiry, expiry);
    }

    public void extendStolenSwiftness(long expiry) {
        this.stolenSwiftnessExpiry = Math.max(this.stolenSwiftnessExpiry, expiry);
    }

    public void extendStolenFerocity(long expiry) {
        this.stolenFerocityExpiry = Math.max(this.stolenFerocityExpiry, expiry);
    }

    public boolean hasStolenVitality(long gameTime) {
        return stolenVitalityExpiry > gameTime;
    }

    public boolean hasStolenPlating(long gameTime) {
        return stolenPlatingExpiry > gameTime;
    }

    public boolean hasStolenSwiftness(long gameTime) {
        return stolenSwiftnessExpiry > gameTime;
    }

    public boolean hasStolenFerocity(long gameTime) {
        return stolenFerocityExpiry > gameTime;
    }

    public boolean hasAnyActiveBuffs(long gameTime) {
        return hasStolenVitality(gameTime)
                || hasStolenPlating(gameTime)
                || hasStolenSwiftness(gameTime)
                || hasStolenFerocity(gameTime)
                || hasCovetedTarget(gameTime);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();

        if (covetedTargetUuid != null) {
            tag.putUUID("CovetedTargetUuid", covetedTargetUuid);
        }

        tag.putLong("CovetedTargetExpiry", covetedTargetExpiry);
        tag.putLong("StolenVitalityExpiry", stolenVitalityExpiry);
        tag.putLong("StolenPlatingExpiry", stolenPlatingExpiry);
        tag.putLong("StolenSwiftnessExpiry", stolenSwiftnessExpiry);
        tag.putLong("StolenFerocityExpiry", stolenFerocityExpiry);

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        covetedTargetUuid = tag.hasUUID("CovetedTargetUuid") ? tag.getUUID("CovetedTargetUuid") : null;
        covetedTargetExpiry = tag.getLong("CovetedTargetExpiry");
        stolenVitalityExpiry = tag.getLong("StolenVitalityExpiry");
        stolenPlatingExpiry = tag.getLong("StolenPlatingExpiry");
        stolenSwiftnessExpiry = tag.getLong("StolenSwiftnessExpiry");
        stolenFerocityExpiry = tag.getLong("StolenFerocityExpiry");
    }
}