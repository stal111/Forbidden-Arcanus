package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.block.PillarBlock;
import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaStandingSignBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaWallSignBlock;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaBlockStateProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Mod Block State Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.client.ModBlockStateProvider
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-02-18
 */
public class ModBlockStateProvider extends ValhelsiaBlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ForbiddenArcanus.REGISTRY_MANAGER, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getRemainingBlocks().removeAll(Arrays.asList(NewModBlocks.OBSIDIAN_SKULL, NewModBlocks.OBSIDIAN_WALL_SKULL, NewModBlocks.ETERNAL_OBSIDIAN_SKULL, NewModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL));
        getRemainingBlocks().removeIf(registryObject -> registryObject.get() instanceof WallSignBlock);

        take(block -> cubeAllCutout(block, modLoc("block/arcane_crystal_ore/" + getName(block)), modLoc("block/arcane_crystal_ore/arcane_crystal_ore_layer"), mcLoc("block/stone")), NewModBlocks.ARCANE_CRYSTAL_ORE, NewModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE);
        take(block -> cubeAllCutout(block, modLoc("block/runic_stone/" + getName(block)), modLoc("block/runic_stone/rune_layer"), mcLoc("block/stone")), NewModBlocks.RUNIC_STONE, NewModBlocks.RUNIC_DEEPSLATE, NewModBlocks.RUNIC_DARKSTONE);
        take(block -> cubeAllCutout(block, modLoc("block/" + getName(block)), modLoc("block/xpetrified_ore_layer"), modLoc("block/" + getName(block))), NewModBlocks.XPETRIFIED_ORE);

        take(block -> cubeColumn(block, modLoc("block/cut_soulless_sandstone_side"), modLoc("block/cut_soulless_sandstone")), NewModBlocks.CUT_SOULLESS_SANDSTONE);

        take(this::withExistingModel,
                NewModBlocks.UTREM_JAR,
                NewModBlocks.NIPA,
                NewModBlocks.ARCANE_POLISHED_DARKSTONE_ROD,
                NewModBlocks.PETRIFIED_ROOT,
                NewModBlocks.HEPHAESTUS_FORGE,
                NewModBlocks.ARCANE_DRAGON_EGG,
                NewModBlocks.DARKSTONE_PEDESTAL,
                NewModBlocks.ARCANE_DARKSTONE_PEDESTAL
        );
        take(block -> pixieUtremJarBlock(block, false), NewModBlocks.PIXIE_UTREM_JAR);
        take(block -> pixieUtremJarBlock(block, true), NewModBlocks.CORRUPTED_PIXIE_UTREM_JAR);
        take(block -> simpleBlock(block, models().cross(Objects.requireNonNull(block.getRegistryName()).getPath(), modLoc("block/fungyss"))), NewModBlocks.FUNGYSS);
        take(this::mushroomBlock, NewModBlocks.FUNGYSS_BLOCK);
        take(block -> logBlock((RotatedPillarBlock) block), NewModBlocks.FUNGYSS_STEM);
        take(block -> axisBlock((RotatedPillarBlock) block, modLoc("block/fungyss_stem"), modLoc("block/fungyss_stem")), NewModBlocks.FUNGYSS_HYPHAE);

        ResourceLocation fungyssPlanks = modLoc("block/fungyss_planks");
        take(block -> pressurePlateBlock(block, fungyssPlanks), NewModBlocks.FUNGYSS_PRESSURE_PLATE);
        take(block -> buttonBlock((ButtonBlock) block, fungyssPlanks), NewModBlocks.FUNGYSS_BUTTON);
        take(block -> trapdoorBlock((TrapDoorBlock) block, modLoc("block/fungyss_trapdoor"), true), NewModBlocks.FUNGYSS_TRAPDOOR);
        take(block -> doorBlock((DoorBlock) block, modLoc("block/fungyss_door_bottom"), modLoc("block/fungyss_door_top")), NewModBlocks.FUNGYSS_DOOR);

        take(block -> fenceBlock((FenceBlock) block, fungyssPlanks), NewModBlocks.FUNGYSS_FENCE);
        take(block -> fenceGateBlock((FenceGateBlock) block, fungyssPlanks), NewModBlocks.FUNGYSS_FENCE_GATE);

        ResourceLocation polishedDarkstone = modLoc("block/polished_darkstone");
        take(block -> pressurePlateBlock(block, polishedDarkstone), NewModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE);
        take(block -> buttonBlock((ButtonBlock) block, polishedDarkstone), NewModBlocks.POLISHED_DARKSTONE_BUTTON);
        take(this::runicChiseledPolishedDarkstone, NewModBlocks.RUNIC_CHISELED_POLISHED_DARKSTONE);
        take(this::arcanePolishedDarkstonePillar, NewModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR);

        take(this::arcaneCrystalObelisk, NewModBlocks.ARCANE_CRYSTAL_OBELISK);

        take(block -> signBlock(NewModBlocks.FUNGYSS_SIGN.getFirst().get(), NewModBlocks.FUNGYSS_SIGN.getSecond().get()), NewModBlocks.FUNGYSS_SIGN.getFirst());
        take(block -> signBlock(NewModBlocks.CHERRYWOOD_SIGN.getFirst().get(), NewModBlocks.CHERRYWOOD_SIGN.getSecond().get()), NewModBlocks.CHERRYWOOD_SIGN.getFirst());
        take(block -> signBlock(NewModBlocks.MYSTERYWOOD_SIGN.getFirst().get(), NewModBlocks.MYSTERYWOOD_SIGN.getSecond().get()), NewModBlocks.MYSTERYWOOD_SIGN.getFirst());
        take(block -> signBlock(NewModBlocks.EDELWOOD_SIGN.getFirst().get(), NewModBlocks.EDELWOOD_SIGN.getSecond().get()), NewModBlocks.EDELWOOD_SIGN.getFirst());

        take(block -> chainBlock(block, modLoc("block/arcane_golden_chain")), NewModBlocks.ARCANE_GOLDEN_CHAIN);

        forEach(block -> block instanceof SaplingBlock, block -> simpleBlock(block, models().cross(getName(block), modLoc("block/" + getName(block)))));
        forEach(block -> block instanceof IronBarsBlock, block -> paneBlock((IronBarsBlock) block, modLoc("block/" + getName(block).substring(0, getName(block).length() - 5)), modLoc("block/" + getName(block) + "_top")));
        forEach(block -> block instanceof StairBlock, block -> {
            ResourceLocation resourceLocation = getName(block).contains("brick") ? modLoc("block/" + getName(block).substring(0, getName(block).length() - 7).concat("s")) : modLoc("block/" + getName(block).substring(0, getName(block).length() - 7));

            if (block.defaultBlockState().getMaterial() == Material.WOOD) {
                resourceLocation = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath().concat("_planks"));
            }
            stairsBlock((StairBlock) block, resourceLocation);
        });
        forEach(block -> block instanceof WallBlock, block -> {
            ResourceLocation resourceLocation = getName(block).contains("brick") ? modLoc("block/" + getName(block).substring(0, getName(block).length() - 5).concat("s")) : modLoc("block/" + getName(block).substring(0, getName(block).length() - 5));

            if (block.defaultBlockState().getMaterial() == Material.WOOD) {
                resourceLocation = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath().concat("_planks"));
            }
            wallBlock((WallBlock) block, resourceLocation);
        });

        forEach(block -> block instanceof FlowerPotBlock, block -> {
            ResourceLocation name = Objects.requireNonNull(((FlowerPotBlock) block).getContent().getRegistryName());
            simpleFlowerPotBlock(block, new ResourceLocation(name.getNamespace(), "block/" + name.getPath()));
        });

        List<Block> slabs = new ArrayList<>();
        forEach(block -> block instanceof SlabBlock, slabs::add);

        forEach(this::simpleBlock);

        slabs.forEach(block -> {
            ResourceLocation resourceLocation = getName(block).contains("brick") ? modLoc("block/" + getName(block).substring(0, getName(block).length() - 5).concat("s")) : modLoc("block/" + getName(block).substring(0, getName(block).length() - 5));

            if (block.defaultBlockState().getMaterial() == Material.WOOD) {
                resourceLocation = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath().concat("_planks"));
            }
            slabBlock((SlabBlock) block, resourceLocation, resourceLocation);
        });
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

    private void cubeColumn(Block block, ResourceLocation side, ResourceLocation end) {
        simpleBlock(block, models().cubeColumn(getName(block), side, end));
    }

    private void pixieUtremJarBlock(Block block, boolean corrupted) {
        ModelFile model = models().withExistingParent(getName(block), modLoc("block/pixie_utrem_jar_template"))
                .texture("bottle_block_top", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_top"))
                .texture("bottle_block_bottom", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_bottom"))
                .texture("bottle_block_side", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_side"))
                .texture("pixie", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_pixie"));

        simpleBlock(block, model);
    }

    private void signBlock(StandingSignBlock signBlock, WallSignBlock wallSignBlock) {
        ModelFile sign = models().sign(getName(signBlock), modLoc("block/" + signBlock.type().name().split(":")[1] + "_planks"));
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
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