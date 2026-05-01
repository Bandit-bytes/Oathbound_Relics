package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class LivingIncomingDamageEvent {
    private final LivingEntity entity;
    private final DamageSource source;
    private final DamageContainer container;
    private float amount;

    public LivingIncomingDamageEvent(LivingEntity entity, DamageSource source, float amount) {
        this.entity = entity;
        this.source = source;
        this.amount = amount;
        this.container = new DamageContainer(amount);
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public DamageSource getSource() {
        return source;
    }

    public DamageContainer getContainer() {
        return container;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
        this.container.setNewDamage(amount);
    }

    public void addReductionModifier(
            DamageContainer.Reduction reduction,
            java.util.function.BiFunction<DamageContainer, Float, Float> modifier
    ) {
        // Compatibility no-op for Fabric.
    }

    public float resultAmount() {
        return container.getNewDamage();
    }
}