package net.bandit.oathboundrelics.registry;

/**
 * NeoForge used data attachments here. The Fabric port keeps the player data in
 * {@link net.bandit.oathboundrelics.data.PlayerDataStorage}.
 */
public final class AttachmentRegistry {
    private AttachmentRegistry() {
    }

    public static void register() {
        // No Fabric registry needed.
    }
}
