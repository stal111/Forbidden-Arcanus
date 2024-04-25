package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.block.entity.UtremJarBlockEntity;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

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
        FluidStack fluidStack = blockEntity.getTank().getFluid();
        int capacity = blockEntity.getTank().getCapacity();

        if (!fluidStack.isEmpty() && capacity > 0) {
            Matrix4f matrix = poseStack.last().pose();
            Matrix3f normal = poseStack.last().normal();

            RenderUtils.renderFluid(poseStack, blockEntity.getTank(), fluidStack, bufferSource, matrix, new AABB(3.5 / 16.0F, 0.5 / 16.0F, 3.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F), IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack.getFluid().defaultFluidState(), blockEntity.getLevel(), blockEntity.getBlockPos()), packedLight, packedOverlay);
        }
    }
}
