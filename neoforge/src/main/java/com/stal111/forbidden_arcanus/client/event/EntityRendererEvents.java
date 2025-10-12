package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.client.model.*;
import com.stal111.forbidden_arcanus.client.renderer.block.BlackHoleRenderer;
import com.stal111.forbidden_arcanus.client.renderer.block.ObsidianSkullRenderer;
import net.minecraft.client.model.geom.PartPose;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * Entity Renderer Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.event.EntityRendererEvents
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-11-28
 */
@EventBusSubscriber
public class EntityRendererEvents {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FAModelLayers.BLACK_HOLE, BlackHoleRenderer::createBlackHoleLayer);
        event.registerLayerDefinition(FAModelLayers.BLACK_HOLE_AURA, BlackHoleRenderer::createAuraLayer);
        event.registerLayerDefinition(FAModelLayers.MAGIC_CIRCLE_OUTER_RING, () -> MagicCircleModel.createLayer(PartPose.offset(0.0F, 0.01F, 0.0F)));
        event.registerLayerDefinition(FAModelLayers.MAGIC_CIRCLE_INNER_RING, () -> MagicCircleModel.createLayer(PartPose.offset(0.0F, 0.02F, 0.0F)));
        event.registerLayerDefinition(FAModelLayers.MAGIC_CIRCLE_VALID_RITUAL_INDICATOR, () -> MagicCircleModel.createLayer(PartPose.ZERO));

        //event.registerLayerDefinition(DracoAurumWingsModel.LAYER_LOCATION, DracoAurumWingsModel::createBodyLayer);
        //event.registerLayerDefinition(DracoAurumHeadModel.LAYER_LOCATION, DracoAurumHeadModel::createBodyLayer);

        event.registerLayerDefinition(FAModelLayers.LOST_SOUL, LostSoulModel::createBodyLayer);
        event.registerLayerDefinition(FAModelLayers.DARK_TRADER, DarkTraderModel::createBodyLayer);
        event.registerLayerDefinition(FAModelLayers.QUANTUM_LIGHT_DOOR, QuantumLightDoorModel::createLayer);
        event.registerLayerDefinition(FAModelLayers.UTREM_JAR_SOULS, UtremJarSoulsModel::createBodyLayer);
        event.registerLayerDefinition(FAModelLayers.QUANTUM_INJECTOR, QuantumInjectorModel::createBodyLayer);

        event.registerLayerDefinition(FAModelLayers.OBSIDIAN_SKULL, ObsidianSkullRenderer::createObsidianSkullLayer);
        event.registerLayerDefinition(FAModelLayers.DETAILED_OBSIDIAN_SKULL, ObsidianSkullRenderer::createDetailedObsidianSkullLayer);
        event.registerLayerDefinition(FAModelLayers.FORBIDDENOMICON, AbstractForbiddenomiconModel::createBodyLayer);
    }
}
