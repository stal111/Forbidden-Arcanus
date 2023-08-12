package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.stal111.forbidden_arcanus.client.model.DarkTraderModel;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTrader;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-08-11
 */
public class DarkTraderRenderer extends MobRenderer<DarkTrader, DarkTraderModel> {

    public DarkTraderRenderer(EntityRendererProvider.Context context) {
        super(context, new DarkTraderModel(context.bakeLayer(DarkTraderModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DarkTrader entity) {
        return entity.getVariant().texture();
    }
}
