package com.stal111.forbidden_arcanus.datagen.block.forge

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType
import com.stal111.forbidden_arcanus.common.block.entity.forge.magiccircle.BuiltInMagicCircles
import net.minecraft.data.worldgen.BootstrapContext
import net.valhelsia.dataforge.RegistryDataProvider

object ModMagicCircles : RegistryDataProvider<MagicCircleType> {

    //INNER TEXTURES
    private val UNION_TEXTURE = ForbiddenArcanus.location("textures/effect/magic_circle/inner/union.png")
    private val ORIGIN_TEXTURE = ForbiddenArcanus.location("textures/effect/magic_circle/inner/origin.png")

    //OUTER TEXTURES
    private val PURE_TEXTURE = ForbiddenArcanus.location("textures/effect/magic_circle/outer/pure.png")
    private val PACTUM_TEXTURE = ForbiddenArcanus.location("textures/effect/magic_circle/outer/pactum.png")

    override fun bootstrap(context: BootstrapContext<MagicCircleType>) {
        context.register(BuiltInMagicCircles.CREATE_ITEM, MagicCircleType(UNION_TEXTURE, PURE_TEXTURE))
        context.register(BuiltInMagicCircles.UPGRADE_TIER, MagicCircleType(ORIGIN_TEXTURE, PURE_TEXTURE))
        context.register(BuiltInMagicCircles.UPGRADE_FINAL_TIER, MagicCircleType(ORIGIN_TEXTURE, PACTUM_TEXTURE))
    }
}
