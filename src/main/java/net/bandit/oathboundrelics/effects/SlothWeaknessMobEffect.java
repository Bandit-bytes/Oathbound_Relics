package net.bandit.oathboundrelics.effects;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SlothWeaknessMobEffect extends MobEffect {

    public SlothWeaknessMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x5E4A2A);

        addAttributeModifier(
                Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "sloth_weakness_attack_speed"),
                -0.30D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );

        addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, "sloth_weakness_attack_damage"),
                -0.30D,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
}