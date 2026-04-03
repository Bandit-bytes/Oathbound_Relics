package net.bandit.oathboundrelics.effects;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class LazinessMobEffect extends MobEffect {

    public LazinessMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x7A5A2B);

        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "laziness_attack_speed"),
                -0.05D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "laziness_attack_damage"),
                -0.05D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}