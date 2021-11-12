package com.stal111.forbidden_arcanus.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.block.tileentity.render.ObsidianSkullTileEntityRenderer;
import com.stal111.forbidden_arcanus.item.block.EternalObsidianSkullItem;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;

/**
 * Obsidian Skull Item Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.renderer.ObsidianSkullItemRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ObsidianSkullItemRenderer extends BlockEntityWithoutLevelRenderer {

    public ObsidianSkullItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ObsidianSkullTileEntityRenderer.render(null, 180.0F, 0.0F, matrixStack, buffer, combinedLight, stack.getItem() instanceof EternalObsidianSkullItem);
    }
}
