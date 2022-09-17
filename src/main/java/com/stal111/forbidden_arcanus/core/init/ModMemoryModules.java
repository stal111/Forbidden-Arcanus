package com.stal111.forbidden_arcanus.core.init;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

import java.util.Optional;

/**
 * @author stal111
 * @since 2022-09-15
 */
public class ModMemoryModules implements RegistryClass {

    public static final RegistryHelper<MemoryModuleType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.MEMORY_MODULE_TYPES);

    public static final RegistryObject<MemoryModuleType<Integer>> SCARED_TIME = HELPER.register("scared_time", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));

}
