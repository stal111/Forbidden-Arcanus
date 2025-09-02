package com.stal111.forbidden_arcanus.datagen.model

import com.google.common.collect.ImmutableMap
import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.block.DeskBlock
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock
import com.stal111.forbidden_arcanus.common.block.pedestal.PedestalBlock
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart
import com.stal111.forbidden_arcanus.common.block.properties.PillarType
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoCenterType
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoSideType
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
import net.minecraft.core.Direction
import net.minecraft.data.BlockFamily
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.data.models.blockstates.PropertyDispatch
import net.minecraft.data.models.blockstates.Variant
import net.minecraft.data.models.blockstates.VariantProperties
import net.minecraft.data.models.model.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.valhelsia.dataforge.model.BlockModelGenerator
import java.util.function.BiFunction
import java.util.function.Function

/**
 * @author stal111
 * @since 09.09.2023
 */
class ModBlockModels(private val defaultGenerators: BlockModelGenerators) : BlockModelGenerator(defaultGenerators) {
    private val texturedModels = mapOf(
        ModBlocks.CUT_SOULLESS_SANDSTONE.get() to TexturedModel.COLUMN.get(ModBlocks.CUT_SOULLESS_SANDSTONE.get())
    )

    override fun generate() {
        val generators = this.defaultGenerators

        FABlockFamilies.getAllFamilies()
            .filter { obj: BlockFamily? -> obj!!.shouldGenerateModel() }
            .forEach { blockFamily: BlockFamily? -> this.family(blockFamily!!.baseBlock).generateFor(blockFamily) }

        this.createSimpleFlatItemModel(ModBlocks.DEORUM_CHAIN.get().asItem())
        this.createSimpleFlatItemModel(ModBlocks.ARCANE_DRAGON_EGG.get().asItem())
        this.createSimpleFlatItemModel(ModBlocks.EDELWOOD_LADDER.get())

        generators.createTrivialCube(ModBlocks.SOULLESS_SAND.get())
        generators.createTrivialCube(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get())
        generators.createTrivialCube(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get())
        generators.createTrivialCube(ModBlocks.QUANTUM_INJECTOR.get())
        this.createEmissiveLayerCube(ModBlocks.ARCANE_CRYSTAL_ORE.get(), "arcane_crystal_ore")
        this.createEmissiveLayerCube(ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get(), "arcane_crystal_ore")
        this.createEmissiveLayerCube(ModBlocks.RUNIC_STONE.get(), "runic_stone")
        this.createEmissiveLayerCube(ModBlocks.RUNIC_DEEPSLATE.get(), "runic_stone")
        this.createEmissiveLayerCube(ModBlocks.RUNIC_DARKSTONE.get(), "runic_stone")
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
        this.createPedestal(ModBlocks.DARKSTONE_PEDESTAL.get())
        this.createPedestal(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get())
        this.createClibanoCore(ModBlocks.CLIBANO_CORE.get())
        this.createClibanoCenter(ModBlocks.CLIBANO_CENTER.get())
        this.createClibanoCorner(ModBlocks.CLIBANO_CORNER.get())
        this.createClibanoSideHorizontal(ModBlocks.CLIBANO_SIDE_HORIZONTAL.get())
        this.createClibanoSideVertical(ModBlocks.CLIBANO_SIDE_VERTICAL.get())
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_1.get())
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_2.get())
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_3.get())
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_4.get())
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_5.get())
        this.createObelisk(ModBlocks.ARCANE_CRYSTAL_OBELISK.get())
        this.createObelisk(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK.get())
        this.createUtremJar(ModBlocks.UTREM_JAR.get())
        this.blockStateOutput.accept(
            createSimpleBlock(
                ModBlocks.ESSENCE_UTREM_JAR.get(), ModelLocationUtils.getModelLocation(
                    ModBlocks.UTREM_JAR.get()
                )
            )
        )
        ModModelTemplates.UTREM_JAR_ITEM.create(
            ModelLocationUtils.getModelLocation(ModBlocks.ESSENCE_UTREM_JAR.get().asItem()), TextureMapping.particle(
                ModBlocks.UTREM_JAR.get()
            ), this.modelOutput
        )
        this.createPillar(ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR.get())
        this.createNonTemplateModelBlock(ModBlocks.QUANTUM_CORE.get())
        generators.createDoor(ModBlocks.DEORUM_DOOR.get())
        generators.createTrapdoor(ModBlocks.DEORUM_TRAPDOOR.get())
        generators.createAxisAlignedPillarBlockCustomModel(
            ModBlocks.DEORUM_CHAIN.get(), ModelLocationUtils.getModelLocation(
                ModBlocks.DEORUM_CHAIN.get()
            )
        )
        generators.createGlassBlocks(ModBlocks.DEORUM_GLASS.get(), ModBlocks.DEORUM_GLASS_PANE.get())
        generators.createGlassBlocks(ModBlocks.RUNIC_GLASS.get(), ModBlocks.RUNIC_GLASS_PANE.get())
        generators.createLantern(ModBlocks.DEORUM_LANTERN.get())
        generators.createLantern(ModBlocks.DEORUM_SOUL_LANTERN.get())
        generators.createPlant(
            ModBlocks.FUNGYSS.get(),
            ModBlocks.POTTED_FUNGYSS.get(),
            BlockModelGenerators.TintState.NOT_TINTED
        )
        generators.createPlant(
            ModBlocks.AURUM_SAPLING.get(),
            ModBlocks.POTTED_AURUM_SAPLING.get(),
            BlockModelGenerators.TintState.NOT_TINTED
        )
        generators.createPlant(
            ModBlocks.GROWING_EDELWOOD.get(),
            ModBlocks.POTTED_GROWING_EDELWOOD.get(),
            BlockModelGenerators.TintState.NOT_TINTED
        )
        generators.createPlant(
            ModBlocks.YELLOW_ORCHID.get(),
            ModBlocks.POTTED_YELLOW_ORCHID.get(),
            BlockModelGenerators.TintState.NOT_TINTED
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
        this.createNonTemplateHorizontalBlock(ModBlocks.EDELWOOD_LADDER.get())
        this.createMortar(ModBlocks.MORTAR.get())

        this.blockEntityModels(
            ModelLocationUtils.getModelLocation(ModBlocks.OBSIDIAN_SKULL.getSkull()),
            Blocks.SOUL_SAND
        ).createWithCustomBlockItemModel(
            ModelTemplates.SKULL_INVENTORY,
            ModBlocks.OBSIDIAN_SKULL.getSkull(),
            ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull(),
            ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull(),
            ModBlocks.FADING_OBSIDIAN_SKULL.getSkull(),
            ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull(),
            ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull()
        ).createWithoutBlockItem(
            ModBlocks.OBSIDIAN_SKULL.getWallSkull(),
            ModBlocks.CRACKED_OBSIDIAN_SKULL.getWallSkull(),
            ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getWallSkull(),
            ModBlocks.FADING_OBSIDIAN_SKULL.getWallSkull(),
            ModBlocks.AUREALIC_OBSIDIAN_SKULL.getWallSkull(),
            ModBlocks.ETERNAL_OBSIDIAN_SKULL.getWallSkull()
        )
    }

    fun family(block: Block): BlockModelGenerators.BlockFamilyProvider {
        val texturedModel = this.texturedModels.getOrDefault(block, TexturedModel.CUBE.get(block))
        return this.defaultGenerators.BlockFamilyProvider(texturedModel.mapping)
            .fullBlock(block, texturedModel.template)
    }

    private fun blockEntityModels(
        modelLocation: ResourceLocation,
        block: Block
    ): BlockModelGenerators.BlockEntityModelGenerator {
        return this.defaultGenerators.BlockEntityModelGenerator(modelLocation, block)
    }

    private fun createEmissiveCube(block: Block) {
        this.blockStateOutput.accept(
            createSimpleBlock(
                block,
                ModModelTemplates.CUBE_ALL_EMISSIVE.create(block, emissiveCube(block), this.modelOutput)
            )
        )
    }

    private fun createEmissiveLayerCube(block: Block, folder: String) {
        this.blockStateOutput.accept(
            createSimpleBlock(
                block,
                ModModelTemplates.CUBE_ALL_EMISSIVE_LAYER.create(
                    block,
                    emissiveLayerCube(block, folder),
                    this.modelOutput
                )
            )
        )
    }

    private fun createForbiddenomicon(block: Block) {
        val textureMapping = forbiddenomicon(block)
        val model = ModModelTemplates.FORBIDDENOMICON.create(block, textureMapping, this.modelOutput)

        this.blockStateOutput.accept(
            createSimpleBlock(
                block,
                model
            ).with(BlockModelGenerators.createHorizontalFacingDispatch())
        )
    }

    private fun createDesk(block: DeskBlock, research: Boolean) {
        val textureMapping = desk(research)
        val model = ModModelTemplates.DESK.create(block, textureMapping, this.modelOutput)

        this.blockStateOutput.accept(
            createSimpleBlock(
                block,
                model
            ).with(BlockModelGenerators.createHorizontalFacingDispatch())
        )
    }

    private fun createPedestal(block: PedestalBlock) {
        val textureMapping = pedestal(block)
        val model = ModModelTemplates.PEDESTAL.create(block, textureMapping, this.modelOutput)

        this.blockStateOutput.accept(createSimpleBlock(block, model))
    }

    private fun createClibanoCore(block: Block) {
        val textureMapping = clibanoCore()
        val model = ModelTemplates.CUBE_ORIENTABLE.create(block, textureMapping, this.modelOutput)

        this.blockStateOutput.accept(
            createSimpleBlock(
                block,
                model
            ).with(BlockModelGenerators.createHorizontalFacingDispatch())
        )
    }

    private fun createClibanoCenter(block: Block) {
        val dispatch =
            PropertyDispatch.property<ClibanoCenterType?>(ModBlockStateProperties.CLIBANO_CENTER_TYPE).generate(
                Function { type: ClibanoCenterType? ->
                    val model = ModModelTemplates.CLIBANO_CENTER.createWithSuffix(
                        block,
                        "_" + type!!.getSerializedName(),
                        ModTextureMapping.clibanoCenter(type),
                        this.modelOutput
                    )
                    Variant.variant().with<ResourceLocation?>(VariantProperties.MODEL, model)
                })

        this.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(block).with(dispatch).with(BlockModelGenerators.createFacingDispatch())
        )
    }

    private fun createClibanoCorner(block: Block) {
        val model = ForbiddenArcanus.location("block/clibano_corner")

        val dispatch =
            PropertyDispatch.property(BlockStateProperties.BOTTOM).generate { bottom ->
                Variant.variant().with(
                    VariantProperties.X_ROT,
                    if (bottom) VariantProperties.Rotation.R90 else VariantProperties.Rotation.R0
                )
            }

        this.blockStateOutput.accept(
            createSimpleBlock(block, model)
                .with(dispatch)
                .with(BlockModelGenerators.createHorizontalFacingDispatch())
        )
    }

    private fun createClibanoSideHorizontal(block: Block) {
        val typeDispatch =
            PropertyDispatch.property<ClibanoSideType>(ModBlockStateProperties.CLIBANO_SIDE_TYPE).generate { type ->
                val textureMapping = ModTextureMapping.clibanoSide(type!!)
                val model = ModModelTemplates.CLIBANO_SIDE_HORIZONTAL.createWithSuffix(
                    block,
                    "_" + type.serializedName,
                    textureMapping,
                    this.modelOutput
                )
                Variant.variant().with<ResourceLocation>(VariantProperties.MODEL, model)
            }

        val facingDispatch: PropertyDispatch = PropertyDispatch.properties(
            BlockStateProperties.HORIZONTAL_FACING,
            ModBlockStateProperties.MIRRORED
        )
            .select(
                Direction.EAST,
                false,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            )
            .select(
                Direction.EAST,
                true,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0)
                    .with(
                        VariantProperties.X_ROT, VariantProperties.Rotation.R180
                    )
            )
            .select(
                Direction.SOUTH,
                false,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
            )
            .select(
                Direction.SOUTH,
                true,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                    .with(
                        VariantProperties.X_ROT, VariantProperties.Rotation.R180
                    )
            )
            .select(
                Direction.WEST,
                false,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
            )
            .select(
                Direction.WEST,
                true,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                    .with(
                        VariantProperties.X_ROT, VariantProperties.Rotation.R180
                    )
            )
            .select(Direction.NORTH, false, Variant.variant())
            .select(
                Direction.NORTH,
                true,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                    .with(
                        VariantProperties.X_ROT, VariantProperties.Rotation.R180
                    )
            )

        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(typeDispatch).with(facingDispatch))
    }

    private fun createClibanoSideVertical(block: Block) {
        val typeDispatch =
            PropertyDispatch.property<ClibanoSideType>(ModBlockStateProperties.CLIBANO_SIDE_TYPE).generate { type ->
                val textureMapping = ModTextureMapping.clibanoSide(type!!)
                val model = ModModelTemplates.CLIBANO_SIDE_VERTICAL.createWithSuffix(
                    block,
                    "_" + type.serializedName,
                    textureMapping,
                    this.modelOutput
                )
                Variant.variant().with<ResourceLocation>(VariantProperties.MODEL, model)
            }

        val facingDispatch: PropertyDispatch = PropertyDispatch.properties(
            BlockStateProperties.HORIZONTAL_FACING,
            ModBlockStateProperties.MIRRORED
        )
            .select(
                Direction.EAST,
                false,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            )
            .select(
                Direction.EAST,
                true,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                    .with(
                        VariantProperties.X_ROT, VariantProperties.Rotation.R180
                    )
            )
            .select(
                Direction.SOUTH,
                false,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
            )
            .select(
                Direction.SOUTH,
                true,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0)
                    .with(
                        VariantProperties.X_ROT, VariantProperties.Rotation.R180
                    )
            )
            .select(
                Direction.WEST,
                false,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
            )
            .select(
                Direction.WEST,
                true,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                    .with(
                        VariantProperties.X_ROT, VariantProperties.Rotation.R180
                    )
            )
            .select(Direction.NORTH, false, Variant.variant())
            .select(
                Direction.NORTH,
                true,
                Variant.variant()
                    .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                    .with(
                        VariantProperties.X_ROT, VariantProperties.Rotation.R180
                    )
            )

        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(typeDispatch).with(facingDispatch))
    }

    private fun createHephaestusForge(block: HephaestusForgeBlock) {
        val textureMapping = hephaestusForge(block.level.asInt)
        val model = ModModelTemplates.HEPHAESTUS_FORGE.create(block, textureMapping, this.modelOutput)

        this.blockStateOutput.accept(createSimpleBlock(block, model))
    }

    private fun createObelisk(block: Block) {
        val dispatch = PropertyDispatch.property<ObeliskPart>(ModBlockStateProperties.OBELISK_PART)
            .generate { part ->
                val textureMapping = ModTextureMapping.obelisk(block, part)
                val model = ModModelTemplates.OBELISK[part]!!
                    .createWithSuffix(block, "_" + part.serializedName, textureMapping, this.modelOutput)
                Variant.variant().with<ResourceLocation>(VariantProperties.MODEL, model)
            }

        this.createSimpleFlatItemModel(block.asItem())
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(dispatch))
    }

    private fun createMortar(block: Block) {
        this.blockStateOutput.accept(createSimpleBlock(block, ForbiddenArcanus.location("block/mortar")))
    }

    private fun createUtremJar(block: Block) {
        val textureMapping = utremJar(block)
        val model = ModModelTemplates.UTREM_JAR.create(block, textureMapping, this.modelOutput)

        this.blockStateOutput.accept(createSimpleBlock(block, model))
    }

    private fun createPillar(block: Block) {
        val dispatch = PropertyDispatch.properties(
            ModBlockStateProperties.PILLAR_TYPE,
            RotatedPillarBlock.AXIS
        ).generate { part, axis ->
            Variant.variant()
                .with(
                    VariantProperties.MODEL,
                    ForbiddenArcanus.location("block/arcane_polished_darkstone_pillar" + (if (part == PillarType.SINGLE) "" else "_" + (if (axis === Direction.Axis.Z) part!!.getOpposite() else part)!!.getSerializedName()))
                )
                .with(
                    VariantProperties.Y_ROT,
                    if (axis === Direction.Axis.X) VariantProperties.Rotation.R90 else VariantProperties.Rotation.R0
                )
                .with(
                    VariantProperties.X_ROT,
                    if (axis === Direction.Axis.Y) VariantProperties.Rotation.R0 else VariantProperties.Rotation.R90
                )
        }

        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(dispatch))
    }

    private fun createHollowLog(block: Block) {
        val textureMapping = edelwoodLog()
        val model = ModModelTemplates.HOLLOW_LOG.create(block, textureMapping, this.modelOutput)

        this.blockStateOutput.accept(createSimpleBlock(block, model))
    }

    private fun createHollowLogWithFace(block: Block) {
        val textureMapping = edelwoodLogWithFace(false)
        val textureMappingWithLeaves = edelwoodLogWithFace(true)

        val model = ModModelTemplates.HOLLOW_LOG_FACE.create(block, textureMapping, this.modelOutput)
        val modelWithLaves = ModModelTemplates.HOLLOW_LOG_FACE_AND_LEAVES.createWithSuffix(
            block,
            "_leaves",
            textureMappingWithLeaves,
            this.modelOutput
        )

        val dispatch = PropertyDispatch.property(ModBlockStateProperties.LEAVES)
            .generate { hasLeaves ->
                Variant.variant().with(
                    VariantProperties.MODEL, if (hasLeaves) modelWithLaves else model
                )
            }

        this.blockStateOutput.accept(
            createSimpleBlock(
                block,
                model
            ).with(BlockModelGenerators.createHorizontalFacingDispatch()).with(dispatch)
        )
    }

    private fun createEdelwoodBranch() {
        this.blockStateOutput.accept(
            createSimpleBlock(
                ModBlocks.EDELWOOD_BRANCH.get(), ModelLocationUtils.getModelLocation(
                    ModBlocks.EDELWOOD_BRANCH.get()
                )
            ).with(BlockModelGenerators.createHorizontalFacingDispatch())
        )
    }

    private fun createMagicalFarmland() {
        val textureMapping = TextureMapping()
            .put(TextureSlot.DIRT, ForbiddenArcanus.location("block/magical_dirt"))
            .put(TextureSlot.TOP, TextureMapping.getBlockTexture(ModBlocks.MAGICAL_FARMLAND.get()))
        val moistTextureMapping = TextureMapping()
            .put(TextureSlot.DIRT, ForbiddenArcanus.location("block/magical_dirt"))
            .put(TextureSlot.TOP, TextureMapping.getBlockTexture(ModBlocks.MAGICAL_FARMLAND.get(), "_moist"))

        val model = ModelTemplates.FARMLAND.create(ModBlocks.MAGICAL_FARMLAND.get(), textureMapping, this.modelOutput)
        val moistModel = ModelTemplates.FARMLAND.create(
            TextureMapping.getBlockTexture(ModBlocks.MAGICAL_FARMLAND.get(), "_moist"),
            moistTextureMapping,
            this.modelOutput
        )

        this.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(ModBlocks.MAGICAL_FARMLAND.get()).with(
                BlockModelGenerators.createEmptyOrFullDispatch<Int?>(
                    BlockStateProperties.MOISTURE,
                    7,
                    moistModel,
                    model
                )
            )
        )
    }

    private fun createNonTemplateModelBlock(pBlock: Block, pModelBlock: Block = pBlock) {
        this.blockStateOutput.accept(createSimpleBlock(pBlock, ModelLocationUtils.getModelLocation(pModelBlock)))
    }

    private fun createNonTemplateHorizontalBlock(horizontalBlock: Block) {
        this.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(
                horizontalBlock, Variant.variant().with<ResourceLocation?>(
                    VariantProperties.MODEL, ModelLocationUtils.getModelLocation(horizontalBlock)
                )
            ).with(
                BlockModelGenerators.createHorizontalFacingDispatch()
            )
        )
    }

    fun createSimpleFlatItemModel(item: Item) {
        ModelTemplates.FLAT_ITEM.create(
            ModelLocationUtils.getModelLocation(item.asItem()),
            TextureMapping.layer0(item.asItem()),
            this.modelOutput
        )
    }

    fun createSimpleFlatItemModel(block: Block) {
        ModelTemplates.FLAT_ITEM.create(
            ModelLocationUtils.getModelLocation(block.asItem()),
            TextureMapping.layer0(block),
            this.modelOutput
        )
    }

    fun delegateItemModel(block: Block, resourceLocation: ResourceLocation) {
        this.modelOutput.accept(ModelLocationUtils.getModelLocation(block.asItem()), DelegatedModel(resourceLocation))
    }

    companion object {
        fun createSimpleBlock(block: Block, resourceLocation: ResourceLocation): MultiVariantGenerator {
            return MultiVariantGenerator.multiVariant(
                block,
                Variant.variant().with(VariantProperties.MODEL, resourceLocation)
            )
        }
    }
}
