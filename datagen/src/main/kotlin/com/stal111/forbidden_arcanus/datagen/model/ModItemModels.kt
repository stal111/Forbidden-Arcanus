package com.stal111.forbidden_arcanus.datagen.model

import com.stal111.forbidden_arcanus.client.renderer.item.WandItemModel
import com.stal111.forbidden_arcanus.client.renderer.item.properties.EssenceFillPercentage
import com.stal111.forbidden_arcanus.client.renderer.special.EctoBlasterSpecialRenderer
import com.stal111.forbidden_arcanus.core.init.ModItems
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.quantumCatcher
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.model.*
import net.minecraft.client.resources.model.sprite.Material
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Item
import net.valhelsia.dataforge.model.ItemModelGenerator
import net.valhelsia.dataforge.model.createModel
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryEntry

class ModItemModels(val generators: ItemModelGenerators) : ItemModelGenerator(generators) {
    override fun generate() {
        this.generateFlatItem(ModItems.EDELWOOD_BUCKET)
        this.generateFlatItem(ModItems.EDELWOOD_WATER_BUCKET)
        this.generateFlatItem(ModItems.EDELWOOD_LAVA_BUCKET)
        this.generateFlatItem(ModItems.EDELWOOD_MILK_BUCKET)
        this.generateFlatItem(ModItems.EDELWOOD_POWDER_SNOW_BUCKET)
        this.generateFlatItem(ModItems.OBSIDIANSTEEL_INGOT)
        this.generateFlatItem(ModItems.MUNDABITUR_DUST)
        this.generateFlatItem(ModItems.CORRUPTI_DUST)
        this.generateFlatItem(ModItems.FERROGNETIC_MIXTURE)
        this.generateFlatItem(ModItems.SOUL)
        this.generateFlatItem(ModItems.CORRUPT_SOUL)
        this.generateFlatItem(ModItems.ENCHANTED_SOUL)
        this.generateFlatItem(ModItems.AUREAL_BOTTLE)
        this.generateFlatItem(ModItems.SPLASH_AUREAL_BOTTLE)
        this.generateFlatItem(ModItems.ECTOPLASM_BOTTLE)
        this.generateFlatItem(ModItems.ARCANE_CRYSTAL)
        this.generateFlatItem(ModItems.CORRUPTED_ARCANE_CRYSTAL)
        this.generateFlatItem(ModItems.RUNE)
        this.generateFlatItem(ModItems.STELLARITE_PIECE)
        this.generateFlatItem(ModItems.DARK_NETHER_STAR)
        this.generateFlatItem(ModItems.DEORUM_NUGGET)
        this.generateFlatItem(ModItems.DEORUM_INGOT)
        this.generateFlatItem(ModItems.ARCANE_CRYSTAL_DUST)
        this.generateFlatItem(ModItems.ARCANE_CRYSTAL_DUST_SPECK)
        this.generateFlatItem(ModItems.ARCANE_BONE_MEAL)
        this.generateFlatItem(ModItems.DARK_MATTER)
        this.generateFlatItem(ModItems.ENDER_PEARL_FRAGMENT)
        this.generateFlatItem(ModItems.DRAGON_SCALE)
        this.generateFlatItem(ModItems.SILVER_DRAGON_SCALE)
        this.generateFlatItem(ModItems.GOLDEN_DRAGON_SCALE)
        this.generateFlatItem(ModItems.AQUATIC_DRAGON_SCALE)
        this.generateFlatItem(ModItems.BAT_WING)
        this.generateFlatItem(ModItems.BAT_SOUP)
        this.generateFlatItem(ModItems.TENTACLE)
        this.generateFlatItem(ModItems.COOKED_TENTACLE)
        this.generateFlatItem(ModItems.EDELWOOD_STICK, ModelTemplates.FLAT_HANDHELD_ITEM)
        this.generateFlatItem(ModItems.WAX)
        this.generateFlatItem(ModItems.SPAWNER_SCRAP)
        this.generateFlatItem(ModItems.BOOM_ARROW)
        this.generateFlatItem(ModItems.DRACO_ARCANUS_ARROW)
        this.generateFlatItem(ModItems.EDELWOOD_OIL)
        this.generateFlatItem(ModItems.OMEGA_ARCOIN)
//        this.generateFlatItem(ModItems.AURUM_BOAT)
//        this.generateFlatItem(ModItems.AURUM_CHEST_BOAT)
//        this.generateFlatItem(ModItems.EDELWOOD_BOAT)
//        this.generateFlatItem(ModItems.EDELWOOD_CHEST_BOAT)
        this.generateFlatItem(ModItems.APPLY_MODIFIER_SMITHING_TEMPLATE)
        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_STAFF, ModelTemplates.FLAT_HANDHELD_ITEM)
//        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM)
//        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM)
//        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM)
//        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_AXE, ModelTemplates.FLAT_HANDHELD_ITEM)
//        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_HOE, ModelTemplates.FLAT_HANDHELD_ITEM)
        this.generateFlatItem(TOOL, ModItems.DRACO_ARCANUS_SCEPTER, ModelTemplates.FLAT_HANDHELD_ITEM)
        this.generateFlatItem(ARMOR, ModItems.DRACO_ARCANUS_HELMET, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem(ARMOR, ModItems.DRACO_ARCANUS_CHESTPLATE, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem(ARMOR, ModItems.DRACO_ARCANUS_LEGGINGS, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem(ARMOR, ModItems.DRACO_ARCANUS_BOOTS, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem(ARMOR, ModItems.TYR_HELMET, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem(ARMOR, ModItems.TYR_CHESTPLATE, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem(ARMOR, ModItems.TYR_LEGGINGS, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem(ARMOR, ModItems.TYR_BOOTS, ModelTemplates.FLAT_ITEM)
//        this.generateFlatItem(ARMOR, ModItems.MORTEM_HELMET, ModelTemplates.FLAT_ITEM)
//        this.generateFlatItem(ARMOR, ModItems.MORTEM_CHESTPLATE, ModelTemplates.FLAT_ITEM)
//        this.generateFlatItem(ARMOR, ModItems.MORTEM_LEGGINGS, ModelTemplates.FLAT_ITEM)
//        this.generateFlatItem(ARMOR, ModItems.MORTEM_BOOTS, ModelTemplates.FLAT_ITEM)

        this.generateFlatItem("enhancer", ModItems.ARTISAN_RELIC, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem("enhancer", ModItems.CRESCENT_MOON, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem("enhancer", ModItems.CRIMSON_STONE, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem("enhancer", ModItems.SOUL_CRIMSON_STONE, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem("enhancer", ModItems.ELEMENTARIUM, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem("enhancer", ModItems.DIVINE_PACT, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem("enhancer", ModItems.MALEDICTUS_PACT, ModelTemplates.FLAT_ITEM)
        this.generateFlatItem(ModItems.BLACKSMITH_GAVEL_HEAD)
        this.generateFlatItem("tool", ModItems.WOODEN_BLACKSMITH_GAVEL, ModelTemplates.FLAT_HANDHELD_ITEM)
        this.generateFlatItem("tool", ModItems.STONE_BLACKSMITH_GAVEL, ModelTemplates.FLAT_HANDHELD_ITEM)
        this.generateFlatItem("tool", ModItems.GOLDEN_BLACKSMITH_GAVEL, ModelTemplates.FLAT_HANDHELD_ITEM)
        this.generateFlatItem("tool", ModItems.IRON_BLACKSMITH_GAVEL, ModelTemplates.FLAT_HANDHELD_ITEM)
        this.generateFlatItem("tool", ModItems.DIAMOND_BLACKSMITH_GAVEL, ModelTemplates.FLAT_HANDHELD_ITEM)
        this.generateFlatItem("tool", ModItems.NETHERITE_BLACKSMITH_GAVEL, ModelTemplates.FLAT_HANDHELD_ITEM)
//        this.generateFlatItem("tool", ModItems.REINFORCED_DEORUM_BLACKSMITH_GAVEL, ModelTemplates.FLAT_HANDHELD_ITEM)
        this.generateFlatItem(ModItems.ETERNAL_STELLA)
        this.generateFlatItem(ModItems.TERRASTOMP_PRISM)
        this.generateFlatItem(ModItems.SEA_PRISM)
        this.generateFlatItem(ModItems.WHIRLWIND_PRISM)
        this.generateFlatItem(ModItems.SMELTER_PRISM)
        this.generateFlatItem(ModItems.SOUL_BINDING_CRYSTAL)
        this.generateFlatItem(ModItems.AUREAL_WARDSTONE)
        this.generateFlatItem(ModItems.CONTAINMENT_CAPSULE)
        this.generateFlatItem(ModItems.ENCAPSULATED_BLACK_HOLE)
        this.generateFlatItem(ModItems.CONDENSED_EXPERIENCE)

        this.generateWandItem(ModItems.OAK_WAND)
        this.generateWandItem(ModItems.SPRUCE_WAND)
        this.generateWandItem(ModItems.BIRCH_WAND)
        this.generateWandItem(ModItems.JUNGLE_WAND)
        this.generateWandItem(ModItems.ACACIA_WAND)
        this.generateWandItem(ModItems.DARK_OAK_WAND)
        this.generateWandItem(ModItems.MANGROVE_WAND)
        this.generateWandItem(ModItems.CHERRY_WAND)
        this.generateWandItem(ModItems.PALE_OAK_WAND)
        this.generateWandItem(ModItems.BAMBOO_WAND)
        this.generateWandItem(ModItems.CRIMSON_WAND)
        this.generateWandItem(ModItems.WARPED_WAND)
        this.generateWandItem(ModItems.AURUM_WAND)
        this.generateWandItem(ModItems.EDELWOOD_WAND)

        generators.declareCustomModelItem(ModItems.SPECTRAL_EYE_AMULET.get())

        generateEctoBlaster()

        generateQuantumCatcher(ModItems.QUANTUM_CATCHER)
        generateQuantumCatcher(ModItems.BOSS_CATCHER, "boss_catcher")
        ModItems.DYED_QUANTUM_CATCHERS.forEach { (color, registryEntry) ->
            generateQuantumCatcher(registryEntry, color.serializedName)
        }

        val aurealTank0 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_0", ModelTemplates.FLAT_ITEM)
        val aurealTank1 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_1", ModelTemplates.FLAT_ITEM)
        val aurealTank2 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_2", ModelTemplates.FLAT_ITEM)
        val aurealTank3 = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_3", ModelTemplates.FLAT_ITEM)

        val aurealTankMax = this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max", ModelTemplates.FLAT_ITEM)
        val aurealTankMax0 =
            this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max_0", ModelTemplates.FLAT_ITEM)
        val aurealTankMax1 =
            this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max_1", ModelTemplates.FLAT_ITEM)
        val aurealTankMax2 =
            this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max_2", ModelTemplates.FLAT_ITEM)
        val aurealTankMax3 =
            this.generateFlatItem("aureal_tank", ModItems.AUREAL_TANK, "_max_3", ModelTemplates.FLAT_ITEM)

        output.accept(ModItems.AUREAL_TANK.get(), ItemModelUtils.plainModel(aurealTank0))

//        this.generateWithOverrides(
//            "aureal_tank", ModItems.AUREAL_TANK,
//            ModItemModels.ModelPredicate(
//                aurealTank0,
//                ModelProperty.of("amount", 0.25f),
//                ModelProperty.of("max", 0.0f)
//            ),
//            ModItemModels.ModelPredicate(
//                aurealTank1,
//                ModelProperty.of("amount", 0.5f),
//                ModelProperty.of("max", 0.0f)
//            ),
//            ModItemModels.ModelPredicate(
//                aurealTank2,
//                ModelProperty.Companion.of("amount", 0.75f),
//                ModelProperty.Companion.of("max", 0.0f)
//            ),
//            ModItemModels.ModelPredicate(
//                aurealTank3,
//                ModelProperty.Companion.of("amount", 1.0f),
//                ModelProperty.Companion.of("max", 0.0f)
//            ),
//            ModItemModels.ModelPredicate(
//                aurealTankMax,
//                ModelProperty.Companion.of("amount", 0.0f),
//                ModelProperty.Companion.of("max", 1.0f)
//            ),
//            ModItemModels.ModelPredicate(
//                aurealTankMax0,
//                ModelProperty.Companion.of("amount", 0.25f),
//                ModelProperty.Companion.of("max", 1.0f)
//            ),
//            ModItemModels.ModelPredicate(
//                aurealTankMax1,
//                ModelProperty.Companion.of("amount", 0.5f),
//                ModelProperty.Companion.of("max", 1.0f)
//            ),
//            ModItemModels.ModelPredicate(
//                aurealTankMax2,
//                ModelProperty.Companion.of("amount", 0.75f),
//                ModelProperty.Companion.of("max", 1.0f)
//            ),
//            ModItemModels.ModelPredicate(
//                aurealTankMax3,
//                ModelProperty.Companion.of("amount", 1.0f),
//                ModelProperty.Companion.of("max", 1.0f)
//            )
//        )

        this.generateFlatItem(ModItems.TEST_TUBE)

        val bloodTestTube0 =
            this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_0", ModelTemplates.FLAT_ITEM)
        val bloodTestTube1 =
            this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_1", ModelTemplates.FLAT_ITEM)
        val bloodTestTube2 =
            this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_2", ModelTemplates.FLAT_ITEM)
        val bloodTestTube3 =
            this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_3", ModelTemplates.FLAT_ITEM)
        val bloodTestTube4 =
            this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_4", ModelTemplates.FLAT_ITEM)
        val bloodTestTube5 =
            this.generateFlatItem("blood_test_tube", ModItems.BLOOD_TEST_TUBE, "_5", ModelTemplates.FLAT_ITEM)

        output.accept(
            ModItems.BLOOD_TEST_TUBE.get(), ItemModelUtils.rangeSelect(
                EssenceFillPercentage(),
                ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModItems.TEST_TUBE.get())),
                ItemModelUtils.override(ItemModelUtils.plainModel(bloodTestTube0), 0.1f),
                ItemModelUtils.override(ItemModelUtils.plainModel(bloodTestTube1), 0.25f),
                ItemModelUtils.override(ItemModelUtils.plainModel(bloodTestTube2), 0.5f),
                ItemModelUtils.override(ItemModelUtils.plainModel(bloodTestTube3), 0.75f),
                ItemModelUtils.override(ItemModelUtils.plainModel(bloodTestTube4), 0.9f),
                ItemModelUtils.override(ItemModelUtils.plainModel(bloodTestTube5), 1.0f),
            )
        )
    }

    private fun generateFlatItem(
        folder: String,
        item: ItemRegistryEntry<out Item>,
        template: ModelTemplate
    ) = output.accept(
        item.get(), ItemModelUtils.plainModel(
            template.createModel(
                ModelLocationUtils.getModelLocation(item.get()),
                TextureMapping.layer0(getItemTexture(item.get(), folder, "")),
            )
        )
    )

    private fun generateFlatItem(
        item: ItemRegistryEntry<out Item?>,
        template: ModelTemplate = ModelTemplates.FLAT_ITEM
    ) = generators.generateFlatItem(item.get(), template)

    private fun generateFlatItem(
        folder: String,
        item: ItemRegistryEntry<out Item>,
        modelSuffix: String,
        template: ModelTemplate
    ): Identifier {
        return template.createModel(
            ModLocationUtils.getItem(folder, item, modelSuffix),
            TextureMapping.layer0(getItemTexture(item.get(), folder, modelSuffix)),
        )
    }

    private fun generateFlatItem(
        item: ItemRegistryEntry<Item>,
        modelSuffix: String,
        template: ModelTemplate
    ): Identifier {
        return template.createModel(
            ModelLocationUtils.getModelLocation(item.get(), modelSuffix), TextureMapping.layer0(
                TextureMapping.getItemTexture(item.get(), modelSuffix)
            )
        )
    }

    private fun generateWandItem(item: ItemRegistryEntry<out Item>) {
        val model = ModelTemplates.FLAT_HANDHELD_ROD_ITEM.createModel(
            ModelLocationUtils.getModelLocation(item.get()),
            TextureMapping.layer0(Material(ModLocationUtils.getItem("wand", item)))
        )

        output.accept(item.get(), WandItemModel.Unbaked(model))
    }

    private fun generateQuantumCatcher(
        item: ItemRegistryEntry<out Item>,
        color: String? = null,
    ) {
        val model = ModModelTemplates.QUANTUM_CATCHER.createModel(
            ModelLocationUtils.getModelLocation(item.get()),
            quantumCatcher(if (color != null) "/$color" else ""),
        )

        output.accept(item.get(), ItemModelUtils.plainModel(model))
    }

    private fun generateEctoBlaster() {
        val model = ModelLocationUtils.getModelLocation(ModItems.ECTO_BLASTER.get())

        generators.itemModelOutput.accept(
            ModItems.ECTO_BLASTER.get(),
            ItemModelUtils.composite(
                ItemModelUtils.plainModel(model),
                ItemModelUtils.specialModel(
                    model,
                    EctoBlasterSpecialRenderer.Unbaked()
                )
            )
        )
    }

    companion object {
        private const val ARMOR = "armor"
        private const val TOOL = "tool"

        fun getItemTexture(item: Item, folder: String, suffix: String): Material {
            val identifier = BuiltInRegistries.ITEM.getKey(item)
            return Material(identifier.withPath { path: String? -> "item/$folder/$path$suffix" })
        }
    }
}
