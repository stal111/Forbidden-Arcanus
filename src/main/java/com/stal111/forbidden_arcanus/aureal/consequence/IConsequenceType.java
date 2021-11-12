package com.stal111.forbidden_arcanus.aureal.consequence;

import net.minecraft.resources.ResourceLocation;

/**
 * Consequence Type Interface
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.consequence.IConsequenceType
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-06
 */
public interface IConsequenceType {
    ResourceLocation getName();
    IConsequence createConsequence();
}
