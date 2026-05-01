package net.neoforged.neoforge.event.entity.player;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class CanPlayerSleepEvent {
    private final Player entity;
    private final BlockPos pos;
    private Object problem;
    public CanPlayerSleepEvent(Player entity, BlockPos pos) { this.entity = entity; this.pos = pos; }
    public Player getEntity() { return entity; }
    public BlockPos getPos() { return pos; }
    public void setProblem(Object problem) { this.problem = problem; }
    public Object getProblem() { return problem; }
}
