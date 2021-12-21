package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stal111.forbidden_arcanus.client.FARenderTypes;
import com.stal111.forbidden_arcanus.client.model.MagicCircleModel;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.init.ModParticles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Random;

/**
 * Magic Circle <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.MagicCircle
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-16
 */
public class MagicCircle {

    private final RitualManager ritualManager;

    private int rotation;

    public MagicCircle(RitualManager ritualManager) {
        this.ritualManager = ritualManager;
    }

    public void tick() {
        if (this.ritualManager.isRitualActive()) {
            this.rotation++;
        } else if (this.rotation != 0) {
            this.rotation = 0;
        }
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void render(PoseStack poseStack, float partialTicks, MultiBufferSource buffer, int packedLight, MagicCircleModel model) {
        if (this.ritualManager.isRitualActive()) {
            Ritual ritual = this.ritualManager.getActiveRitual();

            poseStack.pushPose();

            float ticks = this.rotation + partialTicks;

            poseStack.translate(0.5D, 0.0D, 0.5D);

            float size = 1 + Math.min(ticks, 100) / 100.0F * 7.5F;
            poseStack.scale(size, 1.0F, size);

            float alpha = ticks > ritual.getTime() * 0.9F ? Math.max((ritual.getTime() - ticks), 0) / (ritual.getTime() * 0.1F) : 1.0F;

            poseStack.mulPose(Vector3f.YN.rotationDegrees(ticks));
            model.getOuterRing().render(poseStack, buffer.getBuffer(FARenderTypes.entityFullbrightTranslucent(ritual.getOuterTexture())), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            poseStack.mulPose(Vector3f.YN.rotationDegrees(-ticks * 2));
            model.getInnerRing().render(poseStack, buffer.getBuffer(FARenderTypes.entityFullbrightTranslucent(ritual.getInnerTexture())), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            poseStack.popPose();

            if (ticks > ritual.getTime() * 0.9F) {
                Level level = Objects.requireNonNull(this.ritualManager.getBlockEntity().getLevel());
                BlockPos pos = this.ritualManager.getBlockEntity().getBlockPos();
                Random rand = level.getRandom();

                double posX = pos.getX() + 0.25D + rand.nextFloat() + rand.nextInt(4);
                double posZ = pos.getZ() + 0.25D + rand.nextFloat() + rand.nextInt(4);
                double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.125D;

                level.addParticle(ModParticles.AUREAL_MOTE.get(), posX - 2.0D, pos.getY() + 0.1F, posZ - 2.0D, 0, ySpeed, 0);
            }
        }
    }
}
