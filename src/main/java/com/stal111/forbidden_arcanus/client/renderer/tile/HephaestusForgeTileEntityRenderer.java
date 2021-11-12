package com.stal111.forbidden_arcanus.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import com.mojang.math.Vector3f;

import javax.annotation.Nonnull;

/**
 * Hephaestus Forge Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.tile.HephaestusForgeTileEntityRenderer
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-16
 */
public class HephaestusForgeTileEntityRenderer implements BlockEntityRenderer<HephaestusForgeTileEntity> {

    public HephaestusForgeTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        //TODO
    }

    @Override
    public void render(@Nonnull HephaestusForgeTileEntity tileEntity, float partialTicks, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        tileEntity.getMagicCircle().render(matrixStack, partialTicks, buffer, combinedLight);

        ItemStack stack = tileEntity.getItem(4);

        if (!stack.isEmpty()) {
            matrixStack.pushPose();

            matrixStack.translate(0.5D, 1.1D, 0.5D);
            matrixStack.mulPose(Vector3f.YP.rotation((tileEntity.getDisplayCounter() + partialTicks) / 20));

           // Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.GROUND, combinedLight, OverlayTexture.NO_OVERLAY, matrixStack, buffer);

            matrixStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(@Nonnull HephaestusForgeTileEntity tileEntity) {
        return tileEntity.getRitualManager().isRitualActive();
    }
}
