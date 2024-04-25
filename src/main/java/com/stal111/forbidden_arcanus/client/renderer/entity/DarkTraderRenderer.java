package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.client.model.DarkTraderModel;
import com.stal111.forbidden_arcanus.client.model.QuantumLightDoorModel;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTrader;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-08-11
 */
public class DarkTraderRenderer extends MobRenderer<DarkTrader, DarkTraderModel> {

    private final QuantumLightDoorModel<DarkTrader> portalModel;

    public DarkTraderRenderer(EntityRendererProvider.Context context) {
        super(context, new DarkTraderModel(context.bakeLayer(DarkTraderModel.LAYER_LOCATION)), 0.5F);
        this.portalModel = new QuantumLightDoorModel<>(context);
    }

    @Override
    public void render(@NotNull DarkTrader entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (entity.getPose() != Pose.EMERGING ||entity.spawnAnimationState.isStarted()) {
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }

        if (entity.portalAnimationState.isStarted()) {
            this.portalModel.render(entity, poseStack, buffer, packedLight, entity.tickCount + partialTicks);
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DarkTrader entity) {
        return entity.getVariant().value().texture();
    }
}
