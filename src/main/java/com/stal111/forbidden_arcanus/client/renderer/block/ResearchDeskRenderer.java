package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.DeskForbiddenomiconModel;
import com.stal111.forbidden_arcanus.common.block.entity.desk.ResearchDeskBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 30.10.2023
 */
public class ResearchDeskRenderer implements BlockEntityRenderer<ResearchDeskBlockEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/forbiddenomicon.png");

    private final DeskForbiddenomiconModel<?> model;

    public ResearchDeskRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new DeskForbiddenomiconModel<>(context);
    }

    @Override
    public void render(@NotNull ResearchDeskBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        poseStack.translate(0.5F, 2.25F, 0.5F);

        poseStack.mulPose(Axis.ZP.rotationDegrees(180));

        float f1;
        for (f1 = blockEntity.rot - blockEntity.oRot; f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) {
        }

        while (f1 < -Math.PI) {
            f1 += (float) (Math.PI * 2F);
        }

        float rotation = blockEntity.oRot + f1 * partialTick;
        poseStack.mulPose(Axis.YP.rotation(rotation));

        this.model.setupAnim(blockEntity, 0.0F, 0.0F, blockEntity.getTickCount() + partialTick, 0.0F, 0.0F);
        this.model.renderToBuffer(poseStack, bufferSource.getBuffer(this.model.renderType(TEXTURE)), packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
}
