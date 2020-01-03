package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class ArcaneCrystalObeliskBlock extends CutoutBlock {

    private static final VoxelShape LOWER_SHAPE = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(0, 0, 0, 16, 8, 16),
            Block.makeCuboidShape(1, 8, 1, 15, 16, 15)
    );
    private static final VoxelShape MIDDLE_SHAPE = Block.makeCuboidShape(2, 0, 2, 14, 16, 14);
    private static final VoxelShape UPPER_SHAPE = Block.makeCuboidShape(3, 0, 3, 13, 14, 13);

    public static final EnumProperty<ArcaneCrystalObeliskPart> PART = EnumProperty.create("part", ArcaneCrystalObeliskPart.class);

    public ArcaneCrystalObeliskBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(PART, ArcaneCrystalObeliskPart.LOWER));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        ArcaneCrystalObeliskPart part = state.get(PART);
        if (part == ArcaneCrystalObeliskPart.LOWER) {
            return LOWER_SHAPE;
        } else if (part == ArcaneCrystalObeliskPart.MIDDLE) {
            return  MIDDLE_SHAPE;
        } else {
            return UPPER_SHAPE;
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState p_196271_3_, IWorld world, BlockPos pos, BlockPos p_196271_6_) {
        ArcaneCrystalObeliskPart part = state.get(PART);
        BlockState stateUp = world.getBlockState(pos.up());
        BlockState stateDoubleUp = world.getBlockState(pos.up(2));
        BlockState stateDown = world.getBlockState(pos.down());
        BlockState stateDoubleDown = world.getBlockState(pos.down(2));
        if (part == ArcaneCrystalObeliskPart.LOWER) {
            return stateUp.getBlock() == this && stateDoubleUp.getBlock() == this && stateUp.get(PART) != part && stateDoubleUp.get(PART) != part && state.isValidPosition(world, pos)  ? state : Blocks.AIR.getDefaultState();
        } else if (part == ArcaneCrystalObeliskPart.MIDDLE) {
            return stateUp.getBlock() == this && stateDown.getBlock() == this && stateUp.get(PART) != part && stateDown.get(PART) != part ? state : Blocks.AIR.getDefaultState();
        } else {
            return stateDown.getBlock() == this && stateDoubleDown.getBlock() == this && stateDown.get(PART) != part && stateDoubleDown.get(PART) != part ? state : Blocks.AIR.getDefaultState();
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        if (pos.getY() < 254 && world.getBlockState(pos.up()).isReplaceable(context)) {
            return this.getDefaultState().with(PART, ArcaneCrystalObeliskPart.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        world.setBlockState(pos.up(), state.with(PART, ArcaneCrystalObeliskPart.MIDDLE), 3);
        world.setBlockState(pos.up(2), state.with(PART, ArcaneCrystalObeliskPart.UPPER), 3);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos posDown = pos.down();
        BlockState stateDown = world.getBlockState(posDown);
        if (state.get(PART) == ArcaneCrystalObeliskPart.LOWER) {
            return stateDown.func_224755_d(world, posDown, Direction.UP);
        } else {
            return stateDown.getBlock() == this;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        double lvt_6_1_ = (double)pos.getX() + 0.7D - (double)(random.nextFloat() * 0.45F);
        double lvt_8_1_ = (double)pos.getY() + 0.6D - (double)(random.nextFloat() * 0.1F);
        double lvt_10_1_ = (double)pos.getZ() + 0.7D - (double)(random.nextFloat() * 0.45F);
        double lvt_12_1_ = (0.4F - (random.nextFloat() + random.nextFloat()) * 0.4F);
        if (state.get(PART) != ArcaneCrystalObeliskPart.LOWER) {
            world.addParticle(ParticleTypes.END_ROD, lvt_6_1_ + lvt_12_1_, lvt_8_1_ + lvt_12_1_, lvt_10_1_ + lvt_12_1_, random.nextGaussian() * 0.005D, random.nextGaussian() * 0.005D, random.nextGaussian() * 0.005D);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }
}
