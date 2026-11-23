package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.advancements.predicates.entity.EntitySubPredicate;
import net.minecraft.core.registries.Registries;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;

import java.util.function.Supplier;

public class FAEntitySubPredicates {

    public static final RegistryHelper<Codec<? extends EntitySubPredicate>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.ENTITY_SUB_PREDICATE_TYPE);

    public static final Supplier<Codec<EssenceValueEntityPredicate>> ESSENCE = HELPER.register("essence", () -> EssenceValueEntityPredicate.CODEC);

}
