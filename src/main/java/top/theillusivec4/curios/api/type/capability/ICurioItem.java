package top.theillusivec4.curios.api.type.capability;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketEnums;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

public interface ICurioItem extends Trinket {

    default SlotContext oathbound$slotContext(SlotReference slot, LivingEntity entity) {
        return new SlotContext(
                slot.inventory().getSlotType().getName(),
                entity,
                slot.index()
        );
    }

    // -------------------------
    // Curios-style methods
    // -------------------------

    default boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return false;
    }

    default boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    default boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    default ICurio.DropRule getDropRule(
            SlotContext context,
            DamageSource source,
            boolean recentlyHit,
            ItemStack stack
    ) {
        return ICurio.DropRule.DEFAULT;
    }

    default int getLootingLevel(
            SlotContext slotContext,
            @Nullable LootContext lootContext,
            ItemStack stack
    ) {
        return 0;
    }

    default int getFortuneLevel(
            SlotContext slotContext,
            @Nullable LootContext lootContext,
            ItemStack stack
    ) {
        return 0;
    }

    default Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext,
            ResourceLocation id,
            ItemStack stack
    ) {
        return LinkedHashMultimap.create();
    }

    // -------------------------
    // Trinkets bridge methods
    // -------------------------

    @Override
    default boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return canEquip(oathbound$slotContext(slot, entity), stack);
    }

    @Override
    default boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof Player player && player.isCreative()) {
            return true;
        }

        return canUnequip(oathbound$slotContext(slot, entity), stack);
    }

    @Override
    default boolean canEquipFromUse(ItemStack stack, LivingEntity entity) {
        return canEquipFromUse(new SlotContext("", entity, -1), stack);
    }

    @Override
    default TrinketEnums.DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
        ICurio.DropRule curiosRule = getDropRule(
                oathbound$slotContext(slot, entity),
                entity.damageSources().generic(),
                false,
                stack
        );

        return switch (curiosRule) {
            case ALWAYS_KEEP -> TrinketEnums.DropRule.KEEP;
            case ALWAYS_DROP -> TrinketEnums.DropRule.DROP;
            case DESTROY -> TrinketEnums.DropRule.DESTROY;
            case DEFAULT -> TrinketEnums.DropRule.DEFAULT;
        };
    }

    @Override
    default Multimap<Holder<Attribute>, AttributeModifier> getModifiers(
            ItemStack stack,
            SlotReference slot,
            LivingEntity entity,
            ResourceLocation slotIdentifier
    ) {
        return getAttributeModifiers(oathbound$slotContext(slot, entity), slotIdentifier, stack);
    }
}