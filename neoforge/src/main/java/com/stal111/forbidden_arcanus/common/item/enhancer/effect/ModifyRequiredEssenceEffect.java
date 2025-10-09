package com.stal111.forbidden_arcanus.common.item.enhancer.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.other.ModEnhancerEffects;

/**
 * @author stal111
 * @since 2023-02-19
 */
public record ModifyRequiredEssenceEffect(EssenceType essenceType, int value) implements ValueModifierEffect<Integer>, EssenceModifier {

    public static final MapCodec<ModifyRequiredEssenceEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("essence_type").forGetter(ModifyRequiredEssenceEffect::essenceType),
            Codec.INT.fieldOf("value").forGetter(ModifyRequiredEssenceEffect::value)
    ).apply(instance, ModifyRequiredEssenceEffect::new));

    @Override
    public Integer getModifiedValue(Integer originalValue) {
        return Math.max(0, originalValue + this.value);
    }

    @Override
    public EssenceType getEssenceType() {
        return this.essenceType;
    }

    @Override
    public EnhancerEffectType<? extends EnhancerEffect> getType() {
        return ModEnhancerEffects.MODIFY_REQUIRED_ESSENCE.get();
    }
}
