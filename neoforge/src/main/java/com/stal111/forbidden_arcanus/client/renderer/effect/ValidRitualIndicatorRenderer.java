package com.stal111.forbidden_arcanus.client.renderer.effect;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.FAModelLayers;
import com.stal111.forbidden_arcanus.client.renderer.effect.state.ValidRitualIndicatorRenderState;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.util.ARGB;
import net.minecraft.util.Unit;

public class ValidRitualIndicatorRenderer {

    private static final Material TEXTURE = MagicCircleRenderer.MAPPER.apply(ForbiddenArcanus.location("valid_ritual_indicator"));
    private static final int ANIMATION_DURATION = 60;

    private final MaterialSet materials;
    private final Model.Simple model;

    public ValidRitualIndicatorRenderer(BlockEntityRendererProvider.Context context) {
        this.materials = context.materials();
        this.model = new Model.Simple(context.bakeLayer(FAModelLayers.MAGIC_CIRCLE_VALID_RITUAL_INDICATOR), RenderType::entityTranslucentEmissive);
    }

    public void submit(ValidRitualIndicatorRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        poseStack.translate(0.5D, 0.0002D, 0.5D);
        poseStack.scale(0.65F, 1.0F, 0.65F);

        nodeCollector.submitModel(this.model, Unit.INSTANCE, poseStack, TEXTURE.renderType(this.model::renderType), renderState.lightCoords, OverlayTexture.NO_OVERLAY, ARGB.colorFromFloat(Math.min(1.0F, easeOutBack(renderState.ageInTicks, 0.0F, 1.0F, ANIMATION_DURATION)), 1.0F, 1.0F, 1.0F), this.materials.get(TEXTURE), 0, null);

        poseStack.popPose();
    }

    public static float easeOutBack(float progress, float start, float change, float duration) {
        float s = 1.70158f;
        return change * ((progress = progress / duration - 1) * progress * ((s + 1) * progress + s) + 1) + start;
    }
}
