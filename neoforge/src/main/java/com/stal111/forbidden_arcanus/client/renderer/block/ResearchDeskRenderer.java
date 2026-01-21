package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.AbstractForbiddenomiconModel;
import com.stal111.forbidden_arcanus.client.model.DeskForbiddenomiconModel;
import com.stal111.forbidden_arcanus.client.model.FAModelLayers;
import com.stal111.forbidden_arcanus.client.renderer.block.state.ResearchDeskRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.desk.ResearchDeskBlockEntity;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * @author stal111
 * @since 30.10.2023
 */
public class ResearchDeskRenderer implements BlockEntityRenderer<ResearchDeskBlockEntity, ResearchDeskRenderState> {

    public static final Material TEXTURE_MATERIAL = Sheets.BLOCK_ENTITIES_MAPPER.apply(ForbiddenArcanus.identifier("forbiddenomicon"));

    private final MaterialSet materials;
    private final DeskForbiddenomiconModel<?> model;

    public ResearchDeskRenderer(BlockEntityRendererProvider.Context context) {
        this.materials = context.materials();
        this.model = new DeskForbiddenomiconModel<>(context.bakeLayer(FAModelLayers.FORBIDDENOMICON));
    }

    @Override
    public ResearchDeskRenderState createRenderState() {
        return new ResearchDeskRenderState();
    }

    @Override
    public void extractRenderState(ResearchDeskBlockEntity blockEntity, ResearchDeskRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        state.stillAnimation.copyFrom(blockEntity.stillAnimation);
        state.openingAnimation.copyFrom(blockEntity.openingAnimation);
        state.closingAnimation.copyFrom(blockEntity.closingAnimation);
        state.levitateAnimation.copyFrom(blockEntity.levitateAnimation);
        state.pageAnimation.copyFrom(blockEntity.pageAnimation);
        state.ageInTicks = blockEntity.getAgeInTicks(partialTicks);

        float or = blockEntity.rot - blockEntity.oRot;

        while (or >= (float) Math.PI) {
            or -= (float) (Math.PI * 2);
        }

        while (or < (float) -Math.PI) {
            or += (float) (Math.PI * 2);
        }

        state.yRot = blockEntity.oRot + or * partialTicks;
    }

    @Override
    public void submit(ResearchDeskRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        poseStack.translate(0.5F, 2.25F, 0.5F);

        poseStack.mulPose(Axis.ZP.rotationDegrees(180));

        poseStack.mulPose(Axis.YP.rotation(renderState.yRot));

        AbstractForbiddenomiconModel.State state = new AbstractForbiddenomiconModel.State(
                renderState.stillAnimation,
                renderState.openingAnimation,
                renderState.closingAnimation,
                renderState.levitateAnimation,
                renderState.pageAnimation,
                renderState.ageInTicks,
                renderState.yRot
        );

        this.model.setupAnim(state);
        nodeCollector.submitModel(this.model, state, poseStack, TEXTURE_MATERIAL.renderType(this.model::renderType), renderState.lightCoords, OverlayTexture.NO_OVERLAY, -1, this.materials.get(TEXTURE_MATERIAL), 0, renderState.breakProgress);

        poseStack.popPose();
    }
}
