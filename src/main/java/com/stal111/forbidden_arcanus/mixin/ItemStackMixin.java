package com.stal111.forbidden_arcanus.mixin;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.valhelsia.valhelsia_core.util.ItemStackUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract ItemStack copy();

    @Inject(at = @At(value = "HEAD"), method = "attemptDamageItem", cancellable = true)
    private void handleIndestructibleEnchantment(int amount, Random rand, ServerPlayerEntity damager, CallbackInfoReturnable<Boolean> info) {
        if (ItemStackUtils.hasStackEnchantment(this.copy(), ModEnchantments.INDESTRUCTIBLE.get())) {
            info.setReturnValue(false);
        }
    }
}
