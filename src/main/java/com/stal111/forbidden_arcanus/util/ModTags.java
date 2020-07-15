package com.stal111.forbidden_arcanus.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class ModTags {

    public static class Items {
        public static final ITag.INamedTag<Item> SEEDS = ItemTags.makeWrapperTag("seeds");
    }

    public static class Blocks {
        public static final ITag.INamedTag<Block> CANDELABRAS = BlockTags.makeWrapperTag("candelabras");
        public static final ITag.INamedTag<Block> STANDING_CANDELABRAS = BlockTags.makeWrapperTag("standing_candelabras");
        public static final ITag.INamedTag<Block> WALL_CANDELABRAS = BlockTags.makeWrapperTag("wall_candelabras");
        public static final ITag.INamedTag<Block> HANGING_CANDELABRAS = BlockTags.makeWrapperTag("hanging_candelabras");
    }
}
