package com.stal111.forbidden_arcanus.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

/**
 * Hephaestus Forge Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.tile.HephaestusForgeTileEntityRenderer
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-16
 */
public class HephaestusForgeTileEntityRenderer extends TileEntityRenderer<HephaestusForgeTileEntity> {

    public HephaestusForgeTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(@Nonnull HephaestusForgeTileEntity tileEntity, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        tileEntity.getMagicCircle().render(matrixStack, partialTicks, buffer, combinedLight);

        ItemStack stack = tileEntity.getStackInSlot(4);

        if (!stack.isEmpty()) {
            matrixStack.push();

            matrixStack.translate(0.5D, 1.1D, 0.5D);
            matrixStack.rotate(Vector3f.YP.rotation((tileEntity.getDisplayCounter() + partialTicks) / 20));

            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, combinedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer);

            matrixStack.pop();
        }
    }
}
