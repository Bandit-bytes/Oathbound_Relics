package net.bandit.oathboundrelics.registry;

import net.bandit.oathboundrelics.OathboundRelicsMod;
import net.bandit.oathboundrelics.curio.items.*;
import net.bandit.oathboundrelics.items.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import java.util.function.Function;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

public final class ItemRegistry {

    public static final FabricRegistrySupplier<Item> OATHBOUND_RELIC = registerItem(
            "oathbound_relic",
            OathboundRelicItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> ASHEN_NAIL = registerItem(
            "ashen_nail",
            AshenNailItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> BRANDKEEPERS_MERCY = registerItem(
            "brandkeepers_mercy",
            BrandkeepersMercyItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> SOUL_LANTERN = register(
            "soul_lantern",
            () -> new SoulLanternItem(
                    BlockRegistry.SOUL_LANTERN_BLOCK.get(),
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
            )
    );

    public static final FabricRegistrySupplier<Item> GRAVEBELL_LOCKET = registerItem(
            "gravebell_locket",
            GravebellLocketItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> HUNTERS_SIGIL = registerItem(
            "hunters_sigil",
            HuntersSigilItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> PILGRIMS_THORN = registerItem(
            "pilgrims_thorn",
            PilgrimsThornItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> SHROUD_OF_THE_FORSAKEN = registerItem(
            "shroud_of_the_forsaken",
            ShroudOfTheForsakenItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> VULTURE_CHARM = registerItem(
            "vulture_charm",
            VultureCharmItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> HOLLOW_EYE = registerItem(
            "hollow_eye",
            HollowEyeItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> CENSER_OF_ASH = registerItem(
            "censer_of_ash",
            CenserOfAshItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> MOURNERS_THREAD = registerItem(
            "mourners_thread",
            MournersThreadItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> THORNBOUND_CARAPACE = registerItem(
            "thornbound_carapace",
            ThornboundCarapaceItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> VOIDSTEP_BAND = registerItem(
            "voidstep_band",
            VoidstepBandItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> EXECUTIONERS_COIN = registerItem(
            "executioners_coin",
            ExecutionersCoinItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> RELIC_OF_THE_LAST_BREATH = registerItem(
            "relic_of_the_last_breath",
            RelicOfTheLastBreathItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> TORCH_OF_GRAVESONG = registerItem(
            "torch_of_gravesong",
            TorchOfGravesongItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> BRANDED_TIME_CHECKER = registerItem(
            "branded_time_checker",
            BrandedTimeCheckerItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> LETHARGIC_GREATSWORD = registerItem(
            "lethargic_greatsword",
            LethargicFlailItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> OATHBOUND_RELIQUARY = registerItem(
            "oathbound_reliquary",
            OathboundReliquaryItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> CHAIN_OF_THE_PENITENT = registerItem(
            "chain_of_the_penitent",
            ChainOfThePenitentItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> EYE_OF_THE_SLEEPLESS_WITNESS = registerItem(
            "eye_of_the_sleepless_witness",
            EyeOfTheSleeplessWitnessItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> CENSER_OF_HOLLOW_PRAYER = registerItem(
            "censer_of_hollow_prayer",
            CenserOfHollowPrayerItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> NAIL_OF_THE_FIRST_MARTYR = registerItem(
            "nail_of_the_first_martyr",
            NailOfTheFirstMartyrItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> GOLD_RING = registerItem(
            "gold_ring",
            GoldRingItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> CYAN_RING = registerItem(
            "cyan_ring",
            CyanRingItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> NEBULA_RING = registerItem(
            "nebula_ring",
            NebulaRingItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> SOUL_GEM = registerItem(
            "soul_gem",
            SoulGemItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> VANITYS_EDGE = registerItem(
            "vanitys_edge",
            VanitysEdgeItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> COVETFANG = registerItem(
            "covetfang",
            CovetfangItem::new,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> OBLIVION_STONE = registerItem(
            "oblivion_stone",
            OblivionStoneItem::new,
            new Item.Properties().stacksTo(16).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> FRACTURED_RELIC_ASH = register(
            "fractured_relic_ash",
            () -> new FracturedRelicAshItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant())
    );

    public static final FabricRegistrySupplier<Item> AREDRITE_GEM = registerItem(
            "aredrite_gem",
            AredriteGemItem::new,
            new Item.Properties().stacksTo(64).rarity(Rarity.EPIC).fireResistant()
    );

    public static final FabricRegistrySupplier<Item> AREDRITE_HELMET = register(
            "aredrite_helmet",
            () -> new AredriteArmorItem(
                    ArmorMaterialRegistry.AREDRITE,
                    ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .durability(ArmorItem.Type.HELMET.getDurability(37))
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            )
    );

    public static final FabricRegistrySupplier<Item> AREDRITE_CHESTPLATE = register(
            "aredrite_chestplate",
            () -> new AredriteArmorItem(
                    ArmorMaterialRegistry.AREDRITE,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .durability(ArmorItem.Type.CHESTPLATE.getDurability(37))
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            )
    );

    public static final FabricRegistrySupplier<Item> AREDRITE_LEGGINGS = register(
            "aredrite_leggings",
            () -> new AredriteArmorItem(
                    ArmorMaterialRegistry.AREDRITE,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .durability(ArmorItem.Type.LEGGINGS.getDurability(37))
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            )
    );

    public static final FabricRegistrySupplier<Item> AREDRITE_BOOTS = register(
            "aredrite_boots",
            () -> new AredriteArmorItem(
                    ArmorMaterialRegistry.AREDRITE,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .durability(ArmorItem.Type.BOOTS.getDurability(37))
                            .rarity(Rarity.EPIC)
                            .fireResistant()
            )
    );

    public static final FabricRegistrySupplier<Item> TABLET_OF_STILLNESS = register(
            "tablet_of_stillness",
            () -> new SinTabletItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    "tablet_of_stillness"
            )
    );

    public static final FabricRegistrySupplier<Item> TABLET_OF_EXALTATION = register(
            "tablet_of_exaltation",
            () -> new SinTabletItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    "tablet_of_exaltation"
            )
    );

    public static final FabricRegistrySupplier<Item> TABLET_OF_COVETING = register(
            "tablet_of_coveting",
            () -> new SinTabletItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    "tablet_of_coveting"
            )
    );

    public static final FabricRegistrySupplier<Item> BURDENED_FLAIL_IDOL = register(
            "burdened_flail_idol",
            () -> new SinIdolItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    "burdened_flail_idol"
            )
    );

    public static final FabricRegistrySupplier<Item> MIRRORSTEEL_IDOL = register(
            "mirrorsteel_idol",
            () -> new SinIdolItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    "mirrorsteel_idol"
            )
    );

    public static final FabricRegistrySupplier<Item> HOLLOW_FANG_IDOL = register(
            "hollow_fang_idol",
            () -> new SinIdolItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    "hollow_fang_idol"
            )
    );

    public static final FabricRegistrySupplier<Item> VOID_ASHES = register(
            "void_ashes",
            () -> new VoidAshesItem(
                    new Item.Properties().stacksTo(64).rarity(Rarity.RARE).fireResistant()
            )
    );

    public static final FabricRegistrySupplier<Item> COLOSSUS_HEART = register(
            "colossus_heart",
            () -> new TitansRemnantItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    TitanRemnantType.COLOSSUS_HEART
            )
    );

    public static final FabricRegistrySupplier<Item> EMBER_SEED = register(
            "ember_seed",
            () -> new TitansRemnantItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    TitanRemnantType.EMBER_SEED
            )
    );

    public static final FabricRegistrySupplier<Item> TIDE_PEARL = register(
            "tide_pearl",
            () -> new TitansRemnantItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    TitanRemnantType.TIDE_PEARL
            )
    );

    public static final FabricRegistrySupplier<Item> SKYBRAND_FEATHER = register(
            "skybrand_feather",
            () -> new TitansRemnantItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    TitanRemnantType.SKYBRAND_FEATHER
            )
    );

    public static final FabricRegistrySupplier<Item> NEBULA_LENS = register(
            "nebula_lens",
            () -> new TitansRemnantItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    TitanRemnantType.NEBULA_LENS
            )
    );

    public static final FabricRegistrySupplier<Item> VOID_PEARL = register(
            "void_pearl",
            () -> new TitansRemnantItem(
                    new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(),
                    TitanRemnantType.VOID_PEARL
            )
    );

    private ItemRegistry() {
    }

    public static void register() {
        // Static initializers perform registration on Fabric.
    }

    private static <T extends Item> FabricRegistrySupplier<Item> register(String name, Supplier<T> factory) {
        return new FabricRegistrySupplier<>(Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(OathboundRelicsMod.MOD_ID, name), factory.get()));
    }

    private static <T extends Item> FabricRegistrySupplier<Item> registerItem(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
        return register(name, () -> factory.apply(properties));
    }
}