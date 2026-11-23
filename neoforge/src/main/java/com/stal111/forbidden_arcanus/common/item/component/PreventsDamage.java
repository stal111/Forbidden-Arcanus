package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.advancements.critereon.EssenceValueEntityPredicate;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;

import java.util.List;
import java.util.Optional;

public record PreventsDamage(List<Rule> rules) {

    public static final PreventsDamage EMPTY = new PreventsDamage(List.of());

    public static final Codec<PreventsDamage> CODEC = Rule.CODEC.listOf().xmap(PreventsDamage::new, PreventsDamage::rules);

    public static final EntityPredicate HAS_AUREAL_PREDICATE = EntityPredicate.Builder.entity()
            .put(EssenceValueEntityPredicate.CODEC, new EssenceValueEntityPredicate(EssenceValue.of(EssenceType.AUREAL, 1)))
            .build();

    public PreventsDamage {
        rules = List.copyOf(rules);
    }

    public static PreventsDamage of(Rule... rules) {
        return new PreventsDamage(List.of(rules));
    }

    public static PreventsDamage fire(HolderLookup.Provider context, EquipmentSlotGroup slot) {
        return of(Rule.of(context.getOrThrow(DamageTypeTags.IS_FIRE), slot));
    }

    public static PreventsDamage fire(HolderLookup.Provider context, EquipmentSlotGroup slot, EntityPredicate predicate) {
        return of(Rule.of(context.getOrThrow(DamageTypeTags.IS_FIRE), slot, predicate));
    }

    public boolean preventsDamage(ServerLevel level, EquipmentSlot slot, DamageSource source, Entity entity) {
        for (Rule rule : this.rules) {
            if (rule.preventsDamage(level, slot, source, entity)) {
                return true;
            }
        }
        return false;
    }

    public record Rule(HolderSet<DamageType> damageTypes, EquipmentSlotGroup slot, Optional<EntityPredicate> predicate) {

        public static final Codec<Rule> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RegistryCodecs.homogeneousList(Registries.DAMAGE_TYPE).fieldOf("damage_types").forGetter(Rule::damageTypes),
                EquipmentSlotGroup.CODEC.fieldOf("slot").forGetter(Rule::slot),
                EntityPredicate.CODEC.optionalFieldOf("predicate").forGetter(Rule::predicate)
        ).apply(instance, Rule::new));

        public static Rule of(HolderSet<DamageType> damageTypes, EquipmentSlotGroup slot) {
            return new Rule(damageTypes, slot, Optional.empty());
        }

        public static Rule of(HolderSet<DamageType> damageTypes, EquipmentSlotGroup slot, EntityPredicate predicate) {
            return new Rule(damageTypes, slot, Optional.of(predicate));
        }

        public boolean preventsDamage(ServerLevel level, EquipmentSlot slot, DamageSource source, Entity entity) {
            return this.slot.test(slot) && this.damageTypes.contains(source.typeHolder()) && this.entityMatches(level, entity);
        }

        private boolean entityMatches(ServerLevel level, Entity entity) {
            return this.predicate
                    .map(predicate -> predicate.matches(level, entity.position(), entity))
                    .orElse(true);
        }
    }
}
