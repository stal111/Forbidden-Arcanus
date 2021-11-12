package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTags {

    public static class Blocks {
        public static final Tag.Named<Block> CANDELABRAS = modTag("candelabras");
        public static final Tag.Named<Block> STANDING_CANDELABRAS = modTag("standing_candelabras");
        public static final Tag.Named<Block> WALL_CANDELABRAS = modTag("wall_candelabras");
        public static final Tag.Named<Block> HANGING_CANDELABRAS = modTag("hanging_candelabras");
        public static final Tag.Named<Block> FUNGYSS_STEMS = modTag("fungyss_stems");
        public static final Tag.Named<Block> CHERRYWOOD_LOGS = modTag("cherrywood_logs");
        public static final Tag.Named<Block> MYSTERYWOOD_LOGS = modTag("mysterywood_logs");
        public static final Tag.Named<Block> BLACKSMITH_GAVEL_UNAFFECTED = modTag("blacksmith_gavel_unaffected");
        public static final Tag.Named<Block> MAGICAL_FARMLAND_BLACKLISTED = modTag("magical_farmland_blacklisted");

        private static Tag.Named<Block> forgeTag(String name) {
            return BlockTags.bind(new ResourceLocation("forge", name).toString());
        }

        private static Tag.Named<Block> modTag(String name) {
            return BlockTags.bind(new ResourceLocation(ForbiddenArcanus.MOD_ID, name).toString());
        }

        private static Tag.Named<Block> vanillaTag(String name) {
            return BlockTags.bind(new ResourceLocation(name).toString());
        }
    }

    public static class Items {
        public static final Tag.Named<Item> SEEDS = vanillaTag("seeds");
        public static final Tag.Named<Item> BLACK_HOLE_UNAFFECTED = modTag("black_hole_unaffected");
        public static final Tag.Named<Item> OBSIDIAN_SKULLS = modTag("obsidian_skulls");
        public static final Tag.Named<Item> INDESTRUCTIBLE_BLACKLISTED = modTag("indestructible_blacklisted");
        public static final Tag.Named<Item> FUNGYSS_STEMS = modTag("fungyss_stems");
        public static final Tag.Named<Item> BLACKSMITH_GAVEL = modTag("blacksmith_gavel");
        public static final Tag.Named<Item> ARCANE_GOLD_INGOTS = forgeTag("ingots/arcane_gold");
        public static final Tag.Named<Item> OBSIDIAN_INGOTS = forgeTag("ingots/obsidian");
        public static final Tag.Named<Item> MAGICAL_FARMLAND_BLACKLISTED = modTag("magical_farmland_blacklisted");

        private static Tag.Named<Item> forgeTag(String name) {
            return ItemTags.bind(new ResourceLocation("forge", name).toString());
        }

        private static Tag.Named<Item> modTag(String name) {
            return ItemTags.bind(new ResourceLocation(ForbiddenArcanus.MOD_ID, name).toString());
        }

        private static Tag.Named<Item> vanillaTag(String name) {
            return ItemTags.bind(new ResourceLocation(name).toString());
        }
    }

    public static class EntityTypes {
        public static final Tag.Named<EntityType<?>> QUANTUM_CATCHER_BLACKLISTED = modTag("quantum_catcher_blacklisted");

        private static Tag.Named<EntityType<?>> modTag(String name) {
            return EntityTypeTags.bind(ForbiddenArcanus.MOD_ID + ":" + name);
        }
    }

    public static class Enchantments {
        public static final Tag.Named<Enchantment> INDESTRUCTIBLE_BLACKLISTED = modTag("indestructible_blacklisted");

        private static Tag.Named<Enchantment> modTag(String name) {
            return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ENCHANTMENTS, new ResourceLocation(ForbiddenArcanus.MOD_ID, name));
        }
    }
}
