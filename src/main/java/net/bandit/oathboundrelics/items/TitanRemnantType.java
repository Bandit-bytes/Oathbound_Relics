package net.bandit.oathboundrelics.items;

public enum TitanRemnantType {
    COLOSSUS_HEART("colossus_heart"),
    EMBER_SEED("ember_seed"),
    TIDE_PEARL("tide_pearl"),
    SKYBRAND_FEATHER("skybrand_feather"),
    NEBULA_LENS("nebula_lens"),
    VOID_PEARL("void_pearl");

    private final String key;

    TitanRemnantType(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}