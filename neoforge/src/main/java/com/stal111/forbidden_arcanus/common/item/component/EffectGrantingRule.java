package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.advancements.critereon.EssenceValueEntityPredicate;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;

import java.util.Optional;

public record EffectGrantingRule(Holder<MobEffect> effect, EquipmentSlotGroup slot, Optional<EntityPredicate> predicate) {

    public static final Codec<EffectGrantingRule> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MobEffect.CODEC.fieldOf("effects").forGetter(EffectGrantingRule::effect),
            EquipmentSlotGroup.CODEC.fieldOf("slot").forGetter(EffectGrantingRule::slot),
            EntityPredicate.CODEC.optionalFieldOf("predicate").forGetter(EffectGrantingRule::predicate)
    ).apply(instance, EffectGrantingRule::new));

    public static final EffectGrantingRule GRANT_FIRE_RESISTANCE = new EffectGrantingRule(MobEffects.FIRE_RESISTANCE, EquipmentSlotGroup.HEAD, Optional.empty());
    public static final EffectGrantingRule GRANT_FIRE_RESISTANCE_IF_HAS_AUREAL = new EffectGrantingRule(MobEffects.FIRE_RESISTANCE, EquipmentSlotGroup.HEAD, Optional.of(EntityPredicate.Builder.entity().subPredicate(new EssenceValueEntityPredicate(EssenceValue.of(EssenceType.AUREAL, 1))).build()));

    public boolean shouldGrantEffect(ServerLevel level, EquipmentSlot slot, Holder<MobEffect> effect, Entity entity) {
        return this.slot().test(slot) && this.effect().is(effect) && this.entityMatches(level, entity);
    }

    private boolean entityMatches(ServerLevel level, Entity entity) {
        return this.predicate
                .map(predicate -> predicate.matches(level, null, entity))
                .orElse(true);
    }
}
