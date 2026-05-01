package net.bandit.oathboundrelics.config;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public final class FabricConfigBridge {

    private FabricConfigBridge() {
    }

    public static void register() {
        Path path = FabricLoader.getInstance()
                .getConfigDir()
                .resolve(OathboundRelicsMod.MOD_ID + "-common.toml");

        try {
            Files.createDirectories(path.getParent());

            OathboundConfig.SPEC.loadOrCreate(path);
        } catch (Exception e) {
        }
    }
}