package net.bandit.oathboundrelics.blocks;

import com.mojang.serialization.MapCodec;
import net.bandit.oathboundrelics.blocks.blockentity.RiteOfSeveranceBlockEntity;
import net.bandit.oathboundrelics.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class RiteOfSeveranceBlock extends BaseEntityBlock {

    public static final MapCodec<RiteOfSeveranceBlock> CODEC = simpleCodec(RiteOfSeveranceBlock::new);

    public RiteOfSeveranceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof RiteOfSeveranceBlockEntity altar)) {
            return InteractionResult.PASS;
        }

        return altar.onUsed(player);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RiteOfSeveranceBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, BlockEntityRegistry.RITE_OF_SEVERANCE_BE.get(), RiteOfSeveranceBlockEntity::tick);
    }

    protected boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }
}