package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

/**
 * Golden Orchid Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.GoldenOrchidBlock
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-10
 */
public class GoldenOrchidBlock extends CropBlock {

    private static final int MAX_AGE = 6;

    public static final IntegerProperty AGE = ModBlockStateProperties.AGE_6;

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D),
            Block.box(5.0D, 0.0D, 5.0D, 13.0D, 6.0D, 13.0D),
            Block.box(4.0D, 0.0D, 4.0D, 14.0D, 7.0D, 14.0D),
            Block.box(3.0D, 0.0D, 3.0D, 15.0D, 9.0D, 15.0D),
            Block.box(2.0D, 0.0D, 2.0D, 15.0D, 10.0D, 15.0D),
            Block.box(1.0D, 0.0D, 1.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
    };

    public GoldenOrchidBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPES[state.getValue(AGE)].move(vec3.x, vec3.y, vec3.z);
    }

    @Nonnull
    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Nonnull
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.GOLDEN_ORCHID_SEEDS.get();
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}
