package com.stal111.forbidden_arcanus.item.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.block.tileentity.render.ObsidianSkullTileEntityRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;

/**
 * Obsidian Skull Item Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.renderer.ObsidianSkullItemRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ObsidianSkullItemRenderer extends ItemStackTileEntityRenderer {

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ObsidianSkullTileEntityRenderer.render(null, 180.0F, 0.0F, matrixStack, buffer, combinedLight);
    }
}
