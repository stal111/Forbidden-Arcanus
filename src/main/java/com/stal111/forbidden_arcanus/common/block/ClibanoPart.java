package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.core.init.other.ModPOITypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Clibano Part <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ClibanoPart
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2022-05-24
 */
public interface ClibanoPart extends EntityBlock {

    default void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState) {
        if (state.is(newState.getBlock())) {
            return;
        }

        this.findMainPos(level, pos).ifPresent(blockPos -> {
            var positions = BlockPos.betweenClosedStream(blockPos.relative(Direction.DOWN).relative(Direction.NORTH).relative(Direction.WEST), blockPos.relative(Direction.UP).relative(Direction.SOUTH).relative(Direction.EAST));

            if (positions.noneMatch(pos1 -> pos1.equals(pos))) {
                return;
            }

            level.removeBlock(blockPos, false);

            level.destroyBlock(pos, true);
        });

        level.removeBlockEntity(pos);
    }

    default InteractionResult openScreen(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.SUCCESS;
        }

        this.findMainPos(level, pos).ifPresent(blockPos -> {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if (blockEntity instanceof ClibanoMainBlockEntity clibanoMainBlockEntity) {
                player.openMenu(clibanoMainBlockEntity);
            }
        });

        return InteractionResult.CONSUME;
    }

    default Optional<BlockPos> findMainPos(@Nonnull Level level, @Nonnull BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel.getPoiManager().find(poiType -> poiType.value() == ModPOITypes.CLIBANO_MAIN_PART.get(), blockPos -> !level.getBlockState(blockPos).isAir(), pos, 2, PoiManager.Occupancy.ANY);
        }
        return Optional.empty();
    }
}
