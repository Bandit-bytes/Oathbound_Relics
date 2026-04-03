package net.bandit.oathboundrelics.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AredriteGemItem extends Item {

    public AredriteGemItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite.line_1")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.oathboundrelics.aredrite.line_2")
                .withStyle(ChatFormatting.GRAY));
    }
}