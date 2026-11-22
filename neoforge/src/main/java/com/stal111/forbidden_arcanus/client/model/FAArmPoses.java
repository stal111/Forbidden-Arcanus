package com.stal111.forbidden_arcanus.client.model;

import com.stal111.forbidden_arcanus.common.item.EctoplasmExtractorItem;
import com.stal111.forbidden_arcanus.common.item.MagicWandItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Ease;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public final class FAArmPoses {

    private static final float WAND_RAISE_TICKS = 6.0F;
    private static final float WAND_ORBIT_PERIOD_TICKS = 10.0F;
    private static final float WAND_SWAY_RADIANS = 0.045F;
    private static final float WAND_BASE_X_ROT = -0.75F;
    private static final float WAND_HEAD_X_FOLLOW = 0.65F;
    private static final float WAND_OUTWARD_Y_ROT = 0.14F;
    private static final float WAND_OUTWARD_Z_ROT = 0.12F;

    private static final float BLASTER_RAISE_TICKS = 5.0F;
    private static final float BLASTER_PULSE_PERIOD_TICKS = 10.0F;
    private static final float BLASTER_PULSE_PEAK_OFFSET = 5.0F;
    private static final float BLASTER_INWARD_Y_ROT = 0.06F;
    private static final float BLASTER_PULSE_KICK_RADIANS = 0.055F;
    private static final float BLASTER_RUMBLE_X_RADIANS = 0.01F;
    private static final float BLASTER_RUMBLE_Y_RADIANS = 0.008F;
    private static final float BLASTER_RUMBLE_FREQ_X = 2.6F;
    private static final float BLASTER_RUMBLE_FREQ_Y = 3.1F;

    public static final EnumProxy<HumanoidModel.ArmPose> WAND_CAST = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            false,
            false,
            (IArmPoseTransformer) FAArmPoses::applyWandCastPose
    );

    public static final EnumProxy<HumanoidModel.ArmPose> ECTO_BLASTER_USE = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            false,
            false,
            (IArmPoseTransformer) FAArmPoses::applyEctoBlasterPose
    );

    private FAArmPoses() {
    }

    private static void applyWandCastPose(HumanoidModel<?> model, HumanoidRenderState state, HumanoidArm arm) {
        ModelPart armPart = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        int invert = arm == HumanoidArm.RIGHT ? 1 : -1;

        float ticksUsing = state.ticksUsingItem(arm);
        float raise = Ease.outCubic(Mth.clamp(ticksUsing / WAND_RAISE_TICKS, 0.0F, 1.0F));
        float charge = Mth.clamp(ticksUsing / MagicWandItem.CHARGE_DURATION, 0.0F, 1.0F);

        float idleXRot = armPart.xRot * 0.5F - Mth.PI / 10.0F;
        float targetXRot = Mth.clamp(model.head.xRot, -1.0F, 0.8F) * WAND_HEAD_X_FOLLOW + WAND_BASE_X_ROT;
        float targetYRot = Mth.clamp(model.head.yRot, -0.8F, 0.8F) + invert * WAND_OUTWARD_Y_ROT;
        float targetZRot = invert * WAND_OUTWARD_Z_ROT;

        float angle = ticksUsing * (Mth.TWO_PI / WAND_ORBIT_PERIOD_TICKS);
        float sway = WAND_SWAY_RADIANS * raise * (0.4F + 0.6F * charge);

        armPart.xRot = Mth.lerp(raise, idleXRot, targetXRot) + sway * Mth.cos(angle);
        armPart.yRot = Mth.lerp(raise, 0.0F, targetYRot) + invert * sway * Mth.sin(angle);
        armPart.zRot = Mth.lerp(raise, armPart.zRot, targetZRot);
    }

    private static void applyEctoBlasterPose(HumanoidModel<?> model, HumanoidRenderState state, HumanoidArm arm) {
        ModelPart armPart = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        int invert = arm == HumanoidArm.RIGHT ? 1 : -1;

        float ticksUsing = state.ticksUsingItem(arm);
        float raise = Ease.outCubic(Mth.clamp(ticksUsing / BLASTER_RAISE_TICKS, 0.0F, 1.0F));
        float progress = Mth.clamp(ticksUsing / EctoplasmExtractorItem.USE_DURATION, 0.0F, 1.0F);

        // Peak in sync with EctoplasmExtractorItem's 10-tick extraction bursts (ticks 5, 15, 25, 35)
        float pulseWave = 0.5F * (1.0F + Mth.cos((ticksUsing - BLASTER_PULSE_PEAK_OFFSET) * (Mth.TWO_PI / BLASTER_PULSE_PERIOD_TICKS)));
        float pulseKick = raise * Mth.cube(pulseWave);
        float rumbleIntensity = raise * (0.35F + 0.65F * Ease.inQuad(progress));

        float idleXRot = armPart.xRot * 0.5F - Mth.PI / 10.0F;
        float targetXRot = Mth.clamp(model.head.xRot, -1.2F, 1.2F) - Mth.HALF_PI;
        float targetYRot = Mth.clamp(model.head.yRot, -0.8F, 0.8F) - invert * BLASTER_INWARD_Y_ROT;

        armPart.xRot = Mth.lerp(raise, idleXRot, targetXRot)
                - BLASTER_PULSE_KICK_RADIANS * pulseKick
                + Mth.sin(ticksUsing * BLASTER_RUMBLE_FREQ_X) * BLASTER_RUMBLE_X_RADIANS * rumbleIntensity;
        armPart.yRot = Mth.lerp(raise, 0.0F, targetYRot)
                + Mth.cos(ticksUsing * BLASTER_RUMBLE_FREQ_Y) * BLASTER_RUMBLE_Y_RADIANS * rumbleIntensity;
    }
}
