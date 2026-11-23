package com.stal111.forbidden_arcanus.core.mixin.client;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public abstract class GuiMixin {

    //TODO
//    @Shadow @Nullable protected abstract Player getCameraPlayer();
//
//    @Inject(method = "extractCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getAttackStrengthScale(F)F"), cancellable = true)
//    private void forbiddenArcanus_renderCrosshair$hideAttackIndicator(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
//        Player player = this.getCameraPlayer();
//
//        if (player != null && player.getMainHandItem().getItem() instanceof MagicWandItem) {
//            ci.cancel();
//        }
//    }
}
