package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

/**
 * @author stal111
 * @since 2022-10-01
 */
public class ModActivities implements RegistryClass {

    public static final RegistryHelper<Activity> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.ACTIVITIES);

    public static final RegistryObject<Activity> SOUL_EXTRACTING = HELPER.register("soul_extracting", () -> new Activity("soul_extracting"));

}
