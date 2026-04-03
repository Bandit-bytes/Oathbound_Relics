package net.bandit.oathboundrelics.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.bandit.oathboundrelics.entity.SoulGemEntity;
import net.bandit.oathboundrelics.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class SoulGemRenderer extends EntityRenderer<SoulGemEntity> {

    public SoulGemRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SoulGemEntity entity,
                       float entityYaw,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight) {
        LocalPlayer player = Minecraft.getInstance().player;
        UUID owner = entity.getOwnerUUID();

        if (player == null || owner == null || !owner.equals(player.getUUID())) {
            return;
        }

        poseStack.pushPose();

        float age = entity.tickCount + partialTick;
        float bob = Mth.sin(age * 0.08F) * 0.18F;

        poseStack.translate(0.0D, 0.85D + bob, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(age * 2.5F));
        poseStack.scale(1.45F, 1.45F, 1.45F);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                new ItemStack(ItemRegistry.SOUL_GEM.get()),
                ItemDisplayContext.GROUND,
                0xF000F0,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SoulGemEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}