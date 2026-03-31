package com.stal111.forbidden_arcanus.client.renderer.block.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class QuantumInjectorRenderState extends BlockEntityRenderState {
    public final AnimationState transformAnimation = new AnimationState();
    public final AnimationState rotateAnimation = new AnimationState();
    public float ageInTicks;
    public boolean enabled;
}
