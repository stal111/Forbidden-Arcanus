package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.QuantumInjectorModel;
import com.stal111.forbidden_arcanus.common.block.entity.QuantumInjectorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 03.06.2024
 */
public class QuantumInjectorRenderer implements BlockEntityRenderer<QuantumInjectorBlockEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/quantum_injector.png");
    private static final ResourceLocation LAYER_TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/quantum_injector_layer.png");

    private final QuantumInjectorModel<?> model;

    public QuantumInjectorRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new QuantumInjectorModel<>(context.getModelSet());
    }

    @Override
    public void render(@NotNull QuantumInjectorBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (!blockEntity.getBlockState().getValue(BlockStateProperties.ENABLED)) {
            return;
        }

        poseStack.pushPose();

        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));

        this.model.setupAnim(blockEntity, 0.0F, 0.0F, blockEntity.getTickCount() + partialTick, 0.0F, 0.0F);
        this.model.renderToBuffer(poseStack, bufferSource.getBuffer(this.model.renderType(TEXTURE)), packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        this.model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucentEmissive(LAYER_TEXTURE)), packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
}
