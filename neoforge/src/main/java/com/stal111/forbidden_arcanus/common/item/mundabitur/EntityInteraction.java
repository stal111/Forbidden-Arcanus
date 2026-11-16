package com.stal111.forbidden_arcanus.common.item.mundabitur;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

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

    public record EntityInteractionContext(LivingEntity entity, InteractionHand hand) implements Context {

        public static EntityInteractionContext of(LivingEntity entity, InteractionHand hand) {
            return new EntityInteractionContext(entity, hand);
        }

        @Override
        public BlockPos getPos() {
            return this.entity.blockPosition();
        }
    }
}
