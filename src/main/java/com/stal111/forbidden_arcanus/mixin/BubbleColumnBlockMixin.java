package com.stal111.forbidden_arcanus.mixin;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Bubble Column Block Mixin <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.mixin.BubbleColumnBlockMixin
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-21
 */
@Mixin(BubbleColumnBlock.class)
public class BubbleColumnBlockMixin {

    @Inject(at = @At(value = "HEAD"), method = "getColumnState", cancellable = true)
    private static void forbiddenArcanus_getColumnState(BlockState state, CallbackInfoReturnable<BlockState> cir) {
        if (state.is(ModBlocks.SOULLESS_SAND.get())) {
            cir.setReturnValue(Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(BubbleColumnBlock.DRAG_DOWN, false));
        }
    }
}
