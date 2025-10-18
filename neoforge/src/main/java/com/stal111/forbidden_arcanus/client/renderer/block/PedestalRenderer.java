package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.client.renderer.block.state.PedestalRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record PedestalRenderer(ItemModelResolver itemModelResolver) implements BlockEntityRenderer<PedestalBlockEntity, PedestalRenderState> {

    public PedestalRenderer(BlockEntityRendererProvider.Context itemModelResolver) {
        this(itemModelResolver.itemModelResolver());
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(PedestalBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).expandTowards(0.0D, 1.0D, 0.0D);
    }

    @Override
    public PedestalRenderState createRenderState() {
        return new PedestalRenderState();
    }

    @Override
    public void extractRenderState(PedestalBlockEntity blockEntity, PedestalRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemStackRenderState, blockEntity.getStack(), ItemDisplayContext.FIXED, blockEntity.getLevel(), blockEntity, 0);

        renderState.itemStackRenderState = itemStackRenderState;
        renderState.itemHeight = blockEntity.getItemHeight();
        renderState.ageInTicks = blockEntity.getAgeInTicks(partialTick);
        renderState.hoverStart = blockEntity.hoverStart;
    }

    @Override
    public void submit(PedestalRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (!renderState.itemStackRenderState.isEmpty()) {
            poseStack.pushPose();

            poseStack.translate(0.5D, renderState.itemHeight, 0.5D);
            poseStack.mulPose(Axis.YP.rotation(ItemEntity.getSpin(renderState.ageInTicks, renderState.hoverStart)));

            poseStack.scale(0.5F, 0.5F, 0.5F);

            renderState.itemStackRenderState.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

            poseStack.popPose();
        }
    }
}
