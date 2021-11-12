package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.block.tileentity.UtremJarTileEntity;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import com.mojang.math.Matrix4f;
import net.minecraftforge.fluids.FluidStack;

/**
 * Utrem Jar Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.render.UtremJarTileEntityRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-19
 */
public class UtremJarTileEntityRenderer implements BlockEntityRenderer<UtremJarTileEntity> {

    public UtremJarTileEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(UtremJarTileEntity tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        FluidStack fluidStack = tileEntity.getTank().getFluid();
        Fluid fluid = fluidStack.getFluid();
        int capacity = tileEntity.getTank().getCapacity();

        if (!fluidStack.isEmpty() && capacity > 0) {
            Matrix4f matrix = matrixStack.last().pose();

            RenderUtils.renderFluid(tileEntity.getTank(), fluidStack, buffer, matrix, new AABB(3.5 / 16.0F, 0.5 / 16.0F, 3.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F), fluid.getAttributes().getColor(tileEntity.getLevel(), tileEntity.getBlockPos()), combinedLight);
        }
    }
}
