package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.projectile.BoomArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Boom Arrow Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.entity.BoomArrowRenderer
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-16
 */
public class BoomArrowRenderer extends ArrowRenderer<BoomArrow> {

    private static final ResourceLocation LOCATION = ForbiddenArcanus.location("textures/entity/projectiles/boom_arrow.png");

    public BoomArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull BoomArrow entity) {
        return LOCATION;
    }
}
