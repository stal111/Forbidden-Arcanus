package com.stal111.forbidden_arcanus.entity.render;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.entity.model.PixieModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class PixieRenderer extends MobRenderer<PixieEntity, PixieModel> {

    private static final ResourceLocation PIXIE_TEXTURES = new ResourceLocation(Main.MOD_ID, "textures/entity/pixie.png");
    private static final RenderType RENDER_TYPE = RenderType.getEntityTranslucent(PIXIE_TEXTURES);

    public PixieRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PixieModel(), 0.4F);
    }

    @Override
    public ResourceLocation getEntityTexture(PixieEntity entity) {
        return PIXIE_TEXTURES;
    }

    @Nullable
    @Override
    protected RenderType func_230042_a_(PixieEntity p_230042_1_, boolean p_230042_2_, boolean p_230042_3_) {
        return RENDER_TYPE;
    }
}
