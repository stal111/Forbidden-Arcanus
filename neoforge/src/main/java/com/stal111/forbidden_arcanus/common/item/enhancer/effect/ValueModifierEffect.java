package com.stal111.forbidden_arcanus.common.item.enhancer.effect;

import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectCondition;

import java.util.List;

/**
 * @author stal111
 * @since 02.03.2024
 */
public abstract class ValueModifierEffect<T> extends EnhancerEffect {

    protected ValueModifierEffect(List<EffectCondition> conditions) {
        super(conditions);
    }

    public abstract T getModifiedValue(T originalValue);
}
