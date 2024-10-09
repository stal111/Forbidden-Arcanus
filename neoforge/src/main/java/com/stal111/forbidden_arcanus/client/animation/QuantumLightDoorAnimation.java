package com.stal111.forbidden_arcanus.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * @author stal111
 * @since 2023-08-14
 */
public class QuantumLightDoorAnimation {

    public static final AnimationDefinition SPAWN = AnimationDefinition.Builder.withLength(1f)
            .addAnimation("light_door",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("top",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.72f, KeyframeAnimations.posVec(0f, 20f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("top",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0.2f, KeyframeAnimations.scaleVec(0f, 1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("down",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.32f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.72f, KeyframeAnimations.posVec(0f, -16f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("down",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0.2f, KeyframeAnimations.scaleVec(0f, 1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.52f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("inner",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 2f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("inner",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0.32f, KeyframeAnimations.scaleVec(0f, 1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.scaleVec(1f, 1.1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.scaleVec(0f, 1.6f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.88f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition MODEL_MEDIUM = AnimationDefinition.Builder.withLength(0f)
            .addAnimation("light_door",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 10f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("light_door",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1.6f, 1.6f, 1.6f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition MODEL_BIG = AnimationDefinition.Builder.withLength(0f)
            .addAnimation("light_door",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 16f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("light_door",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(2f, 2f, 2f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
}
