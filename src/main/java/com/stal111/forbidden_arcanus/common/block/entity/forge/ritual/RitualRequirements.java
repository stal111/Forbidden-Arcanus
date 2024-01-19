package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.core.Holder;

import java.util.List;
import java.util.Optional;

/**
 * @author stal111
 * @since 2023-04-29
 */
public record RitualRequirements(int tier, List<Holder<EnhancerDefinition>> enhancers) {

    public static final Codec<RitualRequirements> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.optionalFieldOf("forge_tier").forGetter(requirements -> {
                return Optional.ofNullable(requirements.tier == 1 ? null : requirements.tier);
            }),
            EnhancerDefinition.REFERENCE_CODEC.listOf().optionalFieldOf("enhancers").forGetter(requirements -> {
                return Optional.ofNullable(requirements.enhancers.isEmpty() ? null : requirements.enhancers);
            })
    ).apply(instance, (requiredTier, requiredEnhancers) -> {
        return new RitualRequirements(requiredTier.orElse(1), requiredEnhancers.orElse(List.of()));
    }));

    public boolean checkRequirements(int forgeTier, List<EnhancerDefinition> enhancers) {
        if (forgeTier < this.tier) {
            return false;
        }

        for (Holder<EnhancerDefinition> enhancer : this.enhancers) {
            if (!enhancers.contains(enhancer.value())) {
                return false;
            }
        }

        return true;
    }
}
