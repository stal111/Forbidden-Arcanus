package com.stal111.forbidden_arcanus.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;

public class ModSpriteRenderer extends SpriteRenderer {

    public ModSpriteRenderer(EntityRendererManager manager, float p_i226035_3_, boolean p_i226035_4_) {
        super(manager, Minecraft.getInstance().getItemRenderer(), p_i226035_3_, p_i226035_4_);
    }

    public ModSpriteRenderer(EntityRendererManager manager) {
        super(manager, Minecraft.getInstance().getItemRenderer());
    }
}
