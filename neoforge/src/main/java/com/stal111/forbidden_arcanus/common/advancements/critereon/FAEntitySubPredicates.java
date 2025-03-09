package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.registries.Registries;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.function.Supplier;

public class FAEntitySubPredicates {

    public static final MappedRegistryHelper<MapCodec<? extends EntitySubPredicate>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.ENTITY_SUB_PREDICATE_TYPE);

    public static final Supplier<MapCodec<EssenceValueEntityPredicate>> ESSENCE = HELPER.register("essence", () -> EssenceValueEntityPredicate.MAP_CODEC);

}
