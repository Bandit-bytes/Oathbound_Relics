package net.bandit.oathboundrelics.client.altar;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.blocks.blockentity.RiteOfSeveranceBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/** Renders the original animated Blockbench altar with vanilla Minecraft rendering only. */
public final class RiteOfSeveranceRenderer implements BlockEntityRenderer<RiteOfSeveranceBlockEntity> {
    private static final ResourceLocation BODY = tex("wave_altar_hell.png");
    private static final ResourceLocation LIGHT_RED = tex("altar_lights_red.png");
    private static final ResourceLocation LIGHT_GREEN = tex("altar_lights_green.png");
    private static final ResourceLocation LIGHT_BLUE = tex("altar_lights_blue.png");
    private static final ResourceLocation LIGHT_PURPLE = tex("altar_lights_purple.png");
    private static final ResourceLocation AURA_RED = tex("wave_altar_red.png");
    private static final ResourceLocation AURA_GREEN = tex("wave_altar_green.png");
    private static final ResourceLocation AURA_BLUE = tex("wave_altar_blue.png");
    private static final ResourceLocation AURA_PURPLE = tex("wave_altar_purple.png");

    private final NativeAltarModel body = NativeAltarModel.body();
    private final NativeAltarModel[] lights = {
            NativeAltarModel.lightRed(), NativeAltarModel.lightGreen(),
            NativeAltarModel.lightBlue(), NativeAltarModel.lightPurple()
    };
    private final NativeAltarModel[] aura = {
            NativeAltarModel.auraRed(), NativeAltarModel.auraGreen(),
            NativeAltarModel.auraBlue(), NativeAltarModel.auraPurple()
    };

    public RiteOfSeveranceRenderer(BlockEntityRendererProvider.Context context) {
        registerBaseBones(body);
        for (NativeAltarModel model : lights) registerLightBones(model);
        for (NativeAltarModel model : aura) registerAuraBones(model);
    }

