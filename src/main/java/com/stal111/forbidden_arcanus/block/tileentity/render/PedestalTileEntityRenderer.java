package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import com.mojang.math.Vector3f;

import javax.annotation.Nonnull;

/**
 * Pedestal Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.render.PedestalTileEntityRenderer
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-25
 */
public class PedestalTileEntityRenderer implements BlockEntityRenderer<PedestalTileEntity> {

    public PedestalTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        //TODO
    }

    @Override
    public void render(@Nonnull PedestalTileEntity tileEntity, float partialTicks, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack stack = tileEntity.getStack();

        if (!stack.isEmpty()) {
            matrixStack.pushPose();

            matrixStack.translate(0.5D, tileEntity.getItemHeight() / 100.0F, 0.5D);
            matrixStack.mulPose(Vector3f.YP.rotation(tileEntity.getItemHover(partialTicks)));

           // Minecraft.getInstance().getItemRenderer().renderStatic(tileEntity.getStack(), ItemTransforms.TransformType.GROUND, combinedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer);

            matrixStack.popPose();
        }
    }
}
