package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.data.EnvyStateData;
import net.bandit.oathboundrelics.data.PrideStateData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;

public final class OathboundUtil {

    private static final String TAG_SEVERANCE_ACTIVE = OathboundRelicsMod.MOD_ID + "_severance_active";
    private static final String TAG_SEVERANCE_ALLOW_UNEQUIP = OathboundRelicsMod.MOD_ID + "_severance_allow_unequip";

    private OathboundUtil() {
    }

    public static boolean isBranded(Player player) {
        return hasCurio(player, ItemRegistry.OATHBOUND_RELIC.get());
    }

    public static boolean hasCurio(Player player, Item item) {
        if (player == null || player.isSpectator()) {
            return false;
        }

        return CuriosApi.getCuriosInventory(player)
                .map(curiosInventory -> !curiosInventory.findCurios(item).isEmpty())
                .orElse(false);
    }

    public static boolean canSeverRelic(Player player) {
        return player != null && player.getPersistentData().getBoolean(TAG_SEVERANCE_ALLOW_UNEQUIP);
    }

    public static boolean isInSeveranceRitual(Player player) {
        return player != null && player.getPersistentData().getBoolean(TAG_SEVERANCE_ACTIVE);
    }

    public static void setSeveranceActive(Player player, boolean active) {
        CompoundTag tag = player.getPersistentData();
        tag.putBoolean(TAG_SEVERANCE_ACTIVE, active);
    }

    public static void setSeveranceUnequipAllowed(Player player, boolean allowed) {
        CompoundTag tag = player.getPersistentData();
        tag.putBoolean(TAG_SEVERANCE_ALLOW_UNEQUIP, allowed);
    }

    public static boolean mayStartSeverance(Level level) {
        return level.dimension() == Level.END || level.isNight();
    }

    public static boolean hasSeveranceMaterials(Player player) {
        return countItem(player, ItemRegistry.OBLIVION_STONE.get()) >= 1
                && countItem(player, net.minecraft.world.item.Items.ECHO_SHARD) >= 4
                && countItem(player, net.minecraft.world.item.Items.CRYING_OBSIDIAN) >= 4
                && countItem(player, net.minecraft.world.item.Items.SOUL_SOIL) >= 16;
    }

    public static void consumeSeveranceMaterials(Player player) {
        shrinkItem(player, ItemRegistry.OBLIVION_STONE.get(), 1);
        shrinkItem(player, net.minecraft.world.item.Items.ECHO_SHARD, 4);
        shrinkItem(player, net.minecraft.world.item.Items.CRYING_OBSIDIAN, 4);
        shrinkItem(player, net.minecraft.world.item.Items.SOUL_SOIL, 16);
    }

    public static int countItem(Player player, Item item) {
        int total = 0;

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(item)) {
                total += stack.getCount();
            }
        }

        for (ItemStack stack : player.getInventory().offhand) {
            if (stack.is(item)) {
                total += stack.getCount();
            }
        }

        return total;
    }

    public static void shrinkItem(Player player, Item item, int amount) {
        int remaining = amount;

        for (ItemStack stack : player.getInventory().items) {
            if (remaining <= 0) {
                return;
            }

            if (stack.is(item)) {
                int taken = Math.min(stack.getCount(), remaining);
                stack.shrink(taken);
                remaining -= taken;
            }
        }

        for (ItemStack stack : player.getInventory().offhand) {
            if (remaining <= 0) {
                return;
            }

            if (stack.is(item)) {
                int taken = Math.min(stack.getCount(), remaining);
                stack.shrink(taken);
                remaining -= taken;
            }
        }
    }

    public static boolean removeOathboundRelic(Player player) {
        var curiosOpt = CuriosApi.getCuriosInventory(player);
        if (curiosOpt.isEmpty()) {
            return false;
        }

        var curiosInventory = curiosOpt.get();
        var matches = curiosInventory.findCurios(ItemRegistry.OATHBOUND_RELIC.get());

        if (matches.isEmpty()) {
            return false;
        }

        var result = matches.getFirst();
        var handlerOpt = curiosInventory.getStacksHandler(result.slotContext().identifier());

        if (handlerOpt.isEmpty()) {
            return false;
        }

        var stackHandler = handlerOpt.get().getStacks();
        stackHandler.setStackInSlot(result.slotContext().index(), ItemStack.EMPTY);
        player.containerMenu.broadcastChanges();
        return true;
    }

    public static void clearRitualFlags(Player player) {
        setSeveranceActive(player, false);
        setSeveranceUnequipAllowed(player, false);
    }

    public static void clearOathboundState(Player player) {
        clearRitualFlags(player);

        BrandedTimeData brandedTimeData = new BrandedTimeData();
        brandedTimeData.setBrandedProgressTicks(0L);
        player.setData(AttachmentRegistry.BRANDED_TIME.get(), brandedTimeData);

        player.setData(AttachmentRegistry.SOUL_FRACTURE_COUNT.get(), 0);
        player.setData(AttachmentRegistry.PRIDE_STATE.get(), new PrideStateData());
        player.setData(AttachmentRegistry.ENVY_STATE.get(), new EnvyStateData());
    }

    public static void giveFracturedAsh(ServerPlayer player) {
        ItemStack reward = new ItemStack(ItemRegistry.FRACTURED_RELIC_ASH.get());
        if (!player.addItem(reward)) {
            player.drop(reward, false);
        }
    }

    public static void awardSeveranceAdvancement(ServerPlayer player) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
                OathboundRelicsMod.MOD_ID,
                "ritual/severed_oath"
        );

        AdvancementHolder advancement = player.server.getAdvancements().get(id);
        if (advancement == null) {
            return;
        }

        var progress = player.getAdvancements().getOrStartProgress(advancement);
        for (String criterion : progress.getRemainingCriteria()) {
            player.getAdvancements().award(advancement, criterion);
        }
    }
}