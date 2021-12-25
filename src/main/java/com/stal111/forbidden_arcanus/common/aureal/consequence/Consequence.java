package com.stal111.forbidden_arcanus.common.aureal.consequence;

import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;

/**
 * Consequence <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.aureal.consequence.Consequence
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-04
 */
public abstract class Consequence {

    private final ConsequenceType<?> type;

    public Consequence(ConsequenceType<?> type) {
        this.type = type;
    }

    public ResourceLocation getName() {
        return this.getType().name();
    }

    public abstract void tick(Player player);

    public ConsequenceType<?> getType() {
        return this.type;
    }
}
