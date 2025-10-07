package com.stal111.forbidden_arcanus.datagen.atlas

import com.stal111.forbidden_arcanus.client.renderer.block.BlackHoleRenderer
import com.stal111.forbidden_arcanus.client.renderer.block.QuantumInjectorRenderer
import net.minecraft.client.data.AtlasProvider
import net.minecraft.client.renderer.texture.atlas.SpriteSource
import net.minecraft.client.renderer.texture.atlas.SpriteSources
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile
import net.minecraft.data.AtlasIds
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.valhelsia.dataforge.DataProviderContext
import java.util.concurrent.CompletableFuture

class ModAtlasProvider(val context: DataProviderContext) : AtlasProvider(context.packOutput) {
    override fun run(output: CachedOutput): CompletableFuture<*> {
        return CompletableFuture.allOf(
            storeAtlas(output, AtlasIds.BLOCKS, blocksList())
        )
    }

    private fun blocksList(): List<SpriteSource> = listOf(
        SingleFile(QuantumInjectorRenderer.TEXTURE_MATERIAL.texture()),
        SingleFile(QuantumInjectorRenderer.LAYER_MATERIAL.texture()),
        DirectoryLister(BlackHoleRenderer.MAPPER.prefix, BlackHoleRenderer.MAPPER.prefix + "/"),
    )

    private fun storeAtlas(
        output: CachedOutput,
        atlasId: ResourceLocation,
        sources: List<SpriteSource>
    ): CompletableFuture<*> {
        return DataProvider.saveStable(
            output,
            SpriteSources.FILE_CODEC,
            sources,
            this.context.packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "atlases").json(atlasId)
        )
    }
}
