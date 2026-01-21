package com.stal111.forbidden_arcanus.client.renderer.block.state;

import com.stal111.forbidden_arcanus.client.renderer.effect.state.MagicCircleRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class WandDeskRenderState extends BlockEntityRenderState {
    public MagicCircleRenderState magicCircleRenderState;
    public ItemStackRenderState itemStackRenderState;
    public float ageInTicks;
}
