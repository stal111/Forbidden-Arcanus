package com.stal111.forbidden_arcanus.core.mixin;

import com.stal111.forbidden_arcanus.common.item.modifier.DemolishingModifierBlockBreaker;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author stal111
 * @since 2023-04-03
 */
@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Shadow @Final protected ServerPlayer player;

    @Shadow protected ServerLevel level;

    @Shadow private int gameTicks;

    @Shadow private int lastSentState;

    @Inject(at = @At(value = "HEAD"), method = "incrementDestroyProgress")
    public void forbiddenArcanus_incrementDestroyProgress(BlockState state, BlockPos pos, int destroyProgressStart, CallbackInfoReturnable<Float> cir) {
        if (ModifierHelper.getModifier(this.player.getMainHandItem()) == ModItemModifiers.DEMOLISHING.get()) {
            int i = this.gameTicks - destroyProgressStart;
            float f = state.getDestroyProgress(this.player, this.player.level, pos) * (float) (i + 1);
            int progress = (int) (f * 10.0F);

            if (progress != this.lastSentState) {
                var blockBreaker = DemolishingModifierBlockBreaker.getOrCreate(this.level, pos, state, this.player);

                blockBreaker.update(progress);
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "destroyBlock")
    public void forbiddenArcanus_destroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        DemolishingModifierBlockBreaker.get(this.level, this.player).ifPresent(breaker -> {
            breaker.breakBlocks(this.player);
        });
    }
}
