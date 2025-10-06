package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.QuantumInjectorModel;
import com.stal111.forbidden_arcanus.client.renderer.block.state.QuantumInjectorRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.QuantumInjectorBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * @author stal111
 * @since 03.06.2024
 */
public class QuantumInjectorRenderer implements BlockEntityRenderer<QuantumInjectorBlockEntity, QuantumInjectorRenderState> {

    private static final ResourceLocation TEXTURE = ForbiddenArcanus.location("textures/entity/quantum_injector.png");
    private static final ResourceLocation LAYER_TEXTURE = ForbiddenArcanus.location("textures/entity/quantum_injector_layer.png");

    private final QuantumInjectorModel<?> model;

    public QuantumInjectorRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new QuantumInjectorModel<>(context.bakeLayer(QuantumInjectorModel.LAYER_LOCATION));
    }

    @Override
    public QuantumInjectorRenderState createRenderState() {
        return new QuantumInjectorRenderState();
    }

    @Override
    public void submit(QuantumInjectorRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (!renderState.blockState.getValue(BlockStateProperties.ENABLED)) {
            return;
        }

        poseStack.pushPose();

        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));

        //TODO
//        this.model.setupAnim(blockEntity, 0.0F, 0.0F, blockEntity.getTickCount() + partialTick, 0.0F, 0.0F);
//        this.model.renderToBuffer(poseStack, bufferSource.getBuffer(this.model.renderType(TEXTURE)), packedLight, packedOverlay);
//
//        this.model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucentEmissive(LAYER_TEXTURE)), packedLight, packedOverlay);

        poseStack.popPose();
    }
}
