package net.bandit.oathboundrelics.curio.items;

import net.bandit.oathboundrelics.items.TitanRemnantType;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

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

    private boolean isAwakenedForViewer() {
        Player player = Minecraft.getInstance().player;
        return player != null && OathboundUtil.isBranded(player);
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltip,
            TooltipFlag flag
    ) {
        boolean awakened = isAwakenedForViewer();

        addStateLine(tooltip, awakened);
        tooltip.add(Component.empty());

        addFlavorTooltip(tooltip, "tooltip.oathboundrelics." + type.key() + ".flavor");
        tooltip.add(Component.empty());

        addDormantLine(tooltip, "tooltip.oathboundrelics." + type.key() + ".dormant_header");
        addDormantLine(tooltip, "tooltip.oathboundrelics." + type.key() + ".dormant_1");
        addDormantLine(tooltip, "tooltip.oathboundrelics." + type.key() + ".dormant_2");

        tooltip.add(Component.empty());

        addAwakenedLine(tooltip, "tooltip.oathboundrelics." + type.key() + ".awakened_header");
        addAwakenedLine(tooltip, "tooltip.oathboundrelics." + type.key() + ".awakened_1");
        addAwakenedLine(tooltip, "tooltip.oathboundrelics." + type.key() + ".awakened_2");
    }
}