package com.stal111.forbidden_arcanus.datagen.model

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoCenterType
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoSideType
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import net.minecraft.client.data.models.model.TextureMapping
import net.minecraft.client.data.models.model.TextureSlot
import net.minecraft.client.resources.model.sprite.Material
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

/**
 * @author stal111
 * @since 28.10.2023
 */
object ModTextureMapping {
    private const val FORBIDDENOMICON = "forbiddenomicon"
    private const val DESK = "desk"
    private const val PEDESTAL = "pedestal"
    private const val CLIBANO = "clibano"
    private const val HEPHAESTUS_FORGE = "hephaestus_forge"
    private const val OBELISK = "obelisk"
    private const val UTREM_JAR = "utrem_jar"
    private const val QUANTUM_CATCHER = "quantum_catcher"
    private const val EDELWOOD_LOG = "edelwood_log"

    private val FORGE_BLOCK_TEXTURES = mapOf(
        1 to Identifier.withDefaultNamespace("block/smithing_table_bottom"),
        2 to ForbiddenArcanus.identifier("block/edelwood_planks"),
        3 to ForbiddenArcanus.identifier("block/chiseled_polished_darkstone"),
        4 to ForbiddenArcanus.identifier("block/chiseled_polished_darkstone"),
        5 to ForbiddenArcanus.identifier("block/stellarite_block")
    )

    fun emissiveCube(block: Block): TextureMapping = TextureMapping()
        .put(TextureSlot.ALL, getBlockTexture(block))

    fun emissiveLayerCube(block: Block, folder: String): TextureMapping = TextureMapping()
        .put(TextureSlot.ALL, getBlockTexture(block, folder))
        .put(ModTextureSlots.LAYER, getBlockTexture(block, folder, "_layer"))

    fun forbiddenomicon(block: Block): TextureMapping = TextureMapping()
        .put(TextureSlot.FRONT, getBlockTexture(block, FORBIDDENOMICON, "_front"))
        .put(TextureSlot.BACK, getBlockTexture(block, FORBIDDENOMICON, "_back"))
        .put(TextureSlot.INSIDE, getBlockTexture(block, FORBIDDENOMICON, "_inside"))
        .put(TextureSlot.SIDE, getBlockTexture(block, FORBIDDENOMICON, "_side"))

    fun desk(research: Boolean): TextureMapping {
        val desk: Block = ModBlocks.DESK.get()
        val researchDesk: Block = ModBlocks.RESEARCH_DESK.get()

        return TextureMapping().put(TextureSlot.FRONT, getBlockTexture(if (research) researchDesk else desk, DESK, "_front"))
            .put(TextureSlot.BACK, getBlockTexture(if (research) researchDesk else desk, DESK, "_back"))
            .put(TextureSlot.INSIDE, getBlockTexture(desk, DESK, "_inside"))
            .put(TextureSlot.SIDE, getBlockTexture(if (research) researchDesk else desk, DESK, "_side"))
            .put(TextureSlot.TOP, getBlockTexture(if (research) researchDesk else desk, DESK, "_top"))
            .put(TextureSlot.BOTTOM, getBlockTexture(desk, DESK, "_bottom"))
    }

    fun pedestal(block: Block): TextureMapping = TextureMapping()
        .put(TextureSlot.SIDE, getBlockTexture(block, PEDESTAL))
        .put(TextureSlot.TOP, getBlockTexture(block, PEDESTAL, "_top"))

    fun clibanoCore(): TextureMapping = TextureMapping()
        .put(TextureSlot.SIDE, getBlockTexture(CLIBANO, "clibano_center_side"))
        .put(TextureSlot.FRONT, getBlockTexture(CLIBANO, "clibano_center_front_off"))
        .put(TextureSlot.TOP, getBlockTexture(CLIBANO, "clibano_center_top"))

    fun clibanoCenter(type: ClibanoCenterType): TextureMapping = TextureMapping()
        .put(
            TextureSlot.TEXTURE, getBlockTexture(
                CLIBANO,
                if (type.fireType != null) type.fireType!!.serializedName + "/clibano_center_front" else "clibano_center_" + type.serializedName
            )
        )

    fun clibanoSide(type: ClibanoSideType): TextureMapping = TextureMapping()
        .put(
            TextureSlot.SIDE, getBlockTexture(
                CLIBANO,
                if (type == ClibanoSideType.OFF) "clibano_side_off" else type.serializedName + "/clibano_side"
            )
        )

