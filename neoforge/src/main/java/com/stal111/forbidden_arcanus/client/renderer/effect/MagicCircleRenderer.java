package com.stal111.forbidden_arcanus.client.renderer.effect;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.client.renderer.effect.state.MagicCircleRenderState;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.util.Unit;

public class MagicCircleRenderer {

    public static final MaterialMapper MAPPER = new MaterialMapper(TextureAtlas.LOCATION_BLOCKS, "entity/magic_circle");

    private final MaterialSet materials;
    private final Model.Simple outerRing;
    private final Model.Simple innerRing;

    public MagicCircleRenderer(BlockEntityRendererProvider.Context context, ModelLayerLocation outerRingLayer, ModelLayerLocation innerRingLayer) {
        this.materials = context.materials();
        this.outerRing = new Model.Simple(context.bakeLayer(outerRingLayer), RenderTypes::entityTranslucentEmissive);
        this.innerRing = new Model.Simple(context.bakeLayer(innerRingLayer), RenderTypes::entityTranslucentEmissive);
    }

    public void submit(MagicCircleRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.magicCircleType == null) {
            return;
        }

        float progress = renderState.ageInTicks / renderState.duration;
        Material outerTexture = MAPPER.apply(renderState.magicCircleType.outerTexture());
        Material innerTexture = MAPPER.apply(renderState.magicCircleType.innerTexture());

        poseStack.pushPose();

        float size = this.easeSineOut(progress, 0.15D, 0.5D, 0.32D);

        poseStack.scale(size, 1.0F, size);

        poseStack.mulPose(Axis.YN.rotationDegrees(renderState.ageInTicks));

        nodeCollector.submitModel(this.outerRing, Unit.INSTANCE, poseStack, outerTexture.renderType(this.outerRing::renderType), renderState.lightCoords, OverlayTexture.NO_OVERLAY, -1, this.materials.get(outerTexture), 0, null);

        poseStack.mulPose(Axis.YN.rotationDegrees(-renderState.ageInTicks * 2));

        nodeCollector.submitModel(this.innerRing, Unit.INSTANCE, poseStack, innerTexture.renderType(this.innerRing::renderType), renderState.lightCoords, OverlayTexture.NO_OVERLAY, -1, this.materials.get(innerTexture), 0, null);

        poseStack.popPose();

        if (progress > 0.9F) {
//            RandomSource random = this.level.getRandom();
//
//            double posX = this.pos.getX() + 0.25D + random.nextFloat() + random.nextInt(4);
//            double posZ = this.pos.getZ() + 0.25D + random.nextFloat() + random.nextInt(4);
//            double ySpeed = ((double) random.nextFloat() - 0.4D) * 0.125D;
//
//            this.level.addParticle(ModParticles.AUREAL_MOTE.get(), posX - 2.0D, this.pos.getY() + 0.1F, posZ - 2.0D, 0, ySpeed, 0);
        }
    }

    public float easeSineIn(double progress, double start, double change, double duration) {
        double clampedProgress = Math.min(progress, duration);

        return (float) (-change * Math.cos(clampedProgress / duration * (Math.PI / 2)) + change + start);
    }

    public float easeSineOut(double progress, double start, double change, double duration) {
        double clampedProgress = Math.min(progress, duration);

        return (float) (change * Math.sin(clampedProgress / duration * (Math.PI / 2)) + start);
    }
}
