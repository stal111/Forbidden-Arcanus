package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.projectile.DracoArcanusArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Draco Arcanus Arrow Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.entity.DracoArcanusArrowRenderer
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-16
 */
public class DracoArcanusArrowRenderer extends ArrowRenderer<DracoArcanusArrow, ArrowRenderState> {

    private static final Identifier LOCATION = ForbiddenArcanus.identifier("textures/entity/projectiles/draco_arcanus_arrow.png");

    public DracoArcanusArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected @NotNull Identifier getTextureLocation(@NotNull ArrowRenderState renderState) {
        return LOCATION;
    }
}
