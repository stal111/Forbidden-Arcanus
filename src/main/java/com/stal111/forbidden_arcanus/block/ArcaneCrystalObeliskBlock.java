package com.stal111.forbidden_arcanus.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

/**
 * Arcane Crystal Obelisk Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock
 *
 * @author stal111
 * @version 2.0.0
 */
public class ArcaneCrystalObeliskBlock extends CutoutBlock implements IWaterLoggable {

    public static final EnumProperty<ArcaneCrystalObeliskPart> PART = EnumProperty.create("part", ArcaneCrystalObeliskPart.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final Map<ArcaneCrystalObeliskPart, VoxelShape> SHAPES = ImmutableMap.<ArcaneCrystalObeliskPart, VoxelShape>builder()
            .put(ArcaneCrystalObeliskPart.LOWER, VoxelShapeHelper.combineAll(Block.makeCuboidShape(0, 0, 0, 16, 8, 16), Block.makeCuboidShape(1, 8, 1, 15, 16, 15)))
            .put(ArcaneCrystalObeliskPart.MIDDLE, Block.makeCuboidShape(2, 0, 2, 14, 16, 14))
            .put(ArcaneCrystalObeliskPart.UPPER, Block.makeCuboidShape(3, 0, 3, 13, 14, 13))
            .build();

    public ArcaneCrystalObeliskBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(PART, ArcaneCrystalObeliskPart.LOWER).with(WATERLOGGED, false));
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

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPES.get(state.get(PART));
    }

    @Nonnull
    @Override
    public BlockState updatePostPlacement(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld world, @Nonnull BlockPos pos, @Nonnull BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        ArcaneCrystalObeliskPart part = state.get(PART);

        if (facing.getAxis() == Direction.Axis.Y && ((part == ArcaneCrystalObeliskPart.LOWER == (facing == Direction.UP)) || part == ArcaneCrystalObeliskPart.MIDDLE)) {
            return facingState.getBlock() == this && facingState.get(PART) != part ? state : Blocks.AIR.getDefaultState();
        }

        if (part == ArcaneCrystalObeliskPart.LOWER && facing == Direction.DOWN && !state.isValidPosition(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }

        return super.updatePostPlacement(state, facing, facingState, world, pos, facingPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;

        if (pos.getY() > world.getHeight() - 3 || !world.getBlockState(pos.up()).isReplaceable(context) || !world.getBlockState(pos.up(2)).isReplaceable(context)) {
            return null;
        }

        return this.getDefaultState().with(WATERLOGGED, flag);
    }

    @Override
    public void onBlockHarvested(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull PlayerEntity player) {
        if (!world.isRemote() && player.abilities.isCreativeMode) {
            ArcaneCrystalObeliskPart part = state.get(PART);

            if (part != ArcaneCrystalObeliskPart.LOWER) {
                BlockPos offsetPos = pos.down(part == ArcaneCrystalObeliskPart.MIDDLE ? 1 : 2);

                world.setBlockState(offsetPos, Blocks.AIR.getDefaultState(), 35);
                world.playEvent(player, 2001, offsetPos, Block.getStateId(world.getBlockState(offsetPos)));
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, @Nonnull ItemStack stack) {
        world.setBlockState(pos.up(), state.with(PART, ArcaneCrystalObeliskPart.MIDDLE).with(WATERLOGGED, world.getFluidState(pos.up()).getFluid() == Fluids.WATER), 3);
        world.setBlockState(pos.up(2), state.with(PART, ArcaneCrystalObeliskPart.UPPER).with(WATERLOGGED, world.getFluidState(pos.up(2)).getFluid() == Fluids.WATER), 3);
    }

    @Override
    public boolean isValidPosition(@Nonnull BlockState state, @Nonnull IWorldReader world, @Nonnull BlockPos pos) {
        ArcaneCrystalObeliskPart part = state.get(PART);
        BlockPos posDown = pos.down();

        if (part == ArcaneCrystalObeliskPart.LOWER) {
            return world.getBlockState(posDown).isSolidSide(world, posDown, Direction.UP);
        }

        return true;
    }

    @Nonnull
    @Override
    public PushReaction getPushReaction(@Nonnull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (Minecraft.getInstance().player != null) {
            if (!Minecraft.getInstance().player.inventory.hasItemStack(NewModItems.Stacks.LENS_OF_VERITATIS) || state.get(PART) == ArcaneCrystalObeliskPart.LOWER) {
                return;
            }

            double j = 0.6D * rand.nextFloat();
            double k = 0.6D * rand.nextFloat();
            double posX = pos.getX() + 0.5D + (rand.nextBoolean() ? j : -j);
            double posY = (float) pos.getY() + 0.1D + rand.nextFloat() / 2;
            double posZ = pos.getZ() + 0.5D + (rand.nextBoolean() ? k : -k);
            double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.1D;
            world.addParticle(ModParticles.AUREAL_MOTE.get(), posX, posY, posZ, 0, ySpeed, 0);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PART, WATERLOGGED);
    }

    @Override
    public boolean allowsMovement(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull PathType type) {
        return false;
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
