package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.research.Constellation;
import com.stal111.forbidden_arcanus.common.research.Knowledge;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class RegistryEvents {

    @SubscribeEvent
    public void newDatapackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(FARegistries.RITUAL, Ritual.DIRECT_CODEC, Ritual.NETWORK_CODEC);
        event.dataPackRegistry(FARegistries.ENHANCER_DEFINITION, EnhancerDefinition.CODEC, EnhancerDefinition.NETWORK_CODEC);
        event.dataPackRegistry(FARegistries.KNOWLEDGE, Knowledge.DIRECT_CODEC, Knowledge.DIRECT_CODEC);
        event.dataPackRegistry(FARegistries.CONSTELLATION, Constellation.CODEC, Constellation.CODEC);
        event.dataPackRegistry(FARegistries.RESIDUE_TYPE, ResidueType.DIRECT_CODEC, ResidueType.DIRECT_CODEC);
        event.dataPackRegistry(FARegistries.MAGIC_CIRCLE, MagicCircleType.DIRECT_CODEC, MagicCircleType.DIRECT_CODEC);
    }

    @SubscribeEvent
    public void newRegistry(NewRegistryEvent event) {
        event.register(FARegistries.RITUAL_RESULT_TYPE_REGISTRY);
        event.register(FARegistries.FORGE_INPUT_REGISTRY);
        event.register(FARegistries.ITEM_MODIFIER_REGISTRY);
        event.register(FARegistries.ENHANCER_EFFECT_REGISTRY);
        event.register(FARegistries.ENHANCER_EFFECT_CONDITION_REGISTRY);
        event.register(FARegistries.DARK_TRADER_VARIANT_REGISTRY);
        event.register(FARegistries.MUNDABITUR_INTERACTION_REGISTRY);
        event.register(FARegistries.PEDESTAL_EFFECT_REGISTRY);
    }
}
