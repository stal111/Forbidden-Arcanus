package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.desk.ResearchDeskBlockEntity;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 30.10.2023
 */
public class ResearchDeskBlock extends DeskBlock implements EntityBlock {

    public ResearchDeskBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ResearchDeskBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
        if (player.isShiftKeyDown()) {
            BlockState newState = ModBlocks.DESK.get().defaultBlockState()
                    .setValue(FACING, state.getValue(FACING))
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED));

            level.setBlockAndUpdate(pos, newState);

            ItemStack forbiddenomicon = new ItemStack(ModBlocks.FORBIDDENOMICON.get());

            if (!player.addItem(forbiddenomicon)) {
                player.drop(forbiddenomicon, false);
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof ResearchDeskBlockEntity blockEntity) {
            player.openMenu(blockEntity);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.RESEARCH_DESK.get(), ResearchDeskBlockEntity::clientTick);
        }
        return null;
    }
}
