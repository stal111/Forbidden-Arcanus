package com.stal111.forbidden_arcanus.datagen.model

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object ModLocationUtils {
    fun getBlock(block: Block, folder: String): Identifier {
        val resourceLocation = BuiltInRegistries.BLOCK.getKey(block)
        return resourceLocation.withPrefix("block/$folder/")
    }

    fun getBlock(block: Block, folder: String, suffix: String): Identifier {
        val resourceLocation = BuiltInRegistries.BLOCK.getKey(block)
        return resourceLocation.withPath { "block/$folder/$it$suffix" }
    }

    fun getBlock(folder: String, name: String): Identifier {
        return ForbiddenArcanus.identifier("block/$folder/$name")
    }

    fun getBlock(name: String): Identifier {
        return ForbiddenArcanus.identifier("block/$name")
    }

    fun getItem(folder: String, item: Holder<Item>, suffix: String): Identifier {
        return BuiltInRegistries.ITEM.getKey(item.value()).withPrefix("item/$folder/").withSuffix(suffix)
    }

    fun getItem(folder: String, item: Holder<Item>): Identifier {
        return BuiltInRegistries.ITEM.getKey(item.value()).withPrefix("item/$folder/")
    }

    fun getItem(item: Holder<Item>): Identifier {
        return BuiltInRegistries.ITEM.getKey(item.value()).withPrefix("item/")
    }

    fun getItem(folder: String, name: String): Identifier {
        return ForbiddenArcanus.identifier("item/$folder/$name")
    }

    fun getItem(name: String): Identifier {
        return ForbiddenArcanus.identifier("item/$name")
    }
}
