package com.stal111.forbidden_arcanus.entity.render;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.entity.model.PixieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PixieRenderer extends MobRenderer<PixieEntity, PixieModel> {

    private static final ResourceLocation PIXIE_TEXTURES = new ResourceLocation(Main.MOD_ID, "textures/entity/pixie.png");

    public PixieRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PixieModel(), 0.25F);
    }

    @Override
    public ResourceLocation getEntityTexture(PixieEntity entity) {
        return PIXIE_TEXTURES;
    }
}
