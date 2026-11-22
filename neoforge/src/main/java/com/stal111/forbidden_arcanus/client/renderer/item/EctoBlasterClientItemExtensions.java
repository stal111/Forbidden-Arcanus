package com.stal111.forbidden_arcanus.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.client.model.FAArmPoses;
import com.stal111.forbidden_arcanus.common.item.EctoplasmExtractorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Ease;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Quaternionf;
import org.jspecify.annotations.Nullable;

public final class EctoBlasterClientItemExtensions implements IClientItemExtensions {

    private static final float BASE_ARM_X = 0.56F;
    private static final float BASE_ARM_Y = -0.52F;
    private static final float BASE_ARM_Z = -0.72F;
    private static final float EQUIP_Y_SCALE = -0.6F;

    private static final float RAISE_TICKS = 5.0F;
    private static final float PULSE_PERIOD_TICKS = 10.0F;
    private static final float PULSE_PEAK_OFFSET = 5.0F;

    private static final float RUMBLE_FREQ_X = 2.6F;
    private static final float RUMBLE_FREQ_Y = 3.1F;
    private static final float RUMBLE_FREQ_ROLL = 2.9F;
    private static final float RUMBLE_OFFSET_X = 0.003F;
    private static final float RUMBLE_OFFSET_Y = 0.0025F;

    private static final float AIM_OFFSET_X = -0.14F;
    private static final float AIM_OFFSET_Y = 0.06F;
    private static final float AIM_OFFSET_Z = -0.06F;
    private static final float SUCTION_PULL_Z = 0.03F;
    private static final float PULSE_RECOIL_Z = 0.045F;

    private static final float AIM_PITCH_DEGREES = -6.0F;
    private static final float PULSE_PITCH_DEGREES = 2.5F;
    private static final float AIM_YAW_DEGREES = 10.0F;
    private static final float RUMBLE_ROLL_DEGREES = 0.9F;

    private static final float GRIP_PIVOT_Y = -0.1F;
    private static final float GRIP_PIVOT_Z = 0.1F;

    @Override
    public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        if (entityLiving.isUsingItem() && entityLiving.getUseItemRemainingTicks() > 0 && entityLiving.getUsedItemHand() == hand) {
            return FAArmPoses.ECTO_BLASTER_USE.getValue();
        }
        return null;
    }

    @Override
    public boolean applyForgeHandTransform(
            PoseStack poseStack,
            LocalPlayer player,
            HumanoidArm arm,
            ItemStack itemInHand,
            float partialTick,
            float equipProcess,
            float swingProcess
    ) {
        InteractionHand hand = arm == player.getMainArm() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        if (!player.isUsingItem() || player.getUseItemRemainingTicks() <= 0 || player.getUsedItemHand() != hand) {
            return false;
        }

        int invert = arm == HumanoidArm.RIGHT ? 1 : -1;
        float ticksUsing = player.getTicksUsingItem(partialTick);
        float raise = Ease.outCubic(Mth.clamp(ticksUsing / RAISE_TICKS, 0.0F, 1.0F));
        float progress = Mth.clamp(ticksUsing / EctoplasmExtractorItem.USE_DURATION, 0.0F, 1.0F);

        // Sync suction pulse peaks with EctoplasmExtractorItem's 10-tick extraction bursts (ticks 5, 15, 25, 35)
        float pulseWave = 0.5F * (1.0F + Mth.cos((ticksUsing - PULSE_PEAK_OFFSET) * (Mth.TWO_PI / PULSE_PERIOD_TICKS)));
        float pulseKick = raise * Mth.cube(pulseWave);
        float rumbleIntensity = raise * (0.35F + 0.65F * Ease.inQuad(progress));

        float rumbleX = Mth.sin(ticksUsing * RUMBLE_FREQ_X) * RUMBLE_OFFSET_X * rumbleIntensity;
        float rumbleY = Mth.cos(ticksUsing * RUMBLE_FREQ_Y) * RUMBLE_OFFSET_Y * rumbleIntensity;

        poseStack.translate(
                invert * (BASE_ARM_X + AIM_OFFSET_X * raise) + rumbleX,
                BASE_ARM_Y + equipProcess * EQUIP_Y_SCALE + AIM_OFFSET_Y * raise + rumbleY,
                BASE_ARM_Z + AIM_OFFSET_Z * raise + SUCTION_PULL_Z * progress + PULSE_RECOIL_Z * pulseKick
        );

        float pitchRad = (AIM_PITCH_DEGREES * raise + PULSE_PITCH_DEGREES * pulseKick) * Mth.DEG_TO_RAD;
        float yawRad = invert * (AIM_YAW_DEGREES * raise) * Mth.DEG_TO_RAD;
        float rollRad = invert * (Mth.sin(ticksUsing * RUMBLE_FREQ_ROLL) * RUMBLE_ROLL_DEGREES * rumbleIntensity) * Mth.DEG_TO_RAD;

        poseStack.rotateAround(new Quaternionf().rotationXYZ(pitchRad, yawRad, rollRad), 0.0F, GRIP_PIVOT_Y, GRIP_PIVOT_Z);

        return true;
    }
}
