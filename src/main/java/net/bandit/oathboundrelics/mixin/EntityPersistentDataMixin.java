package net.bandit.oathboundrelics.mixin;

import net.bandit.oathboundrelics.data.OathboundPersistentDataHolder;
import net.bandit.oathboundrelics.data.PlayerDataStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityPersistentDataMixin implements OathboundPersistentDataHolder {

    @Unique
    private static final String OATHBOUND_PERSISTENT_DATA_KEY = "OathboundRelicsPersistentData";

    @Unique
    private CompoundTag oathbound$persistentData;

    @Override
    public CompoundTag oathbound$getPersistentData() {
        if (this.oathbound$persistentData == null) {
            this.oathbound$persistentData = new CompoundTag();
        }

        return this.oathbound$persistentData;
    }

    @Inject(method = "saveWithoutId", at = @At("HEAD"))
    private void oathbound$beforeSave(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        if ((Object) this instanceof Player player) {
            PlayerDataStorage.saveToPersistent(player);
        }
    }

    @Inject(method = "saveWithoutId", at = @At("RETURN"))
    private void oathbound$savePersistentData(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag output = cir.getReturnValue();

        if (output == null) {
            output = tag;
        }

        if (this.oathbound$persistentData != null && !this.oathbound$persistentData.isEmpty()) {
            output.put(OATHBOUND_PERSISTENT_DATA_KEY, this.oathbound$persistentData.copy());
        }
    }

    @Inject(method = "load", at = @At("TAIL"))
    private void oathbound$readPersistentData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(OATHBOUND_PERSISTENT_DATA_KEY)) {
            this.oathbound$persistentData = tag.getCompound(OATHBOUND_PERSISTENT_DATA_KEY).copy();
        } else {
            this.oathbound$persistentData = new CompoundTag();
        }

        if ((Object) this instanceof Player player) {
            PlayerDataStorage.invalidate(player);
        }
    }
}