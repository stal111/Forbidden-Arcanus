package com.stal111.forbidden_arcanus.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Obsidian Skull Shield Item Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.item.ObsidianSkullShieldItemRenderer
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-12
 */
public class ObsidianSkullShieldItemRenderer extends BlockEntityWithoutLevelRenderer {

    private final ShieldModel model;
    private final ResourceLocation texture = new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/obsidian_skull_shield");

    public ObsidianSkullShieldItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
        this.model = new ShieldModel(modelSet.bakeLayer(ModelLayers.SHIELD));
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemTransforms.TransformType transformType, PoseStack poseStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);

        Material material = new Material(TextureAtlas.LOCATION_BLOCKS, this.texture);
        VertexConsumer vertexConsumer = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(material.atlasLocation()), true, stack.hasFoil()));

        this.model.renderToBuffer(poseStack, vertexConsumer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
