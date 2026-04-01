package com.stal111.forbidden_arcanus.datagen.item

import com.stal111.forbidden_arcanus.common.essence.EssenceType
import com.stal111.forbidden_arcanus.common.item.enhancer.BuiltInEnhancers
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectCondition
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.TimeCondition
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.ConditionalEnhancerEffect
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.EnhancerEffect
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.MultiplyRequiredEssenceEffect
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.MultiplySoulDurationEffect
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.network.chat.Component
import net.valhelsia.dataforge.RegistryDataProvider
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

object ModEnhancerDefinitions : RegistryDataProvider<EnhancerDefinition> {
    override fun bootstrap(context: BootstrapContext<EnhancerDefinition>) {
        context.register(
            BuiltInEnhancers.ARTISAN_RELIC,
            EnhancerDefinition.create(
                generateDescription("artisan_relic", EnhancerTarget.HEPHAESTUS_FORGE, EnhancerTarget.CLIBANO),
                ModItems.ARTISAN_RELIC.get(),
                effect(MultiplyRequiredEssenceEffect(EssenceType.EXPERIENCE, 0.75))
            )
        )
        context.register(
            BuiltInEnhancers.CRESCENT_MOON,
            EnhancerDefinition.create(
                generateDescription("crescent_moon", EnhancerTarget.HEPHAESTUS_FORGE),
                ModItems.CRESCENT_MOON.get(),
                effect(
                    MultiplyRequiredEssenceEffect(EssenceType.AUREAL, 0.9),
                    TimeCondition(23000, 23999)
                ),
                effect(
                    MultiplyRequiredEssenceEffect(EssenceType.AUREAL, 0.9),
                    TimeCondition(0, 12999)
                ),
                effect(
                    MultiplyRequiredEssenceEffect(EssenceType.AUREAL, 0.99),
                    TimeCondition(13000, 22999)
                )
            )
        )
        context.register(
            BuiltInEnhancers.CRIMSON_STONE,
            EnhancerDefinition.create(
                generateDescription("crimson_stone", EnhancerTarget.HEPHAESTUS_FORGE, EnhancerTarget.CLIBANO),
                ModItems.CRIMSON_STONE.get(),
                effect(MultiplyRequiredEssenceEffect(EssenceType.ECTOPLASM, 0.5)),
                effect(MultiplySoulDurationEffect(1.3))
            )
        )
        context.register(
            BuiltInEnhancers.SOUL_CRIMSON_STONE,
            EnhancerDefinition.create(
                generateDescription("soul_crimson_stone", EnhancerTarget.HEPHAESTUS_FORGE),
                ModItems.SOUL_CRIMSON_STONE.get(),
                effect(MultiplyRequiredEssenceEffect(EssenceType.AUREAL, 0.0)),
                effect(MultiplyRequiredEssenceEffect(EssenceType.ECTOPLASM, 0.0)),
                effect(MultiplyRequiredEssenceEffect(EssenceType.BLOOD, 0.0)),
                effect(MultiplyRequiredEssenceEffect(EssenceType.EXPERIENCE, 0.0))
            )
        )
        context.register(
            BuiltInEnhancers.ELEMENTARIUM,
            EnhancerDefinition.create(
                generateDescription("elementarium", EnhancerTarget.HEPHAESTUS_FORGE),
                ModItems.ELEMENTARIUM.get()
            )
        )
        context.register(
            BuiltInEnhancers.DIVINE_PACT,
            EnhancerDefinition.create(
                generateDescription("divine_pact", EnhancerTarget.HEPHAESTUS_FORGE),
                ModItems.DIVINE_PACT.get()
            )
        )
        context.register(
            BuiltInEnhancers.MALEDICTUS_PACT,
            EnhancerDefinition.create(
                generateDescription("maledictus_pact", EnhancerTarget.HEPHAESTUS_FORGE),
                ModItems.MALEDICTUS_PACT.get()
            )
        )
    }

    private fun generateDescription(
        key: String,
        vararg targets: EnhancerTarget
    ): MutableMap<EnhancerTarget, Component> {
        return Arrays.stream(targets).collect(
            Collectors.toMap(Function.identity()) { Component.translatable("item.forbidden_arcanus.enhancer." + key + "." + it.serializedName) }
        )
    }

    private fun <T : EnhancerEffect> effect(effect: T): ConditionalEnhancerEffect<T> {
        return ConditionalEnhancerEffect.of<T>(effect)
    }

    private fun <T : EnhancerEffect> effect(effect: T, condition: EffectCondition): ConditionalEnhancerEffect<T> {
        return ConditionalEnhancerEffect.of<T>(effect, listOf(condition))
    }
}
