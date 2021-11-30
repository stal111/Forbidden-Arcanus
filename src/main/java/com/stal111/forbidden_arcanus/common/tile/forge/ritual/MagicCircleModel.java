package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.stal111.forbidden_arcanus.common.tile.forge.MagicCircle;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Magic Circle Model <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.MagicCircleModel
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-30
 */
public class MagicCircleModel {

    private final ModelRenderer outerRing;
    private final ModelRenderer innerRing;

    public MagicCircleModel() {
        this.outerRing = new ModelRenderer(10, 10, 0, 0);
        this.outerRing.addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F);
        this.innerRing = new ModelRenderer(10, 10, 0, 0);
        this.innerRing.addBox(-5.0F, 0.0F, -5.0F, 10.0F, 0.1F, 10.0F);
    }

    public ModelRenderer getOuterRing() {
        return outerRing;
    }

    public ModelRenderer getInnerRing() {
        return innerRing;
    }
}
