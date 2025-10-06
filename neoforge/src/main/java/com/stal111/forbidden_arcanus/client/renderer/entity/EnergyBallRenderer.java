package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.projectile.EnergyBall;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Energy Ball Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.entity.EnergyBallRenderer
 *
 * @author stal111
 * @version 2.0.0
 */
public class EnergyBallRenderer extends EntityRenderer<EnergyBall, EntityRenderState> {

    private static final ResourceLocation LOCATION = ForbiddenArcanus.location("textures/effect/energy_ball.png");

    public EnergyBallRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(EntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
    }

    //TODO
//    @Override
//    public void render(@NotNull EntityRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
//        poseStack.pushPose();
//
//        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(LOCATION));
//        Matrix4f matrix4f = poseStack.last().pose();
//
//        long t = System.currentTimeMillis() % 6;
//
//        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
//
//        vertexConsumer.addVertex(matrix4f, -1, -1, 0).setColor(255, 255, 255, 255).setUv(0, 0 +  t * (1.0f / 4.0f)).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 1, 0);
//        vertexConsumer.addVertex(matrix4f, -1, 1, 0).setColor(255, 255, 255, 255).setUv(0, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 1, 0);
//        vertexConsumer.addVertex(matrix4f, 1, 1, 0).setColor(255, 255, 255, 255).setUv(1, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 1, 0);
//        vertexConsumer.addVertex(matrix4f, 1, -1, 0).setColor(255, 255, 255, 255).setUv(1, 0 +  t * (1.0f / 4.0f)).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(0, 1, 0);
//
//        poseStack.popPose();
//
//        super.render(renderState, poseStack, bufferSource, packedLight);
//    }
}