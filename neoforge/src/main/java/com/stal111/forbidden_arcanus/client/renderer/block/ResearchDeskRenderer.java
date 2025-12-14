package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.DeskForbiddenomiconModel;
import com.stal111.forbidden_arcanus.client.model.FAModelLayers;
import com.stal111.forbidden_arcanus.client.renderer.block.state.ResearchDeskRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.desk.ResearchDeskBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

/**
 * @author stal111
 * @since 30.10.2023
 */
public class ResearchDeskRenderer implements BlockEntityRenderer<ResearchDeskBlockEntity, ResearchDeskRenderState> {

    private static final Identifier TEXTURE = ForbiddenArcanus.identifier("textures/entity/forbiddenomicon.png");

    private final DeskForbiddenomiconModel<?> model;

    public ResearchDeskRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new DeskForbiddenomiconModel<>(context.bakeLayer(FAModelLayers.FORBIDDENOMICON));
    }

    @Override
    public ResearchDeskRenderState createRenderState() {
        return new ResearchDeskRenderState();
    }

    @Override
    public void submit(ResearchDeskRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
//        poseStack.pushPose();
//
//        poseStack.translate(0.5F, 2.25F, 0.5F);
//
//        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
//
//        float f1;
//        for (f1 = blockEntity.rot - blockEntity.oRot; f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) {
//        }
//
//        while (f1 < -Math.PI) {
//            f1 += (float) (Math.PI * 2F);
//        }
//
//        float rotation = blockEntity.oRot + f1 * partialTick;
//        poseStack.mulPose(Axis.YP.rotation(rotation));
//
//        this.model.setupAnim(blockEntity, 0.0F, 0.0F, blockEntity.getTickCount() + partialTick, 0.0F, 0.0F);
//        this.model.renderToBuffer(poseStack, bufferSource.getBuffer(this.model.renderType(TEXTURE)), packedLight, packedOverlay);
//
//        poseStack.popPose();
    }
}
