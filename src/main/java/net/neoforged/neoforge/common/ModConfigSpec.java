package net.neoforged.neoforge.common;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class ModConfigSpec {

    private final Map<String, ConfigValue<?>> values;

    private ModConfigSpec(Map<String, ConfigValue<?>> values) {
        this.values = new LinkedHashMap<>(values);
    }

    public void loadOrCreate(Path path) {
        CommentedFileConfig config = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        config.load();

        for (ConfigValue<?> value : values.values()) {
            value.loadOrCreate(config);
        }

        config.save();
        config.close();
    }

    public static class Builder {
        private final Map<String, ConfigValue<?>> values = new LinkedHashMap<>();
        private final Deque<String> path = new ArrayDeque<>();
        private final List<String> pendingComments = new ArrayList<>();

        public <T> Pair<T, ModConfigSpec> configure(Function<Builder, T> factory) {
            T result = factory.apply(this);
            return Pair.of(result, new ModConfigSpec(values));
        }

        public Builder push(String name) {
            path.addLast(name);
            return this;
        }

        public Builder pop() {
            if (!path.isEmpty()) {
                path.removeLast();
            }
            return this;
        }

        public Builder comment(String... comments) {
            pendingComments.addAll(Arrays.asList(comments));
            return this;
        }

        public BooleanValue define(String name, boolean defaultValue) {
            BooleanValue value = new BooleanValue(fullPath(name), defaultValue, takeComments());
            values.put(value.path, value);
            return value;
        }

        public IntValue defineInRange(String name, int defaultValue, int min, int max) {
            IntValue value = new IntValue(fullPath(name), defaultValue, min, max, takeComments());
            values.put(value.path, value);
            return value;
        }

        public LongValue defineInRange(String name, long defaultValue, long min, long max) {
            LongValue value = new LongValue(fullPath(name), defaultValue, min, max, takeComments());
            values.put(value.path, value);
            return value;
        }

        public DoubleValue defineInRange(String name, double defaultValue, double min, double max) {
            DoubleValue value = new DoubleValue(fullPath(name), defaultValue, min, max, takeComments());
            values.put(value.path, value);
            return value;
        }

        private String fullPath(String name) {
            if (path.isEmpty()) {
                return name;
            }

            return String.join(".", path) + "." + name;
        }

        private List<String> takeComments() {
            List<String> comments = List.copyOf(pendingComments);
            pendingComments.clear();
            return comments;
        }
    }

    public abstract static class ConfigValue<T> {
        protected final String path;
        protected final T defaultValue;
        protected T value;
        protected final List<String> comments;

        protected ConfigValue(String path, T defaultValue, List<String> comments) {
            this.path = path;
            this.defaultValue = defaultValue;
            this.value = defaultValue;
            this.comments = comments;
        }

        public T get() {
            return value;
        }

        protected abstract T read(Object raw);

        @SuppressWarnings("unchecked")
        private void loadOrCreate(CommentedFileConfig config) {
            if (!comments.isEmpty()) {
                try {
                    config.setComment(path, String.join("\n", comments));
                } catch (Exception ignored) {
                    // Comments are nice to have, but should never break config loading.
                }
            }

            if (!config.contains(path)) {
                config.set(path, defaultValue);
                value = defaultValue;
                return;
            }

            Object raw = config.get(path);
            T loaded = read(raw);

            if (loaded == null) {
                config.set(path, defaultValue);
                value = defaultValue;
            } else {
                value = loaded;
                config.set(path, loaded);
            }
        }
    }

    public static class BooleanValue extends ConfigValue<Boolean> {
        public BooleanValue(String path, boolean defaultValue, List<String> comments) {
            super(path, defaultValue, comments);
        }

        @Override
        protected Boolean read(Object raw) {
            if (raw instanceof Boolean bool) {
                return bool;
            }

            if (raw instanceof String string) {
                return Boolean.parseBoolean(string);
            }

            return null;
        }
    }

    public static class IntValue extends ConfigValue<Integer> {
        private final int min;
        private final int max;

        public IntValue(String path, int defaultValue, int min, int max, List<String> comments) {
            super(path, defaultValue, comments);
            this.min = min;
            this.max = max;
        }

        @Override
        protected Integer read(Object raw) {
            if (raw instanceof Number number) {
                int value = number.intValue();
                return Math.max(min, Math.min(max, value));
            }

            if (raw instanceof String string) {
                try {
                    int value = Integer.parseInt(string);
                    return Math.max(min, Math.min(max, value));
                } catch (NumberFormatException ignored) {
                    return null;
                }
            }

            return null;
        }
    }

    public static class LongValue extends ConfigValue<Long> {
        private final long min;
        private final long max;

        public LongValue(String path, long defaultValue, long min, long max, List<String> comments) {
            super(path, defaultValue, comments);
            this.min = min;
            this.max = max;
        }

        @Override
        protected Long read(Object raw) {
            if (raw instanceof Number number) {
                long value = number.longValue();
                return Math.max(min, Math.min(max, value));
            }

            if (raw instanceof String string) {
                try {
                    long value = Long.parseLong(string);
                    return Math.max(min, Math.min(max, value));
                } catch (NumberFormatException ignored) {
                    return null;
                }
            }

            return null;
        }
    }

    public static class DoubleValue extends ConfigValue<Double> {
        private final double min;
        private final double max;

        public DoubleValue(String path, double defaultValue, double min, double max, List<String> comments) {
            super(path, defaultValue, comments);
            this.min = min;
            this.max = max;
        }

        @Override
        protected Double read(Object raw) {
            if (raw instanceof Number number) {
                double value = number.doubleValue();
                return Math.max(min, Math.min(max, value));
            }

            if (raw instanceof String string) {
                try {
                    double value = Double.parseDouble(string);
                    return Math.max(min, Math.min(max, value));
                } catch (NumberFormatException ignored) {
                    return null;
                }
            }

            return null;
        }
    }
}