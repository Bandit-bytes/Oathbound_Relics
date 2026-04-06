package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.text.DecimalFormat;

public final class SoulHarvestUtil {

    public static final String HARVESTED_SOULS_TAG = "oathboundrelics_harvested_souls";

    private static final ResourceLocation SOUL_TAX_ID =
            ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "soul_lantern_tax");

    private static final double HP_PER_GEM = 1.0D;
    private static final double MINIMUM_REMAINING_BASE_HEALTH = 6.0D;

    private static final DecimalFormat HEALTH_FORMAT = new DecimalFormat("0.#");

    private SoulHarvestUtil() {}

    public static int getHarvestedSouls(Player player) {
        return Math.max(0, player.getPersistentData().getInt(HARVESTED_SOULS_TAG));
    }

    public static void setHarvestedSouls(Player player, int amount) {
        player.getPersistentData().putInt(HARVESTED_SOULS_TAG, Math.max(0, amount));
    }

    public static String getCostDisplay() {
        return HEALTH_FORMAT.format(HP_PER_GEM) + " HP";
    }

    public static String getMinimumRemainingDisplay() {
        return HEALTH_FORMAT.format(MINIMUM_REMAINING_BASE_HEALTH) + " HP";
    }

    public static double getRemainingBaseHealth(Player player) {
        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            return 0.0D;
        }

        return Math.max(0.0D, maxHealth.getBaseValue() - (getHarvestedSouls(player) * HP_PER_GEM));
    }

    public static String getRemainingBaseHealthDisplay(Player player) {
        return HEALTH_FORMAT.format(getRemainingBaseHealth(player)) + " HP";
    }

    public static boolean canHarvest(Player player) {
        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            return false;
        }

        double remainingAfterNextHarvest = maxHealth.getBaseValue() - ((getHarvestedSouls(player) + 1) * HP_PER_GEM);
        return remainingAfterNextHarvest >= MINIMUM_REMAINING_BASE_HEALTH;
    }

    public static void harvestSoul(Player player) {
        int newTotal = getHarvestedSouls(player) + 1;
        setHarvestedSouls(player, newTotal);

        applySoulTax(player);

        ItemStack soulGem = new ItemStack(ItemRegistry.SOUL_GEM.get());
        if (!player.addItem(soulGem)) {
            player.drop(soulGem, false);
        }

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth((float) player.getMaxHealth());
        }

        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.8F, 0.5F);
    }

    public static void applySoulTax(Player player) {
        AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            return;
        }

        maxHealth.removeModifier(SOUL_TAX_ID);

        int harvested = getHarvestedSouls(player);
        if (harvested <= 0) {
            return;
        }

        double amount = -(harvested * HP_PER_GEM);

        maxHealth.addPermanentModifier(new AttributeModifier(
                SOUL_TAX_ID,
                amount,
                AttributeModifier.Operation.ADD_VALUE
        ));

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth((float) player.getMaxHealth());
        }
    }
    public static boolean canRestore(Player player) {
        return getHarvestedSouls(player) > 0;
    }

    public static boolean restoreSoul(Player player) {
        int harvested = getHarvestedSouls(player);
        if (harvested <= 0) {
            return false;
        }

        setHarvestedSouls(player, harvested - 1);
        applySoulTax(player);

        player.heal((float) getHealthCostPerGem());

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth((float) player.getMaxHealth());
        }

        return true;
    }

    public static double getHealthCostPerGem() {
        return HP_PER_GEM;
    }
}