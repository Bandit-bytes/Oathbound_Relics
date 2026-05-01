package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingDeathEvent {
    private final LivingEntity entity;
    private final DamageSource source;
    public LivingDeathEvent(LivingEntity entity, DamageSource source) { this.entity = entity; this.source = source; }
    public LivingEntity getEntity() { return entity; }
    public DamageSource getSource() { return source; }
}
