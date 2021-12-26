package com.stal111.forbidden_arcanus.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.client.renderer.block.ObsidianSkullRenderer;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Obsidian Skull Item Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.item.ObsidianSkullItemRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ObsidianSkullItemRenderer extends BlockEntityWithoutLevelRenderer {

    private final Pair<SkullModel, SkullModel> models;

    public ObsidianSkullItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
        this.models = ObsidianSkullRenderer.createModels(modelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, @Nonnull ItemTransforms.TransformType transformType, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            ObsidianSkullRenderer.render(null, 180.0F, poseStack, buffer, combinedLight, this.models, blockItem.getBlock());
        }
    }
}
