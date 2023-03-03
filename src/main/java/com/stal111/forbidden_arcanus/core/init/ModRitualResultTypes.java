package com.stal111.forbidden_arcanus.core.init;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResultType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-02-05
 */
public class ModRitualResultTypes implements RegistryClass {

    public static final MappedRegistryHelper<RitualResultType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(FARegistries.RITUAL_RESULT_TYPE);

    public static final RegistryObject<RitualResultType<CreateItemResult>> CREATE_ITEM = register("create_item", CreateItemResult.CODEC);
    public static final RegistryObject<RitualResultType<UpgradeTierResult>> UPGRADE_TIER = register("upgrade_tier", UpgradeTierResult.CODEC);

    public static <T extends RitualResult> RegistryObject<RitualResultType<T>> register(String name, Codec<T> codec) {
        return HELPER.register(name, () -> new RitualResultType<>(codec));
    }
}
