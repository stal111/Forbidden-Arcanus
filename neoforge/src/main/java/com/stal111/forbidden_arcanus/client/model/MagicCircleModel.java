package com.stal111.forbidden_arcanus.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

/**
 * Magic Circle Model <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.model.MagicCircleModel
 *
 * @author stal111
 * @since 2021-12-21
 */
public record MagicCircleModel(ModelPart outerRing, ModelPart innerRing, ModelPart validRitualIndicator) {

    public MagicCircleModel(BlockEntityRendererProvider.Context context) {
        this(context.bakeLayer(FAModelLayers.MAGIC_CIRCLE_OUTER_RING), context.bakeLayer(FAModelLayers.MAGIC_CIRCLE_INNER_RING_LAYER), context.bakeLayer(FAModelLayers.MAGIC_CIRCLE_VALID_RITUAL_INDICATOR));
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        meshDefinition.getRoot().addOrReplaceChild("outer_ring", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 10, 10);
    }
}
