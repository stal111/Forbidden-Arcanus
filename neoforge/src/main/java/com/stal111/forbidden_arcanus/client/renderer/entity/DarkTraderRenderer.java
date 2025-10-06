package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.client.model.DarkTraderModel;
import com.stal111.forbidden_arcanus.client.model.QuantumLightDoorModel;
import com.stal111.forbidden_arcanus.client.renderer.entity.state.DarkTraderRenderState;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTrader;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-08-11
 */
public class DarkTraderRenderer extends MobRenderer<DarkTrader, DarkTraderRenderState, DarkTraderModel> {

    private final QuantumLightDoorModel portalModel;

    public DarkTraderRenderer(EntityRendererProvider.Context context) {
        super(context, new DarkTraderModel(context.bakeLayer(DarkTraderModel.LAYER_LOCATION)), 0.5F);
        this.portalModel = new QuantumLightDoorModel(context.bakeLayer(QuantumLightDoorModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull DarkTraderRenderState createRenderState() {
        return new DarkTraderRenderState();
    }

    @Override
    public void extractRenderState(DarkTrader entity, DarkTraderRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);

        reusedState.texture = entity.getVariant().value().textureAsset().texturePath();
    }

    @Override
    public void submit(DarkTraderRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (renderState.pose != Pose.EMERGING ||renderState.spawnAnimation.isStarted()) {
            super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
        }

        if (renderState.spawnAnimation.isStarted()) {
            //TODO
//            this.portalModel.render(new QuantumLightDoorRenderState(), poseStack, bufferSource, packedLight);
        }

        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
    }

    @Override
    public ResourceLocation getTextureLocation(DarkTraderRenderState renderState) {
        return renderState.texture;
    }
}
