package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.effects.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EffectRegistry {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, OathboundRelicsMod.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> LAZINESS =
            MOB_EFFECTS.register("laziness", LazinessMobEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> SLOTH_WEAKNESS =
            MOB_EFFECTS.register("sloth_weakness", SlothWeaknessMobEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> HEAVY_STUN =
            MOB_EFFECTS.register("heavy_stun", HeavyStunMobEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> MARTYRS_CLAIM =
            MOB_EFFECTS.register("martyrs_claim", MartyrsClaimMobEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> JUDGED =
            MOB_EFFECTS.register("judged", JudgedMobEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> PRIDE_SUPPRESSION =
            MOB_EFFECTS.register("pride_suppression", PrideSuppressionMobEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> LOSING_PRIDE =
            MOB_EFFECTS.register("losing_pride", LosingPrideMobEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> BRAVERY =
            MOB_EFFECTS.register("bravery", BraveryMobEffect::new);



    private EffectRegistry() {
    }

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}