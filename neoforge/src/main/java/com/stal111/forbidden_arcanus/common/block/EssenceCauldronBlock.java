package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.EssenceCauldronBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceStorageBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class EssenceCauldronBlock extends Block implements EntityBlock {

    public static final VoxelShape SHAPE_INSIDE = Block.column(12.0, 4.0, 16.0);
    public static final VoxelShape SHAPE = Shapes.join(
            Shapes.block(),
            Shapes.or(Block.column(16.0, 8.0, 0.0, 3.0), Block.column(8.0, 16.0, 0.0, 3.0), Block.column(12.0, 0.0, 3.0), SHAPE_INSIDE),
            BooleanOp.ONLY_FIRST
    );

    public static final EnumProperty<EssenceType> ESSENCE_TYPE = ModBlockStateProperties.ESSENCE_TYPE;

    public EssenceCauldronBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ESSENCE_TYPE, EssenceType.AUREAL));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new EssenceCauldronBlockEntity(worldPosition, blockState);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return SHAPE_INSIDE;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        return Items.CAULDRON.getDefaultInstance();
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.getBlockEntity(pos) instanceof EssenceStorageBlockEntity blockEntity) {
            return blockEntity.tryFillWithEssence(stack, player, hand);
        }

        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public <T extends BlockEntity> @org.jetbrains.annotations.Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.ESSENCE_CAULDRON.get(), EssenceCauldronBlockEntity::serverTick);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ESSENCE_TYPE);
    }
}
