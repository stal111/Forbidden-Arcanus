package com.stal111.forbidden_arcanus.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
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
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author stal111
 * @since 10.09.2023
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Unique
    private static final EquipmentSlot[] FORBIDDEN_ARCANUS$SLOTS = EquipmentSlot.values();

    @Unique
    @Nullable
    private List<EffectGrantingRule> forbiddenArcanus$activeEffectRules;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot pSlot);

    @Inject(at = @At(value = "HEAD"), method = "onEquipItem")
    private void forbiddenArcanus_onEquipItem(EquipmentSlot slot, ItemStack oldStack, ItemStack stack, CallbackInfo ci) {
        this.forbiddenArcanus$activeEffectRules = null;
    }

    @Inject(at = @At(value = "RETURN"), method = "collectEquipmentChanges")
    private void forbiddenArcanus_collectEquipmentChanges(CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cir) {
        if (cir.getReturnValue() != null) {
            this.forbiddenArcanus$activeEffectRules = null;
        }
    }

    @ModifyReturnValue(at = @At(value = "RETURN"), method = "hasEffect")
    public boolean forbiddenArcanus_hasEffect$grantEffects(boolean original, Holder<MobEffect> effect) {
        if (original || !(this.level() instanceof ServerLevel serverLevel)) {
            return original;
        }

        List<EffectGrantingRule> rules = this.forbiddenArcanus$getActiveEffectRules();
        if (rules.isEmpty()) {
            return false;
        }

        for (EffectGrantingRule rule : rules) {
            if (rule.shouldGrantEffect(serverLevel, effect, (LivingEntity) (Object) this)) {
                return true;
            }
        }

        return false;
    }

    @Unique
    private List<EffectGrantingRule> forbiddenArcanus$getActiveEffectRules() {
        List<EffectGrantingRule> cached = this.forbiddenArcanus$activeEffectRules;
        if (cached != null) {
            return cached;
        }

        List<EffectGrantingRule> activeRules = null;

        for (EquipmentSlot slot : FORBIDDEN_ARCANUS$SLOTS) {
            ItemStack stack = this.getItemBySlot(slot);
            if (stack.isEmpty()) {
                continue;
            }

            List<EffectGrantingRule> rules = stack.getOrDefault(ModDataComponents.GRANTS_EFFECTS, List.of());
            for (EffectGrantingRule rule : rules) {
                if (rule.slot().test(slot)) {
                    if (activeRules == null) {
                        activeRules = new ArrayList<>();
                    }
                    activeRules.add(rule);
                }
            }
        }

        cached = activeRules != null ? List.copyOf(activeRules) : List.of();
        this.forbiddenArcanus$activeEffectRules = cached;
        return cached;
    }
}
