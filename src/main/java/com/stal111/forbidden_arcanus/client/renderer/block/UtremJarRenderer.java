package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.block.entity.UtremJarBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import javax.annotation.Nonnull;

/**
 * Utrem Jar Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.UtremJarRenderer
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class UtremJarRenderer implements BlockEntityRenderer<UtremJarBlockEntity> {

    public UtremJarRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(@Nonnull UtremJarBlockEntity blockEntity, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
    }
}
