package com.stal111.forbidden_arcanus.core.mixin;

import com.stal111.forbidden_arcanus.common.item.modifier.DemolishingModifierBlockBreaker;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author stal111
 * @since 2023-04-05
 */
@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {

    @Shadow @Final private Minecraft minecraft;

    @Shadow private float destroyProgress;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;destroyBlockProgress(ILnet/minecraft/core/BlockPos;I)V"), method = "continueDestroyBlock")
    public void forbiddenArcanus_continueDestroyBlock$updateBlockBreaker(BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> cir) {
        if (ModifierHelper.getModifier(this.minecraft.player.getMainHandItem()) == ModItemModifiers.DEMOLISHING.get()) {
            var blockBreaker = DemolishingModifierBlockBreaker.getOrCreate(this.minecraft.level, pos, this.minecraft.level.getBlockState(pos), this.minecraft.player);

            blockBreaker.update((int) (this.destroyProgress * 10.0F));
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;startPrediction(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/multiplayer/prediction/PredictiveAction;)V"), method = "continueDestroyBlock")
    public void forbiddenArcanus_continueDestroyBlock$finishBlockBreaker(BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> cir) {
        DemolishingModifierBlockBreaker.get(this.minecraft.level, this.minecraft.player).ifPresent(DemolishingModifierBlockBreaker::stop);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;destroyBlockProgress(ILnet/minecraft/core/BlockPos;I)V"), method = "stopDestroyBlock")
    public void forbiddenArcanus_stopDestroyBlock(CallbackInfo ci) {
        DemolishingModifierBlockBreaker.get(this.minecraft.level, this.minecraft.player).ifPresent(DemolishingModifierBlockBreaker::stop);
    }
}
