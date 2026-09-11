package net.bandit.oathboundrelics.network;

import io.netty.buffer.ByteBuf;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.util.TitansRemnantUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SkybrandGlidePayload(boolean gliding) implements CustomPacketPayload {

    public static final Type<SkybrandGlidePayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    OathboundRelicsMod.MOD_ID,
                    "skybrand_glide"
            ));

    public static final StreamCodec<ByteBuf, SkybrandGlidePayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    SkybrandGlidePayload::gliding,
                    SkybrandGlidePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(
            SkybrandGlidePayload payload,
            IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            if (context.player() != null) {
                TitansRemnantUtil.setSkybrandGliding(
                        context.player(),
                        payload.gliding()
                );
            }
        });
    }
}