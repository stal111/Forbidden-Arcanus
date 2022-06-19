package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.properties.PillarType;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaBlockStateProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Mod Block State Provider <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.client.ModBlockStateProvider
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2021-02-18
 */
public class ModBlockStateProvider extends ValhelsiaBlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ForbiddenArcanus.REGISTRY_MANAGER, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Arrays.asList(
                ModBlocks.OBSIDIAN_SKULL,
                ModBlocks.OBSIDIAN_WALL_SKULL,
                ModBlocks.ETERNAL_OBSIDIAN_SKULL,
                ModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL,
                ModBlocks.THIN_CHERRY_LOG,
                ModBlocks.CLIBANO_MAIN_PART
        ).forEach(getRemainingBlocks()::remove);

        getRemainingBlocks().removeIf(registryObject -> registryObject.get() instanceof WallSignBlock);

        take(block -> cubeAllCutout(block, modLoc("block/arcane_crystal_ore/" + getName(block)), modLoc("block/arcane_crystal_ore/arcane_crystal_ore_layer"), mcLoc("block/stone")), ModBlocks.ARCANE_CRYSTAL_ORE, ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE);
        take(block -> cubeAllCutout(block, modLoc("block/runic_stone/" + getName(block)), modLoc("block/runic_stone/rune_layer"), mcLoc("block/stone")), ModBlocks.RUNIC_STONE, ModBlocks.RUNIC_DEEPSLATE, ModBlocks.RUNIC_DARKSTONE);
        take(block -> cubeAllCutout(block, modLoc("block/" + getName(block)), modLoc("block/xpetrified_ore_layer"), modLoc("block/" + getName(block))), ModBlocks.XPETRIFIED_ORE);

        take(block -> cubeColumn(block, modLoc("block/cut_soulless_sandstone_side"), modLoc("block/cut_soulless_sandstone")), ModBlocks.CUT_SOULLESS_SANDSTONE);

        take(this::withExistingModel,
                ModBlocks.UTREM_JAR,
                ModBlocks.NIPA,
                ModBlocks.PETRIFIED_ROOT,
                ModBlocks.HEPHAESTUS_FORGE,
                ModBlocks.ARCANE_DRAGON_EGG,
                ModBlocks.DARKSTONE_PEDESTAL,
                ModBlocks.ARCANE_DARKSTONE_PEDESTAL
        );
        take(this::randomRotation, ModBlocks.DARKSTONE);
        take(block -> simpleBlock(block, models().cross(getName(block), modLoc("block/" + getName(block)))), ModBlocks.YELLOW_ORCHID);
        take(block -> pixieUtremJarBlock(block, false), ModBlocks.PIXIE_UTREM_JAR);
        take(block -> pixieUtremJarBlock(block, true), ModBlocks.CORRUPTED_PIXIE_UTREM_JAR);
        take(this::mushroomBlock, ModBlocks.FUNGYSS_BLOCK);
        take(block -> logBlock((RotatedPillarBlock) block), ModBlocks.FUNGYSS_STEM, ModBlocks.CHERRY_LOG, ModBlocks.AURUM_LOG, ModBlocks.STRIPPED_AURUM_LOG, ModBlocks.STRIPPED_CHERRY_LOG);
        take(block -> axisBlock((RotatedPillarBlock) block, modLoc("block/fungyss_stem"), modLoc("block/fungyss_stem")), ModBlocks.FUNGYSS_HYPHAE);
        take(block -> {
            String name = getName(block).substring(0, getName(block).length() - 5);
            axisBlock((RotatedPillarBlock) block, modLoc("block/" + name + "_log"), modLoc("block/" + name + "_log"));
        }, ModBlocks.CHERRY_WOOD, ModBlocks.AURUM_WOOD, ModBlocks.STRIPPED_CHERRY_WOOD, ModBlocks.STRIPPED_AURUM_WOOD);
        take(block -> crossCropBlock((CropBlock) block), ModBlocks.GOLDEN_ORCHID, ModBlocks.STRANGE_ROOT);
        take(this::magicalFarmlandBlock, ModBlocks.MAGICAL_FARMLAND);
        take(this::leafCarpetBlock, ModBlocks.CHERRY_LEAF_CARPET);

        ResourceLocation polishedDarkstone = modLoc("block/polished_darkstone");
        take(block -> pressurePlateBlock(block, polishedDarkstone), ModBlocks.POLISHED_DARKSTONE_PRESSURE_PLATE);
        take(block -> buttonBlock((ButtonBlock) block, polishedDarkstone), ModBlocks.POLISHED_DARKSTONE_BUTTON);
        take(this::runicChiseledPolishedDarkstone, ModBlocks.RUNIC_CHISELED_POLISHED_DARKSTONE);
        take(this::pillarBlock, ModBlocks.ARCANE_POLISHED_DARKSTONE_PILLAR, ModBlocks.ARCANE_POLISHED_DARKSTONE_ROD);

        take(this::arcaneCrystalObelisk, ModBlocks.ARCANE_CRYSTAL_OBELISK);

        take(block -> simpleBlock(block, models().cross(getName(block), modLoc("block/" + getName(block)))), ModBlocks.FUNGYSS, ModBlocks.GROWING_EDELWOOD);

        take(this::edelwoodLogBlock, ModBlocks.EDELWOOD_LOG);
        take(this::carvedEdelwoodLogBlock, ModBlocks.CARVED_EDELWOOD_LOG);
        take(block -> horizontalBlock(block, getExistingModel(modLoc("edelwood_branch")), 90), ModBlocks.EDELWOOD_BRANCH);

        take(block -> horizontalBlock(block, models().getExistingFile(modLoc("block/edelwood_ladder"))), ModBlocks.EDELWOOD_LADDER);
        take(block -> pressurePlateBlock(block, modLoc("block/arcane_gold_block")), ModBlocks.ARCANE_GOLD_PRESSURE_PLATE);

        take(block -> signBlock(ModBlocks.FUNGYSS_SIGN.getFirst().get(), ModBlocks.FUNGYSS_SIGN.getSecond().get()), ModBlocks.FUNGYSS_SIGN.getFirst());
        take(block -> signBlock(ModBlocks.CHERRY_SIGN.getFirst().get(), ModBlocks.CHERRY_SIGN.getSecond().get()), ModBlocks.CHERRY_SIGN.getFirst());
        take(block -> signBlock(ModBlocks.AURUM_SIGN.getFirst().get(), ModBlocks.AURUM_SIGN.getSecond().get()), ModBlocks.AURUM_SIGN.getFirst());
        take(block -> signBlock(ModBlocks.EDELWOOD_SIGN.getFirst().get(), ModBlocks.EDELWOOD_SIGN.getSecond().get()), ModBlocks.EDELWOOD_SIGN.getFirst());

        take(this::cherryFlowerVinesBlock, ModBlocks.CHERRY_FLOWER_VINES);
        take(this::cherryFlowerVinesPlantBlock, ModBlocks.CHERRY_FLOWER_VINES_PLANT);

        take(block -> {
            ResourceLocation front = modLoc("block/clibano_combustion/clibano_center_front_off");
            ResourceLocation side = modLoc("block/clibano_combustion/clibano_center_side");
            ResourceLocation top = modLoc("block/clibano_combustion/clibano_center_top");

            horizontalBlock(block, models().cube(getName(block), side, top, front, side, side, side).texture("particle", side));
        }, ModBlocks.CLIBANO_CORE);
        take(block -> {
            ModelFile bottom = getExistingModel(modLoc("clibano_corner_bottom"));
            ModelFile top = getExistingModel(modLoc("clibano_corner_top"));

            horizontalBlock(block, state -> state.getValue(BlockStateProperties.BOTTOM) ? bottom : top, 90);
        }, ModBlocks.CLIBANO_CORNER);
        take(block -> {
            ModelFile side = models().withExistingParent(getName(block) + "_side", modLoc("block/clibano_center")).texture("texture", modLoc("block/clibano_combustion/clibano_center_side"));
            ModelFile top = models().withExistingParent(getName(block) + "_top", modLoc("block/clibano_center")).texture("texture", modLoc("block/clibano_combustion/clibano_center_top"));
            ModelFile frontOff = models().withExistingParent(getName(block) + "_front_off", modLoc("block/clibano_center")).texture("texture", modLoc("block/clibano_combustion/clibano_center_front_off"));
            ModelFile frontFire = models().withExistingParent(getName(block) + "_front_fire", modLoc("block/clibano_center")).texture("texture", modLoc("block/clibano_combustion/clibano_center_front_fire"));
            ModelFile frontBlueFire = models().withExistingParent(getName(block) + "_front_blue_fire", modLoc("block/clibano_center")).texture("texture", modLoc("block/clibano_combustion/clibano_center_front_blue_fire"));

            directionalBlock(block, state -> switch (state.getValue(ModBlockStateProperties.CLIBANO_CENTER_TYPE)) {
                case SIDE -> side;
                case TOP -> top;
                case FRONT_OFF -> frontOff;
                case FRONT_FIRE -> frontFire;
                case FRONT_BLUE_FIRE -> frontBlueFire;
            });
        }, ModBlocks.CLIBANO_CENTER);
        take(block -> {
            ResourceLocation off = modLoc("block/clibano_combustion/clibano_side_off");
            ResourceLocation fire = modLoc("block/clibano_combustion/clibano_side_fire");
            ResourceLocation blueFire = modLoc("block/clibano_combustion/clibano_side_blue_fire");

            models().withExistingParent(getName(block) + "_off", modLoc("block/clibano_side_horizontal")).texture("right", off).texture("left", off);
            models().withExistingParent(getName(block) + "_off_mirrored", modLoc("block/clibano_side_horizontal")).texture("right", off).texture("left", off);
            models().withExistingParent(getName(block) + "_fire", modLoc("block/clibano_side_horizontal")).texture("right", fire).texture("left", off);
            models().withExistingParent(getName(block) + "_fire_mirrored", modLoc("block/clibano_side_horizontal")).texture("right", off).texture("left", fire);
            models().withExistingParent(getName(block) + "_blue_fire", modLoc("block/clibano_side_horizontal")).texture("right", blueFire).texture("left", off);
            models().withExistingParent(getName(block) + "_blue_fire_mirrored", modLoc("block/clibano_side_horizontal")).texture("right", off).texture("left", blueFire);

            horizontalBlock(block, state -> getExistingModel(modLoc(getName(block) + "_"
                    + state.getValue(ModBlockStateProperties.CLIBANO_SIDE_TYPE).getSerializedName()
                    + (state.getValue(ModBlockStateProperties.MIRRORED) ? "_mirrored" : ""))));
        }, ModBlocks.CLIBANO_SIDE_HORIZONTAL);
        take(block -> {
            ResourceLocation off = modLoc("block/clibano_combustion/clibano_side_off");
            ResourceLocation fire = modLoc("block/clibano_combustion/clibano_side_fire");
            ResourceLocation blueFire = modLoc("block/clibano_combustion/clibano_side_blue_fire");

            models().withExistingParent(getName(block) + "_bottom_off", modLoc("block/clibano_side_vertical_bottom")).texture("side", off).texture("bottom", off);
            models().withExistingParent(getName(block) + "_bottom_fire", modLoc("block/clibano_side_vertical_bottom")).texture("side", fire).texture("bottom", off);
            models().withExistingParent(getName(block) + "_bottom_blue_fire", modLoc("block/clibano_side_vertical_bottom")).texture("side", blueFire).texture("bottom", off);

            models().withExistingParent(getName(block) + "_top_off", modLoc("block/clibano_side_vertical_top")).texture("side", off).texture("top", off);
            models().withExistingParent(getName(block) + "_top_fire", modLoc("block/clibano_side_vertical_top")).texture("side", fire).texture("top", off);
            models().withExistingParent(getName(block) + "_top_blue_fire", modLoc("block/clibano_side_vertical_top")).texture("side", blueFire).texture("top", off);

            horizontalBlock(block, state -> getExistingModel(modLoc(getName(block) + "_"
                    + (state.getValue(BlockStateProperties.BOTTOM) ? "bottom" : "top") + "_"
                    + state.getValue(ModBlockStateProperties.CLIBANO_SIDE_TYPE).getSerializedName()
            )));
        }, ModBlocks.CLIBANO_SIDE_VERTICAL);

        take(block -> chainBlock(block, modLoc("block/arcane_golden_chain")), ModBlocks.ARCANE_GOLDEN_CHAIN);

        forEach(block -> block instanceof FenceBlock, block -> {
            ResourceLocation resourceLocation = modLoc("block/" + getName(block).substring(0, getName(block).length() - 5));

            fenceBlock((FenceBlock) block, new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath().concat("planks")));
        });
        forEach(block -> block instanceof FenceGateBlock, block -> {
            ResourceLocation resourceLocation = modLoc("block/" + getName(block).substring(0, getName(block).length() - 10));

            fenceGateBlock((FenceGateBlock) block, new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath().concat("planks")));
        });
        forEach(block -> block instanceof ButtonBlock, block -> {
            ResourceLocation resourceLocation = modLoc("block/" + getName(block).substring(0, getName(block).length() - 6));

            buttonBlock((ButtonBlock) block, new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath().concat("planks")));
        });
        forEach(block -> block instanceof PressurePlateBlock && block.defaultBlockState().getMaterial() == Material.WOOD, block -> {
            ResourceLocation resourceLocation = modLoc("block/" + getName(block).substring(0, getName(block).length() - 14));

            pressurePlateBlock(block, new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath().concat("planks")));
        });
        forEach(block -> block instanceof DoorBlock, block -> doorBlock((DoorBlock) block, modLoc("block/" + getName(block) + "_bottom"), modLoc("block/" + getName(block) + "_top")));
        forEach(block -> block instanceof TrapDoorBlock, block -> trapdoorBlock((TrapDoorBlock) block, modLoc("block/" + getName(block)), true));
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
            ResourceLocation name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(((FlowerPotBlock) block).getContent()));
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

    private void randomRotation(Block block) {
        ModelFile model = models().cubeAll(getName(block), modLoc("block/" + getName(block)));
        ModelFile mirroredModel = models().withExistingParent(getName(block) + "_mirrored", mcLoc("block/cube_mirrored_all"))
                .texture("all", modLoc("block/" + getName(block)));

        getVariantBuilder(block).partialState().addModels(
                ConfiguredModel.builder()
                        .modelFile(model).nextModel()
                        .modelFile(mirroredModel).nextModel()
                        .modelFile(model).rotationY(180).nextModel()
                        .modelFile(mirroredModel).rotationY(180)
                        .build()
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

    private void pillarBlock(Block block) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            PillarType type = state.getValue(ModBlockStateProperties.PILLAR_TYPE);
            Direction.Axis axis = state.getValue(RotatedPillarBlock.AXIS);

            return ConfiguredModel.builder()
                    .modelFile(getExistingModel(modLoc(getName(block) + (type == PillarType.SINGLE ? "" :  "_" + type.getSerializedName()))))
                    .rotationX(axis != Direction.Axis.Y ? 90 : 0)
                    .rotationY(axis == Direction.Axis.X ? 90 : axis == Direction.Axis.Z ? 180 : 0)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
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

    private void edelwoodLogBlock(Block block) {
        getVariantBuilder(block).forAllStatesExcept(
                state -> ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("block/" + getName(block) + (state.getValue(ModBlockStateProperties.OILY) ? "_oily" : ""))))
                        .rotationX(state.getValue(BlockStateProperties.AXIS) != Direction.Axis.Y ? 90 : 0)
                        .rotationY(state.getValue(BlockStateProperties.AXIS) == Direction.Axis.X ? 90 : 0)
                        .build(),
                BlockStateProperties.WATERLOGGED
        );
    }

    private void carvedEdelwoodLogBlock(Block block) {
        getVariantBuilder(block).forAllStatesExcept(
                state -> ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("block/" + getName(block) + (state.getValue(ModBlockStateProperties.LEAVES) ? "_leaves" : "") + (state.getValue(ModBlockStateProperties.OILY) ? "_oily" : ""))))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build(),
                BlockStateProperties.WATERLOGGED
        );
    }

    private void crossCropBlock(CropBlock block) {
        IntegerProperty age = block.getAgeProperty();

        getVariantBuilder(block).forAllStates(
                state -> ConfiguredModel.builder()
                        .modelFile(models().cross(getName(block) + "_stage" + state.getValue(age), modLoc("block/" + getName(block) + "_stage" + state.getValue(age))))
                        .build()
        );
    }

    private void magicalFarmlandBlock(Block block) {
        ModelFile model = models().withExistingParent(getName(block), mcLoc("block/template_farmland"))
                .texture("dirt", modLoc("block/magical_dirt"))
                .texture("top", modLoc("block/magical_farmland"));
        ModelFile modelMoist = models().withExistingParent(getName(block) + "_moist", mcLoc("block/template_farmland"))
                .texture("dirt", modLoc("block/magical_dirt"))
                .texture("top", modLoc("block/magical_farmland_moist"));

        getVariantBuilder(block).forAllStates(
                state -> ConfiguredModel.builder()
                        .modelFile(state.getValue(FarmBlock.MOISTURE) == 7 ? modelMoist : model)
                        .build()
        );
    }

    private void leafCarpetBlock(Block block) {
        String name = this.getName(block);
        ModelFile model = models().getBuilder(name).parent(new ModelFile.UncheckedModelFile("builtin/generated")).texture("layer0", modLoc("block/" + name));

        getVariantBuilder(block).partialState().modelForState()
                .modelFile(model).rotationX(90).nextModel()
                .modelFile(model).rotationX(90).rotationY(90).nextModel()
                .modelFile(model).rotationX(90).rotationY(180).nextModel()
                .modelFile(model).rotationX(90).rotationY(270)
                .addModel();

        this.models().withExistingParent(name + "_inventory", this.mcLoc("block/pressure_plate_up")).texture("texture", modLoc("block/" + name));
    }

    private void cherryFlowerVinesBlock(Block block) {
        String name = this.getName(block);
        ModelFile model = models().cross(name, modLoc("block/" + name));

        getVariantBuilder(block).partialState().modelForState().modelFile(model).addModel();
    }

    private void cherryFlowerVinesPlantBlock(Block block) {
        String name = this.getName(block);
        ModelFile model0 = models().cross(name + "0", modLoc("block/" + name + "0"));
        ModelFile model1 = models().cross(name + "1", modLoc("block/" + name + "1"));

        getVariantBuilder(block).partialState().modelForState()
                .modelFile(model0).nextModel()
                .modelFile(model1)
                .addModel();
    }


    //TODO: remove when forge fixes their door provider

    public void doorBlock(DoorBlock block, ResourceLocation bottom, ResourceLocation top) {
        doorBlockInternal(block, ForgeRegistries.BLOCKS.getKey(block).toString(), bottom, top);
    }

    private void doorBlockInternal(DoorBlock block, String baseName, ResourceLocation bottom, ResourceLocation top) {
        ModelFile bottomLeft = doorBottomLeft(baseName + "_bottom_left", bottom, top);
        ModelFile bottomLeftOpen = doorBottomLeftOpen(baseName + "_bottom_left_open", bottom, top);
        ModelFile bottomRight = doorBottomRight(baseName + "_bottom_right", bottom, top);
        ModelFile bottomRightOpen = doorBottomRightOpen(baseName + "_bottom_right_open", bottom, top);
        ModelFile topLeft = doorTopLeft(baseName + "_top_left", bottom, top);
        ModelFile topLeftOpen = doorTopLeftOpen(baseName + "_top_left_open", bottom, top);
        ModelFile topRight = doorTopRight(baseName + "_top_right", bottom, top);
        ModelFile topRightOpen = doorTopRightOpen(baseName + "_top_right_open", bottom, top);
        doorBlock(block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);

    }

    public void doorBlock(DoorBlock block, ModelFile bottomLeft, ModelFile bottomLeftOpen , ModelFile bottomRight, ModelFile bottomRightOpen, ModelFile topLeft, ModelFile topLeftOpen, ModelFile topRight, ModelFile topRightOpen) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            int yRot = ((int) state.getValue(DoorBlock.FACING).toYRot()) + 90;
            boolean right = state.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT;
            boolean open = state.getValue(DoorBlock.OPEN);

            if (open) {
                yRot += 90;
            }

            if (right && open) {
                yRot += 180;
            }

            yRot %= 360;
            return ConfiguredModel.builder().modelFile(state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? (right ? ( open ? bottomRightOpen : bottomRight ) : ( open ? bottomLeftOpen : bottomLeft )) : (right ? ( open ? topRightOpen : topRight) : ( open ? topLeftOpen : topLeft)))
                    .rotationY(yRot)
                    .build();
            }, DoorBlock.POWERED);

    }

    public BlockModelBuilder doorBottomLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return door(name, "door_bottom_left", bottom, top);
    }

    public BlockModelBuilder doorBottomLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return door(name, "door_bottom_left_open", bottom, top);
    }

    public BlockModelBuilder doorBottomRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return door(name, "door_bottom_right", bottom, top);
    }

    public BlockModelBuilder doorBottomRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return door(name, "door_bottom_right_open", bottom, top);
    }

    public BlockModelBuilder doorTopLeft(String name, ResourceLocation bottom, ResourceLocation top) {
        return door(name, "door_top_left", bottom, top);
    }

    public BlockModelBuilder doorTopLeftOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return door(name, "door_top_left_open", bottom, top);
    }

    public BlockModelBuilder doorTopRight(String name, ResourceLocation bottom, ResourceLocation top) {
        return door(name, "door_top_right", bottom, top);
    }

    public BlockModelBuilder doorTopRightOpen(String name, ResourceLocation bottom, ResourceLocation top) {
        return door(name, "door_top_right_open", bottom, top);
    }

    private BlockModelBuilder door(String name, String model, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, "block/" + model)
                .texture("bottom", bottom)
                .texture("top", top);
    }
}