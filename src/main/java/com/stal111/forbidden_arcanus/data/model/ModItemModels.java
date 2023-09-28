package com.stal111.forbidden_arcanus.data.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

        this.generateFlatItem(ModItems.AUREAL_TANK, "_0", ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.AUREAL_TANK, "_1", ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.AUREAL_TANK, "_2", ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.AUREAL_TANK, "_3", ModelTemplates.FLAT_ITEM);

        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(ModItems.AUREAL_TANK.get()), TextureMapping.layer0(ModItems.AUREAL_TANK.get()), this.modelOutput, (modelLocation, map) -> {
            JsonObject jsonObject = ModelTemplates.TWO_LAYERED_ITEM.createBaseTemplate(modelLocation, map);
            JsonArray jsonArray = new JsonArray();

            for (int i = 0; i <= 3; i++) {
                JsonObject entry = new JsonObject();
                JsonObject predicate = new JsonObject();

                predicate.addProperty("amount", (i + 1) * 0.25F);
                entry.add("predicate", predicate);
                entry.addProperty("model", "forbidden_arcanus:item/aureal_tank_" + i);

                jsonArray.add(entry);
            }

            jsonObject.add("overrides", jsonArray);

            return jsonObject;
        });
    }

    private void generateFlatItem(RegistryEntry<Item> item) {
        this.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private void generateFlatItem(RegistryEntry<Item> item, ModelTemplate template) {
        template.create(ModelLocationUtils.getModelLocation(item.get()), TextureMapping.layer0(item.get()), this.modelOutput);
    }

    private void generateFlatItem(RegistryEntry<Item> item, String modelSuffix, ModelTemplate template) {
        template.create(ModelLocationUtils.getModelLocation(item.get(), modelSuffix), TextureMapping.layer0(TextureMapping.getItemTexture(item.get(), modelSuffix)), this.modelOutput);
    }
}
