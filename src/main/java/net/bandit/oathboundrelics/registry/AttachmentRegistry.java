package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.data.BrandedTimeData;
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
            () -> AttachmentType.serializable(BrandedTimeData::new)
                    .copyOnDeath()
                    .build()
    );

    private AttachmentRegistry() {
    }

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
}