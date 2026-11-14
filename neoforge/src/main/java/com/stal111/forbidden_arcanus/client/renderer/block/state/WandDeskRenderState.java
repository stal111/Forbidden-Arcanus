package com.stal111.forbidden_arcanus.client.renderer.block.state;

import com.stal111.forbidden_arcanus.client.renderer.effect.state.MagicCircleRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class WandDeskRenderState extends BlockEntityRenderState {

    public final ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
    public MagicCircleRenderState magicCircleRenderState;
    public Direction direction = Direction.NORTH;
    public float ageInTicks;
}
