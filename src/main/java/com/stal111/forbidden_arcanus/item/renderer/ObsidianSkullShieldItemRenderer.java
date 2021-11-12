package com.stal111.forbidden_arcanus.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.model.geom.EntityModelSet;
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

/**
 * Obsidian Skull Shield Item Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.renderer.ObsidianSkullShieldItemRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-12
 */
public class ObsidianSkullShieldItemRenderer extends BlockEntityWithoutLevelRenderer {

   // private final ShieldModel model = new ShieldModel();
    private final ResourceLocation texture = new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/obsidian_skull_shield");

    public ObsidianSkullShieldItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        matrixStack.pushPose();
        matrixStack.scale(1.0F, -1.0F, -1.0F);

        //TODO
        Material material = new Material(TextureAtlas.LOCATION_BLOCKS, this.texture);
     //   VertexConsumer vertexBuilder = material.sprite().wrap(ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(material.atlasLocation()), true, stack.hasFoil()));

       // this.model.renderToBuffer(matrixStack, vertexBuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }
}
