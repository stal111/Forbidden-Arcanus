package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.TierPredicate;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author stal111
 * @since 2023-04-29
 */
public record RitualRequirements(EssencesDefinition essences, TierPredicate tier, Optional<HolderSet<EnhancerDefinition>> enhancers) {

    public static final RitualRequirements NONE = new RitualRequirements(EssencesDefinition.EMPTY, TierPredicate.ANY, Optional.empty());

    public static final MapCodec<RitualRequirements> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            EssencesDefinition.CODEC.fieldOf("essences").forGetter(ritual -> {
                return ritual.essences;
            }),
            TierPredicate.CODEC.forGetter(requirements -> {
                return requirements.tier;
            }),
            EnhancerDefinition.LIST_CODEC.optionalFieldOf("enhancers").forGetter(requirements -> {
                return requirements.enhancers;
            })
    ).apply(instance, RitualRequirements::new));

    public static RitualRequirements.Builder builder(EssencesDefinition essences) {
        return new RitualRequirements.Builder(essences);
    }

    public boolean checkRequirements(int forgeTier, HolderSet<EnhancerDefinition> enhancers) {
        if (!this.tier.test(forgeTier)) {
            return false;
        }

        for (Holder<EnhancerDefinition> enhancer : this.enhancers.orElse(HolderSet.empty())) {
            if (!enhancers.contains(enhancer)) {
                return false;
            }
        }

        return true;
    }

    public static class Builder {
        private final EssencesDefinition essences;
        private TierPredicate tier = TierPredicate.ANY;
        private @Nullable HolderSet<EnhancerDefinition> enhancers;

        private Builder(EssencesDefinition essences) {
            this.essences = essences;
        }

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
            return new RitualRequirements(this.essences, this.tier, Optional.ofNullable(this.enhancers));
        }
    }
}
