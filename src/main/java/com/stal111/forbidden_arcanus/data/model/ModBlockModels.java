package com.stal111.forbidden_arcanus.data.model;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.DeskBlock;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.pedestal.PedestalBlock;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.data.FABlockFamilies;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 09.09.2023
 */
public class ModBlockModels {

    private final BlockModelGenerators generators;
    private final Consumer<BlockStateGenerator> blockStateOutput;
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

    private final Consumer<Item> skippedAutoItemModels;

    private final Map<Block, TexturedModel> texturedModels = ImmutableMap.<Block, TexturedModel>builder()
            .put(ModBlocks.CUT_SOULLESS_SANDSTONE.get(), TexturedModel.COLUMN.get(ModBlocks.CUT_SOULLESS_SANDSTONE.get()))
            .build();

    private ModBlockModels(BlockModelGenerators generators, Consumer<Item> skippedAutoItemModels) {
        this.generators = generators;
        this.blockStateOutput = generators.blockStateOutput;
        this.modelOutput = generators.modelOutput;
        this.skippedAutoItemModels = skippedAutoItemModels;

        this.generators.fullBlockModelCustomGenerators = ImmutableMap.<Block, BlockModelGenerators.BlockStateGeneratorSupplier>builder()
                .put(ModBlocks.DARKSTONE.get(), BlockModelGenerators::createMirroredCubeGenerator)
                .build();
    }

    public static void create(BlockModelGenerators generators, Consumer<Item> skippedAutoItemModels) {
        new ModBlockModels(generators, skippedAutoItemModels).createModels();
    }

