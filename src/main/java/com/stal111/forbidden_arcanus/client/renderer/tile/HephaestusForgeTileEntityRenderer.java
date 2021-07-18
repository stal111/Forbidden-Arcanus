package com.stal111.forbidden_arcanus.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

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
    }
}
