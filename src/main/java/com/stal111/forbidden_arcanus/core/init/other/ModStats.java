package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 13.01.2024
 */
public class ModStats {

    public static final MappedRegistryHelper<ResourceLocation> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.CUSTOM_STAT);

    public static final RegistryEntry<ResourceLocation, ResourceLocation> INTERACT_WITH_PEDESTAL = register("interact_with_pedestal", StatFormatter.DEFAULT);

    public static RegistryEntry<ResourceLocation, ResourceLocation> register(String name, StatFormatter formatter) {
        ResourceLocation resourceLocation = new ResourceLocation(ForbiddenArcanus.MOD_ID, name);

        //TODO
        //Stats.CUSTOM.get(resourceLocation, formatter);

        return HELPER.register(name, () -> resourceLocation);
    }
}
