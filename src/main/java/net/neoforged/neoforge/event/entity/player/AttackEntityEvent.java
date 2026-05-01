package net.neoforged.neoforge.event.entity.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class AttackEntityEvent {
    private final Player entity;
    private final Entity target;
    private boolean canceled;
    public AttackEntityEvent(Player entity, Entity target) { this.entity = entity; this.target = target; }
    public Player getEntity() { return entity; }
    public Entity getTarget() { return target; }
    public void setCanceled(boolean canceled) { this.canceled = canceled; }
    public boolean isCanceled() { return canceled; }
}
