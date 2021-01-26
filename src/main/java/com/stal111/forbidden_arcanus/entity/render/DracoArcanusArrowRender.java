package com.stal111.forbidden_arcanus.entity.render;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.entity.projectile.DracoArcanusArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DracoArcanusArrowRender extends ArrowRenderer<DracoArcanusArrowEntity> {

    public DracoArcanusArrowRender(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(DracoArcanusArrowEntity boomArrowEntity) {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/projectiles/draco_arcanus_arrow.png");
    }
}
