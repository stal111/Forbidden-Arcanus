package com.stal111.forbidden_arcanus.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.client.model.FAArmPoses;
import com.stal111.forbidden_arcanus.common.item.MagicWandItem;
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

public final class WandClientItemExtensions implements IClientItemExtensions {

    private static final float BASE_ARM_X = 0.56F;
    private static final float BASE_ARM_Y = -0.52F;
    private static final float BASE_ARM_Z = -0.72F;
    private static final float EQUIP_Y_SCALE = -0.6F;

    private static final float RAISE_TICKS = 6.0F;
    private static final float ORBIT_PERIOD_TICKS = 10.0F;
    private static final float ORBIT_RADIUS = 0.03F;
    private static final float READY_SHAKE_FREQUENCY = 1.3F;
    private static final float READY_SHAKE_AMPLITUDE = 0.004F;

    private static final float CAST_OFFSET_X = -0.26F;
    private static final float CAST_OFFSET_Y = 0.24F;
    private static final float CAST_OFFSET_Z = 0.22F;
    private static final float CHARGE_PULL_Z = 0.05F;

    private static final float CAST_PITCH_DEGREES = -50.0F;
    private static final float ORBIT_WRIST_DEGREES = 3.5F;
    private static final float GRIP_PIVOT_Y = -0.15F;

    @Override
    public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        if (entityLiving.isUsingItem() && entityLiving.getUseItemRemainingTicks() > 0 && entityLiving.getUsedItemHand() == hand) {
            return FAArmPoses.WAND_CAST.getValue();
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
        float charge = Mth.clamp(ticksUsing / MagicWandItem.CHARGE_DURATION, 0.0F, 1.0F);

        float angle = ticksUsing * (Mth.TWO_PI / ORBIT_PERIOD_TICKS);
        float sinAngle = Mth.sin(angle);
        float cosAngle = Mth.cos(angle);

        float orbitScale = raise * (0.4F + 0.6F * charge);
        float xOrbit = invert * ORBIT_RADIUS * orbitScale * sinAngle;
        float yOrbit = ORBIT_RADIUS * orbitScale * cosAngle;
        float readyShake = charge >= 1.0F ? Mth.sin((ticksUsing - 0.1F) * READY_SHAKE_FREQUENCY) * READY_SHAKE_AMPLITUDE : 0.0F;

        poseStack.translate(
                invert * (BASE_ARM_X + CAST_OFFSET_X * raise) + xOrbit,
                BASE_ARM_Y + equipProcess * EQUIP_Y_SCALE + CAST_OFFSET_Y * raise + yOrbit + readyShake,
                BASE_ARM_Z + CAST_OFFSET_Z * raise + CHARGE_PULL_Z * charge
        );

        float pitchRad = (CAST_PITCH_DEGREES * raise + ORBIT_WRIST_DEGREES * orbitScale * cosAngle) * Mth.DEG_TO_RAD;
        float yawRad = invert * (ORBIT_WRIST_DEGREES * orbitScale * sinAngle) * Mth.DEG_TO_RAD;

        poseStack.rotateAround(new Quaternionf().rotationXYZ(pitchRad, yawRad, 0.0F), 0.0F, GRIP_PIVOT_Y, 0.0F);

        return true;
    }
}
