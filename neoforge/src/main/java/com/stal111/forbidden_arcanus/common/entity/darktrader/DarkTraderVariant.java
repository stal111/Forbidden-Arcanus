package com.stal111.forbidden_arcanus.common.entity.darktrader;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-08-12
 */
public record DarkTraderVariant(ResourceLocation texture) implements RegistryClass {

    public static final MappedRegistryHelper<DarkTraderVariant> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.DARK_TRADER_VARIANT);

    public static final RegistryEntry<DarkTraderVariant, DarkTraderVariant> BROOK = register("brook");
    public static final RegistryEntry<DarkTraderVariant, DarkTraderVariant> IDRIL = register("idril");
    public static final RegistryEntry<DarkTraderVariant, DarkTraderVariant> HOHENHEIM = register("hohenheim");

    private static RegistryEntry<DarkTraderVariant, DarkTraderVariant> register(String name) {
        return HELPER.register(name, () -> new DarkTraderVariant(ForbiddenArcanus.location("textures/entity/dark_trader/" + name + ".png")));
    }
}
