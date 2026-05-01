package net.bandit.oathboundrelics.util;

import net.bandit.oathboundrelics.data.PersistentData;

import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import top.theillusivec4.curios.api.CuriosApi;

public final class SoulLanternLightUtil {

    private static final String LIGHT_TAG = "oathboundrelics_soul_lantern_light_pos";
    private static final int LIGHT_LEVEL = 12;

    private SoulLanternLightUtil() {}

    public static void tick(Player player) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        boolean active = isHeld(player) || isEquippedInBelt(player);

        if (!active) {
            clearTrackedLight(player);
            return;
        }

        BlockPos target = findLightPos(serverLevel, player);
        if (target == null) {
            clearTrackedLight(player);
            return;
        }

        BlockPos previous = getTrackedLightPos(player);
        if (previous != null && !previous.equals(target)) {
            removeLightIfOwned(serverLevel, previous);
        }

        placeLight(serverLevel, target);
        setTrackedLightPos(player, target);
    }

    public static boolean isHeld(Player player) {
        return player.getMainHandItem().is(ItemRegistry.SOUL_LANTERN.get())
                || player.getOffhandItem().is(ItemRegistry.SOUL_LANTERN.get());
    }

    public static boolean isEquippedInBelt(Player player) {
        return CuriosApi.getCuriosHelper()
                .findFirstCurio(player, stack -> stack.is(ItemRegistry.SOUL_LANTERN.get()))
                .filter(result -> "belt".equals(result.slotContext().identifier()))
                .isPresent();
    }

    private static BlockPos findLightPos(ServerLevel level, Player player) {
        BlockPos feet = player.blockPosition();
        if (canUse(level, feet)) return feet;

        BlockPos above = feet.above();
        if (canUse(level, above)) return above;

        BlockPos eye = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
        if (canUse(level, eye)) return eye;

        return null;
    }

    private static boolean canUse(ServerLevel level, BlockPos pos) {
        if (!level.hasChunkAt(pos)) {
            return false;
        }

        BlockState state = level.getBlockState(pos);
        return state.isAir() || state.is(Blocks.LIGHT);
    }

    private static void placeLight(ServerLevel level, BlockPos pos) {
        BlockState desired = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, LIGHT_LEVEL);
        BlockState current = level.getBlockState(pos);

        if (!current.is(Blocks.LIGHT) || current.getValue(LightBlock.LEVEL) != LIGHT_LEVEL) {
            level.setBlock(pos, desired, 3);
        }
    }

    public static void clearTrackedLight(Player player) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        BlockPos previous = getTrackedLightPos(player);
        if (previous != null) {
            removeLightIfOwned(serverLevel, previous);
            PersistentData.get(player).remove(LIGHT_TAG);
        }
    }

    private static void removeLightIfOwned(ServerLevel level, BlockPos pos) {
        if (level.getBlockState(pos).is(Blocks.LIGHT)) {
            level.removeBlock(pos, false);
        }
    }

    private static void setTrackedLightPos(Player player, BlockPos pos) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("x", pos.getX());
        tag.putInt("y", pos.getY());
        tag.putInt("z", pos.getZ());
        PersistentData.get(player).put(LIGHT_TAG, tag);
    }

    private static BlockPos getTrackedLightPos(Player player) {
        if (!PersistentData.get(player).contains(LIGHT_TAG)) {
            return null;
        }

        CompoundTag tag = PersistentData.get(player).getCompound(LIGHT_TAG);
        return new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
    }
}
