package net.bandit.oathboundrelics.items;

import net.bandit.oathboundrelics.data.PlayerDataStorage;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BrandedTimeCheckerItem extends Item {

    public BrandedTimeCheckerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (!level.isClientSide) {
            BrandedTimeData data = PlayerDataStorage.brandedTime(player);

            long currentTicks = data.getBrandedProgressTicks();
            long maxTicks = OathboundConfig.slothWeaponMaxBrandedTicks();
            double progressPercent = data.getProgressRatio(maxTicks) * 100.0D;

            boolean qualifies = data.qualifies(
                    maxTicks,
                    OathboundConfig.slothWeaponRequiredBrandedPercent()
            );

            player.sendSystemMessage(
                    Component.translatable("message.oathboundrelics.branded_time.current", formatTicks(currentTicks))
                            .withStyle(ChatFormatting.DARK_PURPLE)
            );

            player.sendSystemMessage(
                    Component.translatable("message.oathboundrelics.branded_time.max", formatTicks(maxTicks))
                            .withStyle(ChatFormatting.GRAY)
            );

            player.sendSystemMessage(
                    Component.translatable(
                                    "message.oathboundrelics.branded_time.percent",
                                    String.format("%.3f", progressPercent) + "%"
                            )
                            .withStyle(ChatFormatting.LIGHT_PURPLE)
            );

            player.sendSystemMessage(
                    qualifies
                            ? Component.translatable("message.oathboundrelics.branded_time.ready").withStyle(ChatFormatting.GOLD)
                            : Component.translatable("message.oathboundrelics.branded_time.not_ready").withStyle(ChatFormatting.RED)
            );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private static String formatTicks(long ticks) {
        long totalSeconds = ticks / 20L;
        long hours = totalSeconds / 3600L;
        long minutes = (totalSeconds % 3600L) / 60L;
        long seconds = totalSeconds % 60L;
        return hours + "h " + minutes + "m " + seconds + "s";
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.branded_time_checker.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));
    }
}
