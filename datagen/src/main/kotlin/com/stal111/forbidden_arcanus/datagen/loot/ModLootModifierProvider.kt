package com.stal111.forbidden_arcanus.datagen.loot

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.common.item.modifier.BuiltInItemModifiers
import com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier
import com.stal111.forbidden_arcanus.common.loot.FieryLootModifier
import com.stal111.forbidden_arcanus.common.loot.MagicalFarmlandLootModifier
import com.stal111.forbidden_arcanus.core.init.ModDataComponents
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.advancements.criterion.DataComponentMatchers
import net.minecraft.advancements.criterion.EnchantmentPredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.core.component.DataComponentExactPredicate
import net.minecraft.core.component.predicates.DataComponentPredicates
import net.minecraft.core.component.predicates.EnchantmentsPredicate
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition.randomChance
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider
import net.neoforged.neoforge.common.loot.AddTableLootModifier
import net.neoforged.neoforge.common.loot.IGlobalLootModifier
import net.neoforged.neoforge.common.loot.LootTableIdCondition
import net.valhelsia.dataforge.DataProviderContext

class ModLootModifierProvider(
    context: DataProviderContext.Server
) : GlobalLootModifierProvider(context.packOutput, context.lookupProvider, ForbiddenArcanus.MOD_ID) {
    override fun start() {
        val itemGetter = this.registries.lookupOrThrow(Registries.ITEM)

        // Entities
        addTable(
            name = "spawner_additions",
            lootTable = ModBlockLootAdditions.SPAWNER_SCRAP_ADDITION,
            conditions = arrayOf(
                lootTableId(Blocks.SPAWNER.lootTable.orElseThrow()),
                doesNotHaveSilkTouch()
            )
        )

        addTable(
            name = "enderman_additions",
            lootTable = ModEntityLootAdditions.ENDER_PEARL_FRAGMENT_ADDITION,
            conditions = arrayOf(
                lootTableId(EntityType.ENDERMAN)
            )
        )

        addTable(
            name = "bat_additions",
            lootTable = ModEntityLootAdditions.BAT_WING_ADDITION,
            conditions = arrayOf(
                lootTableId(EntityType.BAT)
            )
        )

        addTable(
            name = "squid_additions",
            lootTable = ModEntityLootAdditions.TENTACLE_ADDITION,
            conditions = arrayOf(
                lootTableId(EntityType.SQUID),
                randomChance(0.7f)
            )
        )

        addTable(
            name = "ender_dragon_additions",
            lootTable = ModEntityLootAdditions.DRAGON_SCALE_ADDITION,
            conditions = arrayOf(
                lootTableId(EntityType.ENDER_DRAGON)
            )
        )

        // Chests
        addTable(
            name = "simple_dungeon_additions",
            lootTable = ModChestLootAdditions.AUREAL_BOTTLE_ADDITION,
            conditions = arrayOf(
                lootTableId(BuiltInLootTables.SIMPLE_DUNGEON)
            )
        )

        addTable(
            name = "end_city_treasure_additions",
            lootTable = ModChestLootAdditions.DRAGON_SCALE_ADDITION,
            conditions = arrayOf(
                lootTableId(BuiltInLootTables.END_CITY_TREASURE)
            )
        )

        addTable(
            name = "artisan_relic_additions",
            lootTable = ModChestLootAdditions.ARTISAN_RELIC_ADDITION,
            conditions = arrayOf(
                anyLootTableId(
                    BuiltInLootTables.VILLAGE_ARMORER,
                    BuiltInLootTables.VILLAGE_TOOLSMITH,
                    BuiltInLootTables.VILLAGE_WEAPONSMITH
                )
            )
        )

        addTable(
            name = "crimson_stone_addition",
            lootTable = ModChestLootAdditions.CRIMSON_STONE_ADDITION,
            conditions = arrayOf(
                lootTableId(BuiltInLootTables.PILLAGER_OUTPOST)
            )
        )

        addTable(
            name = "elementarium_addition",
            lootTable = ModChestLootAdditions.ELEMENTARIUM_ADDITION,
            conditions = arrayOf(
                anyLootTableId(
                    BuiltInLootTables.JUNGLE_TEMPLE,
                    BuiltInLootTables.DESERT_PYRAMID,
                    BuiltInLootTables.UNDERWATER_RUIN_SMALL,
                    BuiltInLootTables.UNDERWATER_RUIN_BIG
                )
            )
        )

        addTable(
            name = "maledictus_pact_addition",
            lootTable = ModChestLootAdditions.MALEDICTUS_PACT_ADDITION,
            conditions = arrayOf(
                lootTableId(BuiltInLootTables.BASTION_TREASURE)
            )
        )

        // Items
        this.add(
            "fiery_modifier",
            FieryLootModifier(
                arrayOf(
                    MatchTool.toolMatches(
                        ItemPredicate.Builder.item().withComponents(
                            DataComponentMatchers.Builder.components().exact(
                                DataComponentExactPredicate.expect(
                                    ModDataComponents.ITEM_MODIFIER.get(),
                                    this.registries.holderOrThrow(BuiltInItemModifiers.FIERY)
                                )
                            ).build()
                        )
                    ).build()
                ), IGlobalLootModifier.DEFAULT_PRIORITY
            )
        )
        this.add(
            "blacksmith_gavel_ore_doubling",
            BlacksmithGavelLootModifier(
                arrayOf(
                    MatchTool.toolMatches(
                        ItemPredicate.Builder.item().of(itemGetter, ModTags.Items.BLACKSMITH_GAVEL)
                    ).build(),
                    doesNotHaveSilkTouch().build(),
                    randomChance(0.3f).build()
                ),
                IGlobalLootModifier.DEFAULT_PRIORITY
            )
        )

        // Blocks
        this.add(
            "magical_farmland_crop_doubling",
            MagicalFarmlandLootModifier(arrayOf(), IGlobalLootModifier.DEFAULT_PRIORITY)
        )
    }

    private fun addTable(
        name: String,
        lootTable: ResourceKey<LootTable>,
        vararg conditions: LootItemCondition.Builder
    ) = this.add(
        name,
        AddTableLootModifier(
            conditions.map { it.build() }.toTypedArray(),
            IGlobalLootModifier.DEFAULT_PRIORITY,
            lootTable
        )
    )

    private fun lootTableId(lootTable: ResourceKey<LootTable>): LootItemCondition.Builder =
        LootTableIdCondition.builder(lootTable.identifier())

    private fun lootTableId(entityType: EntityType<*>): LootItemCondition.Builder =
        this.lootTableId(entityType.defaultLootTable.orElseThrow())

    private fun anyLootTableId(vararg lootTables: ResourceKey<LootTable>): AnyOfCondition.Builder =
        AnyOfCondition.anyOf(*lootTables.map { this.lootTableId(it) }.toTypedArray())

    private fun doesNotHaveSilkTouch(): LootItemCondition.Builder {
        val enchantmentGetter = this.registries.lookupOrThrow(Registries.ENCHANTMENT)

        return MatchTool.toolMatches(
            ItemPredicate.Builder.item().withComponents(
                DataComponentMatchers.Builder.components().partial(
                    DataComponentPredicates.ENCHANTMENTS,
                    EnchantmentsPredicate.enchantments(
                        listOf(
                            EnchantmentPredicate(
                                enchantmentGetter.getOrThrow(Enchantments.SILK_TOUCH),
                                MinMaxBounds.Ints.atLeast(1)
                            )
                        )
                    )
                ).build()
            )
        ).invert()
    }
}
