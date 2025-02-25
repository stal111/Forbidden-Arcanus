package com.stal111.forbidden_arcanus.core.mixin;

import com.stal111.forbidden_arcanus.common.item.component.EffectGrantingRule;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * @author stal111
 * @since 10.09.2023
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot pSlot);

    @Inject(at = @At(value = "HEAD"), method = "hasEffect", cancellable = true)
    public void forbiddenArcanus_hasEffect$preventEffect(Holder<MobEffect> effect, CallbackInfoReturnable<Boolean> cir) {
        if (this.level() instanceof ServerLevel serverLevel) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = this.getItemBySlot(slot);

                List<EffectGrantingRule> rules = stack.getOrDefault(ModDataComponents.GRANTS_EFFECTS, List.of());

                for (EffectGrantingRule rule : rules) {
                    if (rule.shouldGrantEffect(serverLevel, slot, effect, (LivingEntity) (Object) this)) {
                        cir.setReturnValue(true);
                        return;
                    }
                }
            }
        }
    }
}
