package com.stal111.forbidden_arcanus.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

/**
 * Cherry Flower Vines <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.CherryFlowerVines
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-04-15
 */
public interface CherryFlowerVines {

    static void entityInside(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        if (entity instanceof LivingEntity livingEntity && livingEntity.onClimbable() && !level.getBlockState(livingEntity.getOnPos()).getMaterial().isSolid()) {
            for (int i = 0; i < 3; i++) {
                BlockPos abovePos = pos.above(i + 1);

                if (!(level.getBlockState(abovePos).getBlock() instanceof CherryFlowerVines)) {
                    break;
                }

                if (i == 2) {
                    level.destroyBlock(abovePos, true, livingEntity);
                }
            }
        }
    }
}
