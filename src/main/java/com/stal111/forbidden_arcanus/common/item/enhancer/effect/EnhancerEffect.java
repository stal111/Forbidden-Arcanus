package com.stal111.forbidden_arcanus.common.item.enhancer.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectCondition;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * @author stal111
 * @since 2023-02-18
 */
public abstract class EnhancerEffect {

    public static final Codec<EnhancerEffect> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(FARegistries.ENHANCER_EFFECT_REGISTRY::byNameCodec)
            .dispatch(EnhancerEffect::getType, EnhancerEffectType::codec);

    private final List<EffectCondition> conditions;

    protected EnhancerEffect(List<EffectCondition> conditions) {
        this.conditions = conditions;
    }

    public static <S extends EnhancerEffect> RecordCodecBuilder<S, List<EffectCondition>> conditionsCodec() {
        return EffectCondition.DIRECT_CODEC.listOf().fieldOf("conditions").forGetter(EnhancerEffect::getConditions);
    }

    public boolean checkConditions(Level level) {
        for (EffectCondition condition : this.conditions) {
            if (!condition.test(level)) {
                return false;
            }
        }
        return true;
    }

    public List<EffectCondition> getConditions() {
        return this.conditions;
    }

    public abstract EnhancerEffectType<? extends EnhancerEffect> getType();
}
