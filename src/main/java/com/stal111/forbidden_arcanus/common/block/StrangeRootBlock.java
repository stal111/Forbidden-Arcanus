package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nonnull;

/**
 * Strange Root Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.StrangeRootBlock
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-12-11
 */
public class StrangeRootBlock extends CropBlock {

    private static final int MAX_AGE = 3;

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(1.0D, 0.0D, 1.0D, 14.0D, 6.0D, 14.0D),
            Block.box(0.0D, 0.0D, 0.0D, 15.0D, 9.0D, 15.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D)
    };

    public StrangeRootBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE, 0));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPES[state.getValue(AGE)];
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

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        if (!level.isAreaLoaded(pos, 1) || level.getRawBrightness(pos, 0) >= 5) {
            return;
        }

        int age = this.getAge(state);

        if (age < this.getMaxAge() && CommonHooks.canCropGrow(level, pos, state, random.nextInt((int) ((25.0F / getGrowthSpeed(this, level, pos)) + 1)) == 0)) {
            level.setBlock(pos, state.cycle(AGE), 2);
            CommonHooks.fireCropGrowPost(level, pos, state);
        }
    }

    @Override
    protected boolean mayPlaceOn(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos) {
        return state.is(BlockTags.BASE_STONE_OVERWORLD);
    }

    @Nonnull
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.STRANGE_ROOT.get();
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
