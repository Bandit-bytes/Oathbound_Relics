package net.neoforged.neoforge.common.damagesource;

public class DamageContainer {
    private float newDamage;

    public DamageContainer(float amount) {
        this.newDamage = amount;
    }

    public float getNewDamage() {
        return newDamage;
    }

    public void setNewDamage(float newDamage) {
        this.newDamage = newDamage;
    }

    public enum Reduction {
        ARMOR,
        ENCHANTMENTS,
        ABSORPTION,
        MOB_EFFECTS
    }
}