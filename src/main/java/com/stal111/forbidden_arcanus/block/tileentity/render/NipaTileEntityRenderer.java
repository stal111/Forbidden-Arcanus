package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.block.tileentity.NipaTileEntity;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

/**
 * Nipa Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.render.NipaTileEntityRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-21
 */
public class NipaTileEntityRenderer extends TileEntityRenderer<NipaTileEntity> {


    public NipaTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(NipaTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (tileEntity.getBlockState().get(ModBlockStateProperties.SPECK)) {
            matrixStack.push();

            matrixStack.translate(0.5, tileEntity.getSpeckHeight(), 0.5);
            matrixStack.scale(0.75F, 0.75F, 0.75F);

            matrixStack.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());

            Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get()), ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);

            matrixStack.pop();
        }
    }
}
