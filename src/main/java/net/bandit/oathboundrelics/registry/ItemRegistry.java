package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.curio.items.*;
import net.bandit.oathboundrelics.items.BrandedTimeCheckerItem;
import net.bandit.oathboundrelics.items.LethargicFlailItem;
import net.bandit.oathboundrelics.items.OathboundRelicItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ItemRegistry {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(OathboundRelicsMod.MOD_ID);

    public static final Supplier<Item> OATHBOUND_RELIC = ITEMS.registerItem(
            "oathbound_relic",
            OathboundRelicItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final Supplier<Item> ASHEN_NAIL = ITEMS.registerItem(
            "ashen_nail",
            AshenNailItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> GRAVEBELL_LOCKET = ITEMS.registerItem(
            "gravebell_locket",
            GravebellLocketItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> HUNTERS_SIGIL = ITEMS.registerItem(
            "hunters_sigil",
            HuntersSigilItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> PILGRIMS_THORN = ITEMS.registerItem(
            "pilgrims_thorn",
            PilgrimsThornItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> SHROUD_OF_THE_FORSAKEN = ITEMS.registerItem(
            "shroud_of_the_forsaken",
            ShroudOfTheForsakenItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> VULTURE_CHARM = ITEMS.registerItem(
            "vulture_charm",
            VultureCharmItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> HOLLOW_EYE = ITEMS.registerItem(
            "hollow_eye",
            HollowEyeItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> CENSER_OF_ASH = ITEMS.registerItem(
            "censer_of_ash",
            CenserOfAshItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> MOURNERS_THREAD = ITEMS.registerItem(
            "mourners_thread",
            MournersThreadItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> THORNBOUND_CARAPACE = ITEMS.registerItem(
            "thornbound_carapace",
            ThornboundCarapaceItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> VOIDSTEP_BAND = ITEMS.registerItem(
            "voidstep_band",
            VoidstepBandItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> EXECUTIONERS_COIN = ITEMS.registerItem(
            "executioners_coin",
            ExecutionersCoinItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> RELIC_OF_THE_LAST_BREATH = ITEMS.registerItem(
            "relic_of_the_last_breath",
            RelicOfTheLastBreathItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final Supplier<Item> TORCH_OF_GRAVESONG = ITEMS.registerItem(
            "torch_of_gravesong",
            TorchOfGravesongItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> BRANDED_TIME_CHECKER = ITEMS.registerItem(
            "branded_time_checker",
            BrandedTimeCheckerItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()
    );

    public static final Supplier<Item> LETHARGIC_GREATSWORD = ITEMS.registerItem(
            "lethargic_greatsword",
            LethargicFlailItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final Supplier<Item> OATHBOUND_RELIQUARY = ITEMS.registerItem(
            "oathbound_reliquary",
            OathboundReliquaryItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final Supplier<Item> CHAIN_OF_THE_PENITENT = ITEMS.registerItem(
            "chain_of_the_penitent",
            ChainOfThePenitentItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> EYE_OF_THE_SLEEPLESS_WITNESS = ITEMS.registerItem(
            "eye_of_the_sleepless_witness",
            EyeOfTheSleeplessWitnessItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> CENSER_OF_HOLLOW_PRAYER = ITEMS.registerItem(
            "censer_of_hollow_prayer",
            CenserOfHollowPrayerItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> NAIL_OF_THE_FIRST_MARTYR = ITEMS.registerItem(
            "nail_of_the_first_martyr",
            NailOfTheFirstMartyrItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> GOLD_RING = ITEMS.registerItem(
            "gold_ring",
            GoldRingItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()
    );

    public static final Supplier<Item> CYAN_RING = ITEMS.registerItem(
            "cyan_ring",
            CyanRingItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final Supplier<Item> NEBULA_RING = ITEMS.registerItem(
            "nebula_ring",
            NebulaRingItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    private ItemRegistry() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}