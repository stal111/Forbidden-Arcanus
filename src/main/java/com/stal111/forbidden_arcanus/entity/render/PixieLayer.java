package com.stal111.forbidden_arcanus.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.entity.model.PixieModel;
import com.stal111.forbidden_arcanus.util.CustomRenderType;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class PixieLayer<T extends PixieEntity> extends LayerRenderer<T, PixieModel<T>> {

    private static final RenderType PIXIE_RENDER_TYPE = CustomRenderType.getCutoutFullbright(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/pixie_eyes.png"));
    private static final RenderType CORRUPTED_PIXIE_RENDER_TYPE = CustomRenderType.getCutoutFullbright(new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/corrupted_pixie_eyes.png"));

    public PixieLayer(IEntityRenderer<T, PixieModel<T>> p_i226039_1_) {
        super(p_i226039_1_);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IVertexBuilder ivertexbuilder = entity.getVariant() == 0 ? bufferIn.getBuffer(PIXIE_RENDER_TYPE) : bufferIn.getBuffer(CORRUPTED_PIXIE_RENDER_TYPE);
        this.getEntityModel().render(matrixStackIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
