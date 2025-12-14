package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.stal111.forbidden_arcanus.client.model.FAModelLayers;
import com.stal111.forbidden_arcanus.client.model.LostSoulModel;
import com.stal111.forbidden_arcanus.client.renderer.entity.state.LostSoulRenderState;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.AbstractLostSoul;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2022-09-14
 */
public class LostSoulRenderer extends MobRenderer<AbstractLostSoul, LostSoulRenderState, LostSoulModel> {

    private final Identifier texture;

    public LostSoulRenderer(EntityRendererProvider.Context context, Identifier texture) {
        super(context, new LostSoulModel(context.bakeLayer(FAModelLayers.LOST_SOUL)), 0.0F);
        this.texture = texture;
    }

    @Nonnull
    @Override
    public Identifier getTextureLocation(@Nonnull LostSoulRenderState renderState) {
        return this.texture;
    }

    @Override
    public @NotNull LostSoulRenderState createRenderState() {
        return new LostSoulRenderState();
    }
}
