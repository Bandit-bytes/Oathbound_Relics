package net.bandit.oathboundrelics.effects;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BraveryMobEffect extends MobEffect {

    public BraveryMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xD6A11F);

        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "bravery_attack_speed"),
                2.00D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "bravery_attack_damage"),
                2.00D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}