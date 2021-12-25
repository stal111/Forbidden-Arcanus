package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.NipaBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

/**
 * Nipa Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.NipaBlock
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-13
 */
public class NipaBlock extends BushBlock implements EntityBlock {

    public static final BooleanProperty SPECK = ModBlockStateProperties.SPECK;

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D);

    public NipaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(SPECK, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new NipaBlockEntity(pos, state);
    }

    @Override
    public boolean canBeReplaced(@Nonnull BlockState state, @Nonnull BlockPlaceContext useContext) {
        return false;
    }

    @Nonnull
    @Override
    public InteractionResult use(BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (state.getValue(SPECK)) {
            NipaBlockEntity blockEntity = (NipaBlockEntity) level.getBlockEntity(pos);
            if (blockEntity != null) {
                this.harvestSpeck(state, level, pos, blockEntity);

                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void onRemove(BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && state.getValue(SPECK)) {
            NipaBlockEntity blockEntity = (NipaBlockEntity) level.getBlockEntity(pos);
            if (blockEntity != null) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + blockEntity.getSpeckHeight(), pos.getZ() + 0.5, new ItemStack(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get())));
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public void neighborChanged(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        NipaBlockEntity blockEntity = (NipaBlockEntity) level.getBlockEntity(pos);
        if (blockEntity != null) {
            int power = level.getBestNeighborSignal(pos);

            if (state.getValue(SPECK) && blockEntity.getCachedPower() != power && power != 0) {
                this.harvestSpeck(state, level, pos, blockEntity);
            }

            blockEntity.setCachedPower(power);
        }

        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (Objects.requireNonNull(Minecraft.getInstance().player).getInventory().contains(ModItems.Stacks.LENS_OF_VERITATIS)) {
            double j = 0.4D * rand.nextFloat();
            double k = 0.4D * rand.nextFloat();
            double posX = pos.getX() + 0.5D + (rand.nextBoolean() ? j : -j);
            double posY = (float) pos.getY() + 0.1D + rand.nextFloat() / 2.5;
            double posZ = pos.getZ() + 0.5D + (rand.nextBoolean() ? k : -k);
            double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.1D;

            level.addParticle(ModParticles.AUREAL_MOTE.get(), posX, posY, posZ, 0, ySpeed, 0);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockEntityType) {
        if (level.isClientSide() && state.getValue(SPECK)) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.NIPA.get(), NipaBlockEntity::clientTick);
        } else if (!level.isClientSide()) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.NIPA.get(), NipaBlockEntity::serverTick);
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SPECK);
    }

    private void harvestSpeck(BlockState state, Level world, BlockPos pos, NipaBlockEntity blockEntity) {
        world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + blockEntity.getSpeckHeight(), pos.getZ() + 0.5, new ItemStack(ModItems.ARCANE_CRYSTAL_DUST_SPECK.get())));
        world.setBlockAndUpdate(pos, state.setValue(SPECK, false));

        blockEntity.setSpeckHeight(10);
    }
}
