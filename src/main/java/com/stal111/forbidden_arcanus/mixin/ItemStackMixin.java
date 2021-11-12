package com.stal111.forbidden_arcanus.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

//    @Shadow public abstract ItemStack copy();

//    @Inject(at = @At(value = "HEAD"), method = "attemptDamageItem", cancellable = true)
//    private void handleIndestructibleEnchantment(int amount, Random rand, ServerPlayerEntity damager, CallbackInfoReturnable<Boolean> info) {
//        if (ItemStackUtils.hasStackEnchantment(this.copy(), ModEnchantments.INDESTRUCTIBLE.get())) {
//            info.setReturnValue(false);
//        }
//    }
}
