package com.stal111.forbidden_arcanus.common.item.enhancer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.util.AdditionalCodecs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

/**
 * @author stal111
 * @since 2023-02-19
 */
public record EnhancerDefinition(Item item, Component description, List<EnhancerEffect> effects) {

    public static final Codec<EnhancerDefinition> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(definition -> {
                return definition.item;
            }),
            AdditionalCodecs.COMPONENT.fieldOf("description").forGetter(definition -> {
                return definition.description;
            }),
            EnhancerEffect.DIRECT_CODEC.listOf().fieldOf("effects").forGetter(definition -> {
                return definition.effects;
            })
    ).apply(instance, EnhancerDefinition::new));

    public static final Codec<EnhancerDefinition> NETWORK_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(definition -> {
                return definition.item;
            }),
            AdditionalCodecs.COMPONENT.fieldOf("description").forGetter(definition -> {
                return definition.description;
            })
    ).apply(instance, (item, description) -> {
        return new EnhancerDefinition(item, description, ImmutableList.of());
    }));
}
