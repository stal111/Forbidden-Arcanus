package com.stal111.forbidden_arcanus.common.item.mundabitur;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

/**
 * @author stal111
 * @since 05.11.2023
 */
public abstract class EntityInteraction implements MundabiturInteraction<EntityInteraction.EntityInteractionContext> {

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
