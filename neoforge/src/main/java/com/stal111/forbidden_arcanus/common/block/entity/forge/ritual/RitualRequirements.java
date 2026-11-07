package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeTier;
import com.stal111.forbidden_arcanus.common.block.entity.forge.TierPredicate;
import com.stal111.forbidden_arcanus.common.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerHelper;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;

import java.util.List;

/**
 * @author stal111
 * @since 2023-04-29
 */
public record RitualRequirements(EssenceSet essences, TierPredicate tier, HolderSet<EnhancerDefinition> enhancers) {

    public static final RitualRequirements NONE = new RitualRequirements(EssenceSet.EMPTY, TierPredicate.ANY, HolderSet.empty());

    public static final MapCodec<RitualRequirements> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            EssenceSet.CODEC.fieldOf("essences").forGetter(RitualRequirements::essences),
            TierPredicate.CODEC.forGetter(RitualRequirements::tier),
            EnhancerDefinition.LIST_CODEC.optionalFieldOf("enhancers", HolderSet.empty()).forGetter(RitualRequirements::enhancers)
    ).apply(instance, RitualRequirements::new));

    public static RitualRequirements.Builder builder(EssenceSet essences) {
        return new RitualRequirements.Builder(essences);
    }

    public boolean checkRequirements(HephaestusForgeTier tier, HolderSet<EnhancerDefinition> enhancers, EssenceSet essences) {
        if (!this.tier.test(tier)) {
            return false;
        }

        if (!this.enhancers.stream().allMatch(enhancers::contains)) {
            return false;
        }

        return essences.hasMoreThan(this.getEffectiveEssences(enhancers));
    }

    public EssenceSet getEffectiveEssences(HolderSet<EnhancerDefinition> enhancers) {
        List<EssenceModifier> modifiers = EnhancerHelper.getEssenceModifiers(enhancers, EnhancerTarget.HEPHAESTUS_FORGE);

        return this.essences.applyModifiers(modifiers);
    }

    public static class Builder {
        private final EssenceSet essences;
        private TierPredicate tier = TierPredicate.ANY;
        private HolderSet<EnhancerDefinition> enhancers = HolderSet.empty();

        private Builder(EssenceSet essences) {
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
            return new RitualRequirements(this.essences, this.tier, this.enhancers);
        }
    }
}
