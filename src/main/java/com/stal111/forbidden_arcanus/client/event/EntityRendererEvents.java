package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.*;
import com.stal111.forbidden_arcanus.client.renderer.block.BlackHoleRenderer;
import com.stal111.forbidden_arcanus.client.renderer.block.ObsidianSkullRenderer;
import com.stal111.forbidden_arcanus.common.entity.ModBoat;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
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
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class EntityRendererEvents {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BlackHoleRenderer.BLACK_HOLE_LAYER, BlackHoleRenderer::createHoleLayer);
        event.registerLayerDefinition(BlackHoleRenderer.BLACK_HOLE_AURA_LAYER, BlackHoleRenderer::createAuraLayer);
        event.registerLayerDefinition(MagicCircleModel.OUTER_RING_LAYER, MagicCircleModel::createLayer);
        event.registerLayerDefinition(MagicCircleModel.INNER_RING_LAYER, MagicCircleModel::createLayer);
        event.registerLayerDefinition(MagicCircleModel.VALID_RITUAL_INDICATOR, MagicCircleModel::createLayer);

        //event.registerLayerDefinition(DracoAurumWingsModel.LAYER_LOCATION, DracoAurumWingsModel::createBodyLayer);
        //event.registerLayerDefinition(DracoAurumHeadModel.LAYER_LOCATION, DracoAurumHeadModel::createBodyLayer);

        event.registerLayerDefinition(LostSoulModel.LAYER_LOCATION, LostSoulModel::createBodyLayer);
        event.registerLayerDefinition(DarkTraderModel.LAYER_LOCATION, DarkTraderModel::createBodyLayer);
        event.registerLayerDefinition(QuantumLightDoorModel.LAYER_LOCATION, QuantumLightDoorModel::createLayer);
        event.registerLayerDefinition(UtremJarSoulsModel.LAYER_LOCATION, UtremJarSoulsModel::createBodyLayer);
        event.registerLayerDefinition(QuantumInjectorModel.LAYER_LOCATION, QuantumInjectorModel::createBodyLayer);

        event.registerLayerDefinition(FAModelLayers.OBSIDIAN_SKULL_LAYER, ObsidianSkullRenderer::createObsidianSkullLayer);
        event.registerLayerDefinition(FAModelLayers.DETAILED_OBSIDIAN_SKULL_LAYER, ObsidianSkullRenderer::createDetailedObsidianSkullLayer);
        event.registerLayerDefinition(AbstractForbiddenomiconModel.LAYER_LOCATION, AbstractForbiddenomiconModel::createBodyLayer);

        for (ModBoat.Type type : ModBoat.Type.values()) {
            event.registerLayerDefinition(new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, type.getModelLocation()), "main"), BoatModel::createBodyModel);
            event.registerLayerDefinition(new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, type.getChestModelLocation()), "main"), ChestBoatModel::createBodyModel);
        }
    }
}
