package com.stal111.forbidden_arcanus.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.entity.model.PixieModel;
import com.stal111.forbidden_arcanus.util.CustomRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PixieLayer<T extends PixieEntity> extends RenderLayer<T, PixieModel<T>> {

    private static final RenderType PIXIE_RENDER_TYPE = CustomRenderType.getCutoutFullbright(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/pixie_eyes.png"));
    private static final RenderType CORRUPTED_PIXIE_RENDER_TYPE = CustomRenderType.getCutoutFullbright(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/corrupted_pixie_eyes.png"));

    public PixieLayer(RenderLayerParent<T, PixieModel<T>> p_i226039_1_) {
        super(p_i226039_1_);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer ivertexbuilder = entity.getVariant() == 0 ? bufferIn.getBuffer(PIXIE_RENDER_TYPE) : bufferIn.getBuffer(CORRUPTED_PIXIE_RENDER_TYPE);
        this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
