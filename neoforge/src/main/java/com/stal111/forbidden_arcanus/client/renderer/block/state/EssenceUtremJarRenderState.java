package com.stal111.forbidden_arcanus.client.renderer.block.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class EssenceUtremJarRenderState extends BlockEntityRenderState {
    public final AnimationState rotateAnimation = new AnimationState();
    public int amount;
    public int limit;
    public float ageInTicks;
}
