package com.stal111.forbidden_arcanus.core.mixin;

import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author stal111
 * @since 10.09.2023
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot pSlot);

    @Inject(at = @At(value = "HEAD"), method = "hasEffect", cancellable = true)
    public void forbiddenArcanus_hasEffect$preventFireDamage(MobEffect effect, CallbackInfoReturnable<Boolean> cir) {
        if (effect == MobEffects.FIRE_RESISTANCE) {
            ItemStack stack = this.getItemBySlot(EquipmentSlot.HEAD);

            if (stack.getItem() instanceof ObsidianSkullItem skullItem && skullItem.getType().shouldProtect((LivingEntity) (Object) this)) {
                cir.setReturnValue(true);
            }
        }
    }
}
