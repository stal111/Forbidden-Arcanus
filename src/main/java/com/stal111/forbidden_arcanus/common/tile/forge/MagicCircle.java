package com.stal111.forbidden_arcanus.common.tile.forge;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.util.CustomRenderType;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ModelRenderer;
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

    private final ModelRenderer outerRing;
    private final ModelRenderer innerRing;

    private int rotation;

    public MagicCircle(RitualManager ritualManager) {
        this.ritualManager = ritualManager;

        this.outerRing = new ModelRenderer(10, 10, 0, 0);
        this.outerRing.addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F);
        this.innerRing = new ModelRenderer(10, 10, 0, 0);
        this.innerRing.addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F);
    }

    public void tick() {
        if (this.ritualManager.isRitualActive()) {
            this.rotation++;
        }
    }

    public void render(MatrixStack matrixStack, float partialTicks, IRenderTypeBuffer buffer, int combinedLight) {
        if (this.ritualManager.isRitualActive()) {
            Ritual ritual = this.ritualManager.getActiveRitual();

            matrixStack.push();

            float ticks = this.ritualManager.getCounter() + partialTicks;

            matrixStack.translate(0.5D, 0.0D, 0.5D);

            float size = 1 + Math.min(ticks, 100) / 100.0F * 7.5F;
            matrixStack.scale(size, 1.0F, size);

            float alpha = ticks > ritual.getTime() * 0.95F ? Math.max((ritual.getTime() - ticks), 0) / (ritual.getTime() * 0.05F) : 1.0F;

            matrixStack.rotate(Vector3f.YN.rotationDegrees(this.rotation + partialTicks));
            this.outerRing.render(matrixStack, buffer.getBuffer(CustomRenderType.getCutoutFullbright(ritual.getOuterTexture())), combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            matrixStack.rotate(Vector3f.YN.rotationDegrees(-(this.rotation + partialTicks) * 2));
            this.innerRing.render(matrixStack, buffer.getBuffer(CustomRenderType.getCutoutFullbright(ritual.getInnerTexture())), combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, alpha);

            matrixStack.pop();

            if (ticks > ritual.getTime() * 0.95F) {
                World world = Objects.requireNonNull(this.ritualManager.getTileEntity().getWorld());
                BlockPos pos = this.ritualManager.getTileEntity().getPos();
                Random rand = world.getRandom();

                int x = rand.nextInt(3);
                int z = rand.nextInt(3);
                double posX = pos.getX() + rand.nextFloat() + (double) x + rand.nextFloat() / 2;
                double posZ = pos.getZ() + rand.nextFloat() + (double) z + rand.nextFloat() / 2;
                double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.125D;

                world.addParticle(ModParticles.AUREAL_MOTE.get(), posX - 1.5D, pos.getY() + 0.1F, posZ - 1.5D, 0, ySpeed, 0);
            }
        }
    }
}
