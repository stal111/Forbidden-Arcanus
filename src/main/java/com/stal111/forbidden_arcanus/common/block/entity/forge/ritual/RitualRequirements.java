package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.TierPredicate;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * @author stal111
 * @since 2023-04-29
 */
public record RitualRequirements(TierPredicate tier, Optional<HolderSet<EnhancerDefinition>> enhancers) {

    public static final RitualRequirements NONE = new RitualRequirements(TierPredicate.ANY, Optional.empty());

    public static final MapCodec<RitualRequirements> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            TierPredicate.CODEC.forGetter(requirements -> {
                return requirements.tier;
            }),
            EnhancerDefinition.LIST_CODEC.optionalFieldOf("enhancers").forGetter(requirements -> {
                return requirements.enhancers;
            })
    ).apply(instance, RitualRequirements::new));

    public static RitualRequirements.Builder builder() {
        return new RitualRequirements.Builder();
    }

    public boolean checkRequirements(int forgeTier, List<EnhancerDefinition> enhancers) {
        if (!this.tier.test(forgeTier)) {
            return false;
        }

        for (Holder<EnhancerDefinition> enhancer : this.enhancers.orElse(HolderSet.empty())) {
            if (!enhancers.contains(enhancer.value())) {
                return false;
            }
        }

        return true;
    }

    public static class Builder {
        private TierPredicate tier = TierPredicate.ANY;
        private @Nullable HolderSet<EnhancerDefinition> enhancers;

        public Builder tier(TierPredicate tier) {
            this.tier = tier;
            return this;
        }

        public Builder enhancer(Holder<EnhancerDefinition> enhancer) {
            this.enhancers = HolderSet.direct(enhancer);
            return this;
        }

        public Builder enhancers(HolderSet<EnhancerDefinition> enhancers) {
            this.enhancers = enhancers;
            return this;
        }

        public RitualRequirements build() {
            return new RitualRequirements(this.tier, Optional.ofNullable(this.enhancers));
        }
    }
}
