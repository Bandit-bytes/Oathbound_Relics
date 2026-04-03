package net.bandit.oathboundrelics.client.renderer;

import net.bandit.oathboundrelics.entity.HeavyCubeProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class HeavyCubeProjectileRenderer extends ThrownItemRenderer<HeavyCubeProjectileEntity> {

    public HeavyCubeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context, 3.0F, false);
    }
}