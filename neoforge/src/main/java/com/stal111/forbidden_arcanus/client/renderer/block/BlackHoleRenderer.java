package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.FAModelLayers;
import com.stal111.forbidden_arcanus.client.renderer.block.state.BlackHoleRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.BlackHoleBlockEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.EnumSet;

/**
 * Black Hole Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.BlackHoleRenderer
 *
 * @author stal111
 * @version 2.0.0
 */
public class BlackHoleRenderer implements BlockEntityRenderer<BlackHoleBlockEntity, BlackHoleRenderState> {

    public static final MaterialMapper MAPPER = new MaterialMapper(TextureAtlas.LOCATION_BLOCKS, "entity/black_hole");

    public static final Material BLACK_HOLE_TEXTURE = MAPPER.apply(ForbiddenArcanus.identifier("black_hole"));
    public static final Material[] AURA_TEXTURES = {
            MAPPER.apply(ForbiddenArcanus.identifier("black_hole_aura_0")),
            MAPPER.apply(ForbiddenArcanus.identifier("black_hole_aura_1")),
            MAPPER.apply(ForbiddenArcanus.identifier("black_hole_aura_2"))
    };

    private static final float SIN_45 = (float) Math.sin(Math.PI / 3D);

    private final MaterialSet materials;
    private final ModelPart blackHole;
    private final ModelPart aura;

    public BlackHoleRenderer(BlockEntityRendererProvider.Context context) {
        this.materials = context.materials();
        this.blackHole = context.bakeLayer(FAModelLayers.BLACK_HOLE);
        this.aura = context.bakeLayer(FAModelLayers.BLACK_HOLE_AURA);
    }

    public static LayerDefinition createBlackHoleLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        meshDefinition.getRoot().addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    public static LayerDefinition createAuraLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        meshDefinition.getRoot().addOrReplaceChild("main", CubeListBuilder.create().texOffs(-32, 0).addBox(-16.0F, 0.0F, -16.0F, 32.0F, 0.1F, 32.0F, EnumSet.of(Direction.DOWN)), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Override
    public BlackHoleRenderState createRenderState() {
        return new BlackHoleRenderState();
    }

    @Override
    public void extractRenderState(BlackHoleBlockEntity blockEntity, BlackHoleRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.ageInTicks = blockEntity.getAgeInTicks(partialTick);
        renderState.auraTexture = blockEntity.auraTexture;
    }

    @Override
    public void submit(BlackHoleRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.ageInTicks * 3.0F));

        poseStack.pushPose();
        poseStack.mulPose(new Quaternionf().setAngleAxis(Math.PI / 3F, SIN_45, 0.0F, SIN_45));

        nodeCollector.submitModelPart(this.blackHole, poseStack, BLACK_HOLE_TEXTURE.renderType(RenderTypes::entitySolid), renderState.lightCoords, OverlayTexture.NO_OVERLAY, this.materials.get(BLACK_HOLE_TEXTURE), 0, renderState.breakProgress);

        poseStack.popPose();

        Material auraTexture = AURA_TEXTURES[renderState.auraTexture];
        nodeCollector.submitModelPart(this.aura, poseStack, auraTexture.renderType(RenderTypes::entityCutoutNoCull), renderState.lightCoords, OverlayTexture.NO_OVERLAY, this.materials.get(auraTexture), -1, renderState.breakProgress);

        poseStack.popPose();
    }
}
