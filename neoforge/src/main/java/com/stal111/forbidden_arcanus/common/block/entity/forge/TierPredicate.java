package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Util;

import java.util.function.Predicate;

public record TierPredicate(HephaestusForgeTier tier, boolean matchExact) implements Predicate<HephaestusForgeTier> {

    private static final String MATCH_EXACT = Util.makeDescriptionId("block", ForbiddenArcanus.identifier("hephaestus_forge.tier.match_exact"));
    private static final String AT_LEAST = Util.makeDescriptionId("block", ForbiddenArcanus.identifier("hephaestus_forge.tier.at_least"));

    public static final TierPredicate ANY = new TierPredicate(HephaestusForgeTier.ONE, false);

    public static final MapCodec<TierPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            HephaestusForgeTier.CODEC.optionalFieldOf("forge_tier", HephaestusForgeTier.ONE).forGetter(TierPredicate::tier),
            Codec.BOOL.optionalFieldOf("match_tier_exact", false).forGetter(TierPredicate::matchExact)
    ).apply(instance, TierPredicate::new));

    public static TierPredicate min(HephaestusForgeTier tier) {
        return new TierPredicate(tier, false);
    }

    public static TierPredicate exact(HephaestusForgeTier tier) {
        return new TierPredicate(tier, true);
    }

    @Override
    public boolean test(HephaestusForgeTier value) {
        return this.matchExact ? this.tier == value : value.compareTo(this.tier) >= 0;
    }

    public Component getDescription() {
        return Component.translatable(this.matchExact ? MATCH_EXACT : AT_LEAST, this.tier);
    }
}
