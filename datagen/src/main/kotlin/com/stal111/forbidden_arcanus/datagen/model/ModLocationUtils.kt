package com.stal111.forbidden_arcanus.datagen.model

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import java.util.function.UnaryOperator

object ModLocationUtils {
    fun getBlock(block: Block, folder: String): ResourceLocation {
        val resourceLocation = BuiltInRegistries.BLOCK.getKey(block)
        return resourceLocation.withPrefix("block/$folder/")
    }

    fun getBlock(block: Block, folder: String, suffix: String): ResourceLocation {
        val resourceLocation = BuiltInRegistries.BLOCK.getKey(block)
        return resourceLocation.withPath { "block/$folder/$it$suffix" }
    }

    fun getBlock(folder: String, name: String): ResourceLocation {
        return ForbiddenArcanus.location("block/$folder/$name")
    }

    fun getBlock(name: String): ResourceLocation {
        return ForbiddenArcanus.location("block/$name")
    }

    fun getItem(folder: String, item: Holder<Item>, suffix: String): ResourceLocation {
        return BuiltInRegistries.ITEM.getKey(item.value()).withPrefix("item/$folder/").withSuffix(suffix)
    }

    fun getItem(folder: String, item: Holder<Item>): ResourceLocation {
        return BuiltInRegistries.ITEM.getKey(item.value()).withPrefix("item/$folder/")
    }

    fun getItem(item: Holder<Item>): ResourceLocation {
        return BuiltInRegistries.ITEM.getKey(item.value()).withPrefix("item/")
    }

    fun getItem(folder: String, name: String): ResourceLocation {
        return ForbiddenArcanus.location("item/$folder/$name")
    }

    fun getItem(name: String): ResourceLocation {
        return ForbiddenArcanus.location("item/$name")
    }
}
