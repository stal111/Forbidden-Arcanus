package com.stal111.forbidden_arcanus.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

import javax.annotation.Nonnull;

/**
 * Leaf Carpet Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.LeafCarpetBlock
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-04-10
 */
public class LeafCarpetBlock extends Block {

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);

    private final RegistryEntry<LeavesBlock> leavesBlock;

    public LeafCarpetBlock(RegistryEntry<LeavesBlock> leavesBlock, Properties properties) {
        super(properties);
        this.leavesBlock = leavesBlock;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean canBeReplaced(@Nonnull BlockState state, BlockPlaceContext context) {
        return context.getItemInHand().isEmpty() || context.getItemInHand().getItem() != this.asItem();
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction direction, @Nonnull BlockState neighborState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos neighborPos) {
        return !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public boolean canSurvive(@Nonnull BlockState state, @Nonnull LevelReader level, @Nonnull BlockPos pos) {
        return !level.isEmptyBlock(pos.below());
    }

    public RegistryEntry<LeavesBlock> getLeavesBlock() {
        return this.leavesBlock;
    }
}
