package top.theillusivec4.curios.api.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Predicate;

public class DropRulesEvent {
    private final LivingEntity entity;

    public DropRulesEvent() {
        this.entity = null;
    }

    public DropRulesEvent(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void addOverride(Item item, ICurio.DropRule rule) {
        // Trinkets uses different drop handling. Kept as a no-op compatibility hook.
    }

    public void addOverride(Predicate<ItemStack> predicate, ICurio.DropRule rule) {
        // Trinkets uses different drop handling. Kept as a no-op compatibility hook.
    }
}
