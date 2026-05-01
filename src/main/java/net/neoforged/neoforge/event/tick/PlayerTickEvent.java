package net.neoforged.neoforge.event.tick;

import net.minecraft.world.entity.player.Player;

public class PlayerTickEvent {
    protected final Player entity;
    public PlayerTickEvent(Player entity) { this.entity = entity; }
    public Player getEntity() { return entity; }

    public static class Post extends PlayerTickEvent {
        public Post(Player entity) { super(entity); }
    }
}
