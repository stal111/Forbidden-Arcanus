package com.stal111.forbidden_arcanus.common.item.enhancer.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.other.ModEnhancerEffects;

/**
 * @author stal111
 * @since 2023-02-19
 */
public record MultiplyRequiredEssenceEffect(EssenceType essenceType, double multiplier) implements ValueModifierEffect<Integer>, EssenceModifier {

    public static final MapCodec<MultiplyRequiredEssenceEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("essence_type").forGetter(MultiplyRequiredEssenceEffect::essenceType),
            Codec.DOUBLE.fieldOf("multiplier").forGetter(MultiplyRequiredEssenceEffect::multiplier)
    ).apply(instance, MultiplyRequiredEssenceEffect::new));

    @Override
    public Integer getModifiedValue(Integer originalValue) {
        return (int) (originalValue * this.multiplier);
    }

    @Override
    public EssenceType getEssenceType() {
        return this.essenceType;
    }

    @Override
    public EnhancerEffectType<? extends EnhancerEffect> getType() {
        return ModEnhancerEffects.MULTIPLY_REQUIRED_ESSENCE.get();
    }
}
