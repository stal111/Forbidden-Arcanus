package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2021-06-28
 */
public class ModMenuTypes implements RegistryClass {

    public static final MappedRegistryHelper<MenuType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.MENU);

    public static final RegistryEntry<MenuType<HephaestusForgeMenu>> HEPHAESTUS_FORGE = register("hephaestus_forge", HephaestusForgeMenu::new);
    public static final RegistryEntry<MenuType<ClibanoMenu>> CLIBANO = register("clibano", ClibanoMenu::new);

    private static <T extends AbstractContainerMenu> RegistryEntry<MenuType<T>> register(String name, IContainerFactory<T> factory) {
        return HELPER.register(name, () -> IForgeMenuType.create(factory));
    }
}
