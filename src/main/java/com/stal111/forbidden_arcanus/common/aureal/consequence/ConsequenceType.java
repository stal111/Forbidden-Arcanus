package com.stal111.forbidden_arcanus.common.aureal.consequence;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;

/**
 * Consequence Type <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.common.consequence.ConsequenceType
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-06
 */
public record ConsequenceType<T extends Consequence>(ResourceLocation name, ConsequenceFactory<T> factory) {

    public ConsequenceType(String name, ConsequenceFactory<T> factory) {
        this(new ResourceLocation(ForbiddenArcanus.MOD_ID, name), factory);
    }

    public T create() {
        return this.factory().createConsequence(this);
    }

    @FunctionalInterface
    public interface ConsequenceFactory<T extends Consequence> {
        T createConsequence(ConsequenceType<T> type);
    }
}
