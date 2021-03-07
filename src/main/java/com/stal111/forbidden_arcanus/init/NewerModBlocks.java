package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.registry.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.util.ValhelsiaRenderType;

/**
 * Newer Mod Blocks
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.NewerModBlocks
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NewerModBlocks {

    public static final BlockRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getBlockHelper();

    public static final RegistryObject<ObsidianSkullBlock> OBSIDIAN_SKULL = HELPER.registerNoItem("obsidian_skull", new ObsidianSkullBlock(Block.Properties.from(Blocks.SKELETON_SKULL)));
    public static final RegistryObject<ObsidianWallSkullBlock> OBSIDIAN_WALL_SKULL = HELPER.registerNoItem("obsidian_wall_skull", new ObsidianWallSkullBlock(Block.Properties.from(Blocks.SKELETON_SKULL).lootFrom(OBSIDIAN_SKULL)));
    public static final RegistryObject<ObsidianSkullBlock> ETERNAL_OBSIDIAN_SKULL = HELPER.registerNoItem("eternal_obsidian_skull", new ObsidianSkullBlock(Block.Properties.from(Blocks.SKELETON_SKULL)));
    public static final RegistryObject<ObsidianWallSkullBlock> ETERNAL_OBSIDIAN_WALL_SKULL = HELPER.registerNoItem("eternal_obsidian_wall_skull", new ObsidianWallSkullBlock(Block.Properties.from(Blocks.SKELETON_SKULL).lootFrom(ETERNAL_OBSIDIAN_SKULL)));
    public static final RegistryObject<UtremJarBlock> UTREM_JAR = HELPER.registerNoItem("utrem_jar", new UtremJarBlock(Block.Properties.from(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<PixieUtremJarBlock> PIXIE_UTREM_JAR = HELPER.register("pixie_utrem_jar", new PixieUtremJarBlock(ModItems.PIXIE, Block.Properties.from(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<PixieUtremJarBlock> CORRUPTED_PIXIE_UTREM_JAR = HELPER.register("corrupted_pixie_utrem_jar", new PixieUtremJarBlock(ModItems.CORRUPTED_PIXIE, Block.Properties.from(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<FungyssBlock> FUNGYSS = HELPER.register("fungyss", new FungyssBlock(Block.Properties.from(Blocks.WARPED_FUNGUS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<HugeMushroomBlock> FUNGYSS_BLOCK = HELPER.register("fungyss_block", new HugeMushroomBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BLUE).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> FUNGYSS_STEM = HELPER.register("fungyss_stem", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F).sound(SoundType.HYPHAE)));
    public static final RegistryObject<RotatedPillarBlock> FUNGYSS_HYPHAE = HELPER.register("fungyss_hyphae", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F).sound(SoundType.HYPHAE)));
    public static final RegistryObject<Block> FUNGYSS_PLANKS = HELPER.register("fungyss_planks", new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> FUNGYSS_SLAB = HELPER.register("fungyss_slab", new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StairsBlock> FUNGYSS_STAIRS = HELPER.register("fungyss_stairs", new StairsBlock(() -> NewerModBlocks.FUNGYSS_PLANKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<PressurePlateBlock> FUNGYSS_PRESSURE_PLATE = HELPER.register("fungyss_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<WoodButtonBlock> FUNGYSS_BUTTON = HELPER.register("fungyss_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> FUNGYSS_FENCE = HELPER.register("fungyss_fence", new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> FUNGYSS_FENCE_GATE = HELPER.register("fungyss_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));

}