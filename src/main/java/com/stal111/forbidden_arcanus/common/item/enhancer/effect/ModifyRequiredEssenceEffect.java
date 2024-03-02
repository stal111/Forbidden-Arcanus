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
public class ModifyRequiredEssenceEffect extends EnhancerEffect implements EssenceModifier {

    public static final Codec<ModifyRequiredEssenceEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EnhancerEffect.conditionsCodec(),
            EssenceType.CODEC.fieldOf("essence_type").forGetter(effect -> {
                return effect.essenceType;
            }),
            Codec.INT.fieldOf("value").forGetter(effect -> {
                return effect.value;
            })
    ).apply(instance, ModifyRequiredEssenceEffect::new));

    private final EssenceType essenceType;
    private final int value;

    public ModifyRequiredEssenceEffect(List<EffectCondition> conditions, EssenceType essenceType, int value) {
        super(conditions);
        this.essenceType = essenceType;
        this.value = value;
    }

    @Override
    public EssenceType getEssenceType() {
        return this.essenceType;
    }

    @Override
    public int getModifiedValue(int originalValue) {
        return Math.max(0, originalValue + this.value);
    }

    @Override
    public EnhancerEffectType<? extends EnhancerEffect> getType() {
        return ModEnhancerEffects.MODIFY_REQUIRED_ESSENCE.get();
    }
}