    @Override
    public void render(RiteOfSeveranceBlockEntity altar, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffers, int packedLight, int packedOverlay) {
        float activeTicks = altar.getClientActiveTicks(partialTick);
        float closingTicks = altar.getClientClosingTicks(partialTick);

        body.resetPose();
        for (NativeAltarModel model : lights) model.resetPose();
        for (NativeAltarModel model : aura) model.resetPose();

        if (altar.isActive()) {
            float seconds = activeTicks / 20.0F;
            if (activeTicks < 30.0F) {
                applyAll("opening", seconds);
                applyAuraAll("opening", seconds);
            } else {
                float idleSeconds = (activeTicks - 30.0F) / 20.0F;
                applyAll("idle", idleSeconds);
                applyAll("ring_spin", idleSeconds);
                applyAll("orb_spin", idleSeconds);
                applyAll("dial_spin", idleSeconds);
                applyAuraAll("idle", idleSeconds);

                // The original wave animation is replayed on the same two-second cadence as ritual pulses.
                float pulseTick = (activeTicks - 30.0F) % 40.0F;
                if (pulseTick < 13.3334F) {
                    applyAll("wave_start", pulseTick / 20.0F);
                } else if (pulseTick < 20.0F) {
                    applyAll("wave_end", (pulseTick - 13.3334F) / 20.0F);
                }
            }
        } else if (closingTicks < 17.5F) {
            float seconds = closingTicks / 20.0F;
            applyAll("closing", seconds);
            applyAuraAll("closing", seconds);
        } else {
            applyAll("inactive", 0.0F);
        }

        poseStack.pushPose();
        /*
         * Blockbench's Java/entity coordinate system uses +Y downward while world-space
         * rendering uses +Y upward. ModelPart already converts cube coordinates from
         * model pixels to blocks internally, so applying an additional 1/16 scale here
         * made the altar sixteen times too small.
         *
         * The source model is ~39.4 px (2.46 blocks) tall. The model geometry itself
         * already carries the correct vertical offset, so the renderer should be anchored
         * directly to the block origin before flipping the Blockbench Y axis.
         * Z is flipped as well to match the usual Blockbench -> Minecraft model basis.
         */
        poseStack.translate(0.5D, 0.0D, 0.5D);
        poseStack.scale(1.0F, -1.0F, -1.0F);

        body.render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(BODY)), packedLight, OverlayTexture.NO_OVERLAY);

        if (altar.isActive() || closingTicks < 17.5F) {
            int color = altar.getClientRitualColorIndex();
            ResourceLocation lightTexture = switch (color) {
                case 0 -> LIGHT_RED;
                case 1 -> LIGHT_GREEN;
                case 2 -> LIGHT_BLUE;
                default -> LIGHT_PURPLE;
            };
            ResourceLocation auraTexture = switch (color) {
                case 0 -> AURA_RED;
                case 1 -> AURA_GREEN;
                case 2 -> AURA_BLUE;
                default -> AURA_PURPLE;
            };
            int fullBright = LightTexture.FULL_BRIGHT;
            lights[color].render(poseStack, buffers.getBuffer(RenderType.entityTranslucentEmissive(lightTexture)), fullBright, OverlayTexture.NO_OVERLAY);
            aura[color].render(poseStack, buffers.getBuffer(RenderType.entityTranslucentEmissive(auraTexture)), fullBright, OverlayTexture.NO_OVERLAY);
        }

        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(RiteOfSeveranceBlockEntity blockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 96;
    }

    private void applyAll(String name, float seconds) {
        NativeAltarAnimations.applyBase(body, name, seconds);
        for (NativeAltarModel model : lights) NativeAltarAnimations.applyBase(model, name, seconds);
    }

    private void applyAuraAll(String name, float seconds) {
        for (NativeAltarModel model : aura) NativeAltarAnimations.applyAura(model, name, seconds);
    }

    private static ResourceLocation tex(String file) {
        return ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "textures/block/altar/" + file);
    }

    private static void registerBaseBones(NativeAltarModel m) {
        m.registerBone("root", "root");
        m.registerBone("big_ring2", "root", "big_ring2");
        m.registerBone("light", "root", "big_ring2", "light");
        m.registerBone("big_ring", "root", "big_ring");
        m.registerBone("light2", "root", "big_ring", "light2");
        m.registerBone("light3", "root", "big_ring", "light3");
        m.registerBone("hitbox", "root", "big_ring", "hitbox");
        m.registerBone("innerpool", "root", "big_ring", "innerpool");
        m.registerBone("pool", "root", "big_ring", "pool");
        m.registerBone("ball", "root", "big_ring", "ball");
        m.registerBone("orb", "root", "big_ring", "ball", "orb");
        m.registerBone("particles", "root", "big_ring", "ball", "particles");
    }

    private static void registerLightBones(NativeAltarModel m) {
        m.registerBone("root", "root");
        m.registerBone("big_ring2", "root", "big_ring2");
        m.registerBone("light", "root", "big_ring2", "light");
        m.registerBone("big_ring", "root", "big_ring");
        m.registerBone("light2", "root", "big_ring", "light2");
        m.registerBone("light3", "root", "big_ring", "light3");
        m.registerBone("hitbox", "root", "big_ring", "hitbox");
        m.registerBone("pool", "root", "big_ring", "pool");
        m.registerBone("innerpool", "root", "big_ring", "pool", "innerpool");
        m.registerBone("ball", "root", "big_ring", "ball");
        m.registerBone("orb", "root", "big_ring", "ball", "orb");
        m.registerBone("particles", "root", "big_ring", "ball", "particles");
    }

    private static void registerAuraBones(NativeAltarModel m) {
        m.registerBone("area_effect", "area_effect");
        m.registerBone("area_effect_red", "area_effect", "area_effect_red");
        m.registerBone("area_effect_green", "area_effect", "area_effect_green");
        m.registerBone("area_effect_blue", "area_effect", "area_effect_blue");
        m.registerBone("area_effect_purple", "area_effect", "area_effect_purple");
    }
}
