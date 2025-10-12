package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.client.model.MagicCircleModel;
import com.stal111.forbidden_arcanus.client.renderer.block.state.HephaestusForgeRenderState;
import com.stal111.forbidden_arcanus.client.renderer.effect.ValidRitualIndicatorRenderer;
import com.stal111.forbidden_arcanus.client.renderer.effect.state.ValidRitualIndicatorRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
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

/**
 * Hephaestus Forge Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.HephaestusForgeRenderer
 *
 * @author stal111
 * @since 2021-07-16
 */
public class HephaestusForgeRenderer implements BlockEntityRenderer<HephaestusForgeBlockEntity, HephaestusForgeRenderState> {

    private final ItemModelResolver itemModelResolver;
    private final MagicCircleModel magicCircleModel;

    private final ValidRitualIndicatorRenderer validRitualIndicatorRenderer;

    public HephaestusForgeRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
        this.magicCircleModel = new MagicCircleModel(context);
        this.validRitualIndicatorRenderer = new ValidRitualIndicatorRenderer(context);
    }

    @Override
    public HephaestusForgeRenderState createRenderState() {
        return new HephaestusForgeRenderState();
    }

    @Override
    public void extractRenderState(HephaestusForgeBlockEntity blockEntity, HephaestusForgeRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemStackRenderState, blockEntity.getClientMainItem(), ItemDisplayContext.FIXED, blockEntity.getLevel(), blockEntity, 0);

        renderState.itemStackRenderState = itemStackRenderState;
        renderState.ageInTicks = blockEntity.getAgeInTicks(partialTick);
        renderState.isValidRitual = blockEntity.hasValidRitualIndicator;

        renderState.validRitualIndicatorRenderState = new ValidRitualIndicatorRenderState(renderState.lightCoords, blockEntity.validRitualIndicatorCounter);
    }

    @Override
    public void submit(HephaestusForgeRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        //TODO
//        MagicCircle magicCircle = blockEntity.getMagicCircleController().getMagicCircle();
//
//        if (magicCircle != null) {
//            magicCircle.render(poseStack, partialTick, bufferSource, packedLight, this.magicCircleModel, blockEntity.getClientRitualDuration());
//        }

        if (renderState.isValidRitual) {
            this.validRitualIndicatorRenderer.submit(renderState.validRitualIndicatorRenderState, poseStack, nodeCollector, cameraRenderState);
        }

        if (!renderState.itemStackRenderState.isEmpty()) {
            poseStack.pushPose();

            poseStack.translate(0.5D, 1.3D, 0.5D);
            poseStack.mulPose(Axis.YP.rotation(ItemEntity.getSpin(renderState.ageInTicks, 0.0F)));

            poseStack.scale(0.5F, 0.5F, 0.5F);

            renderState.itemStackRenderState.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(@NotNull HephaestusForgeBlockEntity blockEntity) {
        AABB boundingBox = BlockEntityRenderer.super.getRenderBoundingBox(blockEntity).expandTowards(0.0D, 1.0D, 0.0D);

        if (this.useExpandedRenderBoundingBox(blockEntity)) {
            boundingBox = boundingBox.inflate(2.5F, 0.0F, 2.5D);
        }
        return boundingBox;
    }

    public boolean useExpandedRenderBoundingBox(HephaestusForgeBlockEntity blockEntity) {
        return blockEntity.getRitualManager().isRitualActive() || blockEntity.hasValidRitualIndicator;
    }
}
