package com.stal111.forbidden_arcanus.common.item.mundabitur;

/**
 * An interaction that can be done with {@link MundabiturDustItem}.
 *
 * @see com.stal111.forbidden_arcanus.common.item.mundabitur.EntityInteraction
 * @see com.stal111.forbidden_arcanus.common.item.mundabitur.TransformPatternInteraction
 * @author stal111
 * @since 05.11.2023
 */
public interface MundabiturInteraction<T extends MundabiturInteraction.Context> {

    void interact(T context);
    boolean canInteract(T context);

    interface Context {
    }
}
