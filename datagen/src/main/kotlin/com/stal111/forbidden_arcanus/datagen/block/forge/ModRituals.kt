package com.stal111.forbidden_arcanus.datagen.block.forge

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeTier
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.BuiltInRituals
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual
import com.stal111.forbidden_arcanus.common.block.entity.magiccircle.BuiltInMagicCircles
import com.stal111.forbidden_arcanus.common.item.enhancer.BuiltInEnhancers
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.valhelsia.dataforge.RegistryDataProvider

object ModRituals : RegistryDataProvider<Ritual> {

    override fun bootstrap(context: BootstrapContext<Ritual>) {
        context.rituals {
            createItem(BuiltInRituals.ETERNAL_STELLA, ModItems.ETERNAL_STELLA, Items.DIAMOND) {
                input(ModItems.CONDENSED_EXPERIENCE, 3)
                input(ModItems.STELLARITE_PIECE)
                essences(aureal = 82, ectoplasm = 1, blood = 1000)
                minTier(HephaestusForgeTier.THREE)
            }
            createItem(BuiltInRituals.TERRASTOMP_PRISM, ModItems.TERRASTOMP_PRISM, Blocks.DIAMOND_BLOCK) {
                input(Items.FLINT, 2)
                input(Blocks.DRIPSTONE_BLOCK, 2)
                input(Blocks.POINTED_DRIPSTONE, 2)
                essences(aureal = 300, ectoplasm = 9, blood = 1500)
                minTier(HephaestusForgeTier.TWO)
                enhancer(BuiltInEnhancers.ELEMENTARIUM)
            }
            createItem(BuiltInRituals.SEA_PRISM, ModItems.SEA_PRISM, Items.HEART_OF_THE_SEA) {
                input(Items.PRISMARINE_SHARD, 2)
                input(Items.TURTLE_SCUTE, 2)
                input(Items.LAPIS_LAZULI, 2)
                essences(aureal = 1000, ectoplasm = 8, blood = 2000)
                minTier(HephaestusForgeTier.THREE)
                enhancer(BuiltInEnhancers.ELEMENTARIUM)
            }
            createItem(BuiltInRituals.WHIRLWIND_PRISM, ModItems.WHIRLWIND_PRISM, Blocks.WHITE_WOOL) {
                input(ModItems.BAT_WING)
                input(Items.FEATHER, 2)
                input(Items.PHANTOM_MEMBRANE, 3)
                essences(aureal = 1000, ectoplasm = 3, blood = 2250)
                minTier(HephaestusForgeTier.THREE)
                enhancer(BuiltInEnhancers.ELEMENTARIUM)
            }
            createItem(BuiltInRituals.SMELTER_PRISM, ModItems.SMELTER_PRISM, ModBlocks.ARCANE_CRYSTAL_BLOCK.get()) {
                input(Items.COAL, 2)
                input(Items.BLAZE_POWDER, 4)
                essences(aureal = 200, ectoplasm = 4, blood = 1250)
                enhancer(BuiltInEnhancers.ELEMENTARIUM)
            }
            createItem(BuiltInRituals.FERROGNETIC_MIXTURE, ModItems.FERROGNETIC_MIXTURE, Blocks.LODESTONE) {
                input(Items.CLAY_BALL, 2)
                input(ModItems.WAX, 2)
                input(Items.SLIME_BALL, 2)
                input(Items.IRON_INGOT)
                essences(aureal = 100, ectoplasm = 2, blood = 1250)
            }
            createItem(BuiltInRituals.QUANTUM_CATCHER, ModItems.QUANTUM_CATCHER, ModBlocks.QUANTUM_CORE.get()) {
                input(ModItems.SPAWNER_SCRAP, 4)
                essences(aureal = 200, ectoplasm = 5, blood = 1200, experience = 155)
            }
            createItem(BuiltInRituals.BOSS_CATCHER, ModItems.BOSS_CATCHER, ModItems.QUANTUM_CATCHER) {
                input(Items.NETHER_STAR)
                input(ModItems.STELLARITE_PIECE, 3)
                essences(aureal = 500, ectoplasm = 10, blood = 7500, experience = 1200)
                minTier(HephaestusForgeTier.FOUR)
            }
            createItem(
                BuiltInRituals.QUANTUM_INJECTOR,
                ModBlocks.QUANTUM_INJECTOR.get(),
                ModItems.MUNDABITUR_DUST,
            ) {
                input(ModBlocks.QUANTUM_CORE.get(), 4)
                essences(aureal = 5000, ectoplasm = 50, blood = 3000, experience = 1060)
                minTier(HephaestusForgeTier.THREE)
            }
            createItem(BuiltInRituals.SOUL_BINDING_CRYSTAL, ModItems.SOUL_BINDING_CRYSTAL, Items.QUARTZ) {
                input(ModItems.SOUL)
                input(ModItems.CORRUPT_SOUL)
                input(ModItems.ENCHANTED_SOUL)
                input(Items.AMETHYST_SHARD, 2)
                input(ModItems.ENDER_PEARL_FRAGMENT, 3)
                essences(aureal = 1500, ectoplasm = 20, blood = 375, experience = 220)
                minTier(HephaestusForgeTier.FOUR)
                enhancer(BuiltInEnhancers.DIVINE_PACT)
            }

            transmute(
                BuiltInRituals.DRACO_ARCANUS_HELMET,
                ModItems.DRACO_ARCANUS_HELMET,
                Items.NETHERITE_HELMET,
            ) {
                input(ModItems.DRAGON_SCALE, 2)
                input(ModItems.STYGIAN_STEEL_INGOT, 3)
                essences(aureal = 820, ectoplasm = 6, blood = 475, experience = 420)
                minTier(HephaestusForgeTier.TWO)
            }
            transmute(
                BuiltInRituals.DRACO_ARCANUS_CHESTPLATE,
                ModItems.DRACO_ARCANUS_CHESTPLATE,
                Items.NETHERITE_CHESTPLATE,
            ) {
                input(ModItems.DRAGON_SCALE, 4)
                input(ModItems.STYGIAN_STEEL_INGOT, 3)
                essences(aureal = 1300, ectoplasm = 9, blood = 625, experience = 760)
                minTier(HephaestusForgeTier.TWO)
            }
            transmute(
                BuiltInRituals.DRACO_ARCANUS_LEGGINGS,
                ModItems.DRACO_ARCANUS_LEGGINGS,
                Items.NETHERITE_LEGGINGS,
            ) {
                input(ModItems.DRAGON_SCALE, 3)
                input(ModItems.STYGIAN_STEEL_INGOT, 3)
                essences(aureal = 1000, ectoplasm = 7, blood = 565, experience = 540)
                minTier(HephaestusForgeTier.TWO)
            }
            transmute(
                BuiltInRituals.DRACO_ARCANUS_BOOTS,
                ModItems.DRACO_ARCANUS_BOOTS,
                Items.NETHERITE_BOOTS,
            ) {
                input(ModItems.DRAGON_SCALE, 2)
                input(ModItems.STYGIAN_STEEL_INGOT, 2)
                essences(aureal = 700, ectoplasm = 5, blood = 405, experience = 370)
                minTier(HephaestusForgeTier.TWO)
            }

            transmute(
                BuiltInRituals.TYR_HELMET,
                ModItems.TYR_HELMET,
                ModItems.DRACO_ARCANUS_HELMET,
            ) {
                input(ModItems.AQUATIC_DRAGON_SCALE, 4)
                input(ModItems.GOLDEN_DRAGON_SCALE, 1)
                essences(aureal = 4820, ectoplasm = 40, blood = 5930, experience = 2450)
                minTier(HephaestusForgeTier.FOUR)
            }
            transmute(
                BuiltInRituals.TYR_CHESTPLATE,
                ModItems.TYR_CHESTPLATE,
                ModItems.DRACO_ARCANUS_CHESTPLATE,
            ) {
                input(ModItems.AQUATIC_DRAGON_SCALE, 4)
                input(ModItems.GOLDEN_DRAGON_SCALE, 4)
                essences(aureal = 6300, ectoplasm = 60, blood = 8080, experience = 3420)
                minTier(HephaestusForgeTier.FOUR)
            }
            transmute(
                BuiltInRituals.TYR_LEGGINGS,
                ModItems.TYR_LEGGINGS,
                ModItems.DRACO_ARCANUS_LEGGINGS,
            ) {
                input(ModItems.AQUATIC_DRAGON_SCALE, 4)
                input(ModItems.GOLDEN_DRAGON_SCALE, 3)
                essences(aureal = 5080, ectoplasm = 50, blood = 7620, experience = 3010)
                minTier(HephaestusForgeTier.FOUR)
            }
            transmute(
                BuiltInRituals.TYR_BOOTS,
                ModItems.TYR_BOOTS,
                ModItems.DRACO_ARCANUS_BOOTS,
            ) {
                input(ModItems.AQUATIC_DRAGON_SCALE, 3)
                input(ModItems.GOLDEN_DRAGON_SCALE, 2)
                essences(aureal = 4460, ectoplasm = 40, blood = 5620, experience = 2170)
                minTier(HephaestusForgeTier.FOUR)
            }

            upgradeTier(
                BuiltInRituals.UPGRADE_TIER_2,
                HephaestusForgeTier.TWO,
                ModBlocks.EDELWOOD_PLANKS.get(),
            ) {
                input(ModItems.ARCANE_CRYSTAL, 4)
                input(ModItems.SPAWNER_SCRAP, 4)
                essences(aureal = 500, ectoplasm = 10, blood = 6000)
                exactTier(HephaestusForgeTier.ONE)
                magicCircle = BuiltInMagicCircles.UPGRADE_TIER
            }
            upgradeTier(
                BuiltInRituals.UPGRADE_TIER_3,
                HephaestusForgeTier.THREE,
                ModBlocks.CHISELED_POLISHED_DARKSTONE.get(),
            ) {
                input(ModItems.ARCANE_CRYSTAL, 4)
                input(ModItems.DEORUM_INGOT, 4)
                essences(aureal = 1000, ectoplasm = 50, blood = 9000)
                exactTier(HephaestusForgeTier.TWO)
                magicCircle = BuiltInMagicCircles.UPGRADE_TIER
            }
            upgradeTier(
                BuiltInRituals.UPGRADE_TIER_4,
                HephaestusForgeTier.FOUR,
                ModBlocks.CHISELED_POLISHED_DARKSTONE.get(),
            ) {
                input(ModItems.STELLARITE_PIECE, 4)
                input(ModItems.RUNE, 4)
                essences(aureal = 2000, ectoplasm = 100, blood = 12000)
                exactTier(HephaestusForgeTier.THREE)
                magicCircle = BuiltInMagicCircles.UPGRADE_TIER
            }
            upgradeTier(
                BuiltInRituals.UPGRADE_TIER_5,
                HephaestusForgeTier.FIVE,
                ModBlocks.STELLARITE_BLOCK.get(),
            ) {
                input(Blocks.SCULK_CATALYST, 4)
                input(ModItems.DARK_NETHER_STAR, 2)
                input(ModItems.DRAGON_SCALE, 2)
                essences(aureal = 5000, ectoplasm = 500, blood = 20000)
                exactTier(HephaestusForgeTier.FOUR)
                magicCircle = BuiltInMagicCircles.UPGRADE_FINAL_TIER
            }
        }
    }
}
