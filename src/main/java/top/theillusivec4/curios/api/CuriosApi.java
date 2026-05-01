package top.theillusivec4.curios.api;

import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import dev.emi.trinkets.api.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class CuriosApi {
    private static final CuriosHelper HELPER = new CuriosHelper();

    private CuriosApi() {}

    public static Optional<CuriosInventory> getCuriosInventory(Player player) {
        return TrinketsApi.getTrinketComponent(player).map(CuriosInventory::new);
    }

    public static CuriosHelper getCuriosHelper() {
        return HELPER;
    }

    public static void addSlotModifier(
            Multimap<Holder<Attribute>, AttributeModifier> modifiers,
            String slot,
            ResourceLocation id,
            double amount,
            AttributeModifier.Operation operation
    ) {
        String trinketsSlot = switch (slot) {
            case "ring" -> "hand/ring";
            case "necklace" -> "chest/necklace";
            case "belt" -> "legs/belt";
            default -> slot;
        };

        SlotAttributes.addSlotModifier(
                modifiers,
                trinketsSlot,
                id,
                amount,
                operation
        );
    }

    public static final class CuriosHelper {
        public Optional<SlotResult> findFirstCurio(Player player, Predicate<ItemStack> predicate) {
            return getCuriosInventory(player).flatMap(inv -> inv.findCurios(predicate).stream().findFirst());
        }
    }

    public static final class CuriosInventory {
        private final TrinketComponent component;

        private CuriosInventory(TrinketComponent component) {
            this.component = component;
        }

        public List<SlotResult> findCurios(Item item) {
            return findCurios(stack -> stack.is(item));
        }

        public List<SlotResult> findCurios(Predicate<ItemStack> predicate) {
            List<SlotResult> results = new ArrayList<>();
            for (Tuple<SlotReference, ItemStack> pair : component.getAllEquipped()) {
                SlotReference ref = pair.getA();
                ItemStack stack = pair.getB();

                if (predicate.test(stack)) {
                    results.add(new SlotResult(
                            new SlotContext(ref.inventory().getSlotType().getName(), component.getEntity(), ref.index()),
                            stack,
                            ref
                    ));
                }
            }
            return results;
        }

        public boolean isEquipped(Item item) {
            return !findCurios(item).isEmpty();
        }

        public boolean isEquipped(Predicate<ItemStack> predicate) {
            return !findCurios(predicate).isEmpty();
        }

        public Optional<StacksHandler> getStacksHandler(String identifier) {
            return component.getInventory().values().stream()
                    .flatMap(group -> group.values().stream())
                    .filter(inv -> inv.getSlotType().getName().equals(identifier))
                    .findFirst()
                    .map(StacksHandler::new);
        }
    }

    public record SlotResult(SlotContext slotContext, ItemStack stack, SlotReference reference) {
    }

    public static final class StacksHandler {
        private final TrinketInventory inventory;

        private StacksHandler(TrinketInventory inventory) {
            this.inventory = inventory;
        }

        public StackWrapper getStacks() {
            return new StackWrapper(inventory);
        }
    }

    public static final class StackWrapper {
        private final TrinketInventory inventory;

        private StackWrapper(TrinketInventory inventory) {
            this.inventory = inventory;
        }

        public void setStackInSlot(int index, ItemStack stack) {
            inventory.setItem(index, stack);
        }
    }
}
