package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.client.renderer.block.state.PedestalRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

/**
 * Pedestal Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.PedestalRenderer
 *
 * @author stal111
 * @since 2021-06-25
 */
public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity, PedestalRenderState> {

    public PedestalRenderer(BlockEntityRendererProvider.Context context) {}


    @Override
    public @NotNull AABB getRenderBoundingBox(PedestalBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).expandTowards(0.0D, 1.0D, 0.0D);
    }

    @Override
    public PedestalRenderState createRenderState() {
        return new PedestalRenderState();
    }

    @Override
    public void submit(PedestalRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        //TODO
//        ItemStack stack = blockEntity.getStack();
//
//        if (!stack.isEmpty()) {
//            poseStack.pushPose();
//
//            poseStack.translate(0.5D, blockEntity.getItemHeight() / 100.0F, 0.5D);
//            poseStack.mulPose(Axis.YP.rotation(blockEntity.getItemHover(partialTick)));
//
//            poseStack.scale(0.5F, 0.5F, 0.5F);
//
//            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
//
//            poseStack.popPose();
//        }
    }
}
