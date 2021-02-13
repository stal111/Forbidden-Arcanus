package com.stal111.forbidden_arcanus.item.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Obsidian Skull Shield Item Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.renderer.ObsidianSkullShieldItemRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-12
 */
public class ObsidianSkullShieldItemRenderer extends ItemStackTileEntityRenderer {

    private final ShieldModel model = new ShieldModel();
    private final ResourceLocation texture = new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity/obsidian_skull_shield");

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        matrixStack.push();
        matrixStack.scale(1.0F, -1.0F, -1.0F);

        RenderMaterial material = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, this.texture);
        IVertexBuilder vertexBuilder = material.getSprite().wrapBuffer(ItemRenderer.getEntityGlintVertexBuilder(buffer, this.model.getRenderType(material.getAtlasLocation()), true, stack.hasEffect()));

        this.model.render(matrixStack, vertexBuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
    }
}
