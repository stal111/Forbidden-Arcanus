package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.client.FARenderTypes;
import com.stal111.forbidden_arcanus.client.model.MagicCircleModel;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

/**
 * Magic Circle <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.MagicCircle
 *
 * @author stal111
 * @since 2021-07-16
 */
public class MagicCircle {

    private final MagicCircleType type;

    private final Level level;
    private final BlockPos pos;

    private int counter;

    public MagicCircle(MagicCircleType type, Level level, BlockPos pos) {
        this.type = type;
        this.level = level;
        this.pos = pos;
    }

    public void tick() {
        this.counter++;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void render(PoseStack poseStack, float partialTicks, MultiBufferSource buffer, int packedLight, MagicCircleModel model, int duration) {
        float rotation = this.counter + partialTicks;
        float progress = rotation / duration;

        poseStack.pushPose();

        poseStack.translate(0.5D, 0.0D, 0.5D);

        float size = this.easeSineOut(Math.min(progress, 0.25D), 1.25D, 7.25D, 0.25D);
        poseStack.scale(size, 1.0F, size);

        float alpha = progress > 0.9F ? this.easeSineOut(progress - 0.9F, 1.0D, -1.0D, 0.1D) : 1.0F;

        poseStack.mulPose(Axis.YN.rotationDegrees(rotation));
        model.outerRing().render(poseStack, buffer.getBuffer(FARenderTypes.entityFullbrightTranslucent(this.type.outerTexture())), packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.mulPose(Axis.YN.rotationDegrees(-rotation * 2));
        model.innerRing().render(poseStack, buffer.getBuffer(FARenderTypes.entityFullbrightTranslucent(this.type.innerTexture())), packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();

        if (progress > 0.9F) {
            RandomSource random = this.level.getRandom();

            double posX = this.pos.getX() + 0.25D + random.nextFloat() + random.nextInt(4);
            double posZ = this.pos.getZ() + 0.25D + random.nextFloat() + random.nextInt(4);
            double ySpeed = ((double) random.nextFloat() - 0.4D) * 0.125D;

            this.level.addParticle(ModParticles.AUREAL_MOTE.get(), posX - 2.0D, this.pos.getY() + 0.1F, posZ - 2.0D, 0, ySpeed, 0);
        }
    }

    public float easeSineIn(double progress, double start, double change, double duration) {
        return (float) (-change * Math.cos(progress / duration * (Math.PI / 2)) + change + start);
    }

    public float easeSineOut(double progress, double start, double change, double duration) {
        return (float) (change * Math.sin(progress / duration * (Math.PI / 2)) + start);
    }
}
