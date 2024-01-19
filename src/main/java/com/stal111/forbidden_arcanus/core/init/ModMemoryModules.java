package com.stal111.forbidden_arcanus.core.init;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.Optional;

/**
 * @author stal111
 * @since 2022-09-15
 */
public class ModMemoryModules implements RegistryClass {

    public static final MappedRegistryHelper<MemoryModuleType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.MEMORY_MODULE_TYPE);

    public static final RegistryEntry<MemoryModuleType<Integer>> SCARED_TIME = HELPER.register("scared_time", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));

}
