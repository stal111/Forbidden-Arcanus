package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * @author stal111
 * @since 2022-06-29
 */
public record ResidueType(String name) {

    public MutableComponent getComponent() {
        return Component.translatable("gui.forbidden_arcanus.clibano.residue." + this.name);
    }
}
