package com.stal111.forbidden_arcanus.init.other;

import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.mixin.AccessorFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModFlammables {

    public static void registerFlammables() {
        registerLeaves(NewModBlocks.CHERRYWOOD_LEAVES.get());
        registerLeaves(NewModBlocks.MYSTERYWOOD_LEAVES.get());
        registerLeaves(NewModBlocks.NUGGETY_MYSTERYWOOD_LEAVES.get());

        registerLog(NewModBlocks.CHERRYWOOD_LOG.get());
        registerLog(NewModBlocks.MYSTERYWOOD_LOG.get());
        registerLog(NewModBlocks.EDELWOOD_LOG.get());
        registerLog(NewModBlocks.CARVED_EDELWOOD_LOG.get());
        registerLog(NewModBlocks.STRIPPED_CHERRYWOOD_LOG.get());
        registerLog(NewModBlocks.STRIPPED_MYSTERYWOOD_LOG.get());
        registerLog(NewModBlocks.CHERRYWOOD.get());
        registerLog(NewModBlocks.MYSTERYWOOD.get());
        registerLog(NewModBlocks.STRIPPED_CHERRYWOOD.get());
        registerLog(NewModBlocks.STRIPPED_MYSTERYWOOD.get());

        registerWoodenBlock(NewModBlocks.EDELWOOD_PLANKS.get());
        registerWoodenBlock(NewModBlocks.ARCANE_EDELWOOD_PLANKS.get());
        registerWoodenBlock(NewModBlocks.EDELWOOD_SLAB.get());
        registerWoodenBlock(NewModBlocks.EDELWOOD_STAIRS.get());
        registerWoodenBlock(NewModBlocks.EDELWOOD_FENCE.get());
        registerWoodenBlock(NewModBlocks.EDELWOOD_FENCE_GATE.get());

        registerWoodenBlock(NewModBlocks.CHERRYWOOD_PLANKS.get());
        registerWoodenBlock(NewModBlocks.CHERRYWOOD_SLAB.get());
        registerWoodenBlock(NewModBlocks.CHERRYWOOD_STAIRS.get());
        registerWoodenBlock(NewModBlocks.CHERRYWOOD_FENCE.get());
        registerWoodenBlock(NewModBlocks.CHERRYWOOD_FENCE_GATE.get());

        registerWoodenBlock(NewModBlocks.MYSTERYWOOD_PLANKS.get());
        registerWoodenBlock(NewModBlocks.MYSTERYWOOD_SLAB.get());
        registerWoodenBlock(NewModBlocks.MYSTERYWOOD_STAIRS.get());
        registerWoodenBlock(NewModBlocks.MYSTERYWOOD_FENCE.get());
        registerWoodenBlock(NewModBlocks.MYSTERYWOOD_FENCE_GATE.get());

        registerPlant(NewModBlocks.YELLOW_ORCHID.get());
        registerPlant(NewModBlocks.STRANGE_ROOT.get());
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
