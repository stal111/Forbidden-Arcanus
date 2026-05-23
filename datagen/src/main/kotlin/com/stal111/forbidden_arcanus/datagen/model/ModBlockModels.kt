package com.stal111.forbidden_arcanus.datagen.model

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.client.renderer.special.EssenceUtremJarSpecialRenderer
import com.stal111.forbidden_arcanus.common.block.DeskBlock
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel
import com.stal111.forbidden_arcanus.common.block.pedestal.PedestalBlock
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties
import com.stal111.forbidden_arcanus.common.block.properties.PillarType
import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullType
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.data.FABlockFamilies
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.clibanoCore
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.desk
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.edelwoodLog
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.edelwoodLogWithFace
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.emissiveCube
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.emissiveLayerCube
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.forbiddenomicon
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.hephaestusForge
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.pedestal
import com.stal111.forbidden_arcanus.datagen.model.ModTextureMapping.utremJar
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.BlockModelGenerators.plainVariant
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator
import net.minecraft.client.data.models.blockstates.PropertyDispatch
import net.minecraft.client.data.models.model.*
import net.minecraft.client.renderer.item.ItemModel
import net.minecraft.client.resources.model.sprite.Material
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.valhelsia.dataforge.model.BlockModelGenerator

class ModBlockModels(private val defaultGenerators: BlockModelGenerators) : BlockModelGenerator(defaultGenerators) {
    private val texturedModels = mapOf(
        ModBlocks.CUT_SOULLESS_SANDSTONE.get() to TexturedModel.COLUMN.get(ModBlocks.CUT_SOULLESS_SANDSTONE.get())
    )

