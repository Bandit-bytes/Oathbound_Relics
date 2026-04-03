package net.bandit.oathboundrelics.effects;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HeavyStunMobEffect extends MobEffect {

    public HeavyStunMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x3D3225);

        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "heavy_stun_movement_speed"),
                -0.95D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}