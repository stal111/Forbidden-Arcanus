package com.stal111.forbidden_arcanus.core.mixin;

import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author stal111
 * @since 2023-03-24
 */
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow
    public abstract ItemStack getItem();

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;isInvulnerableToBase(Lnet/minecraft/world/damagesource/DamageSource;)Z"), method = "hurtServer")
    private boolean forbiddenArcanus_hurtServer(ItemEntity instance, DamageSource damageSource) {
        return this.getItem().is(ModTags.Items.EXPLOSION_RESISTANT);
    }
}
