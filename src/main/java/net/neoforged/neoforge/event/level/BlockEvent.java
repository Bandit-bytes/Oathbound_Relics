package net.neoforged.neoforge.event.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlockEvent {
    public static class BreakEvent extends BlockEvent {
        private final Level level;
        private final BlockPos pos;
        private final Player player;
        public BreakEvent(Level level, BlockPos pos, Player player) { this.level = level; this.pos = pos; this.player = player; }
        public Level getLevel() { return level; }
        public BlockPos getPos() { return pos; }
        public Player getPlayer() { return player; }
    }
}
