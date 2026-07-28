package com.stal111.forbidden_arcanus.client.renderer.block.state;

import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.phys.AABB;

public class EssenceStorageRenderState extends BlockEntityRenderState {
    public EssenceStorage essenceStorage;
    public AABB essenceRenderBounds;
}
