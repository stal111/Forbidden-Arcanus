package com.stal111.forbidden_arcanus.datagen.research

import com.stal111.forbidden_arcanus.common.research.BuiltinConstellations
import com.stal111.forbidden_arcanus.common.research.BuiltinKnowledge
import com.stal111.forbidden_arcanus.common.research.Constellation
import com.stal111.forbidden_arcanus.common.research.Knowledge
import com.stal111.forbidden_arcanus.core.registry.FARegistries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.valhelsia.dataforge.RegistryDataProvider

object ModConstellations : RegistryDataProvider<Constellation> {

    override fun bootstrap(context: BootstrapContext<Constellation>) {
        context.constellation(
            key = BuiltinConstellations.URSA_MINOR,
            startX = 0,
            startY = 0,
            startKnowledge = BuiltinKnowledge.WELCOME,
        )
    }

    fun BootstrapContext<Constellation>.constellation(
        key: ResourceKey<Constellation>,
        startX: Int,
        startY: Int,
        startKnowledge: ResourceKey<Knowledge>
    ) = register(
        key,
        Constellation(
            key.identifier().path,
            startX,
            startY,
            lookup(FARegistries.KNOWLEDGE).getOrThrow(startKnowledge)
        )
    )
}