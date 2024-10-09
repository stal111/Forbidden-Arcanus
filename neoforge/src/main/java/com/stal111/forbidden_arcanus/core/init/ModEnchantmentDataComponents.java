package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.List;

public class ModEnchantmentDataComponents {

    public static MappedRegistryHelper<DataComponentType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE);

    public static final RegistryEntry<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> LOST_SOUL_SPAWN_CHANCE = HELPER.register("lost_soul_spawn_chance", () -> DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder().persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ITEM).listOf()).build());
}
