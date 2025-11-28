package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author stal111
 * @since 2023-04-29
 */
public record RitualRequirements(int tier, @Nullable List<ResourceLocation> enhancers) {

    public static final Codec<RitualRequirements> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.optionalFieldOf("forge_tier", 1).forGetter(RitualRequirements::tier),
            ResourceLocation.CODEC.listOf().optionalFieldOf("enhancers").forGetter(definition -> {
                return Optional.ofNullable(definition.enhancers);
            })
    ).apply(instance, (tier, enhancers) -> {
        return new RitualRequirements(tier, enhancers.orElse(List.of()));
    }));

    public boolean checkRequirements(int forgeTier, List<EnhancerDefinition> enhancers) {
        if (forgeTier < this.tier) {
            return false;
        }

        for (EnhancerDefinition enhancer : enhancers) {
            if (!enhancers.contains(enhancer)) {
                return false;
            }
        }

        return true;
    }
}
