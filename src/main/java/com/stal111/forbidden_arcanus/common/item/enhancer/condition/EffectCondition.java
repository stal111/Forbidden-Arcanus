package com.stal111.forbidden_arcanus.common.item.enhancer.condition;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.Level;

/**
 * @author stal111
 * @since 2023-03-03
 */
public abstract class EffectCondition {

    public static final Codec<EffectCondition> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(FARegistries.ENHANCER_EFFECT_CONDITION_REGISTRY::byNameCodec)
            .dispatch(EffectCondition::getType, EffectConditionType::codec);

    public abstract boolean test(Level level);

    public abstract EffectConditionType<? extends EffectCondition> getType();
}
