package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.block.tileentity.NipaTileEntity;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;

/**
 * Nipa Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.render.NipaTileEntityRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-21
 */
public class NipaTileEntityRenderer implements BlockEntityRenderer<NipaTileEntity> {


    public NipaTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        //TODO
    }

    @Override
    public void render(NipaTileEntity tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (tileEntity.getBlockState().getValue(ModBlockStateProperties.SPECK)) {
            matrixStack.pushPose();

            matrixStack.translate(0.5, tileEntity.getSpeckHeight(), 0.5);
            matrixStack.scale(0.75F, 0.75F, 0.75F);

            matrixStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());

          //  Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get()), ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer);

            matrixStack.popPose();
        }
    }
}
