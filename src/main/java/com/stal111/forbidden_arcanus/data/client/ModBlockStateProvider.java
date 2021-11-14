package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.block.PillarBlock;
import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.Objects;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaBlockStateProvider;

/**
 * Mod Block State Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.client.ModBlockStateProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-18
 */
public class ModBlockStateProvider extends ValhelsiaBlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ForbiddenArcanus.REGISTRY_MANAGER, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getRemainingBlocks().removeAll(Arrays.asList(NewModBlocks.OBSIDIAN_SKULL, NewModBlocks.OBSIDIAN_WALL_SKULL, NewModBlocks.ETERNAL_OBSIDIAN_SKULL, NewModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL));

        take(block -> cubeAllCutout(block, modLoc("block/arcane_crystal_ore/" + getName(block)), modLoc("block/arcane_crystal_ore/arcane_crystal_ore_layer"), mcLoc("block/stone")),
                NewModBlocks.ARCANE_CRYSTAL_ORE, NewModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE);
        take(block -> cubeAllCutout(block, modLoc("block/runic_stone/" + getName(block)), modLoc("block/runic_stone/rune_layer"), mcLoc("block/stone")),
                NewModBlocks.RUNIC_STONE, NewModBlocks.RUNIC_DEEPSLATE, NewModBlocks.RUNIC_DARKSTONE);

        take(this::withExistingModel, NewModBlocks.UTREM_JAR, NewModBlocks.NIPA, NewModBlocks.ARCANE_POLISHED_DARKSTONE_ROD);
        take(block -> pixieUtremJarBlock(block, false), NewModBlocks.PIXIE_UTREM_JAR);
        take(block -> pixieUtremJarBlock(block, true), NewModBlocks.CORRUPTED_PIXIE_UTREM_JAR);
        take(block -> simpleBlock(block, models().cross(Objects.requireNonNull(block.getRegistryName()).getPath(), modLoc("block/fungyss"))), NewModBlocks.FUNGYSS);
        take(this::mushroomBlock, NewModBlocks.FUNGYSS_BLOCK);
        take(block -> logBlock((RotatedPillarBlock) block), NewModBlocks.FUNGYSS_STEM);
        take(block -> axisBlock((RotatedPillarBlock) block, modLoc("block/fungyss_stem"), modLoc("block/fungyss_stem")), NewModBlocks.FUNGYSS_HYPHAE);

        ResourceLocation fungyssPlanks = modLoc("block/fungyss_planks");
        take(block -> slabBlock((SlabBlock) block, fungyssPlanks, fungyssPlanks), NewModBlocks.FUNGYSS_SLAB);
        take(block -> stairsBlock((StairBlock) block, fungyssPlanks), NewModBlocks.FUNGYSS_STAIRS);
        take(block -> pressurePlateBlock(block, fungyssPlanks), NewModBlocks.FUNGYSS_PRESSURE_PLATE);
        take(block -> buttonBlock((ButtonBlock) block, fungyssPlanks), NewModBlocks.FUNGYSS_BUTTON);
        take(block -> trapdoorBlock((TrapDoorBlock) block, modLoc("block/fungyss_trapdoor"), true), NewModBlocks.FUNGYSS_TRAPDOOR);
        take(block -> doorBlock((DoorBlock) block, modLoc("block/fungyss_door_bottom"), modLoc("block/fungyss_door_top")), NewModBlocks.FUNGYSS_DOOR);

        take(block -> fenceBlock((FenceBlock) block, fungyssPlanks), NewModBlocks.FUNGYSS_FENCE);
        take(block -> fenceGateBlock((FenceGateBlock) block, fungyssPlanks), NewModBlocks.FUNGYSS_FENCE_GATE);

        ResourceLocation darkstone = modLoc("block/darkstone");
        take(block -> slabBlock((SlabBlock) block, darkstone, darkstone), NewModBlocks.DARKSTONE_SLAB);
        take(block -> stairsBlock((StairBlock) block, darkstone), NewModBlocks.DARKSTONE_STAIRS);
        take(block -> wallBlock((WallBlock) block, darkstone), NewModBlocks.DARKSTONE_WALL);

        ResourceLocation polishedDarkstone = modLoc("block/polished_darkstone");
        take(block -> slabBlock((SlabBlock) block, polishedDarkstone, polishedDarkstone), NewModBlocks.POLISHED_DARKSTONE_SLAB);
        take(block -> stairsBlock((StairBlock) block, polishedDarkstone), NewModBlocks.POLISHED_DARKSTONE_STAIRS);
        take(block -> wallBlock((WallBlock) block, polishedDarkstone), NewModBlocks.POLISHED_DARKSTONE_WALL);
        take(block -> pressurePlateBlock(block, polishedDarkstone), NewModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE);
        take(block -> buttonBlock((ButtonBlock) block, polishedDarkstone), NewModBlocks.POLISHED_DARKSTONE_BUTTON);
        take(this::runicChiseledPolishedDarkstone, NewModBlocks.RUNIC_CHISELED_POLISHED_DARKSTONE);

        ResourceLocation polishedDarkstoneBricks = modLoc("block/polished_darkstone_bricks");
        take(block -> slabBlock((SlabBlock) block, polishedDarkstoneBricks, polishedDarkstoneBricks), NewModBlocks.POLISHED_DARKSTONE_BRICK_SLAB);
        take(block -> stairsBlock((StairBlock) block, polishedDarkstoneBricks), NewModBlocks.POLISHED_DARKSTONE_BRICK_STAIRS);
        take(block -> wallBlock((WallBlock) block, polishedDarkstoneBricks), NewModBlocks.POLISHED_DARKSTONE_BRICK_WALL);

        ResourceLocation arcanePolishedDarkstone = modLoc("block/arcane_polished_darkstone");
        take(block -> slabBlock((SlabBlock) block, arcanePolishedDarkstone, arcanePolishedDarkstone), NewModBlocks.ARCANE_POLISHED_DARKSTONE_SLAB);
        take(block -> stairsBlock((StairBlock) block, arcanePolishedDarkstone), NewModBlocks.ARCANE_POLISHED_DARKSTONE_STAIRS);
        take(block -> wallBlock((WallBlock) block, arcanePolishedDarkstone), NewModBlocks.ARCANE_POLISHED_DARKSTONE_WALL);
        take(this::arcanePolishedDarkstonePillar, NewModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR);

        take(block -> paneBlock((IronBarsBlock) block, modLoc("block/arcane_glass"), modLoc("block/arcane_glass_pane_top")), NewModBlocks.ARCANE_GLASS_PANE);
        take(this::withExistingModel, NewModBlocks.DARKSTONE_PEDESTAL, NewModBlocks.ARCANE_DARKSTONE_PEDESTAL);
        take(this::arcaneCrystalObelisk, NewModBlocks.ARCANE_CRYSTAL_OBELISK);

        take(block -> chainBlock(block, modLoc("block/arcane_golden_chain")), NewModBlocks.ARCANE_GOLDEN_CHAIN);

        forEach(block -> block instanceof FlowerPotBlock, block -> {
            ResourceLocation name = Objects.requireNonNull(((FlowerPotBlock) block).getContent().getRegistryName());
            simpleFlowerPotBlock(block, new ResourceLocation(name.getNamespace(), "block/" + name.getPath()));
        });

        forEach(this::simpleBlock);
    }

    private void cubeAllCutout(Block block, ResourceLocation texture, ResourceLocation emissiveTexture) {
        simpleBlock(block, models().withExistingParent(getName(block), modLoc("block/cube_all_cutout"))
                .texture("all", texture)
                .texture("cutout", emissiveTexture)
        );
    }

    private void cubeAllCutout(Block block, ResourceLocation texture, ResourceLocation emissiveTexture, ResourceLocation particle) {
        simpleBlock(block, models().withExistingParent(getName(block), modLoc("block/cube_all_cutout"))
                .texture("particle", particle)
                .texture("all", texture)
                .texture("cutout", emissiveTexture)
        );
    }

    private void pixieUtremJarBlock(Block block, boolean corrupted) {
        ModelFile model = models().withExistingParent(getName(block), modLoc("block/pixie_utrem_jar_template"))
                .texture("bottle_block_top", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_top"))
                .texture("bottle_block_bottom", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_bottom"))
                .texture("bottle_block_side", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_side"))
                .texture("pixie", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_pixie"));

        simpleBlock(block, model);
    }

    private void mushroomBlock(Block block) {
        ModelFile model = models().withExistingParent(getName(block), mcLoc("block/template_single_face")).texture("texture", modLoc("block/" + getName(block)));
        ModelFile modelInside = models().withExistingParent(getName(block) + "_inside", mcLoc("block/template_single_face")).texture("texture", modLoc("block/" + getName(block) + "_inside")).ao(false);

        getMultipartBuilder(block)
                .part().modelFile(model).addModel().condition(BlockStateProperties.NORTH, true).end()
                .part().modelFile(model).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.EAST, true).end()
                .part().modelFile(model).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.SOUTH, true).end()
                .part().modelFile(model).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.WEST, true).end()
                .part().modelFile(model).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, true).end()
                .part().modelFile(model).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.DOWN, true).end()

                .part().modelFile(modelInside).addModel().condition(BlockStateProperties.NORTH, false).end()
                .part().modelFile(modelInside).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.EAST, false).end()
                .part().modelFile(modelInside).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.SOUTH, false).end()
                .part().modelFile(modelInside).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.WEST, false).end()
                .part().modelFile(modelInside).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, false).end()
                .part().modelFile(modelInside).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.DOWN, false).end();

        this.models().singleTexture(getName(block) + "_inventory", mcLoc("block/cube_all"), "all", modLoc("block/" + getName(block)));
    }

    private void runicChiseledPolishedDarkstone(Block block) {
        getVariantBuilder(block)
                .partialState().with(ModBlockStateProperties.ACTIVATED, false).modelForState().modelFile(models().cubeColumn(getName(block), modLoc("block/runic_chiseled_polished_darkstone"), modLoc("block/chiseled_polished_darkstone"))).addModel()
                .partialState().with(ModBlockStateProperties.ACTIVATED, true).modelForState().modelFile(models().withExistingParent(getName(block) + "_activated", modLoc("block/cube_column_side_layer")).texture("end", modLoc("block/chiseled_polished_darkstone")).texture("side", modLoc("block/runic_chiseled_polished_darkstone_activated")).texture("layer", modLoc("block/runic_chiseled_polished_darkstone_layer"))).addModel();
    }

    private void arcanePolishedDarkstonePillar(Block block) {
        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                .modelFile(getExistingModel(modLoc(getName(block) + "_" + state.getValue(PillarBlock.TYPE).getSerializedName())))
                .rotationX(state.getValue(RotatedPillarBlock.AXIS) != Direction.Axis.Y ? 90 : 0)
                .rotationY(state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.X ? 90 : 0)
                .build(), BlockStateProperties.WATERLOGGED);
    }

    private void arcaneCrystalObelisk(Block block) {
        getVariantBuilder(block).forAllStatesExcept(
                state -> ConfiguredModel.builder()
                        .modelFile(getExistingModel(modLoc(getName(block) + "_" + state.getValue(ArcaneCrystalObeliskBlock.PART).getSerializedName())))
                        .build(),
                BlockStateProperties.WATERLOGGED,
                ModBlockStateProperties.RITUAL
        );
    }

    private void chainBlock(Block block, ResourceLocation resourceLocation) {
        ModelFile model = models().withExistingParent(getName(block), mcLoc("block/chain"))
                .texture("particle", resourceLocation)
                .texture("all", resourceLocation);

        getVariantBuilder(block).forAllStatesExcept(
                state -> ConfiguredModel.builder()
                        .modelFile(model)
                        .rotationX(state.getValue(BlockStateProperties.AXIS) != Direction.Axis.Y ? 90 : 0)
                        .rotationY(state.getValue(BlockStateProperties.AXIS) == Direction.Axis.X ? 90 : 0)
                        .build(),
                BlockStateProperties.WATERLOGGED
        );
    }
}