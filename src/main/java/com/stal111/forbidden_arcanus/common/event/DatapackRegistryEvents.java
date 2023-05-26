package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DataPackRegistryEvent;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class DatapackRegistryEvents {

    @SubscribeEvent
    public void newDatapackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(FARegistries.RITUAL, Ritual.CODEC, Ritual.NETWORK_CODEC);
        event.dataPackRegistry(FARegistries.ENHANCER_DEFINITION, EnhancerDefinition.CODEC, EnhancerDefinition.NETWORK_CODEC);
        event.dataPackRegistry(FARegistries.FORGE_INPUT, HephaestusForgeInput.DIRECT_CODEC);
    }
}
