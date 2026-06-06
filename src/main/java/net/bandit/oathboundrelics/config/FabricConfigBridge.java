package net.bandit.oathboundrelics.config;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * File created:
 * config/oathboundrelics-common.properties
 *
 * Format:
 * key=value
 */
public final class FabricConfigBridge {

    private static final Map<String, String> VALUES = new LinkedHashMap<>();
    private static final Map<String, String> DEFAULTS = new LinkedHashMap<>();
    private static Path configPath;

    private FabricConfigBridge() {
    }

    public static void register() {
        configPath = FabricLoader.getInstance()
                .getConfigDir()
                .resolve(OathboundRelicsMod.MOD_ID + "-common.properties");

        try {
            Files.createDirectories(configPath.getParent());
            loadExistingFile();

            OathboundConfig.initDefaults();

            save();
            System.out.println("[Oathbound Relics] Loaded config from " + configPath);
        } catch (Exception e) {
            System.err.println("[Oathbound Relics] Failed to load config from " + configPath);
            e.printStackTrace();
        }
    }

    public static void defineDefault(String key, boolean defaultValue) {
        defineDefaultString(key, Boolean.toString(defaultValue));
    }

    public static void defineDefault(String key, int defaultValue) {
        defineDefaultString(key, Integer.toString(defaultValue));
    }

    public static void defineDefault(String key, long defaultValue) {
        defineDefaultString(key, Long.toString(defaultValue));
    }

    public static void defineDefault(String key, double defaultValue) {
        defineDefaultString(key, Double.toString(defaultValue));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        defineDefault(key, defaultValue);
        String raw = VALUES.getOrDefault(key, Boolean.toString(defaultValue)).trim();

        if (raw.equalsIgnoreCase("true")) return true;
        if (raw.equalsIgnoreCase("false")) return false;

        warnInvalid(key, raw, Boolean.toString(defaultValue));
        return defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        defineDefault(key, defaultValue);
        String raw = VALUES.getOrDefault(key, Integer.toString(defaultValue)).trim();

        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            warnInvalid(key, raw, Integer.toString(defaultValue));
            return defaultValue;
        }
    }

    public static long getLong(String key, long defaultValue) {
        defineDefault(key, defaultValue);
        String raw = VALUES.getOrDefault(key, Long.toString(defaultValue)).trim();

        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException e) {
            warnInvalid(key, raw, Long.toString(defaultValue));
            return defaultValue;
        }
    }

    public static double getDouble(String key, double defaultValue) {
        defineDefault(key, defaultValue);
        String raw = VALUES.getOrDefault(key, Double.toString(defaultValue)).trim();

        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            warnInvalid(key, raw, Double.toString(defaultValue));
            return defaultValue;
        }
    }

    private static void defineDefaultString(String key, String defaultValue) {
        DEFAULTS.putIfAbsent(key, defaultValue);
        VALUES.putIfAbsent(key, defaultValue);
    }

    private static void loadExistingFile() throws IOException {
        VALUES.clear();
        DEFAULTS.clear();

        if (configPath == null || !Files.exists(configPath)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();

                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }

                int split = trimmed.indexOf('=');
                if (split <= 0) {
                    continue;
                }

                String key = trimmed.substring(0, split).trim();
                String value = trimmed.substring(split + 1).trim();

                if (!key.isEmpty()) {
                    VALUES.put(key, value);
                }
            }
        }
    }

    private static void save() throws IOException {
        if (configPath == null) {
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(configPath, StandardCharsets.UTF_8)) {
            writer.write("# Oathbound Relics Fabric config");
            writer.newLine();
            writer.write("# Change values, save the file, then restart Minecraft.");
            writer.newLine();
            writer.newLine();

            String lastSection = "";
            for (Map.Entry<String, String> entry : DEFAULTS.entrySet()) {
                String key = entry.getKey();
                String section = sectionName(key);

                if (!section.equals(lastSection)) {
                    if (!lastSection.isEmpty()) {
                        writer.newLine();
                    }
                    writer.write("# " + section);
                    writer.newLine();
                    lastSection = section;
                }

                writer.write(key + "=" + VALUES.getOrDefault(key, entry.getValue()));
                writer.newLine();
            }

            boolean wroteExtrasHeader = false;
            for (Map.Entry<String, String> entry : VALUES.entrySet()) {
                if (DEFAULTS.containsKey(entry.getKey())) {
                    continue;
                }

                if (!wroteExtrasHeader) {
                    writer.newLine();
                    writer.write("# Unknown/custom keys preserved from previous config");
                    writer.newLine();
                    wroteExtrasHeader = true;
                }

                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        }
    }

    private static String sectionName(String key) {
        int split = key.indexOf('.');
        return split > 0 ? key.substring(0, split) : "general";
    }

    private static void warnInvalid(String key, String raw, String defaultValue) {
        System.err.println("[Oathbound Relics] Invalid config value for '" + key + "': '" + raw + "'. Using default: " + defaultValue);
    }
}
