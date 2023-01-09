package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.client.FARenderTypes;
import com.stal111.forbidden_arcanus.client.model.MagicCircleModel;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

import java.util.Objects;

/**
 * Magic Circle <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.MagicCircle
 *
 * @author stal111
 * @since 2021-07-16
 */
public class MagicCircle {

    private final RitualManager ritualManager;

    private int counter;

    public MagicCircle(RitualManager ritualManager) {
        this.ritualManager = ritualManager;
    }

    public void tick() {
        if (this.ritualManager.isRitualActive()) {
            this.counter++;
        } else if (this.counter != 0) {
            this.counter = 0;
        }
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void render(PoseStack poseStack, float partialTicks, MultiBufferSource buffer, int packedLight, MagicCircleModel model) {
        if (this.ritualManager.isRitualActive()) {
            Ritual ritual = this.ritualManager.getActiveRitual();
            float rotation = this.counter + partialTicks;
            float ritualProgress = RitualManager.getRitualProgress(rotation);

            poseStack.pushPose();

            poseStack.translate(0.5D, 0.0D, 0.5D);

            float size = this.easeSineOut(Math.min(ritualProgress, 0.25D), 1.25D, 7.25D, 0.25D);
            poseStack.scale(size, 1.0F, size);

            float alpha = ritualProgress > 0.9F ? this.easeSineOut(ritualProgress - 0.9F, 1.0D, -1.0D, 0.1D) : 1.0F;

            poseStack.mulPose(Axis.YN.rotationDegrees(rotation));
            model.getOuterRing().render(poseStack, buffer.getBuffer(FARenderTypes.entityFullbrightTranslucent(ritual.getOuterTexture())), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            poseStack.mulPose(Axis.YN.rotationDegrees(-rotation * 2));
            model.getInnerRing().render(poseStack, buffer.getBuffer(FARenderTypes.entityFullbrightTranslucent(ritual.getInnerTexture())), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            poseStack.popPose();

            if (ritualProgress > 0.9F) {
                Level level = Objects.requireNonNull(this.ritualManager.getBlockEntity().getLevel());
                BlockPos pos = this.ritualManager.getBlockEntity().getBlockPos();
                RandomSource random = level.getRandom();

                double posX = pos.getX() + 0.25D + random.nextFloat() + random.nextInt(4);
                double posZ = pos.getZ() + 0.25D + random.nextFloat() + random.nextInt(4);
                double ySpeed = ((double) random.nextFloat() - 0.4D) * 0.125D;

                level.addParticle(ModParticles.AUREAL_MOTE.get(), posX - 2.0D, pos.getY() + 0.1F, posZ - 2.0D, 0, ySpeed, 0);
            }
        }
    }

    public float easeSineIn(double progress, double start, double change, double duration) {
        return (float) (-change * Math.cos(progress / duration * (Math.PI / 2)) + change + start);
    }

    public float easeSineOut(double progress, double start, double change, double duration) {
        return (float) (change * Math.sin(progress / duration * (Math.PI / 2)) + start);
    }
}
