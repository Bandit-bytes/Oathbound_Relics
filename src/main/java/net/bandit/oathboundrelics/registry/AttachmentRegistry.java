package net.bandit.oathboundrelics.registry;

import com.mojang.serialization.Codec;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.data.BrandedTimeData;
import net.bandit.oathboundrelics.data.EnvyStateData;
import net.bandit.oathboundrelics.data.PrideStateData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class AttachmentRegistry {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, OathboundRelicsMod.MOD_ID);

    public static final Supplier<AttachmentType<BrandedTimeData>> BRANDED_TIME = ATTACHMENT_TYPES.register(
            "branded_time",
            () -> AttachmentType
                    .serializable(BrandedTimeData::new)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<Integer>> SOUL_FRACTURE_COUNT =
            ATTACHMENT_TYPES.register(
                    "soul_fracture_count",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT)
                            .copyOnDeath()
                            .build()
            );

    public static final Supplier<AttachmentType<PrideStateData>> PRIDE_STATE =
            ATTACHMENT_TYPES.register(
            "pride_state",
            () -> AttachmentType
                    .serializable(PrideStateData::new)
                    .build()
    );

    public static final Supplier<AttachmentType<EnvyStateData>> ENVY_STATE =
            ATTACHMENT_TYPES.register(
            "envy_state",
            () -> AttachmentType
                    .serializable(EnvyStateData::new)
                    .build()
    );

    private AttachmentRegistry() {
    }

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
}