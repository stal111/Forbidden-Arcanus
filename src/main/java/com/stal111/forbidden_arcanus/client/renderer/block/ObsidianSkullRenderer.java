package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.block.entity.ObsidianSkullBlockEntity;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import javax.annotation.Nonnull;

/**
 * Obsidian Skull Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.ObsidianSkullRenderer
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-11
 */
public class ObsidianSkullRenderer implements BlockEntityRenderer<ObsidianSkullBlockEntity> {


    public ObsidianSkullRenderer(BlockEntityRendererProvider.Context context) {
    }

    private static MeshDefinition createBaseMesh() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        return meshDefinition;
    }

    public static LayerDefinition createObsidianSkullLayer() {
        return LayerDefinition.create(createBaseMesh(), 32, 16);
    }

    public static LayerDefinition createEternalObsidianSkullLayer() {
        MeshDefinition meshdefinition = createBaseMesh();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.getChild("head").addOrReplaceChild("layer", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 16);
    }

    @Override
    public void render(ObsidianSkullBlockEntity blockEntity, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    }
}
