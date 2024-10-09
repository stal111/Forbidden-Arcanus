package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;

import java.util.function.IntPredicate;

/**
 * @author stal111
 * @since 30.05.2024
 */
public record TierPredicate(int tier, boolean matchExact) implements IntPredicate {

    private static final String MATCH_EXACT = Util.makeDescriptionId("block", ForbiddenArcanus.location("hephaestus_forge.tier.match_exact"));
    private static final String AT_LEAST = Util.makeDescriptionId("block", ForbiddenArcanus.location("hephaestus_forge.tier.at_least"));

    public static final TierPredicate ANY = new TierPredicate(1, false);

    public static final MapCodec<TierPredicate> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.optionalFieldOf("forge_tier", 1).forGetter(predicate -> {
                return predicate.tier;
            }),
            Codec.BOOL.optionalFieldOf("match_tier_exact", false).forGetter(predicate -> {
                return predicate.matchExact;
            })
    ).apply(instance, TierPredicate::new));

    public static TierPredicate min(int tier) {
        return new TierPredicate(tier, false);
    }

    public static TierPredicate exact(int tier) {
        return new TierPredicate(tier, true);
    }

    @Override
    public boolean test(int value) {
        return this.matchExact ? this.tier == value : this.tier <= value;
    }

    public Component getDescription() {
        return Component.translatable(this.matchExact ? MATCH_EXACT : AT_LEAST, this.tier);
    }
}
