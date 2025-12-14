package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class RitualCompletedTrigger extends SimpleCriterionTrigger<RitualCompletedTrigger.TriggerInstance> {

    public void trigger(ServerPlayer player, Identifier ritualId) {
        this.trigger(player, instance -> instance.matches(ritualId));
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player,
                                  Identifier ritualId) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                Identifier.CODEC.fieldOf("recipe_id").forGetter(TriggerInstance::ritualId)
        ).apply(instance, TriggerInstance::new));

        boolean matches(Identifier ritualId) {
            return this.ritualId.equals(ritualId);
        }
    }
}
