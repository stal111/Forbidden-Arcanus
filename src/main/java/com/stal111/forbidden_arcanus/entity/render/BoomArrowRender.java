package com.stal111.forbidden_arcanus.entity.render;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.entity.projectile.BoomArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BoomArrowRender extends ArrowRenderer<BoomArrowEntity> {

    public BoomArrowRender(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(BoomArrowEntity boomArrowEntity) {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/projectiles/boom_arrow.png");
    }
}
