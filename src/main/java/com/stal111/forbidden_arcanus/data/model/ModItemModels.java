package com.stal111.forbidden_arcanus.data.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryEntry;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 17.09.2023
 */
public class ModItemModels {

    private static final String ARMOR = "armor";
    private static final String TOOL = "tool";

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
        this.generateFlatItem(ModItems.OBSIDIANSTEEL_INGOT);
        this.generateFlatItem(ModItems.MUNDABITUR_DUST);
        this.generateFlatItem(ModItems.CORRUPTI_DUST);
        this.generateFlatItem(ModItems.FERROGNETIC_MIXTURE);
        this.generateFlatItem(ModItems.SOUL);
        this.generateFlatItem(ModItems.CORRUPT_SOUL);
        this.generateFlatItem(ModItems.ENCHANTED_SOUL);
        this.generateFlatItem(ModItems.AUREAL_BOTTLE);
        this.generateFlatItem(ModItems.SPLASH_AUREAL_BOTTLE);
        this.generateFlatItem(ModItems.ARCANE_CRYSTAL);
        this.generateFlatItem(ModItems.CORRUPTED_ARCANE_CRYSTAL);
        this.generateFlatItem(ModItems.RUNE);
        this.generateFlatItem(ModItems.DARK_RUNE);
        this.generateFlatItem(ModItems.STELLARITE_PIECE);
        this.generateFlatItem(ModItems.XPETRIFIED_ORB);
        this.generateFlatItem(ModItems.DARK_NETHER_STAR);
        this.generateFlatItem(ModItems.DEORUM_NUGGET);
        this.generateFlatItem(ModItems.DEORUM_INGOT);
        this.generateFlatItem(ModItems.ARCANE_CRYSTAL_DUST);
        this.generateFlatItem(ModItems.ARCANE_CRYSTAL_DUST_SPECK);
        this.generateFlatItem(ModItems.ARCANE_BONE_MEAL);
        this.generateFlatItem(ModItems.DARK_MATTER);
        this.generateFlatItem(ModItems.ENDER_PEARL_FRAGMENT);
        this.generateFlatItem(ModItems.DRAGON_SCALE);
        this.generateFlatItem(ModItems.SILVER_DRAGON_SCALE);
        this.generateFlatItem(ModItems.GOLDEN_DRAGON_SCALE);
        this.generateFlatItem(ModItems.AQUATIC_DRAGON_SCALE);
        this.generateFlatItem(ModItems.BAT_WING);
        this.generateFlatItem(ModItems.BAT_SOUP);
        this.generateFlatItem(ModItems.TENTACLE);
        this.generateFlatItem(ModItems.COOKED_TENTACLE);
        this.generateFlatItem(ModItems.EDELWOOD_STICK);
        this.generateFlatItem(ModItems.WAX);
        this.generateFlatItem(ModItems.SPAWNER_SCRAP);
        this.generateFlatItem(ModItems.BOOM_ARROW);
        this.generateFlatItem(ModItems.DRACO_ARCANUS_ARROW);
        this.generateFlatItem(ModItems.EDELWOOD_OIL);
        this.generateFlatItem(ModItems.GOLDEN_ORCHID_SEEDS);
        this.generateFlatItem(ModItems.AURUM_BOAT);
        this.generateFlatItem(ModItems.AURUM_CHEST_BOAT);
        this.generateFlatItem(ModItems.EDELWOOD_BOAT);
        this.generateFlatItem(ModItems.EDELWOOD_CHEST_BOAT);
        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_STAFF, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_SWORD, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_SHOVEL, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_PICKAXE, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_AXE, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_HOE, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_SCEPTER, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.DRACO_ARCANUS_HELMET, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.DRACO_ARCANUS_CHESTPLATE, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.DRACO_ARCANUS_LEGGINGS, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.DRACO_ARCANUS_BOOTS, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.TYR_HELMET, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.TYR_CHESTPLATE, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.TYR_LEGGINGS, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.TYR_BOOTS, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.MORTEM_HELMET, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.MORTEM_CHESTPLATE, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.MORTEM_LEGGINGS, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ARMOR, ModItems.MORTEM_BOOTS, ModelTemplates.FLAT_ITEM);

        this.generateFlatItem("enhancer", ModItems.ARTISAN_RELIC, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("enhancer", ModItems.CRESCENT_MOON, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("enhancer", ModItems.CRIMSON_STONE, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("enhancer", ModItems.SOUL_CRIMSON_STONE, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("enhancer", ModItems.ELEMENTARIUM, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("enhancer", ModItems.DIVINE_PACT, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("enhancer", ModItems.MALEDICTUS_PACT, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.BLACKSMITH_GAVEL_HEAD);
        this.generateFlatItem("tool", ModItems.WOODEN_BLACKSMITH_GAVEL, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("tool", ModItems.STONE_BLACKSMITH_GAVEL, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("tool", ModItems.GOLDEN_BLACKSMITH_GAVEL, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("tool", ModItems.IRON_BLACKSMITH_GAVEL, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("tool", ModItems.DIAMOND_BLACKSMITH_GAVEL, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("tool", ModItems.NETHERITE_BLACKSMITH_GAVEL, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem("tool", ModItems.REINFORCED_DEORUM_BLACKSMITH_GAVEL, ModelTemplates.FLAT_ITEM);
        this.generateFlatItem(ModItems.ETERNAL_STELLA);
        this.generateFlatItem(ModItems.TERRASTOMP_PRISM);
        this.generateFlatItem(ModItems.SEA_PRISM);
        this.generateFlatItem(ModItems.WHIRLWIND_PRISM);
        this.generateFlatItem(ModItems.SMELTER_PRISM);
        this.generateFlatItem(ModItems.SOUL_BINDING_CRYSTAL);

        ModModelTemplates.QUANTUM_CATCHER.create(ModelLocationUtils.getModelLocation(ModItems.QUANTUM_CATCHER.get()), ModTextureMapping.quantumCatcher(""), this.modelOutput);
        ModItems.DYED_QUANTUM_CATCHERS.forEach((color, registryEntry) -> {
            ModModelTemplates.QUANTUM_CATCHER.create(ModelLocationUtils.getModelLocation(registryEntry.get()), ModTextureMapping.quantumCatcher("/" + color), this.modelOutput);
        });
        ModModelTemplates.QUANTUM_CATCHER.create(ModelLocationUtils.getModelLocation(ModItems.BOSS_CATCHER.get()), ModTextureMapping.quantumCatcher("/boss_catcher"), this.modelOutput);

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

        this.generateFlatItem(ModItems.TEST_TUBE);

        var bloodTestTube0 = this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_0", ModelTemplates.FLAT_ITEM);
        var bloodTestTube1 = this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_1", ModelTemplates.FLAT_ITEM);
        var bloodTestTube2 = this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_2", ModelTemplates.FLAT_ITEM);
        var bloodTestTube3 = this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_3", ModelTemplates.FLAT_ITEM);
        var bloodTestTube4 = this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_4", ModelTemplates.FLAT_ITEM);
        var bloodTestTube5 = this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_5", ModelTemplates.FLAT_ITEM);

        this.generateWithOverrides("blood_test_tube", ModItems.BLOOD_TEST_TUBE,
                new ModelPredicate(bloodTestTube0, ModelProperty.of("amount", 0.1F)),
                new ModelPredicate(bloodTestTube1, ModelProperty.of("amount", 0.25F)),
                new ModelPredicate(bloodTestTube2, ModelProperty.of("amount", 0.5F)),
                new ModelPredicate(bloodTestTube3, ModelProperty.of("amount", 0.75F)),
                new ModelPredicate(bloodTestTube4, ModelProperty.of("amount", 0.9F)),
                new ModelPredicate(bloodTestTube5, ModelProperty.of("amount", 1.0F))
        );
    }

    private ResourceLocation generateFlatItem(ItemRegistryEntry<? extends Item> item) {
        return this.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    private ResourceLocation generateFlatItem(String folder, ItemRegistryEntry<? extends Item> item, ModelTemplate template) {
        return template.create(ModelLocationUtils.getModelLocation(item.get()), TextureMapping.layer0(getItemTexture(item.get(), folder, "")), this.modelOutput);
    }

    private ResourceLocation generateFlatItem(ItemRegistryEntry<? extends Item> item, ModelTemplate template) {
        return template.create(ModelLocationUtils.getModelLocation(item.get()), TextureMapping.layer0(item.get()), this.modelOutput);
    }

    private ResourceLocation generateFlatItem(String folder, ItemRegistryEntry<? extends Item> item, String modelSuffix, ModelTemplate template) {
        return template.create(ModLocationUtils.getItem(folder, item, modelSuffix), TextureMapping.layer0(getItemTexture(item.get(), folder, modelSuffix)), this.modelOutput);
    }

    private ResourceLocation generateFlatItem(ItemRegistryEntry<Item> item, String modelSuffix, ModelTemplate template) {
        return template.create(ModelLocationUtils.getModelLocation(item.get(), modelSuffix), TextureMapping.layer0(TextureMapping.getItemTexture(item.get(), modelSuffix)), this.modelOutput);
    }

    public static ResourceLocation getItemTexture(Item item, String folder, String suffix) {
        ResourceLocation resourcelocation = BuiltInRegistries.ITEM.getKey(item);
        return resourcelocation.withPath(path -> {
            return "item/" + folder + "/" + path + suffix;
        });
    }

    private void generateWithOverrides(String folder, Holder<Item> item, ModelPredicate... predicates) {
        ModelTemplates.FLAT_ITEM.create(ModLocationUtils.getItem(item), TextureMapping.layer0(getItemTexture(item.value(), folder, "")), this.modelOutput, (modelLocation, map) -> {
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
