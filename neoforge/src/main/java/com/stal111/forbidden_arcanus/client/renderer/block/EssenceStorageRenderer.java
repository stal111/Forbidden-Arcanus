package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.client.renderer.FluidBox;
import com.stal111.forbidden_arcanus.client.renderer.block.state.EssenceStorageRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceStorageBlockEntity;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceStorageRenderer implements BlockEntityRenderer<EssenceStorageBlockEntity, EssenceStorageRenderState> {

    public void submitSpecial(PoseStack poseStack, SubmitNodeCollector nodeCollector, int lightCoords, int packedOverlay, EssenceStorage essenceStorage, AABB renderBounds) {
        submit(poseStack, nodeCollector, lightCoords, packedOverlay, FluidBox.create(essenceStorage.type(), renderBounds), essenceStorage.getFillPercentage());
    }

    private static void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int lightCoords, int packedOverlay, FluidBox fluidBox, float fillPercentage) {
        fluidBox.setFillPercentage(fillPercentage);

        fluidBox.submit(poseStack, nodeCollector, lightCoords, packedOverlay);
    }

    @Override
    public EssenceStorageRenderState createRenderState() {
        return new EssenceStorageRenderState();
    }

    @Override
    public void extractRenderState(EssenceStorageBlockEntity blockEntity, EssenceStorageRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.essenceStorage = blockEntity.getEssenceStorage();
        renderState.essenceRenderBounds = blockEntity.getEssenceRenderBounds();
    }

    @Override
    public void submit(EssenceStorageRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (!renderState.essenceStorage.isEmpty()) {
            EssenceType type = renderState.essenceStorage.type();

            submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, FluidBox.create(type, renderState.essenceRenderBounds), renderState.essenceStorage.getFillPercentage());
        }
    }
}
