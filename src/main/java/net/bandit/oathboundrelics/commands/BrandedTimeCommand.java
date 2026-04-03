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
                                                .then(Commands.argument("totalTicks", LongArgumentType.longArg(0))
                                                        .then(Commands.argument("brandedTicks", LongArgumentType.longArg(0))
                                                                .executes(ctx -> setTicks(
                                                                        ctx,
                                                                        EntityArgument.getPlayers(ctx, "targets"),
                                                                        LongArgumentType.getLong(ctx, "totalTicks"),
                                                                        LongArgumentType.getLong(ctx, "brandedTicks")
                                                                ))))))

                                .then(Commands.literal("add")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("ticks", LongArgumentType.longArg(0))
                                                        .executes(ctx -> addBrandedTicks(
                                                                ctx,
                                                                EntityArgument.getPlayers(ctx, "targets"),
                                                                LongArgumentType.getLong(ctx, "ticks")
                                                        )))))

                                .then(Commands.literal("ratio")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("totalTicks", LongArgumentType.longArg(1))
                                                        .then(Commands.argument("ratio", DoubleArgumentType.doubleArg(0.0D, 1.0D))
                                                                .executes(ctx -> setRatio(
                                                                        ctx,
                                                                        EntityArgument.getPlayers(ctx, "targets"),
                                                                        LongArgumentType.getLong(ctx, "totalTicks"),
                                                                        DoubleArgumentType.getDouble(ctx, "ratio")
                                                                ))))))

                                .then(Commands.literal("qualify")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .executes(ctx -> qualify(
                                                        ctx,
                                                        EntityArgument.getPlayers(ctx, "targets")
                                                ))))
                        )
        );
    }

    private static int setTicks(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players, long totalTicks, long brandedTicks) {
        for (ServerPlayer player : players) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            data.setTicks(totalTicks, brandedTicks);
        }

        ctx.getSource().sendSuccess(
                () -> Component.literal("Set Branded Time for " + players.size() + " player(s)."),
                true
        );
        return players.size();
    }

    private static int addBrandedTicks(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players, long ticks) {
        for (ServerPlayer player : players) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            data.addTotalWorldTicks(ticks);
            data.addBrandedWorldTicks(ticks);
        }

        ctx.getSource().sendSuccess(
                () -> Component.literal("Added " + ticks + " Branded ticks to " + players.size() + " player(s)."),
                true
        );
        return players.size();
    }

    private static int setRatio(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players, long totalTicks, double ratio) {
        long brandedTicks = Math.min(totalTicks, Math.max(0L, Math.round(totalTicks * ratio)));

        for (ServerPlayer player : players) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            data.setTicks(totalTicks, brandedTicks);
        }

        ctx.getSource().sendSuccess(
                () -> Component.literal("Set Branded ratio to " + ratio + " for " + players.size() + " player(s)."),
                true
        );
        return players.size();
    }

    private static int qualify(CommandContext<CommandSourceStack> ctx, Collection<ServerPlayer> players) {
        long totalTicks = OathboundConfig.slothWeaponMinimumTotalTicks();
        long brandedTicks = Math.round(totalTicks * OathboundConfig.slothWeaponRequiredBrandedRatio());

        for (ServerPlayer player : players) {
            BrandedTimeData data = player.getData(AttachmentRegistry.BRANDED_TIME.get());
            data.setTicks(totalTicks, brandedTicks);
        }

        ctx.getSource().sendSuccess(
                () -> Component.literal("Qualified " + players.size() + " player(s) for the Lethargic Flail."),
                true
        );
        return players.size();
    }
}