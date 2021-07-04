package com.stal111.forbidden_arcanus.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.tileentity.*;
import com.stal111.forbidden_arcanus.common.container.HephaestusForgeContainer;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.tile.BlackHoleTileEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Mod Containers
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModContainers
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-28
 */
public class ModContainers {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<ContainerType<HephaestusForgeContainer>> HEPHAESTUS_FORGE = register("hephaestus_forge", HephaestusForgeContainer::new);

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, ContainerType.IFactory<T> factory) {
        return CONTAINERS.register(name, () -> new ContainerType<>(factory));
    }
}
