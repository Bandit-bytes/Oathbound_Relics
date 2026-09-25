package net.bandit.oathboundrelics.events;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.commands.BrandedTimeCommand;
import net.bandit.oathboundrelics.util.OathboundUtil;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = OathboundRelicsMod.MOD_ID)
public final class CommandEvents {

    private CommandEvents() {
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        BrandedTimeCommand.register(event.getDispatcher());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCommand(CommandEvent event) {
        String parsedInput = event.getParseResults().getReader().getString();
        String command = parsedInput.trim();
        if (command.startsWith("/")) {
            command = command.substring(1);
        }

        String[] parts = command.split("\\s+");
        if (parts.length < 3
                || !"curios".equals(parts[0])
                || !"clear".equals(parts[1])) {
            return;
        }

        if (parts.length >= 4 && !"ring".equals(parts[3]) && !parts[3].endsWith(":ring")) {
            return;
        }

        try {
            var context = event.getParseResults().getContext().build(parsedInput);
            ServerPlayer target = EntityArgument.getPlayer(context, "player");
            OathboundUtil.authorizeAdministrativeRelicRemoval(target);
        } catch (Exception ignored) {
        }
    }
}