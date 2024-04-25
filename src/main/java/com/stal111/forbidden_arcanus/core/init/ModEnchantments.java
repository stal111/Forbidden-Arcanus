package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.enchantment.AurealReservoirEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 */
public class ModEnchantments implements RegistryClass {

    public static final MappedRegistryHelper<Enchantment> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.ENCHANTMENT);

    public static final RegistryEntry<Enchantment, AurealReservoirEnchantment> AUREAL_RESERVOIR = HELPER.register("aureal_reservoir", AurealReservoirEnchantment::new);
}
