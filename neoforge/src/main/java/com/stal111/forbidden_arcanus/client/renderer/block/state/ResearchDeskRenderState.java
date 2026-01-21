package com.stal111.forbidden_arcanus.client.renderer.block.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class ResearchDeskRenderState extends BlockEntityRenderState {
    public final AnimationState stillAnimation = new AnimationState();
    public final AnimationState openingAnimation = new AnimationState();
    public final AnimationState closingAnimation = new AnimationState();
    public final AnimationState levitateAnimation = new AnimationState();
    public final AnimationState pageAnimation = new AnimationState();
    public float ageInTicks;
    public float yRot;
}
