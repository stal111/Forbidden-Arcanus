package com.stal111.forbidden_arcanus.mixin;

import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.init.other.ModItemModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract ItemStack copy();

    @Shadow public abstract boolean hasCustomHoverName();

    @Inject(at = @At(value = "HEAD"), method = "hurt", cancellable = true)
    private void forbiddenArcanus_hurt(int i, Random random, ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
        if (ModifierHelper.getModifier(this.copy()) == ModItemModifiers.ETERNAL.get()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At(value = "RETURN"), method = "getHoverName", cancellable = true)
    private void forbiddenArcanus_getHoverName(CallbackInfoReturnable<Component> cir) {
        ItemModifier modifier = ModifierHelper.getModifier(this.copy());

        if (!this.hasCustomHoverName() && modifier != null) {
            cir.setReturnValue(modifier.getComponent().append(" ").append(cir.getReturnValue()));
        }
    }
}
