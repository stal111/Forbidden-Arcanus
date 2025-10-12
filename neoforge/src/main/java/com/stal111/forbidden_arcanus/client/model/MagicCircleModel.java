package com.stal111.forbidden_arcanus.client.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.core.Direction;

import java.util.EnumSet;

/**
 * Magic Circle Model <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.model.MagicCircleModel
 *
 * @author stal111
 * @since 2021-12-21
 */
public class MagicCircleModel {

    public static LayerDefinition createLayer(PartPose partPose) {
        MeshDefinition meshDefinition = new MeshDefinition();
        meshDefinition.getRoot().addOrReplaceChild("outer_ring", CubeListBuilder.create().texOffs(-128, 0).addBox(-64.0F, 0.0F, -64.0F, 128.0F, 0.0F, 128.0F, EnumSet.of(Direction.DOWN)), partPose);
        return LayerDefinition.create(meshDefinition, 128, 128);
    }
}
