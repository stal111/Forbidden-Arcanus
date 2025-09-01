package com.stal111.forbidden_arcanus.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class LostSoulRenderState extends LivingEntityRenderState {

    public final AnimationState still = new AnimationState();
    public final AnimationState fear = new AnimationState();
}
