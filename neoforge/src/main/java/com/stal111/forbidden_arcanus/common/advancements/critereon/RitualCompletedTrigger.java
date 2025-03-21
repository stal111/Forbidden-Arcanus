package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class RitualCompletedTrigger extends SimpleCriterionTrigger<RitualCompletedTrigger.TriggerInstance> {

    public void trigger(ServerPlayer player, ResourceLocation ritualId) {
        this.trigger(player, instance -> instance.matches(ritualId));
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player,
                                  ResourceLocation ritualId) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                ResourceLocation.CODEC.fieldOf("recipe_id").forGetter(TriggerInstance::ritualId)
        ).apply(instance, TriggerInstance::new));

        boolean matches(ResourceLocation ritualId) {
            return this.ritualId.equals(ritualId);
        }
    }
}
