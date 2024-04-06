package com.stal111.forbidden_arcanus.common.block.pedestal.effect;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 06.04.2024
 */
public class PedestalEffects {

    public static final MappedRegistryHelper<PedestalEffect> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.PEDESTAL_EFFECT);

    public static final RegistryEntry<PedestalEffect> UPDATE_FORGE_INGREDIENTS = HELPER.register("update_forge_ingredients", UpdateForgeIngredientsEffect::new);
    public static final RegistryEntry<PedestalEffect> SUMMON_DARK_TRADER = HELPER.register("summon_dark_trader", () -> new SummonEntityEffect<>((level, stack) -> stack.is(ModItems.OMEGA_ARCOIN.get()), ModEntities.DARK_TRADER, 10, false));
}
