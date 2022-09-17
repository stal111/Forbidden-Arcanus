package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.google.common.collect.Maps;
import com.stal111.forbidden_arcanus.client.model.LostSoulModel;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author stal111
 * @since 2022-09-14
 */
public class LostSoulRenderer extends MobRenderer<LostSoul, LostSoulModel> {

    private static final Map<LostSoul.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), map -> {
        for (LostSoul.Variant variant : LostSoul.Variant.values()) {
            map.put(variant, variant.getTexture());
        }
    });

    public LostSoulRenderer(EntityRendererProvider.Context context) {
        super(context, new LostSoulModel(context.bakeLayer(LostSoulModel.LAYER_LOCATION)), 0.0F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull LostSoul entity) {
        return TEXTURE_BY_TYPE.get(entity.getVariant());
    }
}