    public void createModels() {
        FABlockFamilies.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(blockFamily -> this.family(blockFamily.getBaseBlock()).generateFor(blockFamily));

        this.generators.createTrivialCube(ModBlocks.OBSIDIANSTEEL_BLOCK.get());
        this.generators.createTrivialCube(ModBlocks.SOULLESS_SAND.get());
        this.generators.createTrivialCube(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get());
        this.generators.createTrivialCube(ModBlocks.TILED_POLISHED_DARKSTONE_BRICKS.get());
        this.createForbiddenomicon(ModBlocks.FORBIDDENOMICON.get());
        this.createDesk(ModBlocks.DESK.get(), false);
        this.createDesk(ModBlocks.RESEARCH_DESK.get(), true);
        this.createPedestal(ModBlocks.DARKSTONE_PEDESTAL.get());
        this.createPedestal(ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get());
        this.createClibanoCore(ModBlocks.CLIBANO_CORE.get());
        this.createClibanoCenter(ModBlocks.CLIBANO_CENTER.get());
        this.createClibanoCorner(ModBlocks.CLIBANO_CORNER.get());
        this.createClibanoSideHorizontal(ModBlocks.CLIBANO_SIDE_HORIZONTAL.get());
        this.createClibanoSideVertical(ModBlocks.CLIBANO_SIDE_VERTICAL.get());
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_1.get());
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_2.get());
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_3.get());
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_4.get());
        this.createHephaestusForge(ModBlocks.HEPHAESTUS_FORGE_TIER_5.get());
        this.createObelisk(ModBlocks.ARCANE_CRYSTAL_OBELISK.get());
        this.createObelisk(ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK.get());
        this.createUtremJar(ModBlocks.UTREM_JAR.get());

        this.blockEntityModels(ModelLocationUtils.getModelLocation(ModBlocks.OBSIDIAN_SKULL.getSkull()), Blocks.SOUL_SAND).createWithCustomBlockItemModel(ModelTemplates.SKULL_INVENTORY, ModBlocks.OBSIDIAN_SKULL.getSkull(), ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull(), ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull(), ModBlocks.FADING_OBSIDIAN_SKULL.getSkull(), ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull()).createWithoutBlockItem(ModBlocks.OBSIDIAN_SKULL.getWallSkull(), ModBlocks.CRACKED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FADING_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.AUREALIC_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.getWallSkull());
    }

    public BlockModelGenerators.BlockFamilyProvider family(Block block) {
        TexturedModel texturedmodel = this.texturedModels.getOrDefault(block, TexturedModel.CUBE.get(block));
        return this.generators.new BlockFamilyProvider(texturedmodel.getMapping()).fullBlock(block, texturedmodel.getTemplate());
    }

    private BlockModelGenerators.BlockEntityModelGenerator blockEntityModels(ResourceLocation modelLocation, Block block) {
        return this.generators.new BlockEntityModelGenerator(modelLocation, block);
    }

    private void createForbiddenomicon(Block block) {
        TextureMapping textureMapping = ModTextureMapping.forbiddenomicon(block);
        ResourceLocation model = ModModelTemplates.FORBIDDENOMICON.create(block, textureMapping, this.modelOutput);

        this.blockStateOutput.accept(createSimpleBlock(block, model).with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    private void createDesk(DeskBlock block, boolean research) {
        TextureMapping textureMapping = ModTextureMapping.desk(research);
        ResourceLocation model = ModModelTemplates.DESK.create(block, textureMapping, this.modelOutput);

        this.blockStateOutput.accept(createSimpleBlock(block, model).with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    private void createPedestal(PedestalBlock block) {
        TextureMapping textureMapping = ModTextureMapping.pedestal(block);
        ResourceLocation model = ModModelTemplates.PEDESTAL.create(block, textureMapping, this.modelOutput);

        this.blockStateOutput.accept(createSimpleBlock(block, model));
    }

    private void createClibanoCore(Block block) {
        TextureMapping textureMapping = ModTextureMapping.clibanoCore();
        ResourceLocation model = ModelTemplates.CUBE_ORIENTABLE.create(block, textureMapping, this.modelOutput);

        this.blockStateOutput.accept(createSimpleBlock(block, model).with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    private void createClibanoCenter(Block block) {
        PropertyDispatch dispatch = PropertyDispatch.property(ModBlockStateProperties.CLIBANO_CENTER_TYPE).generate(type -> {
            ResourceLocation model = ModModelTemplates.CLIBANO_CENTER.createWithSuffix(block, "_" + type.getSerializedName(), ModTextureMapping.clibanoCenter(type), this.modelOutput);

            return Variant.variant().with(VariantProperties.MODEL, model);
        });

        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(dispatch).with(BlockModelGenerators.createFacingDispatch()));
    }

    private void createClibanoCorner(Block block) {
        ResourceLocation model = new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/clibano_corner");

        PropertyDispatch dispatch = PropertyDispatch.property(BlockStateProperties.BOTTOM).generate(bottom -> {
            return Variant.variant().with(VariantProperties.X_ROT, bottom ? VariantProperties.Rotation.R90 : VariantProperties.Rotation.R0);
        });

        this.blockStateOutput.accept(createSimpleBlock(block, model).with(dispatch).with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    private void createClibanoSideHorizontal(Block block) {
        PropertyDispatch typeDispatch = PropertyDispatch.property(ModBlockStateProperties.CLIBANO_SIDE_TYPE).generate(type -> {
            TextureMapping textureMapping = ModTextureMapping.clibanoSide(type);
            ResourceLocation model = ModModelTemplates.CLIBANO_SIDE_HORIZONTAL.createWithSuffix(block, "_" + type.getSerializedName(), textureMapping, this.modelOutput);

            return Variant.variant().with(VariantProperties.MODEL, model);
        });

        PropertyDispatch facingDispatch = PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, ModBlockStateProperties.MIRRORED)
                .select(Direction.EAST, false, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.EAST, true, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.SOUTH, false, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.SOUTH, true, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.WEST, true, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.NORTH, false, Variant.variant())
                .select(Direction.NORTH, true, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180));

        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(typeDispatch).with(facingDispatch));
    }

    private void createClibanoSideVertical(Block block) {
        PropertyDispatch typeDispatch = PropertyDispatch.property(ModBlockStateProperties.CLIBANO_SIDE_TYPE).generate(type -> {
            TextureMapping textureMapping = ModTextureMapping.clibanoSide(type);
            ResourceLocation model = ModModelTemplates.CLIBANO_SIDE_VERTICAL.createWithSuffix(block, "_" + type.getSerializedName(), textureMapping, this.modelOutput);

            return Variant.variant().with(VariantProperties.MODEL, model);
        });

        PropertyDispatch facingDispatch = PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, ModBlockStateProperties.MIRRORED)
                .select(Direction.EAST, false, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.EAST, true, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.SOUTH, false, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.SOUTH, true, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, false, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.WEST, true, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                .select(Direction.NORTH, false, Variant.variant())
                .select(Direction.NORTH, true, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180));

        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(typeDispatch).with(facingDispatch));
    }

    private void createHephaestusForge(HephaestusForgeBlock block) {
        TextureMapping textureMapping = ModTextureMapping.hephaestusForge(block.getLevel().getAsInt());
        ResourceLocation model = ModModelTemplates.HEPHAESTUS_FORGE.create(block, textureMapping, this.modelOutput);

        this.blockStateOutput.accept(createSimpleBlock(block, model));
    }

    private void createObelisk(Block block) {
        PropertyDispatch dispatch = PropertyDispatch.property(ModBlockStateProperties.OBELISK_PART).generate(part -> {
            TextureMapping textureMapping = ModTextureMapping.obelisk(block, part);
            ResourceLocation model = ModModelTemplates.OBELISK.get(part).createWithSuffix(block, "_" + part.getSerializedName(), textureMapping, this.modelOutput);

            return Variant.variant().with(VariantProperties.MODEL, model);
        });

        this.createSimpleFlatItemModel(block.asItem());
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(dispatch));
    }

    private void createUtremJar(Block block) {
        TextureMapping textureMapping = ModTextureMapping.utremJar(block);
        ResourceLocation model = ModModelTemplates.UTREM_JAR.create(block, textureMapping, this.modelOutput);

        this.blockStateOutput.accept(createSimpleBlock(block, model));
    }

    static MultiVariantGenerator createSimpleBlock(Block block, ResourceLocation resourceLocation) {
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, resourceLocation));
    }

    void createSimpleFlatItemModel(Item item) {
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), this.modelOutput);
    }
}
