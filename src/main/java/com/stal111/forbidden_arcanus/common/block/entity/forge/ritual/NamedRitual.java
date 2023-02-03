package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import net.minecraft.resources.ResourceLocation;

/**
 * @author stal111
 * @since 2023-02-03
 */
public record NamedRitual(ResourceLocation name, Ritual ritual) {

    public Ritual get() {
        return this.ritual;
    }
}
