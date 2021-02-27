package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.block.tileentity.UtremJarTileEntity;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.fluids.FluidStack;

/**
 * Utrem Jar Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.render.UtremJarTileEntityRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-19
 */
public class UtremJarTileEntityRenderer extends TileEntityRenderer<UtremJarTileEntity> {

    public UtremJarTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(UtremJarTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        FluidStack fluidStack = tileEntity.getTank().getFluid();
        Fluid fluid = fluidStack.getFluid();
        int capacity = tileEntity.getTank().getCapacity();

        if (!fluidStack.isEmpty() && capacity > 0) {
            Matrix4f matrix = matrixStack.getLast().getMatrix();

            RenderUtils.renderFluid(tileEntity.getTank(), fluidStack, buffer, matrix, new AxisAlignedBB(3.5 / 16.0F, 0.5 / 16.0F, 3.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F), fluid.getAttributes().getColor(tileEntity.getWorld(), tileEntity.getPos()), combinedLight);
        }
    }
}
