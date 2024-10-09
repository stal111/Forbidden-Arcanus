package com.stal111.forbidden_arcanus.core.init.other;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier;
import com.stal111.forbidden_arcanus.common.loot.FieryLootModifier;
import com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2021-09-24
 */
public class ModLootModifiers implements RegistryClass {

    public static final MappedRegistryHelper<MapCodec<? extends IGlobalLootModifier>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS);

    public static final RegistryEntry<MapCodec<? extends IGlobalLootModifier>, MapCodec<BlacksmithGavelLootModifier>> BLACKSMITH_GAVEL = HELPER.register("blacksmith_gavel", BlacksmithGavelLootModifier.CODEC);
    public static final RegistryEntry<MapCodec<? extends IGlobalLootModifier>, MapCodec<FieryLootModifier>> FIERY = HELPER.register("fiery", FieryLootModifier.CODEC);
    public static final RegistryEntry<MapCodec<? extends IGlobalLootModifier>, MapCodec<MagicalFarmlandLootModifier>> MAGICAL_FARMLAND = HELPER.register("magical_farmland", MagicalFarmlandLootModifier.CODEC);
}
