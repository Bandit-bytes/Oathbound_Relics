package net.bandit.oathboundrelics.client.altar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Vector3f;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Tiny dependency-free Blockbench keyframe player used by the Rite of Severance altar.
 * The source data is generated directly from the original .bbmodel animations.
 */
public final class NativeAltarAnimations {
    private static final Gson GSON = new Gson();
    private static final Map<String, Animation> BASE = new HashMap<>();
    private static final Map<String, Animation> AURA = new HashMap<>();

    static {
        try (var in = NativeAltarAnimations.class.getResourceAsStream(
                "/assets/oathboundrelics/animations/rite_of_severance.json")) {
            if (in == null) {
                OathboundRelicsMod.LOGGER.error("Missing native altar animation data");
            } else {
                JsonObject root = GSON.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), JsonObject.class);
                readSet(root.getAsJsonObject("base"), BASE);
                readSet(root.getAsJsonObject("aura"), AURA);
            }
        } catch (Exception e) {
            OathboundRelicsMod.LOGGER.error("Failed to load native altar animations", e);
        }
    }

    private NativeAltarAnimations() {}

    public static void applyBase(NativeAltarModel model, String name, float seconds) {
        apply(model, BASE.get(name), seconds);
    }

    public static void applyAura(NativeAltarModel model, String name, float seconds) {
        apply(model, AURA.get(name), seconds);
    }

    public static float baseLength(String name) {
        Animation a = BASE.get(name);
        return a == null ? 0.0F : a.length;
    }

    private static void apply(NativeAltarModel model, Animation animation, float seconds) {
        if (animation == null) return;
        float time = animation.sampleTime(seconds);
        for (Map.Entry<String, BoneAnimation> entry : animation.bones.entrySet()) {
            ModelPart part = model.bone(entry.getKey());
            if (part == null) continue;
            BoneAnimation bone = entry.getValue();
            if (bone.position != null) {
                Vector3f v = bone.position.sample(time);
                // Blockbench uses +Y up; ModelPart uses +Y down.
                part.x += v.x();
                part.y -= v.y();
                part.z += v.z();
            }
            if (bone.rotation != null) {
                Vector3f v = bone.rotation.sample(time);
                // Reflect Blockbench's Y-up coordinate system into ModelPart coordinates.
                part.xRot += (float) Math.toRadians(-v.x());
                part.yRot += (float) Math.toRadians(v.y());
                part.zRot += (float) Math.toRadians(-v.z());
            }
            if (bone.scale != null) {
                Vector3f v = bone.scale.sample(time);
                part.xScale = v.x();
                part.yScale = v.y();
                part.zScale = v.z();
            }
        }
    }

    private static void readSet(JsonObject set, Map<String, Animation> target) {
        if (set == null) return;
        for (Map.Entry<String, JsonElement> entry : set.entrySet()) {
            JsonObject obj = entry.getValue().getAsJsonObject();
            Animation a = new Animation();
            a.length = obj.get("length").getAsFloat();
            a.loop = "loop".equals(obj.get("loop").getAsString());
            JsonObject bones = obj.getAsJsonObject("bones");
            for (Map.Entry<String, JsonElement> boneEntry : bones.entrySet()) {
                JsonObject boneObj = boneEntry.getValue().getAsJsonObject();
                BoneAnimation b = new BoneAnimation();
                b.position = readChannel(boneObj.getAsJsonArray("position"));
                b.rotation = readChannel(boneObj.getAsJsonArray("rotation"));
                b.scale = readChannel(boneObj.getAsJsonArray("scale"));
                a.bones.put(boneEntry.getKey(), b);
            }
            target.put(entry.getKey(), a);
        }
    }

    private static Channel readChannel(JsonArray array) {
        if (array == null || array.isEmpty()) return null;
        Keyframe[] frames = new Keyframe[array.size()];
        for (int i = 0; i < array.size(); i++) {
            JsonArray v = array.get(i).getAsJsonArray();
            frames[i] = new Keyframe(v.get(0).getAsFloat(),
                    new Vector3f(v.get(1).getAsFloat(), v.get(2).getAsFloat(), v.get(3).getAsFloat()));
        }
        return new Channel(frames);
    }

    private static final class Animation {
        float length;
        boolean loop;
        final Map<String, BoneAnimation> bones = new HashMap<>();
        float sampleTime(float seconds) {
            if (length <= 0.0F) return 0.0F;
            if (loop) {
                float t = seconds % length;
                return t < 0.0F ? t + length : t;
            }
            return Math.max(0.0F, Math.min(length, seconds));
        }
    }

    private static final class BoneAnimation {
        Channel position;
        Channel rotation;
        Channel scale;
    }

    private record Keyframe(float time, Vector3f value) {}

    private record Channel(Keyframe[] frames) {
        Vector3f sample(float time) {
            if (frames.length == 1 || time <= frames[0].time()) return new Vector3f(frames[0].value());
            if (time >= frames[frames.length - 1].time()) return new Vector3f(frames[frames.length - 1].value());
            int low = 0;
            int high = frames.length - 1;
            while (low + 1 < high) {
                int mid = (low + high) >>> 1;
                if (frames[mid].time() <= time) low = mid;
                else high = mid;
            }
            Keyframe a = frames[low];
            Keyframe b = frames[high];
            float span = b.time() - a.time();
            float alpha = span <= 0.00001F ? 0.0F : (time - a.time()) / span;
            return new Vector3f(a.value()).lerp(b.value(), alpha);
        }
    }
}
