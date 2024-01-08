package com.stal111.forbidden_arcanus.data.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
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
        this.generateFlatItem(ModItems.EDELWOOD_BUCKET);
        this.generateFlatItem(ModItems.EDELWOOD_WATER_BUCKET);
        this.generateFlatItem(ModItems.EDELWOOD_LAVA_BUCKET);
        this.generateFlatItem(ModItems.EDELWOOD_MILK_BUCKET);
        this.generateFlatItem(ModItems.EDELWOOD_POWDER_SNOW_BUCKET);

        var aurealTank0 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_0", ModelTemplates.FLAT_ITEM);
        var aurealTank1 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_1", ModelTemplates.FLAT_ITEM);
        var aurealTank2 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_2", ModelTemplates.FLAT_ITEM);
        var aurealTank3 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_3", ModelTemplates.FLAT_ITEM);

        var aurealTankMax = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max", ModelTemplates.FLAT_ITEM);
        var aurealTankMax0 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max_0", ModelTemplates.FLAT_ITEM);
        var aurealTankMax1 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max_1", ModelTemplates.FLAT_ITEM);
        var aurealTankMax2 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max_2", ModelTemplates.FLAT_ITEM);
        var aurealTankMax3 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max_3", ModelTemplates.FLAT_ITEM);

        this.generateWithOverrides("aureal_tank", ModItems.AUREAL_TANK,
                new ModelPredicate(aurealTank0, ModelProperty.of("amount", 0.25F), ModelProperty.of("max", 0.0F)),
                new ModelPredicate(aurealTank1, ModelProperty.of("amount", 0.5F), ModelProperty.of("max", 0.0F)),
                new ModelPredicate(aurealTank2, ModelProperty.of("amount", 0.75F), ModelProperty.of("max", 0.0F)),
                new ModelPredicate(aurealTank3, ModelProperty.of("amount", 1.0F), ModelProperty.of("max", 0.0F)),
                new ModelPredicate(aurealTankMax, ModelProperty.of("amount", 0.0F), ModelProperty.of("max", 1.0F)),
                new ModelPredicate(aurealTankMax0, ModelProperty.of("amount", 0.25F), ModelProperty.of("max", 1.0F)),
                new ModelPredicate(aurealTankMax1, ModelProperty.of("amount", 0.5F), ModelProperty.of("max", 1.0F)),
                new ModelPredicate(aurealTankMax2, ModelProperty.of("amount", 0.75F), ModelProperty.of("max", 1.0F)),
                new ModelPredicate(aurealTankMax3, ModelProperty.of("amount", 1.0F), ModelProperty.of("max", 1.0F))
        );
    }

    private ResourceLocation generateFlatItem(RegistryEntry<? extends Item> item) {
        return this.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private ResourceLocation generateFlatItem(String folder, RegistryEntry<Item> item, ModelTemplate template) {
        return template.create(ModLocationUtils.getItem(folder, item), TextureMapping.layer0(getItemTexture(item.get(), folder, "")), this.modelOutput);
    }

    private ResourceLocation generateFlatItem(RegistryEntry<? extends Item> item, ModelTemplate template) {
        return template.create(ModelLocationUtils.getModelLocation(item.get()), TextureMapping.layer0(item.get()), this.modelOutput);
    }

    private ResourceLocation generateFlatItem(String folder, RegistryEntry<Item> item, String modelSuffix, ModelTemplate template) {
        return template.create(ModLocationUtils.getItem(folder, item, modelSuffix), TextureMapping.layer0(getItemTexture(item.get(), folder, modelSuffix)), this.modelOutput);
    }

    private ResourceLocation generateFlatItem(RegistryEntry<Item> item, String modelSuffix, ModelTemplate template) {
        return template.create(ModelLocationUtils.getModelLocation(item.get(), modelSuffix), TextureMapping.layer0(TextureMapping.getItemTexture(item.get(), modelSuffix)), this.modelOutput);
    }

    public static ResourceLocation getItemTexture(Item item, String folder, String suffix) {
        ResourceLocation resourcelocation = BuiltInRegistries.ITEM.getKey(item);
        return resourcelocation.withPath(path -> {
            return "item/" + folder + "/" + path + suffix;
        });
    }

    private void generateWithOverrides(String folder, RegistryEntry<Item> item, ModelPredicate... predicates) {
        ModelTemplates.FLAT_ITEM.create(ModLocationUtils.getItem(item), TextureMapping.layer0(getItemTexture(item.get(), folder, "")), this.modelOutput, (modelLocation, map) -> {
            JsonObject jsonObject = ModelTemplates.TWO_LAYERED_ITEM.createBaseTemplate(modelLocation, map);
            JsonArray jsonArray = new JsonArray();

            for (ModelPredicate predicate : predicates) {
                predicate.serialize(jsonArray);
            }

            jsonObject.add("overrides", jsonArray);

            return jsonObject;
        });
    }

    public record ModelPredicate(ResourceLocation modelLocation, ModelProperty... properties) {

        public void serialize(JsonArray jsonArray) {
            JsonObject entry = new JsonObject();
            JsonObject predicate = new JsonObject();

            for (ModelProperty property : properties) {
                property.serialize(predicate);
            }

            entry.add("predicate", predicate);
            entry.addProperty("model", this.modelLocation.toString());

            jsonArray.add(entry);
        }
    }

    public record ModelProperty(String name, float value) {

        public static ModelProperty of(String name, float value) {
            return new ModelProperty(name, value);
        }

        public void serialize(JsonObject jsonObject) {
            jsonObject.addProperty(this.name, this.value);
        }
    }
}
