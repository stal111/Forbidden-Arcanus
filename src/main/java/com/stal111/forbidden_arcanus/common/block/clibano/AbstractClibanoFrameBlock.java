package com.stal111.forbidden_arcanus.common.block.clibano;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFrameBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.core.init.other.ModPOITypes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author stal111
 * @since 07.02.2024
 */
public abstract class AbstractClibanoFrameBlock extends Block implements EntityBlock {

    private static final ResourceLocation DYNAMIC_DROP_ID = new ResourceLocation("dynamic_block");


    public AbstractClibanoFrameBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ClibanoFrameBlockEntity(pos, state);
    }

    public abstract DirectionProperty getFacingProperty();

    /**
     * Updates the appearance of the block
     *
     * @param state    the block state
     * @param isLit    if the clibano is lit
     * @param fireType the fire type
     * @return the updated block state
     */
    public BlockState updateAppearance(BlockState state, boolean isLit, ClibanoFireType fireType) {
        return state;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }

        serverLevel.getPoiManager().find(poiType -> poiType.value() == ModPOITypes.CLIBANO_MAIN_PART.get(), blockPos -> !level.getBlockState(blockPos).isAir(), pos, 2, PoiManager.Occupancy.ANY).ifPresent(blockPos -> {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof ClibanoMainBlockEntity clibanoMainBlockEntity) {
                player.openMenu(clibanoMainBlockEntity);
            }
        });

        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.is(newState.getBlock())) {
            return;
        }

        if (level.getBlockEntity(pos) instanceof ClibanoFrameBlockEntity clibanoFrameBlockEntity) {
            BlockPos mainPos = clibanoFrameBlockEntity.getFrameData().mainPos();

            level.removeBlock(mainPos, false);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation rotation) {
        return state.setValue(this.getFacingProperty(), rotation.rotate(state.getValue(this.getFacingProperty())));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(this.getFacingProperty(), mirror.mirror(state.getValue(this.getFacingProperty())));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        BlockEntity blockentity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

        if (blockentity instanceof ClibanoFrameBlockEntity clibanoFrameBlockEntity) {
            builder.withDynamicDrop(DYNAMIC_DROP_ID, consumer -> consumer.accept(new ItemStack(clibanoFrameBlockEntity.getFrameData().replaceState().getBlock())));
        }

        return super.getDrops(state, builder);
    }
}
