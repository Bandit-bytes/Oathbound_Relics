package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;

public class LivingDamageEvent {
    protected final LivingEntity entity;
    protected final DamageSource source;
    protected final DamageContainer container;
    protected float newDamage;
    protected boolean canceled;

    public LivingDamageEvent(LivingEntity entity, DamageSource source, float amount) {
        this.entity = entity;
        this.source = source;
        this.newDamage = amount;
        this.container = new DamageContainer(amount);
    }

    public LivingEntity getEntity() { return entity; }
    public DamageSource getSource() { return source; }
    public DamageContainer getContainer() { return container; }
    public float getNewDamage() { return container.getNewDamage(); }
    public void setNewDamage(float amount) { this.newDamage = amount; this.container.setNewDamage(amount); }
    public boolean isCanceled() { return canceled; }
    public void setCanceled(boolean canceled) { this.canceled = canceled; }

    public static class Pre extends LivingDamageEvent {
        public Pre(LivingEntity entity, DamageSource source, float amount) { super(entity, source, amount); }
    }

    public static class Post extends LivingDamageEvent {
        public Post(LivingEntity entity, DamageSource source, float amount) { super(entity, source, amount); }
    }
}
