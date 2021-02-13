package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ObsidianSkullBlock;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.valhelsia.valhelsia_core.data.ValhelsiaItemModelProvider;

import java.util.Arrays;
import java.util.Set;

/**
 * Mod Item Model Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.client.ModItemModelProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-26
 */
public class ModItemModelProvider extends ValhelsiaItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ForbiddenArcanus.REGISTRY_MANAGER, existingFileHelper);
    }

    @Override
    protected void register(Set<RegistryObject<Item>> items, Set<RegistryObject<Item>> blockItems) {
        items.removeAll(Arrays.asList(NewModItems.LENS_OF_VERITATIS, NewModItems.OBSIDIAN_SKULL_SHIELD));
        forEach(items, this::simpleModel);

        forEach(blockItems, item -> ((BlockItem) item).getBlock() instanceof ObsidianSkullBlock, item -> {});

        forEach(blockItems, this::withParent);
    }
}
