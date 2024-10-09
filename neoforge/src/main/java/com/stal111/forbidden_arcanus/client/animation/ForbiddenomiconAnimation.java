package com.stal111.forbidden_arcanus.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * @author stal111
 * @since 30.10.2023
 */
public class ForbiddenomiconAnimation {


    public static final AnimationDefinition FLIP_PAGE = AnimationDefinition.Builder.withLength(0.44f)
            .addAnimation("rinner",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.08f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12f, KeyframeAnimations.posVec(0f, 0f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.44f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rinner",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.08f, KeyframeAnimations.degreeVec(0f, 7.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16f, KeyframeAnimations.degreeVec(0f, -117.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, -117.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.24f, KeyframeAnimations.degreeVec(0f, -110f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.44f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rinner",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0.08f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.12f, KeyframeAnimations.scaleVec(0f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.16f, KeyframeAnimations.scaleVec(0f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.44f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("linnel",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.28f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.32f, KeyframeAnimations.posVec(0f, 0f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("linnel",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, 110f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.28f, KeyframeAnimations.degreeVec(0f, 117.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.36f, KeyframeAnimations.degreeVec(0f, -7.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4f, KeyframeAnimations.degreeVec(0f, -7.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.44f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("linnel",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0.28f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.32f, KeyframeAnimations.scaleVec(0f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.36f, KeyframeAnimations.scaleVec(0f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.4f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition OPEN_STILL = AnimationDefinition.Builder.withLength(3f).looping()
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 3f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-40f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.52f, KeyframeAnimations.degreeVec(-35f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3f, KeyframeAnimations.degreeVec(-40f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("front",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -30f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("back",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 30f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("lock",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -50f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("locktip",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetr",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -27.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetl",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 27.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("tongue",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(35f, -10f, 5f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rinner",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -35f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("linnel",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 35f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("inner",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -1f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheet",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(16f, 14f, -6f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.52f, KeyframeAnimations.posVec(16f, 12f, -6f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3f, KeyframeAnimations.posVec(16f, 14f, -6f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0.5f, 0.5f, 1f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition CLOSED_FLOATING = AnimationDefinition.Builder.withLength(3f)
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 3f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-40f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.52f, KeyframeAnimations.degreeVec(-35f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3f, KeyframeAnimations.degreeVec(-40f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("front",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("back",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("lock",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -0.1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("locktip",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetr",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetl",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("tongue",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 25f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rinner",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("linnel",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheet",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("inner",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(5f, 9f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition CLOSED = AnimationDefinition.Builder.withLength(0f)
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(-5f, -6f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("front",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("back",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("lock",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -0.1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("locktip",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheet",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetr",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetl",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("tongue",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(45f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("inner",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rinner",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("linnel",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(12f, -1.5f, -2f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, -15f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0.5f, 0.5f, 1f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition OPENING = AnimationDefinition.Builder.withLength(0.8f)
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.2f, KeyframeAnimations.posVec(-5f, -6f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 1f, 3f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(-90f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(-40f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("front",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, -30f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("back",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, 30f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("lock",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -0.1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, -45f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, -50f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("locktip",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, 67.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetr",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, -27.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetl",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, 27.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("tongue",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(45f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(35f, -10f, 5f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rinner",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, -35f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("linnel",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, 35f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("inner",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 0f, -1f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.2f, KeyframeAnimations.posVec(12f, -1.5f, -2f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.posVec(16f, 14f, -6f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.2f, KeyframeAnimations.degreeVec(0f, 90f, -15f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0.5f, 0.5f, 1f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition CLOSING = AnimationDefinition.Builder.withLength(0.8f)
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, 3f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.posVec(-5f, -6f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-40f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(-90f, 0f, 90f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("front",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -30f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("back",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 30f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("lock",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -50f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, -45f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, -0.1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("locktip",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 67.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.8f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetr",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -27.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheetl",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 27.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("tongue",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(35f, -10f, 5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(45f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("rinner",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -35f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("linnel",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 35f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 90f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("inner",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -1f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("sheet",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(16f, 14f, -6f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.posVec(12f, -1.5f, -2f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 45f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 90f, -15f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0.5f, 0.5f, 1f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition LEVITATE_FAMILIAR = AnimationDefinition.Builder.withLength(3f).looping()
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 4f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.52f, KeyframeAnimations.posVec(0f, 8f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3f, KeyframeAnimations.posVec(0f, 4f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("feather",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition LEVITATE_DESK = AnimationDefinition.Builder.withLength(3f).looping()
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.52f, KeyframeAnimations.posVec(0f, 3f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1f, 1f, 1f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
}
