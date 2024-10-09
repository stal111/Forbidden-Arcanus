package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.registries.Registries;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.function.Supplier;

/**
 * @author stal111
 * @since 14.05.2024
 */
public class FAItemSubPredicates {

    public static final MappedRegistryHelper<ItemSubPredicate.Type<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.ITEM_SUB_PREDICATE_TYPE);

    public static final Supplier<ItemSubPredicate.Type<ItemModifierPredicate>> MODIFIER = HELPER.register("modifier", () -> new ItemSubPredicate.Type<>(ItemModifierPredicate.CODEC));
}
