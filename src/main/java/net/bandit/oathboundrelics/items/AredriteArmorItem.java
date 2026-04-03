package net.bandit.oathboundrelics.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AredriteArmorItem extends ArmorItem {

    public AredriteArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_armor.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
        tooltip.add(Component.empty());

        switch (this.type) {
            case HELMET -> {
                tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_helmet.line_1").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_helmet.line_2").withStyle(ChatFormatting.GRAY));
            }
            case CHESTPLATE -> {
                tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_chestplate.line_1").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_chestplate.line_2").withStyle(ChatFormatting.GRAY));
            }
            case LEGGINGS -> {
                tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_leggings.line_1").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_leggings.line_2").withStyle(ChatFormatting.GRAY));
            }
            case BOOTS -> {
                tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_boots.line_1").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_boots.line_2").withStyle(ChatFormatting.GRAY));
            }
            default -> {
            }
        }

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_armor.set_bonus")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_armor.set_bonus_2")
                .withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_armor.set_bonus_3")
                .withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite_armor.set_bonus_4")
                .withStyle(ChatFormatting.DARK_GRAY));
    }
}