package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTags {

    public static class Blocks {
        public static final ITag.INamedTag<Block> CANDELABRAS = modTag("candelabras");
        public static final ITag.INamedTag<Block> STANDING_CANDELABRAS = modTag("standing_candelabras");
        public static final ITag.INamedTag<Block> WALL_CANDELABRAS = modTag("wall_candelabras");
        public static final ITag.INamedTag<Block> HANGING_CANDELABRAS = modTag("hanging_candelabras");

        private static ITag.INamedTag<Block> forgeTag(String name) {
            return BlockTags.makeWrapperTag(new ResourceLocation("forge", name).toString());
        }

        private static ITag.INamedTag<Block> modTag(String name) {
            return BlockTags.makeWrapperTag(new ResourceLocation(ForbiddenArcanus.MOD_ID, name).toString());
        }

        private static ITag.INamedTag<Block> vanillaTag(String name) {
            return BlockTags.makeWrapperTag(new ResourceLocation(name).toString());
        }
    }

    public static class Items {
        public static final ITag.INamedTag<Item> SEEDS = vanillaTag("seeds");
        public static final ITag.INamedTag<Item> BLACK_HOLE_UNAFFECTED = modTag("black_hole_unaffected");
        public static final ITag.INamedTag<Item> OBSIDIAN_SKULLS = modTag("obsidian_skulls");
        public static final ITag.INamedTag<Item> INDESTRUCTIBLE_BLACKLISTED = modTag("indestructible_blacklisted");

        private static ITag.INamedTag<Item> forgeTag(String name) {
            return ItemTags.makeWrapperTag(new ResourceLocation("forge", name).toString());
        }

        private static ITag.INamedTag<Item> modTag(String name) {
            return ItemTags.makeWrapperTag(new ResourceLocation(ForbiddenArcanus.MOD_ID, name).toString());
        }

        private static ITag.INamedTag<Item> vanillaTag(String name) {
            return ItemTags.makeWrapperTag(new ResourceLocation(name).toString());
        }
    }

    public static class EntityTypes {
        public static final ITag.INamedTag<EntityType<?>> QUANTUM_CATCHER_BLACKLISTED = modTag("quantum_catcher_blacklisted");

        private static ITag.INamedTag<EntityType<?>> modTag(String name) {
            return EntityTypeTags.getTagById(ForbiddenArcanus.MOD_ID + ":" + name);
        }
    }

    public static class Enchantments {
        public static final ITag.INamedTag<Enchantment> INDESTRUCTIBLE_BLACKLISTED = modTag("indestructible_blacklisted");

        private static ITag.INamedTag<Enchantment> modTag(String name) {
            return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ENCHANTMENTS, new ResourceLocation(ForbiddenArcanus.MOD_ID, name));
        }
    }
}
