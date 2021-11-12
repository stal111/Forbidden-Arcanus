package com.stal111.forbidden_arcanus.mixin;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Item Mixin <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.mixin.ItemMixin
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-08-04
 */
@Mixin(Item.class)
public class ItemMixin {

//    @Inject(at = @At(value = "RETURN"), method = "inventoryTick", cancellable = true)
//    private void forbiddenArcanus_inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected, CallbackInfo ci) {
//        if (stack.getDamage() == 0) {
//            return;
//        }
//
//        CompoundNBT compound = Objects.requireNonNull(stack.getTag());
//
//        if (compound.getBoolean("Repair")) {
//            stack.setDamage(stack.getDamage() - 1);
//
//            if (stack.getDamage() == 0) {
//                compound.remove("Repair");
//            }
//        }
//    }
}
