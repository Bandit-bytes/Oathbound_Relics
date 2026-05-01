package net.bandit.oathboundrelics.curio.items;

import net.bandit.oathboundrelics.items.TitanRemnantStage;
import net.bandit.oathboundrelics.items.TitanRemnantType;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.bandit.oathboundrelics.util.TitansRemnantUpgradeUtil;
import net.bandit.oathboundrelics.util.TitansRemnantUtil;
import net.bandit.oathboundrelics.util.TooltipAccess;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class TitansRemnantItem extends OathboundCurioItem {

    private final TitanRemnantType type;

    public TitansRemnantItem(Properties properties, TitanRemnantType type) {
        super(properties.stacksTo(1));
        this.type = type;
    }

    public TitanRemnantType getType() {
        return type;
    }

    @Override
    public Component getName(ItemStack stack) {
        TitanRemnantStage stage = TitansRemnantUtil.getStage(stack);
        return Component.translatable("item.oathboundrelics." + type.key() + "." + stage.key());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return TitansRemnantUpgradeUtil.tryUpgrade(level, player, hand);
    }


    private boolean viewerIsOathbearer() {
        Player player = TooltipAccess.getClientPlayer();
        return player != null && OathboundUtil.isBranded(player);
    }

    private boolean shouldRevealStageDetails(TitanRemnantStage stage) {
        return switch (stage) {
            case DORMANT, LATENT, AWAKENED -> true;
            case ASCENDED, TRANSCENDENT, APEX -> viewerIsOathbearer();
        };
    }

    private Component buildStateLine(TitanRemnantStage stage) {
        ChatFormatting color = switch (stage) {
            case DORMANT -> ChatFormatting.DARK_GRAY;
            case LATENT -> ChatFormatting.GRAY;
            case AWAKENED -> ChatFormatting.LIGHT_PURPLE;
            case ASCENDED -> ChatFormatting.AQUA;
            case TRANSCENDENT -> ChatFormatting.BLUE;
            case APEX -> ChatFormatting.GOLD;
        };

        return Component.translatable("tooltip.oathboundrelics.state." + stage.key())
                .withStyle(color);
    }

    private void addNextUpgradeLine(List<Component> tooltip, TitanRemnantStage stage) {
        if (stage == TitanRemnantStage.APEX) {
            tooltip.add(Component.translatable("tooltip.oathboundrelics.remnant.next_upgrade.apex")
                    .withStyle(ChatFormatting.DARK_GRAY));
            return;
        }

        tooltip.add(Component.translatable("tooltip.oathboundrelics.remnant.next_upgrade." + stage.key())
                .withStyle(ChatFormatting.GOLD));
    }

    private void addCurrentStageBody(List<Component> tooltip, TitanRemnantStage stage) {
        String baseKey = "tooltip.oathboundrelics." + type.key() + "." + stage.key();

        if (!shouldRevealStageDetails(stage)) {
            tooltip.add(Component.translatable("tooltip.oathboundrelics.requires_oathbound_relic")
                    .withStyle(ChatFormatting.DARK_PURPLE));
            tooltip.add(Component.translatable("tooltip.oathboundrelics.forbidden_text")
                    .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.OBFUSCATED));
            return;
        }

        tooltip.add(Component.translatable(baseKey + ".line_1")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable(baseKey + ".line_2")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        TitanRemnantStage storedStage = TitansRemnantUtil.getStage(stack);

        tooltip.add(Component.translatable("tooltip.oathboundrelics.titans_remnant.slot")
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(buildStateLine(storedStage));
        tooltip.add(Component.empty());

        addFlavorTooltip(tooltip, "tooltip.oathboundrelics." + type.key() + ".flavor");

        if (!TooltipAccess.hasShiftDown()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.oathboundrelics.titans_remnant.hold_shift")
                    .withStyle(ChatFormatting.DARK_GRAY));
            addNextUpgradeLine(tooltip, storedStage);
            return;
        }

        tooltip.add(Component.empty());
        addCurrentStageBody(tooltip, storedStage);

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.oathboundrelics.titans_remnant.release_shift")
                .withStyle(ChatFormatting.DARK_GRAY));
        addNextUpgradeLine(tooltip, storedStage);
    }
}
