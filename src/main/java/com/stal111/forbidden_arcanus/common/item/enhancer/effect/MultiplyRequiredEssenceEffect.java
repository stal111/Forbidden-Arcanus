package com.stal111.forbidden_arcanus.common.item.enhancer.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectCondition;
import com.stal111.forbidden_arcanus.core.init.other.ModEnhancerEffects;

import java.util.List;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class MultiplyRequiredEssenceEffect extends ValueModifierEffect<Integer> implements EssenceModifier {

    public static final Codec<MultiplyRequiredEssenceEffect> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            EnhancerEffect.conditionsCodec(),
            EssenceType.CODEC.fieldOf("essence_type").forGetter(effect -> {
                return effect.essenceType;
            }),
            Codec.DOUBLE.fieldOf("multiplier").forGetter(effect -> {
                return effect.multiplier;
            })
    ).apply(instance, MultiplyRequiredEssenceEffect::new));

    private final EssenceType essenceType;
    private final double multiplier;

    public MultiplyRequiredEssenceEffect(List<EffectCondition> conditions, EssenceType essenceType, double multiplier) {
        super(conditions);
        this.essenceType = essenceType;
        this.multiplier = multiplier;
    }

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
