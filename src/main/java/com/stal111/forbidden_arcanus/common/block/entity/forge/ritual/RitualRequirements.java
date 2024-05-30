package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;

import java.util.List;
import java.util.Optional;

/**
 * @author stal111
 * @since 2023-04-29
 */
public record RitualRequirements(int tier, Optional<HolderSet<EnhancerDefinition>> enhancers) {

    public static final RitualRequirements NONE = new RitualRequirements(1, Optional.empty());

    public static final MapCodec<RitualRequirements> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.INT.optionalFieldOf("forge_tier").forGetter(requirements -> {
                return Optional.ofNullable(requirements.tier == 1 ? null : requirements.tier);
            }),
            EnhancerDefinition.LIST_CODEC.optionalFieldOf("enhancers").forGetter(requirements -> {
                return requirements.enhancers;
            })
    ).apply(instance, (requiredTier, requiredEnhancers) -> {
        return new RitualRequirements(requiredTier.orElse(1), requiredEnhancers);
    }));

    public static RitualRequirements of(int tier) {
        return new RitualRequirements(tier, Optional.empty());
    }

    public static RitualRequirements of(int tier, HolderSet<EnhancerDefinition> enhancers) {
        return new RitualRequirements(tier, Optional.of(enhancers));
    }

    public boolean checkRequirements(int forgeTier, List<EnhancerDefinition> enhancers) {
        if (forgeTier < this.tier) {
            return false;
        }

        for (Holder<EnhancerDefinition> enhancer : this.enhancers.orElse(HolderSet.empty())) {
            if (!enhancers.contains(enhancer.value())) {
                return false;
            }
        }

        return true;
    }
}
