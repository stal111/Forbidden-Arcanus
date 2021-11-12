package com.stal111.forbidden_arcanus.entity.render;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.entity.projectile.DracoArcanusArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DracoArcanusArrowRender extends ArrowRenderer<DracoArcanusArrowEntity> {

    public DracoArcanusArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(DracoArcanusArrowEntity boomArrowEntity) {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/projectiles/draco_arcanus_arrow.png");
    }
}
