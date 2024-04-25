package com.stal111.forbidden_arcanus.common.item.enhancer.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectCondition;
import com.stal111.forbidden_arcanus.core.init.other.ModEnhancerEffects;

import java.util.List;

/**
 * @author stal111
 * @since 02.03.2024
 */
public class MultiplySoulDurationEffect extends ValueModifierEffect<Integer> {

    public static final MapCodec<MultiplySoulDurationEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EnhancerEffect.conditionsCodec(),
            Codec.doubleRange(0.0D, 10.0D).fieldOf("multiplier").forGetter(effect -> {
                return effect.multiplier;
            })
    ).apply(instance, MultiplySoulDurationEffect::new));

    private final double multiplier;

    public MultiplySoulDurationEffect(List<EffectCondition> conditions, double multiplier) {
        super(conditions);
        this.multiplier = multiplier;
    }

    @Override
    public Integer getModifiedValue(Integer originalValue) {
        return (int) (originalValue * this.multiplier);
    }

    @Override
    public EnhancerEffectType<? extends EnhancerEffect> getType() {
        return ModEnhancerEffects.MULTIPLY_SOUL_DURATION.get();
    }
}
