package com.stal111.forbidden_arcanus.client.model;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

/**
 * Magic Circle Model <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.model.MagicCircleModel
 *
 * @author stal111
 * @since 2021-12-21
 */
public record MagicCircleModel(ModelPart outerRing, ModelPart innerRing, ModelPart validRitualIndicator) {

    public static final ModelLayerLocation OUTER_RING_LAYER = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "magic_circle"), "outer_ring");
    public static final ModelLayerLocation INNER_RING_LAYER = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "magic_circle"), "inner_ring");
    public static final ModelLayerLocation VALID_RITUAL_INDICATOR = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "magic_circle"), "valid_ritual_indicator");


    public MagicCircleModel(BlockEntityRendererProvider.Context context) {
        this(context.bakeLayer(OUTER_RING_LAYER), context.bakeLayer(INNER_RING_LAYER), context.bakeLayer(VALID_RITUAL_INDICATOR));
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        meshDefinition.getRoot().addOrReplaceChild("outer_ring", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 10, 10);
    }
}
