package com.stal111.forbidden_arcanus.common.tile.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.init.ModParticles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Random;

/**
 * Magic Circle Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.MagicCircleRenderer
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-16
 */
public class MagicCircle {

    private final RitualManager ritualManager;

//    private final ModelPart outerRing;
//    private final ModelPart innerRing;

    private int rotation;

    public MagicCircle(RitualManager ritualManager) {
        this.ritualManager = ritualManager;

        //TODO

//        this.outerRing = new ModelPart(10, 10, 0, 0);
//        this.outerRing.addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F);
//        this.innerRing = new ModelPart(10, 10, 0, 0);
//        this.innerRing.addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F);
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

    public void render(PoseStack matrixStack, float partialTicks, MultiBufferSource buffer, int combinedLight) {
        if (this.ritualManager.isRitualActive()) {
            Ritual ritual = this.ritualManager.getActiveRitual();

            matrixStack.pushPose();

            float ticks = this.rotation + partialTicks;

            matrixStack.translate(0.5D, 0.0D, 0.5D);

            float size = 1 + Math.min(ticks, 100) / 100.0F * 7.5F;
            matrixStack.scale(size, 1.0F, size);

            float alpha = ticks > ritual.getTime() * 0.9F ? Math.max((ritual.getTime() - ticks), 0) / (ritual.getTime() * 0.1F) : 1.0F;

            matrixStack.mulPose(Vector3f.YN.rotationDegrees(ticks));
          //  this.outerRing.render(matrixStack, buffer.getBuffer(CustomRenderType.getCutoutFullbright(ritual.getOuterTexture())), combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            matrixStack.mulPose(Vector3f.YN.rotationDegrees(-(ticks) * 2));
          //  this.innerRing.render(matrixStack, buffer.getBuffer(CustomRenderType.getCutoutFullbright(ritual.getInnerTexture())), combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            matrixStack.popPose();

            if (ticks > ritual.getTime() * 0.9F) {
                Level world = Objects.requireNonNull(this.ritualManager.getTileEntity().getLevel());
                BlockPos pos = this.ritualManager.getTileEntity().getBlockPos();
                Random rand = world.getRandom();

                double posX = pos.getX() + 0.25D + rand.nextFloat() + rand.nextInt(4);
                double posZ = pos.getZ() + 0.25D + rand.nextFloat() + rand.nextInt(4);
                double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.125D;

                world.addParticle(ModParticles.AUREAL_MOTE.get(), posX - 2.0D, pos.getY() + 0.1F, posZ - 2.0D, 0, ySpeed, 0);
            }
        }
    }
}
