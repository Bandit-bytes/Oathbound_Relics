package net.bandit.oathboundrelics.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentTooltipMixin {

    @Inject(
            method = "getFullname",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void oathbound$colorOverleveledEnchantments(
            Holder<Enchantment> enchantment,
            int level,
            CallbackInfoReturnable<Component> cir
    ) {
        if (level > enchantment.value().getMaxLevel()) {
            cir.setReturnValue(cir.getReturnValue().copy().withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }
}