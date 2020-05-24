package com.stal111.forbidden_arcanus.entity.render;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.entity.model.PixieModel;
import com.stal111.forbidden_arcanus.util.CustomRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;

public class PixieLayer<T extends PixieEntity> extends AbstractEyesLayer<T, PixieModel<T>> {

    private static final RenderType RENDER_TYPE = CustomRenderType.getEyes(new ResourceLocation(Main.MOD_ID, "textures/entity/pixie_eyes.png"));

    public PixieLayer(IEntityRenderer<T, PixieModel<T>> p_i226039_1_) {
        super(p_i226039_1_);
    }

    @Override
    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
