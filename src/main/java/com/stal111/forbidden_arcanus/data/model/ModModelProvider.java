package com.stal111.forbidden_arcanus.data.model;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.datagen.ValhelsiaModelProvider;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author stal111
 * @since 09.09.2023
 */
public class ModModelProvider extends ValhelsiaModelProvider {

    public ModModelProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators, Consumer<Item> skippedAutoItemModels) {
        ModBlockModels.create(generators, skippedAutoItemModels);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        ModItemModels.create(generators);
    }

    @Override
    public Collection<RegistryEntry<? extends Block>> getBlocks() {
        return ModBlocks.HELPER.getRegistryEntries();
    }
}
