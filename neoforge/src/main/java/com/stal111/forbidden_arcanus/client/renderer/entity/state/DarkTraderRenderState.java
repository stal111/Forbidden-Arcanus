package com.stal111.forbidden_arcanus.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;

public class DarkTraderRenderState extends LivingEntityRenderState {
    public Identifier texture;
    public final AnimationState spawnAnimation = new AnimationState();
}
