package com.stal111.forbidden_arcanus.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

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

    @Inject(at = @At(value = "RETURN"), method = "inventoryTick")
    private void forbiddenArcanus_inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (stack.getDamageValue() == 0) {
            return;
        }

        CompoundTag compound = Objects.requireNonNull(stack.getTag());

        if (compound.getBoolean("Repair")) {
            stack.setDamageValue(stack.getDamageValue() - 1);

            if (stack.getDamageValue() == 0) {
                compound.remove("Repair");
            }
        }
    }
}
