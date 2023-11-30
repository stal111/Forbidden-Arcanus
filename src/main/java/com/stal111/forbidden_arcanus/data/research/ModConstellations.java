package com.stal111.forbidden_arcanus.data.research;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.research.Constellation;
import com.stal111.forbidden_arcanus.common.research.Knowledge;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

/**
 * @author stal111
 * @since 29.11.2023
 */
public class ModConstellations extends DatapackRegistryClass<Constellation> {

    public static final DatapackRegistryHelper<Constellation> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.CONSTELLATION);

    public static final ResourceKey<Constellation> URSA_MINOR = HELPER.createKey("ursa_minor");

    public ModConstellations(BootstapContext<Constellation> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstapContext<Constellation> context) {
        HolderGetter<Knowledge> lookup = context.lookup(FARegistries.KNOWLEDGE);

        context.register(URSA_MINOR, new Constellation("ursa_minor", 0, 0, lookup.getOrThrow(ModKnowledge.WELCOME)));
    }
}
