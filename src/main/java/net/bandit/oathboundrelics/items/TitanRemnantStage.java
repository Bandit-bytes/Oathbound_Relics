package net.bandit.oathboundrelics.items;

public enum TitanRemnantStage {
    DORMANT(0, "dormant"),
    LATENT(1, "latent"),
    AWAKENED(2, "awakened"),
    ASCENDED(3, "ascended"),
    TRANSCENDENT(4, "transcendent"),
    APEX(5, "apex");

    private final int id;
    private final String key;

    TitanRemnantStage(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public int id() {
        return id;
    }

    public String key() {
        return key;
    }

    public boolean atLeast(TitanRemnantStage other) {
        return this.id >= other.id;
    }

    public float modelValue() {
        return this.id / 5.0F;
    }

    public TitanRemnantStage next() {
        return switch (this) {
            case DORMANT -> LATENT;
            case LATENT -> AWAKENED;
            case AWAKENED -> ASCENDED;
            case ASCENDED -> TRANSCENDENT;
            case TRANSCENDENT -> APEX;
            case APEX -> APEX;
        };
    }

    public TitanRemnantStage cap(TitanRemnantStage cap) {
        return this.id <= cap.id ? this : cap;
    }

    public static TitanRemnantStage byId(int id) {
        for (TitanRemnantStage stage : values()) {
            if (stage.id == id) {
                return stage;
            }
        }
        return DORMANT;
    }
}
