package net.bandit.oathboundrelics.registry;

import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public final class FabricRegistrySupplier<T> implements Supplier<T>, ItemLike {
    private final T value;

    public FabricRegistrySupplier(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public net.minecraft.world.item.Item asItem() {
        if (value instanceof ItemLike itemLike) {
            return itemLike.asItem();
        }
        throw new IllegalStateException("Registered value is not ItemLike: " + value);
    }
}
