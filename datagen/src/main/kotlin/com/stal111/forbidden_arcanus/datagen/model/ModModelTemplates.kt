package com.stal111.forbidden_arcanus.datagen.model

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart
import net.minecraft.Util
import net.minecraft.client.data.models.model.ModelTemplate
import net.minecraft.client.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import java.util.*

object ModModelTemplates {
    val CUBE_ALL_EMISSIVE = create("cube_all_emissive", TextureSlot.ALL)
    val CUBE_ALL_EMISSIVE_LAYER = create("cube_all_emissive_layer", TextureSlot.ALL, ModTextureSlots.LAYER)

    val FORBIDDENOMICON = create(
        "template_forbiddenomicon",
        TextureSlot.FRONT, TextureSlot.BACK, TextureSlot.INSIDE, TextureSlot.SIDE
    )
    val DESK = create(
        "template_desk",
        TextureSlot.FRONT, TextureSlot.BACK, TextureSlot.INSIDE, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    )
    val PEDESTAL = create("template_pedestal", TextureSlot.SIDE, TextureSlot.TOP)
    val CLIBANO_CENTER = create("clibano_center", TextureSlot.TEXTURE)
    val CLIBANO_SIDE_HORIZONTAL = create("clibano_side_horizontal", TextureSlot.SIDE)
    val CLIBANO_SIDE_VERTICAL = create("clibano_side_vertical", TextureSlot.SIDE)
    val HEPHAESTUS_FORGE = create(
        "template_hephaestus_forge",
        TextureSlot.TOP,
        ModTextureSlots.TOP_LAYER,
        TextureSlot.SIDE,
        ModTextureSlots.SIDE_LAYER,
        ModTextureSlots.CLOTH_SIDE,
        TextureSlot.BOTTOM,
        ModTextureSlots.BLOCK
    )
    val OBELISK: MutableMap<ObeliskPart, ModelTemplate> = Util.make(
        EnumMap(ObeliskPart::class.java)
    ) { map ->
        map[ObeliskPart.UPPER] = create("obelisk_upper", TextureSlot.TEXTURE, TextureSlot.TOP)
        map[ObeliskPart.MIDDLE] = create("obelisk_middle", TextureSlot.TEXTURE, TextureSlot.TOP)
        map[ObeliskPart.LOWER] = create("obelisk_lower", TextureSlot.TEXTURE, TextureSlot.TOP)
    }
    val UTREM_JAR = create("template_utrem_jar", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM)
    val UTREM_JAR_ITEM = createItem("template_utrem_jar")
    val QUANTUM_CATCHER = createItem(
        "template_quantum_catcher",
        ModTextureSlots.INNER, TextureSlot.TOP, TextureSlot.SIDE
    )
    val HOLLOW_LOG = create("hollow_log", TextureSlot.INSIDE, ModTextureSlots.LOG, TextureSlot.TOP)
    val HOLLOW_LOG_FACE = create(
        "hollow_log_face",
        TextureSlot.INSIDE, ModTextureSlots.LOG, TextureSlot.TOP, TextureSlot.FRONT
    )
    val HOLLOW_LOG_FACE_AND_LEAVES = create(
        "hollow_log_face_and_leaves",
        TextureSlot.INSIDE, ModTextureSlots.LOG, TextureSlot.TOP, TextureSlot.FRONT, ModTextureSlots.LEAVES
    )

    val FLAT_HANDHELD_WAND = createMinecraftItem("handheld_rod", TextureSlot.LAYER0, TextureSlot.LAYER1)

    fun create(name: String, vararg textureSlots: TextureSlot): ModelTemplate {
        return ModelTemplate(
            Optional.of(ForbiddenArcanus.location("block/$name")),
            Optional.empty(),
            *textureSlots
        )
    }

    fun createItem(name: String, vararg textureSlots: TextureSlot): ModelTemplate {
        return ModelTemplate(
            Optional.of(ForbiddenArcanus.location("item/$name")),
            Optional.empty(),
            *textureSlots
        )
    }

    fun createMinecraftItem(name: String, vararg textureSlots: TextureSlot): ModelTemplate {
        return ModelTemplate(
            Optional.of(ResourceLocation.withDefaultNamespace("item/$name")),
            Optional.empty(),
            *textureSlots
        )
    }
}
