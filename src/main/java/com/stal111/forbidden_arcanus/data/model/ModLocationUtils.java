package com.stal111.forbidden_arcanus.data.model;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

/**
 * @author stal111
 * @since 10.09.2023
 */
public class ModLocationUtils {

    public static ResourceLocation getBlock(Block block, String folder) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPrefix("block/" + folder + "/");
    }

    public static ResourceLocation getBlock(Block block, String folder, String suffix) {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(block);
        return resourceLocation.withPath(s -> "block/" + folder + "/" + s + suffix);
    }

    public static ResourceLocation getBlock(String folder, String name) {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/" + folder + "/" + name);
    }

    public static ResourceLocation getBlock(String name) {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/" + name);
    }

    public static ResourceLocation getItem(String folder, RegistryEntry<Item> item, String suffix) {
        return BuiltInRegistries.ITEM.getKey(item.get()).withPrefix("item/" + folder + "/").withSuffix(suffix);
    }

    public static ResourceLocation getItem(String folder, RegistryEntry<Item> item) {
        return BuiltInRegistries.ITEM.getKey(item.get()).withPrefix("item/" + folder + "/");
    }

    public static ResourceLocation getItem(RegistryEntry<Item> item) {
        return BuiltInRegistries.ITEM.getKey(item.get()).withPrefix("item/");
    }
}