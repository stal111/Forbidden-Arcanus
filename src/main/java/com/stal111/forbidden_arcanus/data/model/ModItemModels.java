package com.stal111.forbidden_arcanus.data.model;

import com.google.gson.JsonElement;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 17.09.2023
 */
public class ModItemModels {

    private final ItemModelGenerators generators;
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;


    public ModItemModels(ItemModelGenerators generators) {
        this.generators = generators;
        this.modelOutput = generators.output;
    }

    public static void create(ItemModelGenerators generators) {
        new ModItemModels(generators).createModels();
    }

    public void createModels() {
        this.generateFlatItem(ModItems.SANITY_METER);
    }

    private void generateFlatItem(RegistryEntry<Item> item) {
        this.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void generateFlatItem(RegistryEntry<Item> item, ModelTemplate template) {
        template.create(ModelLocationUtils.getModelLocation(item.get()), TextureMapping.layer0(item.get()), this.modelOutput);
    }
}
