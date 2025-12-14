package com.stal111.forbidden_arcanus.datagen.loot

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import java.util.function.BiConsumer

class ModBlockLootAdditions() : LootTableSubProvider {
    override fun generate(consumer: BiConsumer<ResourceKey<LootTable>, LootTable.Builder>) {
        consumer.accept(
            SPAWNER_SCRAP_ADDITION, LootTable.lootTable().withPool(
                LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.SPAWNER_SCRAP))
            )
        )
    }

    companion object {
        val SPAWNER_SCRAP_ADDITION: ResourceKey<LootTable> = register("blocks/additions/spawner_scrap_addition")

        private fun register(name: String): ResourceKey<LootTable> {
            return ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.identifier(name))
        }
    }
}
