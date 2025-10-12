package com.stal111.forbidden_arcanus.client.renderer.block.state;

import com.stal111.forbidden_arcanus.client.renderer.effect.state.ValidRitualIndicatorRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class HephaestusForgeRenderState extends BlockEntityRenderState {
    public ItemStackRenderState itemStackRenderState;
    public ValidRitualIndicatorRenderState validRitualIndicatorRenderState;
    public float ageInTicks;
    public boolean isValidRitual;
}
