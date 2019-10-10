package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags {

    public static class Items {

        public static final Tag<Item> SEEDS = tag("seeds");

        private static Tag<Item> tag(String name) {
            return new ItemTags.Wrapper(new ResourceLocation("minecraft", name));
        }

    }
}
