package net.bandit.oathboundrelics.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.bandit.oathboundrelics.config.OathboundConfig;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.registry.AttachmentRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public final class BrandedTimeCommand {

    private BrandedTimeCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("oathbound")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("branded_time")

                                .then(Commands.literal("set")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("ticks", LongArgumentType.longArg(0))
                                                        .executes(ctx -> setTicks(
                                                                ctx,
                                                                EntityArgument.getPlayers(ctx, "targets"),
                                                                LongArgumentType.getLong(ctx, "ticks")
                                                        )))))

                                .then(Commands.literal("add")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("ticks", LongArgumentType.longArg(0))
                                                        .executes(ctx -> addTicks(
                                                                ctx,
                                                                EntityArgument.getPlayers(ctx, "targets"),
                                                                LongArgumentType.getLong(ctx, "ticks")
                                                        )))))

                                .then(Commands.literal("percent")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("percent", DoubleArgumentType.doubleArg(0.0D, 100.0D))
                                                        .executes(ctx -> setPercent(
                                                                ctx,
                                                                EntityArgument.getPlayers(ctx, "targets"),
                                                                DoubleArgumentType.getDouble(ctx, "percent")
                                                        )))))

                                .then(Commands.literal("qualify")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .executes(ctx -> qualify(
                                                        ctx,
                                                        EntityArgument.getPlayers(ctx, "targets")
                                                ))))

                                .then(Commands.literal("reset")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .executes(ctx -> reset(
                                                        ctx,
                                                        EntityArgument.getPlayers(ctx, "targets")
                                                ))))
                        )
        );
    }

    private static int setTicks(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players, long ticks) {
        long maxTicks = OathboundConfig.slothWeaponMaxBrandedTicks();
        long clamped = Math.max(0L, Math.min(ticks, maxTicks));

        for (ServerPlayer player : players) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            data.setBrandedProgressTicks(clamped);
        }

        ctx.getSource().sendSuccess(
                () -> Component.literal("Set branded progress to " + clamped + " ticks for " + players.size() + " player(s)."),
                true
        );
        return players.size();
    }

    private static int addTicks(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players, long ticks) {
        long maxTicks = OathboundConfig.slothWeaponMaxBrandedTicks();

        for (ServerPlayer player : players) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            long newValue = Math.min(maxTicks, data.getBrandedProgressTicks() + ticks);
            data.setBrandedProgressTicks(newValue);
        }

        ctx.getSource().sendSuccess(
                () -> Component.literal("Added " + ticks + " branded progress ticks to " + players.size() + " player(s)."),
                true
        );
        return players.size();
    }

    private static int setPercent(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players, double percent) {
        long maxTicks = OathboundConfig.slothWeaponMaxBrandedTicks();
        double clampedPercent = Math.max(0.0D, Math.min(100.0D, percent));
        long ticks = Math.round(maxTicks * (clampedPercent / 100.0D));

        for (ServerPlayer player : players) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            data.setBrandedProgressTicks(ticks);
        }

        ctx.getSource().sendSuccess(
                () -> Component.literal("Set branded progress to " + clampedPercent + "% for " + players.size() + " player(s)."),
                true
        );
        return players.size();
    }

    private static int qualify(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players) {
        long maxTicks = OathboundConfig.slothWeaponMaxBrandedTicks();
        long requiredTicks = Math.round(maxTicks * OathboundConfig.slothWeaponRequiredBrandedPercent());

        for (ServerPlayer player : players) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            data.setBrandedProgressTicks(requiredTicks);
        }

        ctx.getSource().sendSuccess(
                () -> Component.literal("Qualified " + players.size() + " player(s) for branded weapons."),
                true
        );
        return players.size();
    }

    private static int reset(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players) {
        for (ServerPlayer player : players) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            data.setBrandedProgressTicks(0L);
        }

        ctx.getSource().sendSuccess(
                () -> Component.literal("Reset branded progress for " + players.size() + " player(s)."),
                true
        );
        return players.size();
    }
}