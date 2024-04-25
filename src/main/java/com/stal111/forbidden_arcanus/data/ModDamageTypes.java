package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

/**
 * @author stal111
 * @since 2023-03-25
 */
public class ModDamageTypes extends DatapackRegistryClass<DamageType> {

    public static final DatapackRegistryHelper<DamageType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.DAMAGE_TYPE);

    public static final ResourceKey<DamageType> EXTRACT_SOUL = HELPER.createKey("extract_soul");

    public ModDamageTypes(BootstrapContext<DamageType> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<DamageType> context) {
        context.register(EXTRACT_SOUL, new DamageType("extract_soul", 0.1F));
    }
}
