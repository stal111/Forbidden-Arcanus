package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.pattern.UpwardsBlockPattern;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.material.Material;

/**
 * Mod Block Patterns <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ModBlockPatterns
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-01-15
 */
public class ModBlockPatterns {

    public static final BlockPattern HEPHAESTUS_PATTERN = BlockPatternBuilder.start()
            .aisle("***~~~***", "***PPP***")
            .aisle("*~~~~~~~*", "*PPPAPPP*")
            .aisle("*~~~~~~~*", "*PAPPPAP*")
            .aisle("~~~~~~~~~", "PPPPCPPPP")
            .aisle("~~~~^~~~~", "PAPCACPAP")
            .aisle("~~~~~~~~~", "PPPPCPPPP")
            .aisle("*~~~~~~~*", "*PAPPPAP*")
            .aisle("*~~~~~~~*", "*PPPAPPP*")
            .aisle("***~~~***", "***PPP***")
            .where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SMITHING_TABLE)))
            .where('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get())))
            .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get())))
            .where('P', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE.get())))
            .where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
            .where('*', BlockInWorld.hasState(BlockStatePredicate.ANY))
            .build();

    public static final BlockPattern BASE_HEPHAESTUS_PATTERN = BlockPatternBuilder.start()
            .aisle("***PPP***")
            .aisle("*PPPAPPP*")
            .aisle("*PAPPPAP*")
            .aisle("PPPPCPPPP")
            .aisle("PAPCACPAP")
            .aisle("PPPPCPPPP")
            .aisle("*PAPPPAP*")
            .aisle("*PPPAPPP*")
            .aisle("***PPP***")
            .where('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get())))
            .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get())))
            .where('P', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE.get())))
            .where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
            .where('*', BlockInWorld.hasState(BlockStatePredicate.ANY))
            .build();

    public static final BlockPattern ARCANE_CRYSTAL_OBELISK_PATTERN = BlockPatternBuilder.start()
            .aisle("#", "#", "X")
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.ARCANE_CRYSTAL_BLOCK.get())))
            .where('X', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE.get())))
            .build();

    public static final BlockPattern CLIBANO_COMBUSTION_BASE = UpwardsBlockPattern.of(BlockPatternBuilder.start()
            .aisle("PBP", "BBB", "PBP")
            .aisle("BBB", "BAB", "BBB")
            .aisle("PBP", "BCB", "PBP")
            .where('P', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE.get())))
            .where('B', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE_BRICKS.get())))
            .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.CLIBANO_CORE.get())))
            .where('A', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
    );

    public static final BlockPattern CLIBANO_COMBUSTION = UpwardsBlockPattern.of(BlockPatternBuilder.start()
            .aisle("CVC", "HXH", "CVC")
            .aisle("VXV", "XMX", "VXV")
            .aisle("CVC", "HXH", "CVC")
            .where('C', BlockInWorld.hasState(BlockPredicate.forBlock(ModBlocks.CLIBANO_CORNER.get())))
            .where('V', BlockInWorld.hasState(BlockPredicate.forBlock(ModBlocks.CLIBANO_SIDE_VERTICAL.get())))
            .where('H', BlockInWorld.hasState(BlockPredicate.forBlock(ModBlocks.CLIBANO_SIDE_HORIZONTAL.get())))
            .where('M', BlockInWorld.hasState(BlockPredicate.forBlock(ModBlocks.CLIBANO_MAIN_PART.get())))
            .where('X', BlockInWorld.hasState(BlockPredicate.forBlock(ModBlocks.CLIBANO_CENTER.get())))
    );
}
