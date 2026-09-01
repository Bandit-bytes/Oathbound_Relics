package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.commands.BrandedTimeCommand;
import net.bandit.oathboundrelics.fabricbridge.events.RegisterCommandsEvent;

public final class CommandEvents {

    private CommandEvents() {
    }
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        BrandedTimeCommand.register(event.getDispatcher());
    }
}