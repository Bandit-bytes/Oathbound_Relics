package net.neoforged.neoforge.event.entity.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerEvent {
    protected final Player entity;
    public PlayerEvent(Player entity) { this.entity = entity; }
    public Player getEntity() { return entity; }

    public static class Clone extends PlayerEvent {
        private final Player original;
        private final boolean wasDeath;
        public Clone(Player original, Player entity, boolean wasDeath) { super(entity); this.original = original; this.wasDeath = wasDeath; }
        public Player getOriginal() { return original; }
        public boolean isWasDeath() { return wasDeath; }
    }

    public static class PlayerRespawnEvent extends PlayerEvent {
        public PlayerRespawnEvent(Player entity) { super(entity); }
    }

    public static class PlayerLoggedInEvent extends PlayerEvent {
        public PlayerLoggedInEvent(Player entity) { super(entity); }
    }

    public static class PlayerLoggedOutEvent extends PlayerEvent {
        public PlayerLoggedOutEvent(Player entity) { super(entity); }
    }
}
