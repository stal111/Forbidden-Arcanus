package com.stal111.forbidden_arcanus.entity.render;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.BoomArrowEntity;
import com.stal111.forbidden_arcanus.entity.projectile.FrozenArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class FrozenArrowRender extends ArrowRenderer<FrozenArrowEntity> {

    public FrozenArrowRender(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(FrozenArrowEntity boomArrowEntity) {
        return new ResourceLocation(Main.MOD_ID, "textures/entity/projectiles/frozen_arrow.png");
    }
}
