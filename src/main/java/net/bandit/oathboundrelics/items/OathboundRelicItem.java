package net.bandit.oathboundrelics.items;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class OathboundRelicItem extends Item implements ICurioItem {

    public OathboundRelicItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    private record CustomRelicAttribute(
            Holder<Attribute> attribute,
            ResourceLocation attributeId,
            double amount,
            AttributeModifier.Operation operation,
            boolean curse,
            String displayName
    ) {
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

        List<CustomRelicAttribute> customAttributes = getCustomRelicAttributes();

        for (int i = 0; i < customAttributes.size(); i++) {
            CustomRelicAttribute custom = customAttributes.get(i);

            ResourceLocation modifierId = ResourceLocation.fromNamespaceAndPath(
                    OathboundRelicsMod.MOD_ID,
                    id.getPath()
                            + "_custom_"
                            + i
                            + "_"
                            + custom.attributeId().getNamespace()
                            + "_"
                            + custom.attributeId().getPath().replace('/', '_')
            );

            modifiers.put(
                    custom.attribute(),
                    new AttributeModifier(
                            modifierId,
                            custom.amount(),
                            custom.operation()
                    )
            );
        }

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        List<CustomRelicAttribute> customAttributes = getCustomRelicAttributes();

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

        for (CustomRelicAttribute custom : customAttributes) {
            if (custom.curse()) {
                tooltip.add(Component.literal(formatCustomAttributeTooltip(custom))
                        .withStyle(ChatFormatting.GRAY));
            }
        }

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

        for (CustomRelicAttribute custom : customAttributes) {
            if (!custom.curse()) {
                tooltip.add(Component.literal(formatCustomAttributeTooltip(custom))
                        .withStyle(ChatFormatting.YELLOW));
            }
        }

        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.oathboundrelics.oathbound_relic.note")
                .withStyle(ChatFormatting.DARK_GRAY));
    }

    private static String formatCustomAttributeTooltip(CustomRelicAttribute custom) {
        double amount = custom.amount();
        String sign = amount >= 0.0D ? "+ " : "- ";
        double displayAmount = Math.abs(amount);

        if (custom.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                || custom.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
            return sign + formatCustomDecimal(displayAmount * 100.0D) + "% " + custom.displayName();
        }

        return sign + formatCustomDecimal(displayAmount) + " " + custom.displayName();
    }

    private static String formatCustomDecimal(double value) {
        if (Math.abs(value - Math.rint(value)) < 0.0001D) {
            return String.format(Locale.ROOT, "%.0f", value);
        }

        if (Math.abs(value) < 1.0D) {
            return String.format(Locale.ROOT, "%.3f", value)
                    .replaceAll("0+$", "")
                    .replaceAll("\\.$", "");
        }

        return String.format(Locale.ROOT, "%.1f", value);
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

    private static List<CustomRelicAttribute> getCustomRelicAttributes() {
        List<CustomRelicAttribute> parsed = new ArrayList<>();

        for (String entry : OathboundConfig.oathboundRelicCustomAttributes()) {
            if (entry == null || entry.isBlank()) {
                continue;
            }

            String[] parts = entry.split(";", 5);
            if (parts.length < 5) {
                OathboundRelicsMod.LOGGER.warn("Invalid Oathbound Relic custom attribute entry '{}'. Expected format: attribute_id;amount;operation;type;display_name", entry);
                continue;
            }

            ResourceLocation attributeId = ResourceLocation.tryParse(parts[0].trim());
            if (attributeId == null) {
                OathboundRelicsMod.LOGGER.warn("Invalid attribute id in Oathbound Relic custom attribute entry '{}'", entry);
                continue;
            }

            Optional<Holder.Reference<Attribute>> attribute = BuiltInRegistries.ATTRIBUTE.getHolder(
                    ResourceKey.create(Registries.ATTRIBUTE, attributeId)
            );

            if (attribute.isEmpty()) {
                OathboundRelicsMod.LOGGER.warn("Unknown attribute '{}' in Oathbound Relic custom attribute entry '{}'", attributeId, entry);
                continue;
            }

            double amount;
            try {
                amount = Double.parseDouble(parts[1].trim());
            } catch (NumberFormatException exception) {
                OathboundRelicsMod.LOGGER.warn("Invalid amount in Oathbound Relic custom attribute entry '{}'", entry);
                continue;
            }

            AttributeModifier.Operation operation;
            try {
                operation = AttributeModifier.Operation.valueOf(parts[2].trim().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException exception) {
                OathboundRelicsMod.LOGGER.warn("Invalid operation in Oathbound Relic custom attribute entry '{}'", entry);
                continue;
            }

            String type = parts[3].trim().toLowerCase(Locale.ROOT);
            boolean curse = type.equals("curse");

            if (!curse && !type.equals("blessing")) {
                OathboundRelicsMod.LOGGER.warn("Invalid type in Oathbound Relic custom attribute entry '{}'. Use blessing or curse.", entry);
                continue;
            }

            String displayName = parts[4].trim();
            if (displayName.isBlank()) {
                displayName = attributeId.toString();
            }

            parsed.add(new CustomRelicAttribute(
                    attribute.get(),
                    attributeId,
                    amount,
                    operation,
                    curse,
                    displayName
            ));
        }

        return parsed;
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