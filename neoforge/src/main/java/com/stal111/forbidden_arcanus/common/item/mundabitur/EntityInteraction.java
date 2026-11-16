package com.stal111.forbidden_arcanus.common.item.mundabitur;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author stal111
 * @since 05.11.2023
 */
public abstract class EntityInteraction implements MundabiturInteraction<EntityInteraction.EntityInteractionContext> {

    @Override
    public Optional<Runnable> prepare(EntityInteractionContext context) {
        if (!this.canInteract(context)) {
            return Optional.empty();
        }

        return Optional.of(() -> this.interact(context));
    }

    protected abstract boolean canInteract(EntityInteractionContext context);

    protected abstract void interact(EntityInteractionContext context);

    public record EntityInteractionContext(@Nullable Player player, LivingEntity entity, InteractionHand hand) implements Context {

        public static EntityInteractionContext of(@Nullable Player player, LivingEntity entity, InteractionHand hand) {
            return new EntityInteractionContext(player, entity, hand);
        }

        @Override
        public Level getLevel() {
            return this.entity.level();
        }

        @Override
        public @Nullable Player getPlayer() {
            return this.player;
        }

        @Override
        public BlockPos getPos() {
            return this.entity.blockPosition();
        }
    }
}
