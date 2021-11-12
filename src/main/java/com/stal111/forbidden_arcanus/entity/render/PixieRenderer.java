package com.stal111.forbidden_arcanus.entity.render;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.entity.model.PixieModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class PixieRenderer extends MobRenderer<PixieEntity, PixieModel<PixieEntity>> {

    private static final ResourceLocation[] PIXIE_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/pixie.png"),
            new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/corrupted_pixie.png")
    };

    public PixieRenderer(EntityRendererProvider.Context context) {
        super(context, new PixieModel<>(), 0.4F);
        this.addLayer(new PixieLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(PixieEntity entity) {
        return PIXIE_TEXTURES[entity.getVariant()];
    }

    @Nullable
    @Override
    protected RenderType getRenderType(PixieEntity entity, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        return RenderType.entityTranslucent(PIXIE_TEXTURES[entity.getVariant()]);
    }
}
