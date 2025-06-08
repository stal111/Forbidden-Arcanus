package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.projectile.AurealMissile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AurealMissileRenderer extends EntityRenderer<AurealMissile> {

    private static final ResourceLocation TEXTURE_LOCATION = ForbiddenArcanus.location("textures/entity/projectiles/aureal_missile.png");

    public AurealMissileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull AurealMissile entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE_LOCATION));
        PoseStack.Pose pose = poseStack.last();

        poseStack.translate(0, 0.2, 0);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());

        vertex(vertexConsumer, pose, -0.25F, -0.25F, 0, 1, packedLight);
        vertex(vertexConsumer, pose, 0.25F, -0.25F, 1, 1, packedLight);
        vertex(vertexConsumer, pose, 0.25F, 0.25F, 1, 0, packedLight);
        vertex(vertexConsumer, pose, -0.25F, 0.25F, 0, 0, packedLight);

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static void vertex(
            VertexConsumer consumer,
            PoseStack.Pose pose,
            float x,
            float y,
            float u,
            float v,
            int packedLight
    ) {
        consumer.addVertex(pose, x, y, 0.0F)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AurealMissile entity) {
        return TEXTURE_LOCATION;
    }
}
