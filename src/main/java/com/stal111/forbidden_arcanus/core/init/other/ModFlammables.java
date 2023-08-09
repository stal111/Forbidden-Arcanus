package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.mixin.AccessorFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModFlammables {

    public static void registerFlammables() {
        registerLeaves(ModBlocks.AURUM_LEAVES.get());
        registerLeaves(ModBlocks.NUGGETY_AURUM_LEAVES.get());

        registerLog(ModBlocks.AURUM_LOG.get());
        registerLog(ModBlocks.EDELWOOD_LOG.get());
        registerLog(ModBlocks.CARVED_EDELWOOD_LOG.get());
        registerLog(ModBlocks.STRIPPED_AURUM_LOG.get());
        registerLog(ModBlocks.AURUM_WOOD.get());
        registerLog(ModBlocks.STRIPPED_AURUM_WOOD.get());

        registerWoodenBlock(ModBlocks.EDELWOOD_PLANKS.get());
        registerWoodenBlock(ModBlocks.ARCANE_EDELWOOD_PLANKS.get());
        registerWoodenBlock(ModBlocks.EDELWOOD_SLAB.get());
        registerWoodenBlock(ModBlocks.EDELWOOD_STAIRS.get());
        registerWoodenBlock(ModBlocks.EDELWOOD_FENCE.get());
        registerWoodenBlock(ModBlocks.EDELWOOD_FENCE_GATE.get());

        registerWoodenBlock(ModBlocks.AURUM_PLANKS.get());
        registerWoodenBlock(ModBlocks.AURUM_SLAB.get());
        registerWoodenBlock(ModBlocks.AURUM_STAIRS.get());
        registerWoodenBlock(ModBlocks.AURUM_FENCE.get());
        registerWoodenBlock(ModBlocks.AURUM_FENCE_GATE.get());

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
