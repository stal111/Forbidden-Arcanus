package com.stal111.forbidden_arcanus.client.renderer.block;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

/**
 * Obsidian Skull Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.ObsidianSkullRenderer
 *
 * @author stal111
 * @since 2021-02-11
 */
public class ObsidianSkullRenderer {

    private static MeshDefinition createBaseMesh() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        return meshDefinition;
    }

    public static LayerDefinition createObsidianSkullLayer() {
        return LayerDefinition.create(createBaseMesh(), 32, 16);
    }

    public static LayerDefinition createDetailedObsidianSkullLayer() {
        MeshDefinition meshdefinition = createBaseMesh();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.getChild("head").addOrReplaceChild("layer", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 16);
    }
}
