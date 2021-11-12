package com.stal111.forbidden_arcanus.entity.render;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.entity.projectile.BoomArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BoomArrowRender extends ArrowRenderer<BoomArrowEntity> {

    public BoomArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BoomArrowEntity boomArrowEntity) {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/projectiles/boom_arrow.png");
    }
}
