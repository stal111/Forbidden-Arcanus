package com.stal111.forbidden_arcanus.core.mixin;

import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author stal111
 * @since 2023-03-24
 */
@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"), method = "hurt")
    private boolean forbiddenArcanus_hurt(ItemStack stack, Item item) {
        return stack.is(ModTags.Items.EXPLOSION_RESISTANT);
    }
}
