package com.stal111.forbidden_arcanus.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;

@OnlyIn(Dist.CLIENT)
public class EnergyBallRender extends EntityRenderer<EnergyBallEntity> {

    private static final ResourceLocation ENERGY_BALL = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/effect/energy_ball.png");

    public EnergyBallRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(EnergyBallEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLightIn) {
        matrixStack.pushPose();
        matrixStack.scale(1.0F, 1.0F, 1.0F);

        VertexConsumer ivertexbuilder = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        PoseStack.Pose matrixstack$entry = matrixStack.last();
        Matrix4f matrix4f = matrixstack$entry.pose();

        long t = System.currentTimeMillis() % 6;

        matrixStack.mulPose(entityRenderDispatcher.cameraOrientation());


        ivertexbuilder.vertex(matrix4f, -1, -1, 0).color(255, 255, 255, 255).uv(0, 0 +  t * (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.vertex(matrix4f, -1, 1, 0).color(255, 255, 255, 255).uv(0, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.vertex(matrix4f, 1, 1, 0).color(255, 255, 255, 255).uv(1, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.vertex(matrix4f, 1, -1, 0).color(255, 255, 255, 255).uv(1, 0 +  t * (1.0f / 4.0f)).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(0, 1, 0).endVertex();

        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(EnergyBallEntity entity) {
        return ENERGY_BALL;
    }
}