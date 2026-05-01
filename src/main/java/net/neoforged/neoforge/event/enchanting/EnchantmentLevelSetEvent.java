package net.neoforged.neoforge.event.enchanting;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class EnchantmentLevelSetEvent {
    private final Level level;
    private final BlockPos pos;
    private int enchantLevel;

    public EnchantmentLevelSetEvent(int enchantLevel) {
        this(null, BlockPos.ZERO, enchantLevel);
    }

    public EnchantmentLevelSetEvent(Level level, BlockPos pos, int enchantLevel) {
        this.level = level;
        this.pos = pos;
        this.enchantLevel = enchantLevel;
    }

    public Level getLevel() { return level; }
    public BlockPos getPos() { return pos; }
    public int getEnchantLevel() { return enchantLevel; }
    public void setEnchantLevel(int enchantLevel) { this.enchantLevel = enchantLevel; }
}
