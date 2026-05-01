package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.effects.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public final class EffectRegistry {

    public static final Holder<MobEffect> LAZINESS = register("laziness", LazinessMobEffect::new);
    public static final Holder<MobEffect> SLOTH_WEAKNESS = register("sloth_weakness", SlothWeaknessMobEffect::new);
    public static final Holder<MobEffect> HEAVY_STUN = register("heavy_stun", HeavyStunMobEffect::new);
    public static final Holder<MobEffect> MARTYRS_CLAIM = register("martyrs_claim", MartyrsClaimMobEffect::new);
    public static final Holder<MobEffect> JUDGED = register("judged", JudgedMobEffect::new);
    public static final Holder<MobEffect> PRIDE_SUPPRESSION = register("pride_suppression", PrideSuppressionMobEffect::new);
    public static final Holder<MobEffect> LOSING_PRIDE = register("losing_pride", LosingPrideMobEffect::new);
    public static final Holder<MobEffect> BRAVERY = register("bravery", BraveryMobEffect::new);

    private EffectRegistry() {
    }

    public static void register() {
        // Static initializers perform registration on Fabric.
    }

    private static Holder<MobEffect> register(String name, Supplier<MobEffect> factory) {
        MobEffect effect = Registry.register(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, name), factory.get());
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
    }
}
