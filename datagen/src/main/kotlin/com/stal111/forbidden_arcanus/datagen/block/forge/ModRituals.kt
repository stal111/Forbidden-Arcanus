package com.stal111.forbidden_arcanus.datagen.block.forge

import com.stal111.forbidden_arcanus.common.block.entity.forge.TierPredicate
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType
import com.stal111.forbidden_arcanus.common.essence.EssenceSet
import com.stal111.forbidden_arcanus.common.block.entity.forge.magiccircle.BuiltInMagicCircles
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.BuiltInRituals
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualRequirements
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.TransmuteInputResult
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult
import com.stal111.forbidden_arcanus.common.item.enhancer.BuiltInEnhancers
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.core.init.ModItems
import com.stal111.forbidden_arcanus.core.registry.FARegistries
import com.stal111.forbidden_arcanus.data.hephaestus_forge.rituals.RitualBuilder
import net.minecraft.core.Holder
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Blocks
import net.valhelsia.dataforge.RegistryDataProvider
import java.util.function.UnaryOperator

object ModRituals : RegistryDataProvider<Ritual> {
    private var context: BootstrapContext<Ritual>? = null
    private var magicCircleLookup: HolderGetter<MagicCircleType>? = null

    override fun bootstrap(context: BootstrapContext<Ritual>) {
        this.context = context
        this.magicCircleLookup = context.lookup(FARegistries.MAGIC_CIRCLE)
        val enhancerLookup = context.lookup(FARegistries.ENHANCER_DEFINITION)

        val elementarium: Holder<EnhancerDefinition?> = enhancerLookup.getOrThrow(BuiltInEnhancers.ELEMENTARIUM)
        val divinePact: Holder<EnhancerDefinition?> = enhancerLookup.getOrThrow(BuiltInEnhancers.DIVINE_PACT)

        // Requirements
        val eternalStella = requirements(EssenceSet.of(82, 1, 1000, 0)) {
            it.tier(TierPredicate.min(3))
        }
        val terrastompPrism = requirements(EssenceSet.of(300, 9, 1500, 0)) {
            it.tier(TierPredicate.min(2)).enhancer(elementarium)
        }
        val seaPrism = requirements(EssenceSet.of(1000, 8, 2000, 0)) {
            it.tier(TierPredicate.min(3)).enhancer(elementarium)
        }
        val whirlwindPrism = requirements(EssenceSet.of(1000, 3, 2250, 0)) {
            it.tier(TierPredicate.min(3)).enhancer(elementarium)
        }
        val smelterPrism = requirements(EssenceSet.of(200, 4, 1250, 0)) {
            it.enhancer(elementarium)
        }
        val ferrogneticMixture = requirements(EssenceSet.of(100, 2, 1250, 0)) {
            it
        }
        val quantumCatcher = requirements(EssenceSet.of(200, 5, 1200, 155)) {
            it
        }
        val bossCatcher = requirements(EssenceSet.of(500, 10, 7500, 1200)) {
            it.tier(TierPredicate.min(4))
        }
        val quantumInjector = requirements(EssenceSet.of(5000, 50, 3000, 1060)) {
            it.tier(TierPredicate.min(3))
        }
        val soulBindingCrystal = requirements(EssenceSet.of(1500, 20, 375, 220)) {
            it.tier(TierPredicate.min(4)).enhancer(divinePact)
        }

        val dracoArcanusHelmet = requirements(EssenceSet.of(820, 6, 475, 420)) {
            it.tier(TierPredicate.min(2))
        }
        val dracoArcanusChestplate = requirements(EssenceSet.of(1300, 9, 625, 760)) {
            it.tier(TierPredicate.min(2))
        }
        val dracoArcanusLeggings = requirements(EssenceSet.of(1000, 7, 565, 540)) {
            it.tier(TierPredicate.min(2))
        }
        val dracoArcanusBoots = requirements(EssenceSet.of(700, 5, 405, 370)) {
            it.tier(TierPredicate.min(2))
        }

        val tyrHelmet = requirements(EssenceSet.of(4820, 40, 5930, 2450)) {
            it.tier(TierPredicate.min(4))
        }
        val tyrChestplate = requirements(EssenceSet.of(6300, 60, 8080, 3420)) {
            it.tier(TierPredicate.min(4))
        }
        val tyrLeggings = requirements(EssenceSet.of(5080, 50, 7620, 3010)) {
            it.tier(TierPredicate.min(4))
        }
        val tyrBoots = requirements(EssenceSet.of(4460, 40, 5620, 2170)) {
            it.tier(TierPredicate.min(4))
        }

        val tier2 = requirements(EssenceSet.of(500, 10, 6000, 0)) {
            it.tier(TierPredicate.exact(1))
        }
        val tier3 = requirements(EssenceSet.of(1000, 50, 9000, 0)) {
            it.tier(TierPredicate.exact(2))
        }
        val tier4 = requirements(EssenceSet.of(2000, 100, 12000, 0)) {
            it.tier(TierPredicate.exact(3))
        }
        val tier5 = requirements(EssenceSet.of(5000, 500, 20000, 0)) {
            it.tier(TierPredicate.exact(4))
        }

        register(BuiltInRituals.ETERNAL_STELLA, ModItems.ETERNAL_STELLA.get(), Items.DIAMOND) {
            it
                .input(Ingredient.of(ModItems.XPETRIFIED_ORB), 3)
                .input(Ingredient.of(ModItems.STELLARITE_PIECE))
                .requirements(eternalStella)
        }
        register(BuiltInRituals.TERRASTOMP_PRISM, ModItems.TERRASTOMP_PRISM.get(), Blocks.DIAMOND_BLOCK) {
            it
                .input(Ingredient.of(Items.FLINT), 2)
                .input(Ingredient.of(Blocks.DRIPSTONE_BLOCK), 2)
                .input(Ingredient.of(Blocks.POINTED_DRIPSTONE), 2)
                .requirements(terrastompPrism)
        }
        register(BuiltInRituals.SEA_PRISM, ModItems.SEA_PRISM.get(), Items.HEART_OF_THE_SEA) {
            it
                .input(Ingredient.of(Items.PRISMARINE_SHARD), 2)
                .input(Ingredient.of(Items.TURTLE_SCUTE), 2)
                .input(Ingredient.of(Items.LAPIS_LAZULI), 2)
                .requirements(seaPrism)
        }
        register(BuiltInRituals.WHIRLWIND_PRISM, ModItems.WHIRLWIND_PRISM.get(), Blocks.WHITE_WOOL) {
            it
                .input(Ingredient.of(ModItems.BAT_WING))
                .input(Ingredient.of(Items.FEATHER), 2)
                .input(Ingredient.of(Items.PHANTOM_MEMBRANE), 3)
                .requirements(whirlwindPrism)
        }
        register(BuiltInRituals.SMELTER_PRISM, ModItems.SMELTER_PRISM.get(), ModBlocks.ARCANE_CRYSTAL_BLOCK.get()) {
            it
                .input(Ingredient.of(Items.COAL), 2)
                .input(Ingredient.of(Items.BLAZE_POWDER), 4)
                .requirements(smelterPrism)
        }
        register(BuiltInRituals.FERROGNETIC_MIXTURE, ModItems.FERROGNETIC_MIXTURE.get(), Blocks.LODESTONE) {
            it
                .input(Ingredient.of(Items.CLAY_BALL), 2)
                .input(Ingredient.of(ModItems.WAX), 2)
                .input(Ingredient.of(Items.SLIME_BALL), 2)
                .input(Ingredient.of(Items.IRON_INGOT))
                .requirements(ferrogneticMixture)
        }
        register(BuiltInRituals.QUANTUM_CATCHER, ModItems.QUANTUM_CATCHER.get(), ModBlocks.QUANTUM_CORE.get()) {
            it
                .input(Ingredient.of(ModItems.SPAWNER_SCRAP), 4)
                .requirements(quantumCatcher)
        }
        register(BuiltInRituals.BOSS_CATCHER, ModItems.BOSS_CATCHER.get(), ModItems.QUANTUM_CATCHER.get()) {
            it
                .input(Ingredient.of(Items.NETHER_STAR))
                .input(Ingredient.of(ModItems.STELLARITE_PIECE), 3)
                .requirements(bossCatcher)
        }
        register(BuiltInRituals.QUANTUM_INJECTOR, ModBlocks.QUANTUM_INJECTOR.get(), ModItems.MUNDABITUR_DUST.get()) {
            it
                .input(Ingredient.of(ModBlocks.QUANTUM_CORE.get()), 4)
                .requirements(quantumInjector)
        }
        register(BuiltInRituals.SOUL_BINDING_CRYSTAL, ModItems.SOUL_BINDING_CRYSTAL.get(), Items.QUARTZ) {
            it
                .input(Ingredient.of(ModItems.SOUL))
                .input(Ingredient.of(ModItems.CORRUPT_SOUL))
                .input(Ingredient.of(ModItems.ENCHANTED_SOUL))
                .input(Ingredient.of(Items.AMETHYST_SHARD), 2)
                .input(Ingredient.of(ModItems.ENDER_PEARL_FRAGMENT), 3)
                .requirements(soulBindingCrystal)
        }

        register(
            BuiltInRituals.DRACO_ARCANUS_HELMET, TransmuteInputResult(
                context.lookup(Registries.ITEM).getOrThrow(ModItems.DRACO_ARCANUS_HELMET.key!!)
            ),
            Items.NETHERITE_HELMET
        ) {
            it
                .input(Ingredient.of(ModItems.DRAGON_SCALE.get()), 2)
                .input(Ingredient.of(ModItems.OBSIDIANSTEEL_INGOT.get()), 3)
                .requirements(dracoArcanusHelmet)
        }
        register(
            BuiltInRituals.DRACO_ARCANUS_CHESTPLATE,
            TransmuteInputResult(
                context.lookup(Registries.ITEM).getOrThrow(ModItems.DRACO_ARCANUS_CHESTPLATE.key!!)
            ),
            Items.NETHERITE_CHESTPLATE
        ) {
            it
                .input(Ingredient.of(ModItems.DRAGON_SCALE.get()), 4)
                .input(Ingredient.of(ModItems.OBSIDIANSTEEL_INGOT.get()), 3)
                .requirements(dracoArcanusChestplate)
        }
        register(
            BuiltInRituals.DRACO_ARCANUS_LEGGINGS,
            TransmuteInputResult(context.lookup(Registries.ITEM).getOrThrow(ModItems.DRACO_ARCANUS_LEGGINGS.key!!)),
            Items.NETHERITE_LEGGINGS
        ) {
            it
                .input(Ingredient.of(ModItems.DRAGON_SCALE.get()), 3)
                .input(Ingredient.of(ModItems.OBSIDIANSTEEL_INGOT.get()), 3)
                .requirements(dracoArcanusLeggings)
        }
        register(
            BuiltInRituals.DRACO_ARCANUS_BOOTS,
            TransmuteInputResult(context.lookup(Registries.ITEM).getOrThrow(ModItems.DRACO_ARCANUS_BOOTS.key!!)),
            Items.NETHERITE_BOOTS
        ) {
            it
                .input(Ingredient.of(ModItems.DRAGON_SCALE.get()), 2)
                .input(Ingredient.of(ModItems.OBSIDIANSTEEL_INGOT.get()), 2)
                .requirements(dracoArcanusBoots)
        }

        register(
            BuiltInRituals.TYR_HELMET,
            TransmuteInputResult(context.lookup(Registries.ITEM).getOrThrow(ModItems.TYR_HELMET.key!!)),
            ModItems.DRACO_ARCANUS_HELMET
        ) {
            it
                .input(Ingredient.of(ModItems.AQUATIC_DRAGON_SCALE.get()), 4)
                .input(Ingredient.of(ModItems.GOLDEN_DRAGON_SCALE.get()), 1)
                .requirements(tyrHelmet)
        }
        register(
            BuiltInRituals.TYR_CHESTPLATE,
            TransmuteInputResult(context.lookup(Registries.ITEM).getOrThrow(ModItems.TYR_CHESTPLATE.key!!)),
            ModItems.DRACO_ARCANUS_CHESTPLATE
        ) {
            it
                .input(Ingredient.of(ModItems.AQUATIC_DRAGON_SCALE.get()), 4)
                .input(Ingredient.of(ModItems.GOLDEN_DRAGON_SCALE.get()), 4)
                .requirements(tyrChestplate)
        }
        register(
            BuiltInRituals.TYR_LEGGINGS,
            TransmuteInputResult(context.lookup(Registries.ITEM).getOrThrow(ModItems.TYR_LEGGINGS.key!!)),
            ModItems.DRACO_ARCANUS_LEGGINGS
        ) {
            it
                .input(Ingredient.of(ModItems.AQUATIC_DRAGON_SCALE.get()), 4)
                .input(Ingredient.of(ModItems.GOLDEN_DRAGON_SCALE.get()), 3)
                .requirements(tyrLeggings)
        }
        register(
            BuiltInRituals.TYR_BOOTS,
            TransmuteInputResult(context.lookup(Registries.ITEM).getOrThrow(ModItems.TYR_BOOTS.key!!)),
            ModItems.DRACO_ARCANUS_BOOTS
        ) {
            it
                .input(Ingredient.of(ModItems.AQUATIC_DRAGON_SCALE.get()), 3)
                .input(Ingredient.of(ModItems.GOLDEN_DRAGON_SCALE.get()), 2)
                .requirements(tyrBoots)
        }

        register(BuiltInRituals.UPGRADE_TIER_2, UpgradeTierResult(2), ModBlocks.EDELWOOD_PLANKS.get()) {
            it
                .input(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), 4)
                .input(Ingredient.of(ModItems.SPAWNER_SCRAP.get()), 4)
                .requirements(tier2).magicCircle(BuiltInMagicCircles.UPGRADE_TIER)
        }
        register(BuiltInRituals.UPGRADE_TIER_3, UpgradeTierResult(3), ModBlocks.CHISELED_POLISHED_DARKSTONE.get()) {
            it
                .input(Ingredient.of(ModItems.ARCANE_CRYSTAL.get()), 4)
                .input(Ingredient.of(ModItems.DEORUM_INGOT.get()), 4)
                .requirements(tier3).magicCircle(BuiltInMagicCircles.UPGRADE_TIER)
        }
        register(BuiltInRituals.UPGRADE_TIER_4, UpgradeTierResult(4), ModBlocks.CHISELED_POLISHED_DARKSTONE.get()) {
            it
                .input(Ingredient.of(ModItems.STELLARITE_PIECE.get()), 4)
                .input(Ingredient.of(ModItems.RUNE.get()), 4)
                .requirements(tier4)
                .magicCircle(BuiltInMagicCircles.UPGRADE_TIER)
        }
        register(BuiltInRituals.UPGRADE_TIER_5, UpgradeTierResult(5), ModBlocks.STELLARITE_BLOCK.get()) {
            it
                .input(Ingredient.of(Blocks.SCULK_CATALYST), 4)
                .input(Ingredient.of(ModItems.DARK_NETHER_STAR.get()), 2)
                .input(Ingredient.of(ModItems.DRAGON_SCALE.get()), 2)
                .requirements(tier5)
                .magicCircle(BuiltInMagicCircles.UPGRADE_FINAL_TIER)
        }
    }

    private fun requirements(
        essences: EssenceSet,
        builder: UnaryOperator<RitualRequirements.Builder>
    ): RitualRequirements {
        return builder.apply(RitualRequirements.builder(essences)).build()
    }

    private fun register(
        key: ResourceKey<Ritual>,
        result: ItemLike,
        mainIngredient: ItemLike,
        builder: UnaryOperator<RitualBuilder>
    ) {
        context!!.register(
            key, builder.apply(
                RitualBuilder(
                    mainIngredient,
                    CreateItemResult(net.minecraft.world.item.ItemStack(result)),
                    magicCircleLookup
                )
            ).build()
        )
    }

    private fun register(
        key: ResourceKey<Ritual>,
        result: RitualResult,
        mainIngredient: ItemLike,
        builder: UnaryOperator<RitualBuilder>
    ) {
        context!!.register(
            key,
            builder.apply(RitualBuilder(mainIngredient, result, magicCircleLookup)).build()
        )
    }
}
