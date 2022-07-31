package com.stal111.forbidden_arcanus.core.init.other;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier;
import com.stal111.forbidden_arcanus.common.loot.FieryLootModifier;
import com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

/**
 * @author stal111
 * @since 2021-09-24
 */
@Mod.EventBusSubscriber
public class ModLootModifiers implements RegistryClass {

    public static final RegistryHelper<Codec<? extends IGlobalLootModifier>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS);

    public static final RegistryObject<Codec<BlacksmithGavelLootModifier>> BLACKSMITH_GAVEL = HELPER.register("blacksmith_gavel", BlacksmithGavelLootModifier.CODEC);
    public static final RegistryObject<Codec<FieryLootModifier>> FIERY = HELPER.register("fiery", FieryLootModifier.CODEC);
    public static final RegistryObject<Codec<MagicalFarmlandLootModifier>> MAGICAL_FARMLAND = HELPER.register("magical_farmland", MagicalFarmlandLootModifier.CODEC);
}
