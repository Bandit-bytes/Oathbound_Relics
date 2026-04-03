package net.bandit.oathboundrelics.effects;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class LosingPrideMobEffect extends MobEffect {

    public LosingPrideMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x5D315C);

        addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "losing_pride_movement_speed"),
                -0.80D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "losing_pride_attack_speed"),
                -0.80D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "losing_pride_attack_damage"),
                -0.80D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}