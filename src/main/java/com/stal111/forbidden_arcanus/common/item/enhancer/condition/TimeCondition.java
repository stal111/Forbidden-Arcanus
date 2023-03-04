package com.stal111.forbidden_arcanus.common.item.enhancer.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.init.other.ModEnhancerEffectConditions;
import net.minecraft.world.level.Level;

/**
 * @author stal111
 * @since 2023-03-03
 */
public class TimeCondition extends EffectCondition {

    public static final Codec<TimeCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("start").forGetter(condition -> {
                return condition.startTime;
            }),
            Codec.INT.fieldOf("end").forGetter(condition -> {
                return condition.endTime;
            })
    ).apply(instance, TimeCondition::new));

    private final int startTime;
    private final int endTime;

    public TimeCondition(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;

        if (startTime >= endTime) {
            throw new IllegalArgumentException("The ending time needs to be higher than the starting time!");
        }
    }

    @Override
    public boolean test(Level level) {
        long dayTime = level.getDayTime();

        return dayTime >= this.startTime && dayTime <= this.endTime;
    }

    @Override
    public EffectConditionType<? extends EffectCondition> getType() {
        return ModEnhancerEffectConditions.TIME.get();
    }
}
