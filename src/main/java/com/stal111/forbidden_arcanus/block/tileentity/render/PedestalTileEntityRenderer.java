package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

/**
 * Pedestal Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.render.PedestalTileEntityRenderer
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-25
 */
public class PedestalTileEntityRenderer extends TileEntityRenderer<PedestalTileEntity> {

    public PedestalTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(@Nonnull PedestalTileEntity tileEntity, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (tileEntity.getStack() == ItemStack.EMPTY) {
            return;
        }

        matrixStack.push();

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        matrixStack.translate(0.5D, 1.1D, 0.5D);

        float f3 = tileEntity.getItemHover(partialTicks);
        matrixStack.rotate(Vector3f.YP.rotation(f3));

        itemRenderer.renderItem(tileEntity.getStack(), ItemCameraTransforms.TransformType.GROUND, combinedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer);

        matrixStack.pop();
    }
}