    fun hephaestusForge(tier: Int): TextureMapping {
        val folder = "$HEPHAESTUS_FORGE/tier_$tier"
        return TextureMapping().put(TextureSlot.TOP, getBlockTexture(folder, "top"))
            .put(ModTextureSlots.TOP_LAYER, getBlockTexture(folder, "top_layer"))
            .put(TextureSlot.SIDE, getBlockTexture(folder, "side"))
            .put(ModTextureSlots.SIDE_LAYER, getBlockTexture(folder, "side_layer"))
            .put(ModTextureSlots.CLOTH_SIDE, getBlockTexture(folder, "cloth_side"))
            .put(TextureSlot.BOTTOM, getBlockTexture(folder, "bottom"))
            .put(ModTextureSlots.BLOCK, Material(FORGE_BLOCK_TEXTURES[tier]!!))
    }

    fun obelisk(block: Block, part: ObeliskPart): TextureMapping = TextureMapping()
        .put(TextureSlot.TOP, getBlockTexture(block, OBELISK, "_top"))
        .put(TextureSlot.TEXTURE, getBlockTexture(block, OBELISK, "_" + part.serializedName))

    fun utremJar(block: Block): TextureMapping = TextureMapping()
        .put(TextureSlot.SIDE, getBlockTexture(block, UTREM_JAR, "_side"))
        .put(TextureSlot.TOP, getBlockTexture(block, UTREM_JAR, "_top"))
        .put(TextureSlot.BOTTOM, getBlockTexture(block, UTREM_JAR, "_bottom"))

    fun quantumCatcher(folder: String): TextureMapping = TextureMapping()
        .put(TextureSlot.SIDE, getTexture(QUANTUM_CATCHER + folder, "quantum_catcher_side"))
        .put(TextureSlot.TOP, getTexture(QUANTUM_CATCHER + folder, "quantum_catcher_top"))
        .put(ModTextureSlots.INNER, getTexture(QUANTUM_CATCHER, "quantum_catcher_inner"))

    fun edelwoodLog(): TextureMapping {
        val block: Block = ModBlocks.EDELWOOD_LOG.get()

        return TextureMapping()
            .put(ModTextureSlots.LOG, getBlockTexture(block, EDELWOOD_LOG))
            .put(TextureSlot.TOP, getBlockTexture(block, EDELWOOD_LOG, "_top"))
            .put(TextureSlot.INSIDE, getBlockTexture(block, EDELWOOD_LOG, "_inside"))
    }

    fun edelwoodLogWithFace(leaves: Boolean): TextureMapping {
        val block: Block = ModBlocks.EDELWOOD_LOG.get()

        val mapping = TextureMapping()
            .put(ModTextureSlots.LOG, getBlockTexture(block, EDELWOOD_LOG))
            .put(TextureSlot.TOP, getBlockTexture(block, EDELWOOD_LOG, "_top"))
            .put(TextureSlot.INSIDE, getBlockTexture(block, EDELWOOD_LOG, "_inside"))
            .put(TextureSlot.FRONT, getBlockTexture(block, EDELWOOD_LOG, "_face"))

        if (leaves) {
            mapping.put(ModTextureSlots.LEAVES, getBlockTexture(block, EDELWOOD_LOG, "_leaves"))
        }

        return mapping
    }

    fun getBlockTexture(block: Block): Material {
        val resourceLocation = BuiltInRegistries.BLOCK.getKey(block)
        return Material(resourceLocation.withPrefix("block/"))
    }

    fun getBlockTexture(block: Block, folder: String): Material {
        return Material(ModLocationUtils.getBlock(block, folder))
    }

    fun getBlockTexture(block: Block, folder: String, suffix: String): Material {
        return Material(ModLocationUtils.getBlock(block, folder, suffix))
    }

    fun getBlockTexture(folder: String, texture: String): Material {
        return Material(ForbiddenArcanus.identifier("block/$folder/$texture"))
    }

    fun geItemTexture(item: Holder<Item>, folder: String, suffix: String): Material {
        return Material(ModLocationUtils.getItem(folder, item, suffix))
    }

    fun getTexture(folder: String, name: String): Material {
        return Material(ModLocationUtils.getItem(folder, name))
    }
}
