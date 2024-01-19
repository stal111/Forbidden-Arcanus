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
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Arcane Crystal Obelisk Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ArcaneCrystalObeliskBlock
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class ArcaneCrystalObeliskBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final EnumProperty<ObeliskPart> PART = ModBlockStateProperties.OBELISK_PART;
    public static final BooleanProperty RITUAL = ModBlockStateProperties.RITUAL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final Map<ObeliskPart, VoxelShape> SHAPES = Util.make(new EnumMap<>(ObeliskPart.class), map -> {
        map.put(ObeliskPart.LOWER, Shapes.or(Block.box(0, 0, 0, 16, 8, 16), Block.box(1, 8, 1, 15, 16, 15)));
        map.put(ObeliskPart.MIDDLE, Block.box(2, 0, 2, 14, 16, 14));
        map.put(ObeliskPart.UPPER, Block.box(3, 0, 3, 13, 14, 13));
    });

    public ArcaneCrystalObeliskBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, ObeliskPart.LOWER).setValue(RITUAL, false).setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return state.getValue(RITUAL) && state.getValue(PART) == ObeliskPart.LOWER ? new ArcaneCrystalObeliskBlockEntity(pos, state) : null;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
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
                .setValue(RITUAL, this.isArcaneChiseledPolishedDarkstoneBelow(level, pos))
                .setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos pos, @Nonnull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        ObeliskPart part = state.getValue(PART);

        if (facing.getAxis() == Direction.Axis.Y && ((part == ObeliskPart.LOWER == (facing == Direction.UP)) || part == ObeliskPart.MIDDLE)) {
            return facingState.getBlock() == this && facingState.getValue(PART) != part ? state : Blocks.AIR.defaultBlockState();
        }

        if (part == ObeliskPart.LOWER && facing == Direction.DOWN && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, facing, facingState, level, pos, facingPos);
    }

    @Override
    public void neighborChanged(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);

        if (!fromPos.equals(pos.below())) {
            return;
        }

        BlockState newState = state.setValue(RITUAL, this.isArcaneChiseledPolishedDarkstoneBelow(level, pos));

        if (state != newState) {
            level.setBlockAndUpdate(pos, newState);
        }
    }

    @Override
    public @NotNull BlockState playerWillDestroy(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
        if (!level.isClientSide() && player.getAbilities().instabuild) {
            ObeliskPart part = state.getValue(PART);

            if (part != ObeliskPart.LOWER) {
                BlockPos offsetPos = pos.below(part == ObeliskPart.MIDDLE ? 1 : 2);

                level.setBlock(offsetPos, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, offsetPos, Block.getId(level.getBlockState(offsetPos)));
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, @Nonnull ItemStack stack) {
        level.setBlockAndUpdate(pos.above(), state.setValue(PART, ObeliskPart.MIDDLE).setValue(WATERLOGGED, level.getFluidState(pos.above()).getType() == Fluids.WATER));
        level.setBlockAndUpdate(pos.above(2), state.setValue(PART, ObeliskPart.UPPER).setValue(WATERLOGGED, level.getFluidState(pos.above(2)).getType() == Fluids.WATER));
    }

    @Override
    public boolean canSurvive(@Nonnull BlockState state, @Nonnull LevelReader level, @Nonnull BlockPos pos) {
        ObeliskPart part = state.getValue(PART);
        BlockPos posDown = pos.below();

        if (part == ObeliskPart.LOWER) {
            return level.getBlockState(posDown).isFaceSturdy(level, posDown, Direction.UP);
        }

        return true;
    }

    @Nonnull
    @Override
    public PushReaction getPistonPushReaction(@Nonnull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockEntityType) {
        if (!level.isClientSide()) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.ARCANE_CRYSTAL_OBELISK.get(), ArcaneCrystalObeliskBlockEntity::serverTick);
        }
        return null;
    }

    public static boolean isArcaneChiseledPolishedDarkstoneBelow(Level level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(ModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, RITUAL, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull PathComputationType type) {
        return false;
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
