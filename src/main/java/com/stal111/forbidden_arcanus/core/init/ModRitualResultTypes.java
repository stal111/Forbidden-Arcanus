package com.stal111.forbidden_arcanus.core.init;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResultType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-02-05
 */
public class ModRitualResultTypes implements RegistryClass {

    public static final MappedRegistryHelper<RitualResultType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.RITUAL_RESULT_TYPE);

    public static final RegistryEntry<RitualResultType<CreateItemResult>> CREATE_ITEM = register("create_item", CreateItemResult.CODEC);
    public static final RegistryEntry<RitualResultType<UpgradeTierResult>> UPGRADE_TIER = register("upgrade_tier", UpgradeTierResult.CODEC);

    public static <T extends RitualResult> RegistryEntry<RitualResultType<T>> register(String name, Codec<T> codec) {
        return HELPER.register(name, () -> new RitualResultType<>(codec));
    }
}
