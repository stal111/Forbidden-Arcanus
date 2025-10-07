package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.FAModelLayers;
import com.stal111.forbidden_arcanus.client.renderer.block.state.BlackHoleRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.BlackHoleBlockEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Black Hole Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.BlackHoleRenderer
 *
 * @author stal111
 * @version 2.0.0
 */
public class BlackHoleRenderer implements BlockEntityRenderer<BlackHoleBlockEntity, BlackHoleRenderState> {

    private static final ResourceLocation BLACK_HOLE_TEXTURE = ForbiddenArcanus.location("textures/block/black_hole.png");
    private static final ResourceLocation[] BLACK_HOLE_AURA = {
            ForbiddenArcanus.location("textures/block/black_hole_aura_0.png"),
            ForbiddenArcanus.location("textures/block/black_hole_aura_1.png"),
            ForbiddenArcanus.location("textures/block/black_hole_aura_2.png"),
    };

    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(BLACK_HOLE_TEXTURE);
    private static final RenderType[] AURA_RENDER_TYPE = {
            RenderType.entityTranslucentEmissive(BLACK_HOLE_AURA[0]),
            RenderType.entityTranslucentEmissive(BLACK_HOLE_AURA[1]),
            RenderType.entityTranslucentEmissive(BLACK_HOLE_AURA[2])
    };

    private static final float SIN_45 = (float) Math.sin(Math.PI / 3D);

    private final ModelPart hole;
    private final ModelPart aura;

    public BlackHoleRenderer(BlockEntityRendererProvider.Context context) {
        this.hole = context.bakeLayer(FAModelLayers.BLACK_HOLE);
        this.aura = context.bakeLayer(FAModelLayers.BLACK_HOLE_AURA);
    }

    public static LayerDefinition createHoleLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        meshDefinition.getRoot().addOrReplaceChild("hole", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    public static LayerDefinition createAuraLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        meshDefinition.getRoot().addOrReplaceChild("aura", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, 0.0F, -10.0F, 20.0F, 0.1F, 20.0F), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 20, 20);
    }

    @Override
    public BlackHoleRenderState createRenderState() {
        return new BlackHoleRenderState();
    }

    @Override
    public void extractRenderState(BlackHoleBlockEntity blockEntity, BlackHoleRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.auraTexture = blockEntity.auraTexture;
    }

    @Override
    public void submit(BlackHoleRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        //TODO
//        poseStack.translate(0.5D, 0.5D, 0.5D);
//
//        VertexConsumer vertexconsumer = bufferSource.getBuffer(RENDER_TYPE);
//
//        float rotation = ((float) blockEntity.rotation + partialTick) * 3.0F;
//        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
//
//        poseStack.pushPose();
//        poseStack.mulPose(new Quaternionf().setAngleAxis(Math.PI / 3F, SIN_45, 0.0F, SIN_45));
//
//        this.hole.render(poseStack, vertexconsumer, packedLight, packedOverlay);
//        poseStack.popPose();
//
//        vertexconsumer = bufferSource.getBuffer(AURA_RENDER_TYPE[blockEntity.auraTexture]);
//        this.aura.render(poseStack, vertexconsumer, packedLight, packedOverlay);

        poseStack.popPose();
    }
}
