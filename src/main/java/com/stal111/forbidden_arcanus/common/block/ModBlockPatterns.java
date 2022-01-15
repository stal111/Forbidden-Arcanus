package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.material.Material;

/**
 * Mod Block Patterns <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ModBlockPatterns
 *
 * @author stal111
 * @version 1.18.1 - 2.0.1
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
}
