package com.stal111.forbidden_arcanus.common.tile.forge;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.network.UpdateMagicCirclePacket;
import com.stal111.forbidden_arcanus.util.CustomRenderType;
import com.stal111.forbidden_arcanus.util.ISavedData;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

/**
 * Magic Circle Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.MagicCircleRenderer
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-16
 */
public class MagicCircle implements ISavedData {

    private ResourceLocation outerTexture;
    private ResourceLocation innerTexture;

    private final ModelRenderer outerRing;
    private final ModelRenderer innerRing;

    private int size;
    private int rotation;

    public MagicCircle() {
        this.outerRing = new ModelRenderer(10, 10, 0, 0);
        this.outerRing.addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F);
        this.innerRing = new ModelRenderer(10, 10, 0, 0);
        this.innerRing.addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F);
    }

    public void tick() {
        if (this.hasTextures()) {
            if (this.size < 100) {
                this.size++;
            }
            this.rotation++;
        }
    }

    public void setTextures(PlayerEntity player, BlockPos pos, ResourceLocation outerTexture, ResourceLocation innerTexture) {
        this.setOuterTexture(outerTexture);
        this.setInnerTexture(innerTexture);

        this.size = 0;

        NetworkHandler.sendTo(player, new UpdateMagicCirclePacket(pos, outerTexture, innerTexture));
    }

    public boolean hasTextures() {
        return this.innerTexture != null && this.outerTexture != null;
    }

    public void setOuterTexture(ResourceLocation outerTexture) {
        this.outerTexture = outerTexture;
    }

    public void setInnerTexture(ResourceLocation innerTexture) {
        this.innerTexture = innerTexture;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void render(MatrixStack matrixStack, float partialTicks, IRenderTypeBuffer buffer, int combinedLight) {
        if (this.hasTextures()) {
            matrixStack.push();

            matrixStack.translate(0.5D, 0.0D, 0.5D);

            float size = 1 + this.size / 100.0F * 7.5F;
            matrixStack.scale(size, 1.0F, size);

            matrixStack.rotate(Vector3f.YN.rotationDegrees(this.rotation + partialTicks));
            this.outerRing.render(matrixStack, buffer.getBuffer(CustomRenderType.getCutoutFullbright(this.outerTexture)), combinedLight, OverlayTexture.NO_OVERLAY);

            matrixStack.rotate(Vector3f.YN.rotationDegrees(-(this.rotation + partialTicks) * 2 ));
            this.innerRing.render(matrixStack, buffer.getBuffer(CustomRenderType.getCutoutFullbright(this.innerTexture)), combinedLight, OverlayTexture.NO_OVERLAY);

            matrixStack.pop();
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (this.outerTexture != null) {
            compound.putString("OuterTexture", this.outerTexture.toString());
        }
        if (this.innerTexture != null) {
            compound.putString("InnerTexture", this.innerTexture.toString());
        }
        compound.putInt("Size", this.size);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("OuterTexture")) {
            this.outerTexture = new ResourceLocation(compound.getString("OuterTexture"));
        }
        if (compound.contains("InnerTexture")) {
            this.innerTexture = new ResourceLocation(compound.getString("InnerTexture"));
        }
        this.size = compound.getInt("Size");
    }
}
