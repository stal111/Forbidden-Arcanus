package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ObsidianSkullBlock;
import com.stal111.forbidden_arcanus.block.ObsidianWallSkullBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.registry.BlockRegistryHelper;

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
}