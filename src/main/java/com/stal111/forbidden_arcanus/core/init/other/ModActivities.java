package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2022-10-01
 */
public class ModActivities implements RegistryClass {

    public static final MappedRegistryHelper<Activity> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.ACTIVITIES);

    public static final RegistryEntry<Activity> SOUL_EXTRACTING = HELPER.register("soul_extracting", () -> new Activity("soul_extracting"));

}
