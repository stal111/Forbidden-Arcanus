package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.client.FARenderTypes;
import com.stal111.forbidden_arcanus.client.model.MagicCircleModel;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.common.network.clientbound.CreateMagicCirclePacket;
import com.stal111.forbidden_arcanus.common.network.clientbound.RemoveMagicCirclePacket;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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

    private final Level level;
    private final BlockPos pos;

    private final ResourceLocation innerTexture;
    private final ResourceLocation outerTexture;

    private int counter;

    public MagicCircle(Level level, BlockPos pos, ResourceLocation innerTexture, ResourceLocation outerTexture) {
        this.level = level;
        this.pos = pos;
        this.innerTexture = innerTexture;
        this.outerTexture = outerTexture;
    }

    public void tick() {
        this.counter++;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void render(PoseStack poseStack, float partialTicks, MultiBufferSource buffer, int packedLight, MagicCircleModel model) {
        float rotation = this.counter + partialTicks;
        float ritualProgress = RitualManager.getRitualProgress(rotation);

        poseStack.pushPose();

        poseStack.translate(0.5D, 0.0D, 0.5D);

        float size = this.easeSineOut(Math.min(ritualProgress, 0.25D), 1.25D, 7.25D, 0.25D);
        poseStack.scale(size, 1.0F, size);

        float alpha = ritualProgress > 0.9F ? this.easeSineOut(ritualProgress - 0.9F, 1.0D, -1.0D, 0.1D) : 1.0F;

        poseStack.mulPose(Axis.YN.rotationDegrees(rotation));
        model.outerRing().render(poseStack, buffer.getBuffer(FARenderTypes.entityFullbrightTranslucent(this.outerTexture)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

        poseStack.mulPose(Axis.YN.rotationDegrees(-rotation * 2));
        model.innerRing().render(poseStack, buffer.getBuffer(FARenderTypes.entityFullbrightTranslucent(this.innerTexture)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

        poseStack.popPose();

        if (ritualProgress > 0.9F) {
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

    public interface TextureProvider {
        ResourceLocation getInnerTexture();
        ResourceLocation getOuterTexture();

        default void createMagicCircle(Level level, BlockPos pos, int progress) {
            NetworkHandler.sendToTrackingChunk(level.getChunkAt(pos), new CreateMagicCirclePacket(pos, this, progress));
        }

        default void removeMagicCircle(Level level, BlockPos pos) {
            NetworkHandler.sendToTrackingChunk(level.getChunkAt(pos), new RemoveMagicCirclePacket(pos));
        }
    }
}
