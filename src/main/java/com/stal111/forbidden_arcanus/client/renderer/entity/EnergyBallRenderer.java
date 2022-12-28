package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.projectile.EnergyBall;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;

/**
 * Energy Ball Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.entity.EnergyBallRenderer
 *
 * @author stal111
 * @version 2.0.0
 */
public class EnergyBallRenderer extends EntityRenderer<EnergyBall> {

    private static final ResourceLocation LOCATION = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/energy_ball.png");

    public EnergyBallRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@Nonnull EnergyBall entity, float entityYaw, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        Matrix4f matrix4f = poseStack.last().pose();

        long t = System.currentTimeMillis() % 6;

        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());

        vertexConsumer.vertex(matrix4f, -1, -1, 0).color(255, 255, 255, 255).uv(0, 0 +  t * (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
        vertexConsumer.vertex(matrix4f, -1, 1, 0).color(255, 255, 255, 255).uv(0, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
        vertexConsumer.vertex(matrix4f, 1, 1, 0).color(255, 255, 255, 255).uv(1, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
        vertexConsumer.vertex(matrix4f, 1, -1, 0).color(255, 255, 255, 255).uv(1, 0 +  t * (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull EnergyBall entity) {
        return LOCATION;
    }
}