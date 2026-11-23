package com.stal111.forbidden_arcanus.common.advancements.critereon;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;

import java.util.function.Supplier;

public class FACriteriaTriggers {

    public static final RegistryHelper<CriterionTrigger<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.TRIGGER_TYPE);

    public static final Supplier<RitualCompletedTrigger> RITUAL = HELPER.register("ritual_completed", RitualCompletedTrigger::new);
}
