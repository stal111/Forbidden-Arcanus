package com.stal111.forbidden_arcanus.common.item.enhancer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.EnhancerEffect;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author stal111
 * @since 2023-02-19
 */
public record EnhancerDefinition(Item item, Map<EnhancerTarget, Component> description, List<EnhancerEffect> effects) {

    public static final Codec<EnhancerDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(definition -> {
                return definition.item;
            }),
            Codec.simpleMap(EnhancerTarget.CODEC, ComponentSerialization.CODEC, StringRepresentable.keys(EnhancerTarget.values())).fieldOf("description").forGetter(definition -> {
                return definition.description;
            }),
            EnhancerEffect.DIRECT_CODEC.listOf().fieldOf("effects").forGetter(definition -> {
                return definition.effects;
            })
    ).apply(instance, EnhancerDefinition::new));

    public static final Codec<Holder<EnhancerDefinition>> REFERENCE_CODEC = RegistryFileCodec.create(FARegistries.ENHANCER_DEFINITION, CODEC);

    public static final Codec<EnhancerDefinition> NETWORK_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(definition -> {
                return definition.item;
            }),
            Codec.simpleMap(EnhancerTarget.CODEC, ComponentSerialization.CODEC, StringRepresentable.keys(EnhancerTarget.values())).fieldOf("description").forGetter(definition -> {
                return definition.description;
            })
    ).apply(instance, (item, description) -> {
        return new EnhancerDefinition(item, description, ImmutableList.of());
    }));

    public static EnhancerDefinition create(Item item, Map<EnhancerTarget, Component> description, EnhancerEffect... effects) {
        return new EnhancerDefinition(item, description, List.of(effects));
    }

    public Stream<EnhancerEffect> getEffects(EnhancerTarget target) {
        return this.effects.stream().filter(effect -> effect.getType().target() == target);
    }
}
