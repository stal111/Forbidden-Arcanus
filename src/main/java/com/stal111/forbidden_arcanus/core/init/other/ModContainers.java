package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Mod Containers
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModContainers
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2021-06-28
 */
public class ModContainers {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<MenuType<HephaestusForgeMenu>> HEPHAESTUS_FORGE = register("hephaestus_forge", HephaestusForgeMenu::new);
    public static final RegistryObject<MenuType<ClibanoMenu>> CLIBANO = register("clibano", ClibanoMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, MenuType.MenuSupplier<T> factory) {
        return CONTAINERS.register(name, () -> new MenuType<>(factory));
    }
}
