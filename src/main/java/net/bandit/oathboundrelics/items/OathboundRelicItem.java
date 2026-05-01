package net.bandit.oathboundrelics.items;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Locale;

public class OathboundRelicItem extends Item implements ICurioItem {

    public OathboundRelicItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) {
            return false;
        }
        return !OathboundUtil.isBranded(player);
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof Player player)) {
            return false;
        }
        return player.isCreative() || OathboundUtil.canSeverRelic(player);
    }
    @Override
    public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return false;
        }

        if (player.isCreative()) {
            return true;
        }

        return OathboundUtil.canSeverRelic(player);
    }


    @Override
    public ICurio.DropRule getDropRule(SlotContext context, DamageSource source, boolean recentlyHit, ItemStack stack) {
        return ICurio.DropRule.ALWAYS_KEEP;
    }

    @Override
    public int getLootingLevel(SlotContext slotContext, @Nullable LootContext lootContext, ItemStack stack) {
        int base = ICurioItem.super.getLootingLevel(slotContext, lootContext, stack);

        if (!OathboundConfig.enableLootingBlessing()) {
            return base;
        }

        return base + OathboundConfig.lootingBonus();
    }

    @Override
    public int getFortuneLevel(SlotContext slotContext, @Nullable LootContext lootContext, ItemStack stack) {
        int base = ICurioItem.super.getFortuneLevel(slotContext, lootContext, stack);

        if (!OathboundConfig.enableFortuneBlessing()) {
            return base;
        }

        return base + OathboundConfig.fortuneBonus();
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext,
            ResourceLocation id,
            ItemStack stack
    ) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();
        modifiers.putAll(ICurioItem.super.getAttributeModifiers(slotContext, id, stack));

        if (OathboundConfig.enableAttackSpeedBlessing()) {
            ResourceLocation attackSpeedId = ResourceLocation.fromNamespaceAndPath(
                    OathboundRelicsMod.MOD_ID,
                    id.getPath() + "_attack_speed"
            );

            modifiers.put(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(
                            attackSpeedId,
                            OathboundConfig.attackSpeedBonus(),
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        }

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.oathboundrelics.oathbound_relic.flavor")
                .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.oathbound_relic.binding")
                .withStyle(ChatFormatting.RED));

        tooltip.add(Component.translatable("tooltip.oathboundrelics.oathbound_relic.kept_on_death")
                .withStyle(ChatFormatting.GOLD));

        tooltip.add(Component.empty());

        // --- CURSES ---
        tooltip.add(Component.translatable("tooltip.oathboundrelics.oathbound_relic.curses")
                .withStyle(ChatFormatting.LIGHT_PURPLE));

        addCurseLine(tooltip, OathboundConfig.enableFrailty(),
                "tooltip.oathboundrelics.oathbound_relic.curse_1",
                percentIncreaseFromMultiplier(OathboundConfig.incomingDamageMultiplier()));

        addCurseLine(tooltip, OathboundConfig.enableProvocation(),
                "tooltip.oathboundrelics.oathbound_relic.curse_2",
                formatDecimal(OathboundConfig.neutralAggroRange()));

        addCurseLine(tooltip, OathboundConfig.enableShatteredPlate(),
                "tooltip.oathboundrelics.oathbound_relic.curse_3",
                percentReductionFromMultiplier(OathboundConfig.armorEffectiveness()));

        addCurseLine(tooltip, OathboundConfig.enableBloodToll(),
                "tooltip.oathboundrelics.oathbound_relic.curse_4",
                formatDecimal(OathboundConfig.bloodTollHealthCost()));

        addCurseLine(tooltip, OathboundConfig.enableLivingEmber(),
                "tooltip.oathboundrelics.oathbound_relic.curse_5",
                secondsFromTicks(OathboundConfig.minFireTicks()));

        addCurseLine(tooltip, OathboundConfig.enableSoulFracture(),
                "tooltip.oathboundrelics.oathbound_relic.curse_6",
                secondsFromTicks(OathboundConfig.respawnWeaknessDurationTicks()),
                secondsFromTicks(OathboundConfig.respawnSlownessDurationTicks()));

        addCurseLine(tooltip, OathboundConfig.enableWakefulDoom(),
                "tooltip.oathboundrelics.oathbound_relic.curse_7");

        tooltip.add(Component.empty());

        // --- BLESSINGS ---
        tooltip.add(Component.translatable("tooltip.oathboundrelics.oathbound_relic.blessings")
                .withStyle(ChatFormatting.GOLD));

        addBlessingLine(tooltip, OathboundConfig.enableLootingBlessing(),
                "tooltip.oathboundrelics.oathbound_relic.blessing_1",
                OathboundConfig.lootingBonus());

        addBlessingLine(tooltip, OathboundConfig.enableFortuneBlessing(),
                "tooltip.oathboundrelics.oathbound_relic.blessing_2",
                OathboundConfig.fortuneBonus());

        addBlessingLine(tooltip, OathboundConfig.enableXpBlessing(),
                "tooltip.oathboundrelics.oathbound_relic.blessing_3",
                percentIncreaseFromMultiplier(OathboundConfig.xpMultiplier()));

        addBlessingLine(tooltip, OathboundConfig.enableEnchantingBlessing(),
                "tooltip.oathboundrelics.oathbound_relic.blessing_4",
                OathboundConfig.enchantingPowerBonus());

        addBlessingLine(tooltip, OathboundConfig.enableAttackSpeedBlessing(),
                "tooltip.oathboundrelics.oathbound_relic.blessing_5",
                formatDecimal(OathboundConfig.attackSpeedBonus()));

        addBlessingLine(tooltip, OathboundConfig.enableAbsorptionBlessing(),
                "tooltip.oathboundrelics.oathbound_relic.blessing_6",
                formatDecimal(OathboundConfig.absorptionThreshold()),
                formatDecimal(OathboundConfig.absorptionAmount()));

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.oathbound_relic.note")
                .withStyle(ChatFormatting.DARK_GRAY));
    }

    private static void addCurseLine(List<Component> tooltip, boolean enabled, String key, Object... args) {
        if (enabled) {
            tooltip.add(Component.translatable(key, args).withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable(
                    "tooltip.oathboundrelics.oathbound_relic.disabled",
                    Component.translatable(key, args)
            ).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    private static void addBlessingLine(List<Component> tooltip, boolean enabled, String key, Object... args) {
        if (enabled) {
            tooltip.add(Component.translatable(key, args).withStyle(ChatFormatting.YELLOW));
        } else {
            tooltip.add(Component.translatable(
                    "tooltip.oathboundrelics.oathbound_relic.disabled",
                    Component.translatable(key, args)
            ).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    private static String percentIncreaseFromMultiplier(double multiplier) {
        double percent = (multiplier - 1.0D) * 100.0D;
        return formatDecimal(percent) + "%";
    }

    private static String percentReductionFromMultiplier(double multiplier) {
        double percent = (1.0D - multiplier) * 100.0D;
        return formatDecimal(percent) + "%";
    }

    private static String secondsFromTicks(int ticks) {
        return formatDecimal(ticks / 20.0D) + "s";
    }

    private static String formatDecimal(double value) {
        if (Math.abs(value - Math.rint(value)) < 0.0001D) {
            return String.format(Locale.ROOT, "%.0f", value);
        }
        return String.format(Locale.ROOT, "%.1f", value);
    }
}