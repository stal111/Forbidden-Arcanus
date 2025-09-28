package com.stal111.forbidden_arcanus.datagen.loot

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.advancements.critereon.FAItemSubPredicates
import com.stal111.forbidden_arcanus.common.advancements.critereon.ItemModifierPredicate
import com.stal111.forbidden_arcanus.common.item.modifier.BuiltInItemModifiers
import com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier
import com.stal111.forbidden_arcanus.common.loot.FieryLootModifier
import com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier
import net.minecraft.advancements.critereon.*
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider
import net.neoforged.neoforge.common.loot.AddTableLootModifier
import net.neoforged.neoforge.common.loot.LootTableIdCondition
import net.valhelsia.dataforge.DataProviderContext

class ModLootModifierProvider(
    context: DataProviderContext.Server
) : GlobalLootModifierProvider(context.packOutput, context.lookupProvider, ForbiddenArcanus.MOD_ID) {
    override fun start() {
        // Entities
        this.add(
            "spawner_additions",
            AddTableLootModifier(
                arrayOf(
                    InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(
                            ItemPredicate.Builder.item().withSubPredicate(
                                ItemSubPredicates.ENCHANTMENTS,
                                ItemEnchantmentsPredicate.enchantments(
                                    listOf(
                                        EnchantmentPredicate(
                                            this.registries.lookupOrThrow(
                                                Registries.ENCHANTMENT
                                            ).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1)
                                        )
                                    )
                                )
                            )
                        )
                    ).build(), LootTableIdCondition.builder(Blocks.SPAWNER.lootTable.orElseThrow().location()).build()
                ), ModBlockLootAdditions.SPAWNER_SCRAP_ADDITION
            )
        )
        this.add(
            "enderman_additions",
            AddTableLootModifier(
                arrayOf(
                    LootTableIdCondition.builder(
                        EntityType.ENDERMAN.defaultLootTable.orElseThrow().location()
                    ).build()
                ), ModEntityLootAdditions.ENDER_PEARL_FRAGMENT_ADDITION
            )
        )
        this.add(
            "bat_additions",
            AddTableLootModifier(
                arrayOf(
                    LootTableIdCondition.builder(
                        EntityType.BAT.defaultLootTable.orElseThrow().location()
                    ).build()
                ), ModEntityLootAdditions.BAT_WING_ADDITION
            )
        )
        this.add(
            "squid_additions",
            AddTableLootModifier(
                arrayOf(
                    LootItemRandomChanceCondition.randomChance(0.7f).build(),
                    LootTableIdCondition.builder(EntityType.SQUID.defaultLootTable.orElseThrow().location()).build()
                ), ModEntityLootAdditions.TENTACLE_ADDITION
            )
        )
        this.add(
            "ender_dragon_additions",
            AddTableLootModifier(
                arrayOf(
                    LootTableIdCondition.builder(
                        EntityType.ENDER_DRAGON.defaultLootTable.orElseThrow().location()
                    ).build()
                ), ModEntityLootAdditions.DRAGON_SCALE_ADDITION
            )
        )

        // Chests
        this.add(
            "simple_dungeon_additions",
            AddTableLootModifier(
                arrayOf(
                    LootTableIdCondition.builder(BuiltInLootTables.SIMPLE_DUNGEON.location()).build()
                ), ModChestLootAdditions.AUREAL_BOTTLE_ADDITION
            )
        )
        this.add(
            "end_city_treasure_additions",
            AddTableLootModifier(
                arrayOf(
                    LootTableIdCondition.builder(BuiltInLootTables.END_CITY_TREASURE.location()).build()
                ), ModChestLootAdditions.DRAGON_SCALE_ADDITION
            )
        )
        this.add(
            "artisan_relic_addition",
            AddTableLootModifier(
                arrayOf(
                    AnyOfCondition.anyOf(
                        LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_ARMORER.location()),
                        LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_TOOLSMITH.location()),
                        LootTableIdCondition.builder(BuiltInLootTables.VILLAGE_WEAPONSMITH.location())
                    ).build()
                ), ModChestLootAdditions.ARTISAN_RELIC_ADDITION
            )
        )
        this.add(
            "crimson_stone_addition",
            AddTableLootModifier(
                arrayOf(
                    LootTableIdCondition.builder(BuiltInLootTables.PILLAGER_OUTPOST.location()).build()
                ), ModChestLootAdditions.CRIMSON_STONE_ADDITION
            )
        )
        this.add(
            "elementarium_addition",
            AddTableLootModifier(
                arrayOf(
                    AnyOfCondition.anyOf(
                        LootTableIdCondition.builder(BuiltInLootTables.JUNGLE_TEMPLE.location()),
                        LootTableIdCondition.builder(BuiltInLootTables.DESERT_PYRAMID.location()),
                        LootTableIdCondition.builder(BuiltInLootTables.UNDERWATER_RUIN_SMALL.location()),
                        LootTableIdCondition.builder(BuiltInLootTables.UNDERWATER_RUIN_BIG.location())
                    ).build()
                ), ModChestLootAdditions.ELEMENTARIUM_ADDITION
            )
        )
        this.add(
            "maledictus_pact",
            AddTableLootModifier(
                arrayOf(
                    LootTableIdCondition.builder(BuiltInLootTables.BASTION_TREASURE.location()).build()
                ), ModChestLootAdditions.MALEDICTUS_PACT_ADDITION
            )
        )

        // Items
        this.add(
            "fiery_modifier",
            FieryLootModifier(
                arrayOf(
                    MatchTool.toolMatches(
                        ItemPredicate.Builder.item().withSubPredicate(
                            FAItemSubPredicates.MODIFIER.get(),
                            ItemModifierPredicate.modifier(this.registries.holderOrThrow(BuiltInItemModifiers.FIERY))
                        )
                    ).build()
                )
            )
        )
        this.add(
            "blacksmith_gavel_ore_doubling",
            BlacksmithGavelLootModifier(arrayOf(LootItemRandomChanceCondition.randomChance(0.3f).build()))
        )

        // Blocks
        this.add("magical_farmland_crop_doubling", MagicalFarmlandLootModifier(arrayOf()))
    }
}