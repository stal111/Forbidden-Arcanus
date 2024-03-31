package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.ArcaneCrystalObeliskBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class ArcaneCrystalObeliskBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final EnumProperty<ObeliskPart> PART = ModBlockStateProperties.OBELISK_PART;
    public static final BooleanProperty ACTIVE = ModBlockStateProperties.ACTIVE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final Map<ObeliskPart, VoxelShape> SHAPES = Util.make(new EnumMap<>(ObeliskPart.class), map -> {
        map.put(ObeliskPart.LOWER, Shapes.or(Block.box(0, 0, 0, 16, 8, 16), Block.box(1, 8, 1, 15, 16, 15)));
        map.put(ObeliskPart.MIDDLE, Block.box(2, 0, 2, 14, 16, 14));
        map.put(ObeliskPart.UPPER, Block.box(3, 0, 3, 13, 14, 13));
    });

    public ArcaneCrystalObeliskBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, ObeliskPart.LOWER).setValue(ACTIVE, false).setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(ACTIVE) && state.getValue(PART) == ObeliskPart.LOWER ? new ArcaneCrystalObeliskBlockEntity(pos, state) : null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(PART));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        if (pos.getY() > level.getMaxBuildHeight() - 3 || !level.getBlockState(pos.above()).canBeReplaced(context) || !level.getBlockState(pos.above(2)).canBeReplaced(context)) {
            return null;
        }

        return this.defaultBlockState()
                .setValue(ACTIVE, shouldActivate(level, pos))
                .setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        ObeliskPart part = state.getValue(PART);

        if (facing.getAxis() != Direction.Axis.Y) {
            return state;
        }

        if (part == ObeliskPart.LOWER == (facing == Direction.UP) || part == ObeliskPart.MIDDLE) {
            return facingState.is(this) && facingState.getValue(PART) != part ? state : Blocks.AIR.defaultBlockState();
        }

        if (part == ObeliskPart.LOWER && facing == Direction.DOWN && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return state;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!fromPos.equals(pos.below())) {
            return;
        }

        boolean flag = shouldActivate(level, pos);

        if (state.getValue(ACTIVE) != flag) {
            level.setBlockAndUpdate(pos, state.setValue(ACTIVE, flag));
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && (player.isCreative() || !player.hasCorrectToolForDrops(state))) {
            ObeliskPart part = state.getValue(PART);

            if (part != ObeliskPart.LOWER) {
                BlockPos offsetPos = pos.below(part == ObeliskPart.MIDDLE ? 1 : 2);
                BlockState offsetState = level.getBlockState(offsetPos);

                level.setBlock(offsetPos, offsetState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, offsetPos, Block.getId(offsetState));
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        level.setBlockAndUpdate(pos.above(), state.setValue(PART, ObeliskPart.MIDDLE).setValue(WATERLOGGED, level.getFluidState(pos.above()).getType() == Fluids.WATER));
        level.setBlockAndUpdate(pos.above(2), state.setValue(PART, ObeliskPart.UPPER).setValue(WATERLOGGED, level.getFluidState(pos.above(2)).getType() == Fluids.WATER));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos posDown = pos.below();
        BlockState stateDown = level.getBlockState(posDown);

        if (state.getValue(PART) == ObeliskPart.LOWER) {
            return stateDown.isFaceSturdy(level, posDown, Direction.UP);
        }

        return stateDown.is(this);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null && state.getValue(PART) != ObeliskPart.LOWER) {
            if (!player.getInventory().contains(ModItems.Stacks.LENS_OF_VERITATIS)) {
                return;
            }

            double j = 0.6D * random.nextFloat();
            double k = 0.6D * random.nextFloat();
            double posX = pos.getX() + 0.5D + (random.nextBoolean() ? j : -j);
            double posY = (float) pos.getY() + 0.1D + random.nextFloat() / 2;
            double posZ = pos.getZ() + 0.5D + (random.nextBoolean() ? k : -k);
            double ySpeed = ((double) random.nextFloat() - 0.4D) * 0.1D;

            level.addParticle(ModParticles.AUREAL_MOTE.get(), posX, posY, posZ, 0, ySpeed, 0);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!level.isClientSide()) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.ARCANE_CRYSTAL_OBELISK.get(), ArcaneCrystalObeliskBlockEntity::serverTick);
        }
        return null;
    }

    public static boolean shouldActivate(Level level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, ACTIVE, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
