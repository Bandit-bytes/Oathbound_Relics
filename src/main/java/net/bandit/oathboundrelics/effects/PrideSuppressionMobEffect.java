package net.bandit.oathboundrelics.effects;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class PrideSuppressionMobEffect extends MobEffect {

    public PrideSuppressionMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x7B5E57);

        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "pride_suppression_attack_speed"),
                -0.50D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "pride_suppression_attack_damage"),
                -0.50D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}