    override fun generate() {
        val generators = this.defaultGenerators

        FABlockFamilies.getAllFamilies()
            .filter { family -> family.shouldGenerateModel() }
            .forEach { this.family(it.baseBlock).generateFor(it) }

        //TODO
        defaultGenerators.createParticleOnlyBlock(ModBlocks.CLIBANO_MAIN_PART.get())
        defaultGenerators.createParticleOnlyBlock(ModBlocks.BLACK_HOLE.get())
        defaultGenerators.createParticleOnlyBlock(ModBlocks.UPWIND.get())
        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(
                ModBlocks.ARCANE_DRAGON_EGG.get(),
                plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.ARCANE_DRAGON_EGG.get()))
            )
        )
        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(
                ModBlocks.WHIRLWIND.get(),
                plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.WHIRLWIND.get()))
            )
        )

        generators.registerSimpleFlatItemModel(ModBlocks.DEORUM_CHAIN.get().asItem())
        generators.registerSimpleFlatItemModel(ModBlocks.ARCANE_DRAGON_EGG.get().asItem())
        generators.registerSimpleFlatItemModel(ModBlocks.EDELWOOD_LADDER.get())

        generators.createTrivialCube(ModBlocks.SOULLESS_SAND.get())
        generators.createTrivialCube(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get())
        generators.createTrivialCube(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get())
        generators.createTrivialCube(ModBlocks.QUANTUM_INJECTOR.get())
        this.createEmissiveLayerCube(ModBlocks.ARCANE_CRYSTAL_ORE.get(), "arcane_crystal_ore")
        this.createEmissiveLayerCube(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get(), "arcane_crystal_ore")
        this.createEmissiveLayerCube(ModBlocks.RUNIC_STONE.get(), "runic_stone")
        this.createEmissiveLayerCube(ModBlocks.RUNIC_DEEPSLATE.get(), "runic_stone")
        this.createEmissiveLayerCube(ModBlocks.RUNIC_DARKSTONE.get(), "runic_stone")
        generators.createTrivialCube(ModBlocks.METEORITE.get())
        generators.createTrivialCube(ModBlocks.STELLA_ARCANUM.get())
        this.createEmissiveCube(ModBlocks.ARCANE_CRYSTAL_BLOCK.get())
        generators.createTrivialCube(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_BLOCK.get())
        generators.createTrivialCube(ModBlocks.RUNE_BLOCK.get())
        generators.createTrivialCube(ModBlocks.STELLARITE_BLOCK.get())
        generators.createTrivialCube(ModBlocks.DEORUM_BLOCK.get())
        generators.createTrivialCube(ModBlocks.OBSIDIANSTEEL_BLOCK.get())
        generators.createTrivialCube(ModBlocks.AURUM_LEAVES.get())
        generators.createTrivialCube(ModBlocks.NUGGETY_AURUM_LEAVES.get())
        generators.createTrivialCube(ModBlocks.FUNGYSS_BLOCK.get())

        this.createForbiddenomicon(ModBlocks.FORBIDDENOMICON.get())
        this.createDesk(ModBlocks.DESK.get(), false)
        this.createDesk(ModBlocks.RESEARCH_DESK.get(), true)
        this.createDesk(ModBlocks.WAND_DESK.get(), true)
        this.createPedestal(ModBlocks.DARKSTONE_PEDESTAL.get())
        this.createPedestal(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get())
        this.createClibanoCore(ModBlocks.CLIBANO_CORE.get())
        this.createClibanoCenter(ModBlocks.CLIBANO_CENTER.get())
        this.createClibanoCorner(ModBlocks.CLIBANO_CORNER.get())
        this.createClibanoSideHorizontal(ModBlocks.CLIBANO_SIDE_HORIZONTAL.get())
        this.createClibanoSideVertical(ModBlocks.CLIBANO_SIDE_VERTICAL.get())
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE.get())
        this.createObelisk(ModBlocks.ARCANE_CRYSTAL_OBELISK.get())
        this.createObelisk(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK.get())
        this.createUtremJar(ModBlocks.UTREM_JAR.get())
        this.createEssenceUtremJar()

        this.createPillar(ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get())
        defaultGenerators.createNonTemplateModelBlock(ModBlocks.QUANTUM_CORE.get())
        generators.createDoor(ModBlocks.DEORUM_DOOR.get())
        generators.createTrapdoor(ModBlocks.DEORUM_TRAPDOOR.get())
        generators.createAxisAlignedPillarBlockCustomModel(
            ModBlocks.DEORUM_CHAIN.get(),
            plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.DEORUM_CHAIN.get()))
        )
        generators.createGlassBlocks(ModBlocks.DEORUM_GLASS.get(), ModBlocks.DEORUM_GLASS_PANE.get())
        generators.createGlassBlocks(ModBlocks.RUNIC_GLASS.get(), ModBlocks.RUNIC_GLASS_PANE.get())
        generators.createLantern(ModBlocks.DEORUM_LANTERN.get())
        generators.createLantern(ModBlocks.DEORUM_SOUL_LANTERN.get())
        generators.createPlantWithDefaultItem(
            ModBlocks.FUNGYSS.get(),
            ModBlocks.POTTED_FUNGYSS.get(),
            BlockModelGenerators.PlantType.NOT_TINTED
        )
        generators.createPlantWithDefaultItem(
            ModBlocks.AURUM_SAPLING.get(),
            ModBlocks.POTTED_AURUM_SAPLING.get(),
            BlockModelGenerators.PlantType.NOT_TINTED
        )
        generators.createPlantWithDefaultItem(
            ModBlocks.GROWING_EDELWOOD.get(),
            ModBlocks.POTTED_GROWING_EDELWOOD.get(),
            BlockModelGenerators.PlantType.NOT_TINTED
        )
        generators.createPlantWithDefaultItem(
            ModBlocks.YELLOW_ORCHID.get(),
            ModBlocks.POTTED_YELLOW_ORCHID.get(),
            BlockModelGenerators.PlantType.NOT_TINTED
        )
        generators.woodProvider(ModBlocks.FUNGYSS_STEM.get()).log(ModBlocks.FUNGYSS_STEM.get())
            .wood(ModBlocks.FUNGYSS_HYPHAE.get())
        generators.woodProvider(ModBlocks.AURUM_LOG.get()).logWithHorizontal(ModBlocks.AURUM_LOG.get())
            .wood(ModBlocks.AURUM_WOOD.get())
        generators.woodProvider(ModBlocks.STRIPPED_AURUM_LOG.get())
            .logWithHorizontal(ModBlocks.STRIPPED_AURUM_LOG.get()).wood(
                ModBlocks.STRIPPED_AURUM_WOOD.get()
            )
        this.createHollowLog(ModBlocks.EDELWOOD_LOG.get())
        this.createHollowLogWithFace(ModBlocks.CARVED_EDELWOOD_LOG.get())
        this.createEdelwoodBranch()
        this.createMagicalFarmland()
        defaultGenerators.createNonTemplateHorizontalBlock(ModBlocks.EDELWOOD_LADDER.get())
        this.createMortar(ModBlocks.MORTAR.get())

        val skullTemplate = ModelLocationUtils.decorateItemModelLocation("template_skull")

        generators.createHead(
            ModBlocks.OBSIDIAN_SKULL.getSkull(),
            ModBlocks.OBSIDIAN_SKULL.getWallSkull(),
            ObsidianSkullType.DEFAULT,
            skullTemplate
        )
        generators.createHead(
            ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull(),
            ModBlocks.CRACKED_OBSIDIAN_SKULL.getWallSkull(),
            ObsidianSkullType.CRACKED,
            skullTemplate
        )
        generators.createHead(
            ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull(),
            ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getWallSkull(),
            ObsidianSkullType.FRAGMENTED,
            skullTemplate
        )
        generators.createHead(
            ModBlocks.FADING_OBSIDIAN_SKULL.getSkull(),
            ModBlocks.FADING_OBSIDIAN_SKULL.getWallSkull(),
            ObsidianSkullType.FADING,
            skullTemplate
        )
        generators.createHead(
            ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull(),
            ModBlocks.AUREALIC_OBSIDIAN_SKULL.getWallSkull(),
            ObsidianSkullType.AUREALIC,
            skullTemplate
        )
        generators.createHead(
            ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull(),
            ModBlocks.ETERNAL_OBSIDIAN_SKULL.getWallSkull(),
            ObsidianSkullType.ETERNAL,
            skullTemplate
        )
    }

    fun family(block: Block): BlockModelGenerators.BlockFamilyProvider {
        val texturedModel = this.texturedModels.getOrDefault(block, TexturedModel.CUBE.get(block))
        return this.defaultGenerators.BlockFamilyProvider(texturedModel.mapping)
            .fullBlock(block, texturedModel.template)
    }

    private fun createEmissiveCube(block: Block) {
        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(
                block,
                plainVariant(ModModelTemplates.CUBE_ALL_EMISSIVE.create(block, emissiveCube(block), this.modelOutput))
            )
        )
    }

    private fun createEmissiveLayerCube(block: Block, folder: String) {
        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(
                block,
                plainVariant(
                    ModModelTemplates.CUBE_ALL_EMISSIVE_LAYER.create(
                        block,
                        emissiveLayerCube(block, folder),
                        this.modelOutput
                    )
                )
            )
        )
    }

    private fun createForbiddenomicon(block: Block) {
        val textureMapping = forbiddenomicon(block)
        val model = plainVariant(ModModelTemplates.FORBIDDENOMICON.create(block, textureMapping, this.modelOutput))

        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(block, model).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
        )
    }

    private fun createDesk(block: DeskBlock, research: Boolean) {
        val textureMapping = desk(research)
        val model = plainVariant(ModModelTemplates.DESK.create(block, textureMapping, this.modelOutput))

        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(block, model).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
        )
    }

    private fun createPedestal(block: PedestalBlock) {
        val textureMapping = pedestal(block)
        val model = plainVariant(ModModelTemplates.PEDESTAL.create(block, textureMapping, this.modelOutput))

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model))
    }

    private fun createClibanoCore(block: Block) {
        val textureMapping = clibanoCore()
        val model = plainVariant(ModelTemplates.CUBE_ORIENTABLE.create(block, textureMapping, this.modelOutput))

        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(block, model).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
        )
    }

    private fun createClibanoCenter(block: Block) {
        val dispatch = PropertyDispatch.initial(ModBlockStateProperties.CLIBANO_CENTER_TYPE).generate {
            val model = ModModelTemplates.CLIBANO_CENTER.createWithSuffix(
                block,
                "_" + it.serializedName,
                ModTextureMapping.clibanoCenter(it),
                this.modelOutput
            )

            plainVariant(model)
        }

        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(block).with(dispatch).with(BlockModelGenerators.ROTATION_FACING)
        )
    }

    private fun createClibanoCorner(block: Block) {
        val model = plainVariant(ForbiddenArcanus.identifier("block/clibano_corner"))

        val dispatch = PropertyDispatch.modify(BlockStateProperties.BOTTOM).generate {
            if (it) BlockModelGenerators.X_ROT_90 else BlockModelGenerators.NOP
        }

        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(block, model)
                .with(dispatch)
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
        )
    }

    private fun createClibanoSideHorizontal(block: Block) {
        val typeDispatch = PropertyDispatch.initial(ModBlockStateProperties.CLIBANO_SIDE_TYPE).generate {
            val textureMapping = ModTextureMapping.clibanoSide(it)
            val model = ModModelTemplates.CLIBANO_SIDE_HORIZONTAL.createWithSuffix(
                block,
                "_" + it.serializedName,
                textureMapping,
                this.modelOutput
            )

            plainVariant(model)
        }

        val facingDispatch = PropertyDispatch.modify(
            BlockStateProperties.HORIZONTAL_FACING,
            ModBlockStateProperties.MIRRORED
        ).select(
            Direction.EAST,
            false,
            BlockModelGenerators.Y_ROT_90
        ).select(
            Direction.EAST,
            true,
            BlockModelGenerators.X_ROT_180
        ).select(
            Direction.SOUTH,
            false,
            BlockModelGenerators.Y_ROT_180
        ).select(
            Direction.SOUTH,
            true,
            BlockModelGenerators.X_ROT_180.then(BlockModelGenerators.Y_ROT_90)
        ).select(
            Direction.WEST,
            false,
            BlockModelGenerators.Y_ROT_270
        ).select(
            Direction.WEST,
            true,
            BlockModelGenerators.Y_ROT_180.then(BlockModelGenerators.X_ROT_180)
        ).select(
            Direction.NORTH, false, BlockModelGenerators.NOP
        ).select(
            Direction.NORTH,
            true,
            BlockModelGenerators.Y_ROT_270.then(BlockModelGenerators.X_ROT_180)
        )

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(typeDispatch).with(facingDispatch))
    }

    private fun createClibanoSideVertical(block: Block) {
        val typeDispatch = PropertyDispatch.initial(ModBlockStateProperties.CLIBANO_SIDE_TYPE).generate {
            val textureMapping = ModTextureMapping.clibanoSide(it)
            val model = ModModelTemplates.CLIBANO_SIDE_VERTICAL.createWithSuffix(
                block,
                "_" + it.serializedName,
                textureMapping,
                this.modelOutput
            )
            plainVariant(model)
        }

        val facingDispatch = PropertyDispatch.modify(
            BlockStateProperties.HORIZONTAL_FACING,
            ModBlockStateProperties.MIRRORED
        ).select(
            Direction.EAST,
            false,
            BlockModelGenerators.Y_ROT_90
        ).select(
            Direction.EAST,
            true,
            BlockModelGenerators.X_ROT_180.then(BlockModelGenerators.Y_ROT_270)
        ).select(
            Direction.SOUTH,
            false,
            BlockModelGenerators.Y_ROT_180
        ).select(
            Direction.SOUTH,
            true,
            BlockModelGenerators.X_ROT_180
        ).select(
            Direction.WEST,
            false,
            BlockModelGenerators.Y_ROT_270
        ).select(
            Direction.WEST,
            true,
            BlockModelGenerators.Y_ROT_90.then(BlockModelGenerators.X_ROT_180)
        ).select(
            Direction.NORTH, false, BlockModelGenerators.NOP
        ).select(
            Direction.NORTH,
            true,
            BlockModelGenerators.Y_ROT_180.then(BlockModelGenerators.X_ROT_180)
        )

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(typeDispatch).with(facingDispatch))
    }

    private fun createHephaestusForge(block: HephaestusForgeBlock) {
        val modelForTier = mutableMapOf<HephaestusForgeLevel, ItemModel.Unbaked>();

        val dispatch = PropertyDispatch.initial(ModBlockStateProperties.FORGE_TIER).generate {
            val textureMapping = hephaestusForge(it.asInt)
            val model = ModModelTemplates.HEPHAESTUS_FORGE.createWithSuffix(
                block,
                "_tier_" + it.asInt,
                textureMapping,
                this.modelOutput
            )

            modelForTier[it] = ItemModelUtils.plainModel(model)
            plainVariant(model)
        }

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(dispatch))

        defaultGenerators.itemModelOutput.accept(
            block.asItem(),
            ItemModelUtils.selectBlockItemProperty(
                ModBlockStateProperties.FORGE_TIER,
                modelForTier[HephaestusForgeLevel.ONE]!!,
                modelForTier
            )
        )
    }

    private fun createObelisk(block: Block) {
        val dispatch = PropertyDispatch.initial(ModBlockStateProperties.OBELISK_PART).generate {
            val textureMapping = ModTextureMapping.obelisk(block, it)
            val model = ModModelTemplates.OBELISK[it]!!
                .createWithSuffix(block, "_" + it.serializedName, textureMapping, this.modelOutput)

            plainVariant(model)
        }

        defaultGenerators.registerSimpleFlatItemModel(block.asItem())
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(dispatch))
    }

    private fun createMortar(block: Block) {
        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(
                block,
                plainVariant(ForbiddenArcanus.identifier("block/mortar"))
            )
        )
    }

    private fun createUtremJar(block: Block) {
        val textureMapping = utremJar(block)
        val model = plainVariant(ModModelTemplates.UTREM_JAR.create(block, textureMapping, this.modelOutput))

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model))
    }

    private fun createEssenceUtremJar() {
        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(
                ModBlocks.ESSENCE_UTREM_JAR.get(),
                plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.UTREM_JAR.get()))
            )
        )

        val utremJarModel = ModelLocationUtils.getModelLocation(ModBlocks.UTREM_JAR.get())

        defaultGenerators.itemModelOutput.accept(
            ModBlocks.ESSENCE_UTREM_JAR.get().asItem(),
            ItemModelUtils.composite(
                ItemModelUtils.plainModel(utremJarModel),
                ItemModelUtils.specialModel(
                    utremJarModel,
                    EssenceUtremJarSpecialRenderer.Unbaked()
                )
            )
        )
    }

    private fun createPillar(block: Block) {
        val dispatch = PropertyDispatch.initial(ModBlockStateProperties.PILLAR_TYPE, RotatedPillarBlock.AXIS)
            .generate { part, axis ->
                plainVariant(ForbiddenArcanus.identifier("block/arcane_polished_darkstone_pillar" + (if (part == PillarType.SINGLE) "" else "_" + (if (axis === Direction.Axis.Z) part.opposite else part).serializedName)))
                    .with(if (axis === Direction.Axis.X) BlockModelGenerators.Y_ROT_90 else BlockModelGenerators.NOP)
                    .with(if (axis === Direction.Axis.Y) BlockModelGenerators.NOP else BlockModelGenerators.X_ROT_90)
            }

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(dispatch))
    }

    private fun createHollowLog(block: Block) {
        val textureMapping = edelwoodLog()
        val model = plainVariant(ModModelTemplates.HOLLOW_LOG.create(block, textureMapping, this.modelOutput))

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model))
    }

    private fun createHollowLogWithFace(block: Block) {
        val textureMapping = edelwoodLogWithFace(false)
        val textureMappingWithLeaves = edelwoodLogWithFace(true)

        val model = plainVariant(ModModelTemplates.HOLLOW_LOG_FACE.create(block, textureMapping, this.modelOutput))
        val modelWithLaves = plainVariant(
            ModModelTemplates.HOLLOW_LOG_FACE_AND_LEAVES.createWithSuffix(
                block,
                "_leaves",
                textureMappingWithLeaves,
                this.modelOutput
            )
        )
        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(block)
                .with(
                    BlockModelGenerators.createBooleanModelDispatch(
                        ModBlockStateProperties.LEAVES,
                        modelWithLaves,
                        model
                    )
                ).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
        )
    }

    private fun createEdelwoodBranch() {
        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(
                ModBlocks.EDELWOOD_BRANCH.get(),
                plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.EDELWOOD_BRANCH.get()))
            ).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
        )
    }

    private fun createMagicalFarmland() {
        val textureMapping = TextureMapping()
            .put(TextureSlot.DIRT, Material(ForbiddenArcanus.identifier("block/magical_dirt")))
            .put(TextureSlot.TOP, TextureMapping.getBlockTexture(ModBlocks.MAGICAL_FARMLAND.get()))
        val moistTextureMapping = TextureMapping()
            .put(TextureSlot.DIRT, Material(ForbiddenArcanus.identifier("block/magical_dirt")))
            .put(TextureSlot.TOP, TextureMapping.getBlockTexture(ModBlocks.MAGICAL_FARMLAND.get(), "_moist"))

        val model = plainVariant(
            ModelTemplates.FARMLAND.create(
                ModBlocks.MAGICAL_FARMLAND.get(),
                textureMapping,
                this.modelOutput
            )
        )
        val moistModel = plainVariant(
            ModelTemplates.FARMLAND.create(
                ModelLocationUtils.getModelLocation(ModBlocks.MAGICAL_FARMLAND.get(), "_moist"),
                moistTextureMapping,
                this.modelOutput
            )
        )

        this.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(ModBlocks.MAGICAL_FARMLAND.get()).with(
                BlockModelGenerators.createEmptyOrFullDispatch(
                    BlockStateProperties.MOISTURE,
                    7,
                    moistModel,
                    model
                )
            )
        )
    }
}
