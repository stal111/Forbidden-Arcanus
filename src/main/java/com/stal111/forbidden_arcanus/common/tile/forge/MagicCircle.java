package com.stal111.forbidden_arcanus.common.tile.forge;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.MagicCircleModel;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.util.CustomRenderType;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

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

    public void render(MatrixStack matrixStack, float partialTicks, IRenderTypeBuffer buffer, int combinedLight, MagicCircleModel model) {
        if (this.ritualManager.isRitualActive()) {
            Ritual ritual = this.ritualManager.getActiveRitual();

            matrixStack.push();

            float ticks = this.rotation + partialTicks;

            matrixStack.translate(0.5D, 0.0D, 0.5D);

            float size = 1 + Math.min(ticks, 100) / 100.0F * 7.5F;
            matrixStack.scale(size, 1.0F, size);

            float alpha = ticks > ritual.getTime() * 0.9F ? Math.max((ritual.getTime() - ticks), 0) / (ritual.getTime() * 0.1F) : 1.0F;

            matrixStack.rotate(Vector3f.YN.rotationDegrees(ticks));
            model.getOuterRing().render(matrixStack, buffer.getBuffer(CustomRenderType.getCutoutFullbright(ritual.getOuterTexture())), combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            matrixStack.rotate(Vector3f.YN.rotationDegrees(-(ticks) * 2));
            model.getInnerRing().render(matrixStack, buffer.getBuffer(CustomRenderType.getCutoutFullbright(ritual.getInnerTexture())), combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            matrixStack.pop();

            if (ticks > ritual.getTime() * 0.9F) {
                World world = Objects.requireNonNull(this.ritualManager.getTileEntity().getWorld());
                BlockPos pos = this.ritualManager.getTileEntity().getPos();
                Random rand = world.getRandom();

                double posX = pos.getX() + 0.25D + rand.nextFloat() + rand.nextInt(4);
                double posZ = pos.getZ() + 0.25D + rand.nextFloat() + rand.nextInt(4);
                double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.125D;

                world.addParticle(ModParticles.AUREAL_MOTE.get(), posX - 2.0D, pos.getY() + 0.1F, posZ - 2.0D, 0, ySpeed, 0);
            }
        }
    }
}
