package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.FAModelLayers;
import com.stal111.forbidden_arcanus.client.model.QuantumInjectorModel;
import com.stal111.forbidden_arcanus.client.renderer.block.state.QuantumInjectorRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.QuantumInjectorBlockEntity;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 03.06.2024
 */
public class QuantumInjectorRenderer implements BlockEntityRenderer<QuantumInjectorBlockEntity, QuantumInjectorRenderState> {

    public static final SpriteId TEXTURE_MATERIAL = Sheets.BLOCK_ENTITIES_MAPPER.apply(ForbiddenArcanus.identifier("quantum_injector"));
    public static final SpriteId LAYER_MATERIAL = Sheets.BLOCK_ENTITIES_MAPPER.apply(ForbiddenArcanus.identifier("quantum_injector_layer"));

    private final SpriteGetter sprites;
    private final QuantumInjectorModel<?> model;

    public QuantumInjectorRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
        this.model = new QuantumInjectorModel<>(context.bakeLayer(FAModelLayers.QUANTUM_INJECTOR));
    }

    @Override
    public QuantumInjectorRenderState createRenderState() {
        return new QuantumInjectorRenderState();
    }

    @Override
    public void extractRenderState(QuantumInjectorBlockEntity blockEntity, QuantumInjectorRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.transformAnimation.copyFrom(blockEntity.transformAnimation);
        renderState.rotateAnimation.copyFrom(blockEntity.rotateAnimation);
        renderState.ageInTicks = blockEntity.getAgeInTicks(partialTick);
        renderState.enabled = blockEntity.getBlockState().getValue(BlockStateProperties.ENABLED);
    }

    @Override
    public void submit(QuantumInjectorRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (!renderState.enabled) {
            return;
        }

        poseStack.pushPose();

        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));

        QuantumInjectorModel.State state = new QuantumInjectorModel.State(renderState.transformAnimation, renderState.rotateAnimation, renderState.ageInTicks);

        this.model.setupAnim(state);
        nodeCollector.submitModel(this.model, state, poseStack, TEXTURE_MATERIAL.renderType(this.model::renderType), renderState.lightCoords, OverlayTexture.NO_OVERLAY, -1, this.sprites.get(TEXTURE_MATERIAL), 0, renderState.breakProgress);
        nodeCollector.submitModel(this.model, state, poseStack, LAYER_MATERIAL.renderType(RenderTypes::entityTranslucentEmissive), renderState.lightCoords, OverlayTexture.NO_OVERLAY, -1, this.sprites.get(LAYER_MATERIAL), 0, renderState.breakProgress);

        poseStack.popPose();
    }
}
