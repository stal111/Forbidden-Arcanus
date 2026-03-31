package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.projectile.AurealMissile;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class AurealMissileRenderer extends EntityRenderer<AurealMissile, EntityRenderState> {

    private static final Identifier TEXTURE_LOCATION = ForbiddenArcanus.identifier("textures/entity/projectiles/aureal_missile.png");
    private static final RenderType RENDER_TYPE = RenderTypes.entityTranslucentEmissive(TEXTURE_LOCATION);

    public AurealMissileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(EntityRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        poseStack.translate(0, 0.2, 0);
        poseStack.mulPose(cameraRenderState.orientation);

        nodeCollector.submitCustomGeometry(poseStack, RENDER_TYPE, (pose, vertexConsumer) -> {
            vertex(vertexConsumer, pose, -0.25F, -0.25F, 0, 1, renderState.lightCoords);
            vertex(vertexConsumer, pose, 0.25F, -0.25F, 1, 1, renderState.lightCoords);
            vertex(vertexConsumer, pose, 0.25F, 0.25F, 1, 0, renderState.lightCoords);
            vertex(vertexConsumer, pose, -0.25F, 0.25F, 0, 0, renderState.lightCoords);
        });

        poseStack.popPose();

        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
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
}
