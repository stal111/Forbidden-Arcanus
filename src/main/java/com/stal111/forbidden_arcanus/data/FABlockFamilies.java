package com.stal111.forbidden_arcanus.data;

import com.google.common.collect.Maps;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class FABlockFamilies {

    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    public static final BlockFamily SOULLESS_SANDSTONE = familyBuilder(ModBlocks.SOULLESS_SANDSTONE.get())
            .wall(ModBlocks.SOULLESS_SANDSTONE_WALL.get())
            .stairs(ModBlocks.SOULLESS_SANDSTONE_STAIRS.get())
            .slab(ModBlocks.SOULLESS_SANDSTONE_SLAB.get())
            .cut(ModBlocks.CUT_SOULLESS_SANDSTONE.get())
            .polished(ModBlocks.POLISHED_SOULLESS_SANDSTONE.get())
            .getFamily();

    public static final BlockFamily CUT_SOULLESS_SANDSTONE = familyBuilder(ModBlocks.CUT_SOULLESS_SANDSTONE.get())
            .slab(ModBlocks.CUT_SOULLESS_SANDSTONE_SLAB.get())
            .getFamily();

    public static final BlockFamily POLISHED_SOULLESS_SANDSTONE = familyBuilder(ModBlocks.POLISHED_SOULLESS_SANDSTONE.get())
            .stairs(ModBlocks.POLISHED_SOULLESS_SANDSTONE_STAIRS.get())
            .slab(ModBlocks.POLISHED_SOULLESS_SANDSTONE_SLAB.get())
            .getFamily();

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily family = MAP.put(baseBlock, builder.getFamily());

        if (family != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        }

        return builder;
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}
