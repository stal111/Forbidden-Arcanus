package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.predicate.BlockPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.valhelsia.valhelsia_core.api.common.block.pattern.UpwardsBlockPattern;

/**
 * Mod Block Patterns <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ModBlockPatterns
 *
 * @author stal111
 * @since 2022-01-15
 */
public class ModBlockPatterns {

    public static final BlockPattern HEPHAESTUS_PATTERN = UpwardsBlockPattern.builder()
            .aisle("   ~~~   ", "   PPP   ")
            .aisle(" ~~~#~~~ ", " PPPAPPP ")
            .aisle(" ~#~~~#~ ", " PAPPPAP ")
            .aisle("~~~~~~~~~", "PPPPCPPPP")
            .aisle("~#~~^~~#~", "PAPCACPAP")
            .aisle("~~~~~~~~~", "PPPPCPPPP")
            .aisle(" ~#~~~#~ ", " PAPPPAP ")
            .aisle(" ~~~#~~~ ", " PPPAPPP ")
            .aisle("   ~~~   ", "   PPP   ")
            .where('^', BlockStatePredicate.forBlock(Blocks.SMITHING_TABLE))
            .where('A', BlockStatePredicate.forBlock(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get()))
            .where('C', BlockStatePredicate.forBlock(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get()))
            .where('P', BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE.get()))
            .where('~', BlockBehaviour.BlockStateBase::isAir)
            .where('#', state -> state.is(ModTags.Blocks.PEDESTALS) || state.isAir())
            .anchor(4, 0, 4).symmetric().build();

    public static final UpwardsBlockPattern BASE_HEPHAESTUS_PATTERN = UpwardsBlockPattern.builder()
            .aisle("   PPP   ")
            .aisle(" PPPAPPP ")
            .aisle(" PAPPPAP ")
            .aisle("PPPPCPPPP")
            .aisle("PAPCACPAP")
            .aisle("PPPPCPPPP")
            .aisle(" PAPPPAP ")
            .aisle(" PPPAPPP ")
            .aisle("   PPP   ")
            .where('A', BlockStatePredicate.forBlock(ModBlocks.GILDED_CHISELED_POLISHED_DARKSTONE.get()))
            .where('C', BlockStatePredicate.forBlock(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get()))
            .where('P', BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE.get()))
            .anchor(4, 0, 4).symmetric().build();

    public static final BlockPattern ARCANE_CRYSTAL_OBELISK_PATTERN = UpwardsBlockPattern.builder()
            .aisle("#", "#", "X")
            .where('#', BlockStatePredicate.forBlock(ModBlocks.ARCANE_CRYSTAL_BLOCK.get()))
            .where('X', BlockStatePredicate.forBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE.get()))
            .anchor(0, 0, 0).anchor(0, 1, 0).anchor(0, 2, 0).symmetric().build();

    public static final BlockPattern CLIBANO_COMBUSTION_BASE = UpwardsBlockPattern.builder()
            .aisle("PBP", "BBB", "PBP")
            .aisle("BBB", "BAB", "BBB")
            .aisle("PBP", "BCB", "PBP")
            .where('P', BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE.get()))
            .where('B', BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE_BRICKS.get()))
            .where('C', BlockStatePredicate.forBlock(ModBlocks.CLIBANO_CORE.get()))
            .where('A', BlockBehaviour.BlockStateBase::isAir)
            .anchor(1, 1, 2).build();

    public static final BlockPattern CLIBANO_COMBUSTION = UpwardsBlockPattern.builder()
            .aisle("CVC", "HXH", "CVC")
            .aisle("VXV", "XMX", "VXV")
            .aisle("CVC", "HXH", "CVC")
            .where('C', BlockPredicate.forBlock(ModBlocks.CLIBANO_CORNER.get()))
            .where('V', BlockPredicate.forBlock(ModBlocks.CLIBANO_SIDE_VERTICAL.get()))
            .where('H', BlockPredicate.forBlock(ModBlocks.CLIBANO_SIDE_HORIZONTAL.get()))
            .where('M', BlockPredicate.forBlock(ModBlocks.CLIBANO_MAIN_PART.get()))
            .where('X', BlockPredicate.forBlock(ModBlocks.CLIBANO_CENTER.get()))
            .symmetric().build();
}
