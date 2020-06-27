package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class GoldenOrchidBlock extends CropsBlock {

    public static final IntegerProperty GOLDEN_ORCHID_AGE = BlockStateProperties.AGE_0_2;
    private static final VoxelShape[] SHAPE = new VoxelShape[]{Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)};

    public GoldenOrchidBlock(Properties properties) {
        super(properties);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return GOLDEN_ORCHID_AGE;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected IItemProvider getSeedsItem() {
        return ModItems.GOLDEN_ORCHID_SEEDS.get();
    }

    @Override
    public int getMaxAge() {
        return 2;
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(3) != 0) {
            super.tick(state, world, pos, random);
        }
    }

    @Override
    protected int getBonemealAgeIncrease(World worldIn) {
        return super.getBonemealAgeIncrease(worldIn) / 2;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(GOLDEN_ORCHID_AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d vec3d = state.getOffset(worldIn, pos);
        return SHAPE[state.get(this.getAgeProperty())].withOffset(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}
