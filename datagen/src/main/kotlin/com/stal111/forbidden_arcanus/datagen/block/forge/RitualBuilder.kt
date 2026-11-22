package com.stal111.forbidden_arcanus.datagen.block.forge

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeTier
import com.stal111.forbidden_arcanus.common.block.entity.forge.TierPredicate
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualInput
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualRequirements
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.TransmuteInputResult
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult
import com.stal111.forbidden_arcanus.common.block.entity.magiccircle.BuiltInMagicCircles
import com.stal111.forbidden_arcanus.common.block.entity.magiccircle.MagicCircleType
import com.stal111.forbidden_arcanus.common.essence.EssenceSet
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition
import com.stal111.forbidden_arcanus.core.registry.FARegistries
import net.minecraft.core.Holder
import net.minecraft.core.HolderGetter
import net.minecraft.core.HolderSet
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike

@DslMarker
annotation class RitualDsl

fun BootstrapContext<Ritual>.rituals(block: RitualRegistryScope.() -> Unit) {
    RitualRegistryScope(this).apply(block)
}

@RitualDsl
class RitualRegistryScope(private val context: BootstrapContext<Ritual>) {
    private val magicCircleLookup = context.lookup(FARegistries.MAGIC_CIRCLE)
    private val enhancerLookup = context.lookup(FARegistries.ENHANCER_DEFINITION)

    fun createItem(
        key: ResourceKey<Ritual>,
        result: ItemLike,
        mainIngredient: ItemLike,
        block: RitualBuilder.() -> Unit,
    ) = ritual(key, CreateItemResult(ItemStackTemplate(result.asItem())), mainIngredient, block)

    fun transmute(
        key: ResourceKey<Ritual>,
        result: ItemLike,
        mainIngredient: ItemLike,
        block: RitualBuilder.() -> Unit,
    ) = ritual(key, TransmuteInputResult(ItemStackTemplate(result.asItem())), mainIngredient, block)

    fun upgradeTier(
        key: ResourceKey<Ritual>,
        tier: HephaestusForgeTier,
        mainIngredient: ItemLike,
        block: RitualBuilder.() -> Unit,
    ) = ritual(key, UpgradeTierResult(tier), mainIngredient, block)

    fun ritual(
        key: ResourceKey<Ritual>,
        result: RitualResult,
        mainIngredient: ItemLike,
        block: RitualBuilder.() -> Unit,
    ) = ritual(key, result, Ingredient.of(mainIngredient), block)

    fun ritual(
        key: ResourceKey<Ritual>,
        result: RitualResult,
        mainIngredient: Ingredient,
        block: RitualBuilder.() -> Unit,
    ) {
        context.register(key, RitualBuilder(mainIngredient, result, magicCircleLookup, enhancerLookup).apply(block).build())
    }
}

@RitualDsl
class RitualBuilder(
    private val mainIngredient: Ingredient,
    private val result: RitualResult,
    private val magicCircleLookup: HolderGetter<MagicCircleType>,
    private val enhancerLookup: HolderGetter<EnhancerDefinition>,
) {

    private val inputs = mutableListOf<RitualInput>()
    private var essences: EssenceSet = EssenceSet.EMPTY
    var tier: TierPredicate = TierPredicate.ANY
    private val enhancers = mutableListOf<Holder<EnhancerDefinition>>()
    var magicCircle: ResourceKey<MagicCircleType> = BuiltInMagicCircles.CREATE_ITEM
    var duration: Int = Ritual.DEFAULT_DURATION

    fun input(ingredient: Ingredient, amount: Int = 1) {
        require(amount > 0) { "Input amount must be positive, got $amount" }
        inputs += RitualInput(ingredient, amount)
    }

    fun input(item: ItemLike, amount: Int = 1) = input(Ingredient.of(item), amount)

    fun essences(aureal: Int = 0, ectoplasm: Int = 0, blood: Int = 0, experience: Int = 0) {
        essences = EssenceSet.of(aureal, ectoplasm, blood, experience)
    }

    fun essences(essences: EssenceSet) {
        this.essences = essences
    }

    fun minTier(tier: HephaestusForgeTier) {
        this.tier = TierPredicate.min(tier)
    }

    fun exactTier(tier: HephaestusForgeTier) {
        this.tier = TierPredicate.exact(tier)
    }

    fun enhancer(enhancer: ResourceKey<EnhancerDefinition>) {
        enhancers += enhancerLookup.getOrThrow(enhancer)
    }

    fun enhancer(enhancer: Holder<EnhancerDefinition>) {
        enhancers += enhancer
    }

    fun build(): Ritual {
        require(inputs.size in 1..8) { "Ritual must have between 1 and 8 inputs, got ${inputs.size}" }
        require(duration > 0) { "Ritual duration must be positive, got $duration" }

        val requirements = RitualRequirements(
            essences,
            tier,
            if (enhancers.isEmpty()) HolderSet.empty() else HolderSet.direct(enhancers),
        )

        return Ritual(
            inputs.toList(),
            mainIngredient,
            result,
            requirements,
            magicCircleLookup.getOrThrow(magicCircle),
            duration,
        )
    }
}
