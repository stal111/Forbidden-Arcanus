package com.stal111.forbidden_arcanus.block;

import com.google.common.collect.ImmutableMap;
import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

/**
 * Arcane Crystal Obelisk Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock
 *
 * @author stal111
 * @version 2.0.0
 */
public class ArcaneCrystalObeliskBlock extends CutoutBlock implements SimpleWaterloggedBlock {

    public static final EnumProperty<ArcaneCrystalObeliskPart> PART = EnumProperty.create("part", ArcaneCrystalObeliskPart.class);
    public static final BooleanProperty RITUAL = ModBlockStateProperties.RITUAL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final Map<ArcaneCrystalObeliskPart, VoxelShape> SHAPES = ImmutableMap.<ArcaneCrystalObeliskPart, VoxelShape>builder()
            .put(ArcaneCrystalObeliskPart.LOWER, VoxelShapeHelper.combineAll(Block.box(0, 0, 0, 16, 8, 16), Block.box(1, 8, 1, 15, 16, 15)))
            .put(ArcaneCrystalObeliskPart.MIDDLE, Block.box(2, 0, 2, 14, 16, 14))
            .put(ArcaneCrystalObeliskPart.UPPER, Block.box(3, 0, 3, 13, 14, 13))
            .build();

    public ArcaneCrystalObeliskBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, ArcaneCrystalObeliskPart.LOWER).setValue(RITUAL, false).setValue(WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPES.get(state.getValue(PART));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();

        if (pos.getY() > world.getMaxBuildHeight() - 3 || !world.getBlockState(pos.above()).canBeReplaced(context) || !world.getBlockState(pos.above(2)).canBeReplaced(context)) {
            return null;
        }

        return this.defaultBlockState()
                .setValue(RITUAL, world.getBlockState(pos.below()).getBlock() == ModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get())
                .setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor world, @Nonnull BlockPos pos, @Nonnull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        ArcaneCrystalObeliskPart part = state.getValue(PART);

        if (facing.getAxis() == Direction.Axis.Y && ((part == ArcaneCrystalObeliskPart.LOWER == (facing == Direction.UP)) || part == ArcaneCrystalObeliskPart.MIDDLE)) {
            return facingState.getBlock() == this && facingState.getValue(PART) != part ? state : Blocks.AIR.defaultBlockState();
        }

        if (part == ArcaneCrystalObeliskPart.LOWER && facing == Direction.DOWN && !state.canSurvive(world, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    @Override
    public void neighborChanged(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);

        if (!fromPos.equals(pos.below())) {
            return;
        }

        BlockState newState = state.setValue(RITUAL, world.getBlockState(fromPos).getBlock() == ModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get());

        if (state != newState) {
            world.setBlock(pos, newState, 3);
        }
    }

    @Override
    public void playerWillDestroy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
        if (!world.isClientSide() && player.getAbilities().instabuild) {
            ArcaneCrystalObeliskPart part = state.getValue(PART);

            if (part != ArcaneCrystalObeliskPart.LOWER) {
                BlockPos offsetPos = pos.below(part == ArcaneCrystalObeliskPart.MIDDLE ? 1 : 2);

                world.setBlock(offsetPos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, offsetPos, Block.getId(world.getBlockState(offsetPos)));
            }
        }

        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, @Nonnull ItemStack stack) {
        world.setBlock(pos.above(), state.setValue(PART, ArcaneCrystalObeliskPart.MIDDLE).setValue(WATERLOGGED, world.getFluidState(pos.above()).getType() == Fluids.WATER), 3);
        world.setBlock(pos.above(2), state.setValue(PART, ArcaneCrystalObeliskPart.UPPER).setValue(WATERLOGGED, world.getFluidState(pos.above(2)).getType() == Fluids.WATER), 3);
    }

    @Override
    public boolean canSurvive(@Nonnull BlockState state, @Nonnull LevelReader world, @Nonnull BlockPos pos) {
        ArcaneCrystalObeliskPart part = state.getValue(PART);
        BlockPos posDown = pos.below();

        if (part == ArcaneCrystalObeliskPart.LOWER) {
            return world.getBlockState(posDown).isFaceSturdy(world, posDown, Direction.UP);
        }

        return true;
    }

    @Nonnull
    @Override
    public PushReaction getPistonPushReaction(@Nonnull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (Minecraft.getInstance().player != null) {
            if (!Minecraft.getInstance().player.getInventory().contains(NewModItems.Stacks.LENS_OF_VERITATIS) || state.getValue(PART) == ArcaneCrystalObeliskPart.LOWER) {
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, RITUAL, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull PathComputationType type) {
        return false;
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
