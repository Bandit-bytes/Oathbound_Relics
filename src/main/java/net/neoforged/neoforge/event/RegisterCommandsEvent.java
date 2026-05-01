package net.neoforged.neoforge.event;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

public class RegisterCommandsEvent {
    private final CommandDispatcher<CommandSourceStack> dispatcher;
    public RegisterCommandsEvent(CommandDispatcher<CommandSourceStack> dispatcher) { this.dispatcher = dispatcher; }
    public CommandDispatcher<CommandSourceStack> getDispatcher() { return dispatcher; }
}
