package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class ModTags {

    public static class Items {
        public static final ITag.INamedTag<Item> SEEDS = getVanillaTag("seeds");
        public static final ITag.INamedTag<Item> BLACK_HOLE_UNAFFECTED = getTag("black_hole_unaffected");

        private static ITag.INamedTag<Item> getTag(String name) {
            return ItemTags.makeWrapperTag(Main.MOD_ID + ":" + name);
        }

        private static ITag.INamedTag<Item> getVanillaTag(String name) {
            return ItemTags.makeWrapperTag(name);
        }
    }

    public static class Blocks {
        public static final ITag.INamedTag<Block> CANDELABRAS = getTag("candelabras");
        public static final ITag.INamedTag<Block> STANDING_CANDELABRAS = getTag("standing_candelabras");
        public static final ITag.INamedTag<Block> WALL_CANDELABRAS = getTag("wall_candelabras");
        public static final ITag.INamedTag<Block> HANGING_CANDELABRAS = getTag("hanging_candelabras");

        private static ITag.INamedTag<Block> getTag(String name) {
            return BlockTags.makeWrapperTag(Main.MOD_ID + ":" + name);
        }
    }

    public static class EntityTypes {
        public static final ITag.INamedTag<EntityType<?>> QUANTUM_CATCHER_BLACKLISTED = getTag("quantum_catcher_blacklisted");

        private static ITag.INamedTag<EntityType<?>> getTag(String name) {
            return EntityTypeTags.getTagById(Main.MOD_ID + ":" + name);
        }
    }
}
