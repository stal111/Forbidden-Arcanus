package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.block.entity.NipaBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import javax.annotation.Nonnull;

/**
 * Nipa Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.NipaRenderer
 *
 * @author stal111
 * @version 2.0.0
 */
public class NipaRenderer implements BlockEntityRenderer<NipaBlockEntity> {

    public NipaRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(@Nonnull NipaBlockEntity blockEntity, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getBlockState().getValue(ModBlockStateProperties.SPECK)) {
            poseStack.pushPose();

            poseStack.translate(0.5, blockEntity.getSpeckHeight(), 0.5);
            poseStack.scale(0.75F, 0.75F, 0.75F);

            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());

            Minecraft.getInstance().getItemRenderer().renderStatic(ModItems.Stacks.ARCANE_CRYSTAL_DUST_SPECK, ItemTransforms.TransformType.FIXED, packedLight, packedOverlay, poseStack, bufferSource, 0);

            poseStack.popPose();
        }
    }
}
