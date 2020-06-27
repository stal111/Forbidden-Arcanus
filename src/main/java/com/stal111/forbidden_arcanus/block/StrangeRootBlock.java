package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class StrangeRootBlock extends Block implements IGrowable {

    public static final IntegerProperty STRANGE_ROOT_AGE = BlockStateProperties.AGE_0_3;

    public StrangeRootBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(STRANGE_ROOT_AGE, 0));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return Tags.Blocks.STONE.func_230235_a_(world.getBlockState(pos.down()).getBlock());
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    @Override
    public ItemStack getItem(IBlockReader world, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.STRANGE_ROOT.get());
    }

    public int getMaxAge() {
        return 3;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        super.tick(state, worldIn, pos, rand);
        if (!worldIn.isAreaLoaded(pos, 1)) return;
        int i = state.get(STRANGE_ROOT_AGE);
        if (i < this.getMaxAge()) {
            float f = getGrowthChance(this, worldIn, pos);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0)) {
                worldIn.setBlockState(pos, state.with(STRANGE_ROOT_AGE, state.get(STRANGE_ROOT_AGE) + 1), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    protected static float getGrowthChance(Block blockIn, IBlockReader worldIn, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos = pos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
                f1 = 1.0F;
                if (blockstate.isFertile(worldIn, blockpos.add(i, 0, j))) {
                    f1 = 3.0F;
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock();
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean b) {
        return state.get(STRANGE_ROOT_AGE) != getMaxAge();
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = state.get(STRANGE_ROOT_AGE) + this.getBonemealAgeIncrease();
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        world.setBlockState(pos, state.with(STRANGE_ROOT_AGE, state.get(STRANGE_ROOT_AGE) + 1), 2);
    }

    protected int getBonemealAgeIncrease() {
        return 1;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STRANGE_ROOT_AGE);
    }
}
