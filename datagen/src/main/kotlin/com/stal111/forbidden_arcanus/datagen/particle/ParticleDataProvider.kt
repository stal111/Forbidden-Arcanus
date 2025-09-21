package com.stal111.forbidden_arcanus.datagen.particle

import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.core.init.ModParticles
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.valhelsia.dataforge.DataProviderContext
import java.util.*
import java.util.concurrent.CompletableFuture

class ParticleDataProvider(context: DataProviderContext) : DataProvider {
    private val particlePathProvider: PackOutput.PathProvider =
        context.packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "particles")

    private val builders = mutableMapOf<ResourceLocation, ParticleDefinition>()

    fun registerParticles() {
        register(
            ModParticles.SOUL.get(),
            modLoc("soul_0"),
            modLoc("soul_1"),
            modLoc("soul_2"),
            modLoc("soul_3"),
            modLoc("soul_4"),
            modLoc("soul_5"),
            modLoc("soul_6"),
            modLoc("soul_7"),
            modLoc("soul_8"),
            modLoc("soul_9"),
            modLoc("soul_10"),
            modLoc("soul_11"),
            modLoc("soul_12"),
            modLoc("soul_13"),
            modLoc("soul_14"),
            modLoc("soul_15")
        )
        register(ModParticles.AUREAL_MOTE.get(), modLoc("aureal_mote"))
        register(
            ModParticles.MAGIC_EXPLOSION.get(),
            modLoc("magic_explosion_0"),
            modLoc("magic_explosion_1"),
            modLoc("magic_explosion_2"),
            modLoc("magic_explosion_3"),
            modLoc("magic_explosion_4")
        )
        register(ModParticles.HUGE_MAGIC_EXPLOSION.get())
        register(ModParticles.MAGNETIC_GLOW.get(), modLoc("magnetic_glow"))
        register(
            ModParticles.AUREAL_DROP.get(),
            modLoc("drop/aureal_0"),
            modLoc("drop/aureal_1"),
            modLoc("drop/aureal_2"),
            modLoc("drop/aureal_3"),
            modLoc("drop/aureal_4"),
            modLoc("drop/aureal_5"),
            modLoc("drop/aureal_6")
        )
        register(
            ModParticles.SOULS_DROP.get(),
            modLoc("drop/souls_0"),
            modLoc("drop/souls_1"),
            modLoc("drop/souls_2"),
            modLoc("drop/souls_3"),
            modLoc("drop/souls_4"),
            modLoc("drop/souls_5"),
            modLoc("drop/souls_6")
        )
        register(
            ModParticles.BLOOD_DROP.get(),
            modLoc("drop/blood_0"),
            modLoc("drop/blood_1"),
            modLoc("drop/blood_2"),
            modLoc("drop/blood_3"),
            modLoc("drop/blood_4"),
            modLoc("drop/blood_5"),
            modLoc("drop/blood_6")
        )
        register(
            ModParticles.EXPERIENCE_DROP.get(),
            modLoc("drop/experience_0"),
            modLoc("drop/experience_1"),
            modLoc("drop/experience_2"),
            modLoc("drop/experience_3"),
            modLoc("drop/experience_4"),
            modLoc("drop/experience_5"),
            modLoc("drop/experience_6")
        )
        register(
            ModParticles.MAGIC_GLINT.get(),
            modLoc("magic_glint_0"),
            modLoc("magic_glint_1"),
            modLoc("magic_glint_2"),
            modLoc("magic_glint_3"),
            modLoc("magic_glint_4")
        )
        register(
            ModParticles.MAGIC_HIT.get(),
            modLoc("magic_hit_0"),
            modLoc("magic_hit_1"),
            modLoc("magic_hit_2")
        )
        register(
            ModParticles.SPELL_EXPLOSION.get(),
            modLoc("spell_explosion_0"),
            modLoc("spell_explosion_1"),
            modLoc("spell_explosion_2"),
            modLoc("spell_explosion_3"),
            modLoc("spell_explosion_4"),
            modLoc("spell_explosion_5"),
            modLoc("spell_explosion_6"),
            modLoc("spell_explosion_7")
        )
    }

    private fun register(particleType: ParticleType<*>, vararg textures: ResourceLocation) {
        val texturesList = if (textures.isEmpty()) null else mutableListOf(*textures)

        builders[BuiltInRegistries.PARTICLE_TYPE.getKey(particleType)!!] = ParticleDefinition(texturesList)
    }


    override fun run(output: CachedOutput): CompletableFuture<*> {
        registerParticles()

        val futures = builders.entries.map { (key, definition) ->
            val element = ParticleDefinition.CODEC.encodeStart(JsonOps.INSTANCE, definition).getOrThrow()
            DataProvider.saveStable(output, element, particlePathProvider.json(key))
        }

        return CompletableFuture.allOf(*futures.toTypedArray())
    }

    private fun modLoc(path: String): ResourceLocation {
        return ForbiddenArcanus.location(path)
    }

    override fun getName(): String {
        return ForbiddenArcanus.MOD_ID + " - Particles"
    }

    data class ParticleDefinition(val textures: MutableList<ResourceLocation>?) {
        companion object {
            val CODEC: Codec<ParticleDefinition> = RecordCodecBuilder.create {
                it.group(
                    ResourceLocation.CODEC.listOf().optionalFieldOf("textures").forGetter { particleDefinition ->
                        Optional.ofNullable(particleDefinition.textures)
                    }
                ).apply(it) { resourceLocations ->
                    ParticleDefinition(resourceLocations.orElse(null))
                }
            }
        }
    }
}
