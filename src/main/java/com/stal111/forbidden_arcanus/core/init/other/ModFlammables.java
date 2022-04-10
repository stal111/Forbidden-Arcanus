package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.mixin.AccessorFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModFlammables {

    public static void registerFlammables() {
        registerLeaves(ModBlocks.CHERRYWOOD_LEAVES.get());
        registerLeaves(ModBlocks.MYSTERYWOOD_LEAVES.get());
        registerLeaves(ModBlocks.NUGGETY_MYSTERYWOOD_LEAVES.get());

        registerLog(ModBlocks.CHERRYWOOD_LOG.get());
        registerLog(ModBlocks.THIN_CHERRYWOOD_LOG.get());
        registerLog(ModBlocks.MYSTERYWOOD_LOG.get());
        registerLog(ModBlocks.EDELWOOD_LOG.get());
        registerLog(ModBlocks.CARVED_EDELWOOD_LOG.get());
        registerLog(ModBlocks.STRIPPED_CHERRYWOOD_LOG.get());
        registerLog(ModBlocks.STRIPPED_MYSTERYWOOD_LOG.get());
        registerLog(ModBlocks.CHERRYWOOD.get());
        registerLog(ModBlocks.MYSTERYWOOD.get());
        registerLog(ModBlocks.STRIPPED_CHERRYWOOD.get());
        registerLog(ModBlocks.STRIPPED_MYSTERYWOOD.get());

        registerWoodenBlock(ModBlocks.EDELWOOD_PLANKS.get());
        registerWoodenBlock(ModBlocks.ARCANE_EDELWOOD_PLANKS.get());
        registerWoodenBlock(ModBlocks.EDELWOOD_SLAB.get());
        registerWoodenBlock(ModBlocks.EDELWOOD_STAIRS.get());
        registerWoodenBlock(ModBlocks.EDELWOOD_FENCE.get());
        registerWoodenBlock(ModBlocks.EDELWOOD_FENCE_GATE.get());

        registerWoodenBlock(ModBlocks.CHERRYWOOD_PLANKS.get());
        registerWoodenBlock(ModBlocks.CHERRYWOOD_SLAB.get());
        registerWoodenBlock(ModBlocks.CHERRYWOOD_STAIRS.get());
        registerWoodenBlock(ModBlocks.CHERRYWOOD_FENCE.get());
        registerWoodenBlock(ModBlocks.CHERRYWOOD_FENCE_GATE.get());

        registerWoodenBlock(ModBlocks.MYSTERYWOOD_PLANKS.get());
        registerWoodenBlock(ModBlocks.MYSTERYWOOD_SLAB.get());
        registerWoodenBlock(ModBlocks.MYSTERYWOOD_STAIRS.get());
        registerWoodenBlock(ModBlocks.MYSTERYWOOD_FENCE.get());
        registerWoodenBlock(ModBlocks.MYSTERYWOOD_FENCE_GATE.get());

        registerPlant(ModBlocks.YELLOW_ORCHID.get());
        registerPlant(ModBlocks.STRANGE_ROOT.get());
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
