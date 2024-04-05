package com.stal111.forbidden_arcanus.common.block.pedestal.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 02.04.2024
 */
public abstract class PedestalEffect {

    private final PedestalEffectTrigger type;

    protected PedestalEffect(PedestalEffectTrigger type) {
        this.type = type;
    }

    public abstract void execute(ServerLevel level, BlockPos pos, ItemStack stack);
}
