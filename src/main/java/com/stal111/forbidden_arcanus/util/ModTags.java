package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagRegistry;
import net.minecraft.util.ResourceLocation;

public class ModTags {

    public static class Items {

        private static final TagRegistry<Item> collection = new TagRegistry<>();

        public static final ITag.INamedTag<Item> SEEDS = tag("seeds");

        private static ITag.INamedTag<Item> tag(String name) {
            return collection.func_232937_a_(name);
        }

    }
}
