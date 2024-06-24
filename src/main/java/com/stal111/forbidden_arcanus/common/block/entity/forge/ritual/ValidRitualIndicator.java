package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.FARenderTypes;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.valhelsia.valhelsia_core.api.common.counter.SimpleCounter;

/**
 * @author stal111
 * @since 2023-01-19
 */
public class ValidRitualIndicator {

    private static final ResourceLocation INDICATOR = ForbiddenArcanus.location("textures/effect/magic_circle/valid_ritual_indicator.png");
    private static final int ANIMATION_DURATION = 60;

    private final IndicatorCounter counter = new IndicatorCounter();

    public ValidRitualIndicator(boolean playAnimation) {
        this.counter.setActive(playAnimation);
    }

    public static float easeOutBack(float progress, float start, float change, float duration) {
        float s = 1.70158f;
        return change * ((progress = progress / duration - 1) * progress * ((s + 1) * progress + s) + 1) + start;
    }

    public void tick() {
        this.counter.tick();
    }

    public void render(PoseStack poseStack, float partialTicks, MultiBufferSource buffer, int packedLight, ModelPart model) {
        poseStack.pushPose();

        poseStack.translate(0.5D, 0.0002D, 0.5D);

        poseStack.scale(8.5F, 1.0F, 8.5F);

        model.render(poseStack, buffer.getBuffer(FARenderTypes.entityFullbrightTranslucent(INDICATOR)), packedLight, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(Math.min(1.0F, easeOutBack(this.counter.getValue(), 0.0F, 1.0F, ANIMATION_DURATION)), 1.0F, 1.0F, 1.0F));

        poseStack.popPose();
    }

    private static class IndicatorCounter extends SimpleCounter {
        @Override
        public void tick(CompoundTag tag) {
            if (this.isActive()) {
                if (this.value >= ANIMATION_DURATION) {
                    this.setActive(false);

                    return;
                }

                this.increase();
            }
        }
    }
}
