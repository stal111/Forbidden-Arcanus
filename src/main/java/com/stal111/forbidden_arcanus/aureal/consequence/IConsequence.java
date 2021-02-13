package com.stal111.forbidden_arcanus.aureal.consequence;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

/**
 * Consequence Interface
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.consequence.IConsequence
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-04
 */
public interface IConsequence {
    ResourceLocation getName();
    void tick(PlayerEntity player);

    IConsequenceType getType();
}
