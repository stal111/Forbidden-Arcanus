package com.stal111.forbidden_arcanus.common.item.mundabitur;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * An interaction that can be done with {@link MundabiturDustItem}.
 *
 * @see com.stal111.forbidden_arcanus.common.item.mundabitur.EntityInteraction
 * @see com.stal111.forbidden_arcanus.common.item.mundabitur.TransformPatternInteraction
 * @author stal111
 * @since 05.11.2023
 */
public interface MundabiturInteraction<T extends MundabiturInteraction.Context> {

    Optional<Runnable> prepare(T context);

    interface Context {
        Level getLevel();

        @Nullable
        Player getPlayer();

        BlockPos getPos();
    }
}
