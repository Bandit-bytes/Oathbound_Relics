package net.bandit.oathboundrelics.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.bandit.oathboundrelics.items.SoulLanternItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class SoulLanternCurioRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends net.minecraft.client.model.EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource buffer,
            int packedLight,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        LivingEntity living = slotContext.entity();

        if (!(stack.getItem() instanceof SoulLanternItem)) {
            return;
        }

        poseStack.pushPose();

        if (renderLayerParent.getModel() instanceof HumanoidModel<?> humanoidModel) {
            humanoidModel.rightLeg.translateAndRotate(poseStack);
        }

        // Outside of right thigh
        poseStack.translate(0.42D, 0.25D, 0.04D);

        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));

        if (living.isCrouching()) {
            poseStack.translate(0.0D, 0.08D, 0.04D);
        }

        poseStack.scale(0.55F, 0.55F, 0.55F);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                living,
                stack,
                ItemDisplayContext.FIXED,
                false,
                poseStack,
                buffer,
                living.level(),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                living.getId()
        );

        poseStack.popPose();
    }
}