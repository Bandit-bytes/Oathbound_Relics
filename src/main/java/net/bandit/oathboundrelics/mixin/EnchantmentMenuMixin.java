package net.bandit.oathboundrelics.mixin;

import net.bandit.oathboundrelics.util.OathboundEnchantingUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {

    @Shadow @Final
    private DataSlot enchantmentSeed;

    @Inject(
            method = "getEnchantmentList(Lnet/minecraft/core/RegistryAccess;Lnet/minecraft/world/item/ItemStack;II)Ljava/util/List;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void oathbound$applySingleOvercapRoll(
            RegistryAccess registryAccess,
            ItemStack stack,
            int slot,
            int cost,
            CallbackInfoReturnable<List<EnchantmentInstance>> cir
    ) {
        List<EnchantmentInstance> original = cir.getReturnValue();

        if (original == null || original.isEmpty()) {
            return;
        }

        cir.setReturnValue(
                OathboundEnchantingUtil.applySingleOvercapRoll(
                        original,
                        slot,
                        cost,
                        this.enchantmentSeed.get()
                )
        );
    }
}