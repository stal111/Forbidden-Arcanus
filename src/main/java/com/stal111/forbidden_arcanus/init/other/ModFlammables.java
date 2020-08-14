package com.stal111.forbidden_arcanus.init.other;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.mixin.AccessorFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public class ModFlammables {

    public static void registerFlammables() {
        registerLog(ModBlocks.EDELWOOD_LOG.getBlock());
        registerWoodenBlock(ModBlocks.EDELWOOD_PLANKS.getBlock());
        registerWoodenBlock(ModBlocks.ARCANE_EDELWOOD_PLANKS.getBlock());
        registerWoodenBlock(ModBlocks.EDELWOOD_SLAB.getBlock());
        registerWoodenBlock(ModBlocks.EDELWOOD_STAIRS.getBlock());
        registerWoodenBlock(ModBlocks.EDELWOOD_FENCE.getBlock());
        registerWoodenBlock(ModBlocks.EDELWOOD_FENCE_GATE.getBlock());

        registerLog(ModBlocks.CHERRYWOOD_LOG.getBlock());
        registerLog(ModBlocks.CHERRYWOOD.getBlock());
        registerLog(ModBlocks.STRIPPED_CHERRYWOOD_LOG.getBlock());
        registerLog(ModBlocks.STRIPPED_CHERRYWOOD.getBlock());
        registerLeaves(ModBlocks.CHERRYWOOD_LEAVES.getBlock());
        registerWoodenBlock(ModBlocks.CHERRYWOOD_PLANKS.getBlock());
        registerWoodenBlock(ModBlocks.CHERRYWOOD_SLAB.getBlock());
        registerWoodenBlock(ModBlocks.CHERRYWOOD_STAIRS.getBlock());
        registerWoodenBlock(ModBlocks.CHERRYWOOD_FENCE.getBlock());
        registerWoodenBlock(ModBlocks.CHERRYWOOD_FENCE_GATE.getBlock());

        registerLog(ModBlocks.MYSTERYWOOD_LOG.getBlock());
        registerLog(ModBlocks.MYSTERYWOOD.getBlock());
        registerLog(ModBlocks.STRIPPED_MYSTERYWOOD_LOG.getBlock());
        registerLog(ModBlocks.STRIPPED_MYSTERYWOOD.getBlock());
        registerLeaves(ModBlocks.MYSTERYWOOD_LEAVES.getBlock());
        registerWoodenBlock(ModBlocks.MYSTERYWOOD_PLANKS.getBlock());
        registerWoodenBlock(ModBlocks.MYSTERYWOOD_SLAB.getBlock());
        registerWoodenBlock(ModBlocks.MYSTERYWOOD_STAIRS.getBlock());
        registerWoodenBlock(ModBlocks.MYSTERYWOOD_FENCE.getBlock());
        registerWoodenBlock(ModBlocks.MYSTERYWOOD_FENCE_GATE.getBlock());

        registerPlant(ModBlocks.YELLOW_ORCHID.getBlock());
        registerPlant(ModBlocks.STRANGE_ROOT.getBlock());
    }

    private static void register(Block block, int encouragement, int flammability) {
        ((AccessorFireBlock) Blocks.FIRE).forbiddenArcanus_setFireInfo(block, encouragement, flammability);
    }

    private static void registerLog(Block block) {
        register(block, 5, 5);
    }

    private static void registerWoodenBlock(Block block) {
        register(block, 5, 20);
    }

    private static void registerLeaves(Block block) {
        register(block, 30, 60);
    }

    private static void registerPlant(Block block) {
        register(block, 60, 100);
    }
}
