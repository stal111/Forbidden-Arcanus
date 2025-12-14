package com.stal111.forbidden_arcanus.datagen.loot

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import java.util.function.BiConsumer

class ModChestLootAdditions() : LootTableSubProvider {
    override fun generate(consumer: BiConsumer<ResourceKey<LootTable>, LootTable.Builder>) {
        consumer.accept(
            ARTISAN_RELIC_ADDITION, LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.ARTISAN_RELIC))
                    .`when`(LootItemRandomChanceCondition.randomChance(0.28f))
            )
        )
        consumer.accept(
            CRIMSON_STONE_ADDITION, LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.CRIMSON_STONE))
                    .`when`(LootItemRandomChanceCondition.randomChance(0.18f))
            )
        )
        consumer.accept(
            ELEMENTARIUM_ADDITION,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.ELEMENTARIUM))
                    .`when`(LootItemRandomChanceCondition.randomChance(0.21f))
            )
        )
        consumer.accept(
            MALEDICTUS_PACT_ADDITION,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.MALEDICTUS_PACT))
                    .`when`(LootItemRandomChanceCondition.randomChance(0.18f))
            )
        )
        consumer.accept(
            DRAGON_SCALE_ADDITION,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.DRAGON_SCALE))
                    .`when`(LootItemRandomChanceCondition.randomChance(0.56f))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
            )
        )
        consumer.accept(
            AUREAL_BOTTLE_ADDITION,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.AUREAL_BOTTLE))
                    .`when`(LootItemRandomChanceCondition.randomChance(0.2f))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
            )
        )
    }

    companion object {
        val ARTISAN_RELIC_ADDITION: ResourceKey<LootTable> = register("chests/additions/artisan_relic_addition")
        val CRIMSON_STONE_ADDITION: ResourceKey<LootTable> = register("chests/additions/crimson_stone_addition")
        val ELEMENTARIUM_ADDITION: ResourceKey<LootTable> = register("chests/additions/elementarium_addition")
        val MALEDICTUS_PACT_ADDITION: ResourceKey<LootTable> = register("chests/additions/maledictus_pact_addition")
        val DRAGON_SCALE_ADDITION: ResourceKey<LootTable> = register("chests/additions/dragon_scale_addition")
        val AUREAL_BOTTLE_ADDITION: ResourceKey<LootTable> = register("chests/additions/aureal_bottle_addition")

        private fun register(name: String): ResourceKey<LootTable> {
            return ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.identifier(name))
        }
    }
}
