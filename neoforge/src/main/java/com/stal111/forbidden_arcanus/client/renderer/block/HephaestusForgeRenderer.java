package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.client.model.MagicCircleModel;
import com.stal111.forbidden_arcanus.client.renderer.block.state.HephaestusForgeRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

/**
 * Hephaestus Forge Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.HephaestusForgeRenderer
 *
 * @author stal111
 * @since 2021-07-16
 */
public class HephaestusForgeRenderer implements BlockEntityRenderer<HephaestusForgeBlockEntity, HephaestusForgeRenderState> {

    private final MagicCircleModel magicCircleModel;

    public HephaestusForgeRenderer(BlockEntityRendererProvider.Context context) {
        this.magicCircleModel = new MagicCircleModel(context);
    }

    @Override
    public HephaestusForgeRenderState createRenderState() {
        return new HephaestusForgeRenderState();
    }

    @Override
    public void submit(HephaestusForgeRenderState hephaestusForgeRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        //TODO
//        MagicCircle magicCircle = blockEntity.getMagicCircleController().getMagicCircle();
//
//        if (magicCircle != null) {
//            magicCircle.render(poseStack, partialTick, bufferSource, packedLight, this.magicCircleModel, blockEntity.getClientRitualDuration());
//        }
//
//        if (blockEntity.hasValidRitualIndicator()) {
//            blockEntity.getValidRitualIndicator().render(poseStack, partialTick, bufferSource, packedLight, this.magicCircleModel.validRitualIndicator());
//        }
//
//        ItemStack stack = blockEntity.getClientMainItem();
//
//        if (!stack.isEmpty()) {
//            poseStack.pushPose();
//
//            poseStack.translate(0.5D, 1.3D, 0.5D);
//            poseStack.mulPose(Axis.YP.rotation((blockEntity.getDisplayCounter() + partialTick) / 20));
//
//            poseStack.scale(0.5F, 0.5F, 0.5F);
//
//            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
//
//            poseStack.popPose();
//        }
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(@NotNull HephaestusForgeBlockEntity blockEntity) {
        AABB boundingBox = BlockEntityRenderer.super.getRenderBoundingBox(blockEntity).expandTowards(0.0D, 1.0D, 0.0D);

        if (this.useExpandedRenderBoundingBox(blockEntity)) {
            boundingBox = boundingBox.inflate(2.5F, 0.0F, 2.5D);
        }
        return boundingBox;
    }

    public boolean useExpandedRenderBoundingBox(HephaestusForgeBlockEntity blockEntity) {
        return blockEntity.getRitualManager().isRitualActive() || blockEntity.hasValidRitualIndicator();
    }
}
