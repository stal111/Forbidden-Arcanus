package com.stal111.forbidden_arcanus.datagen.loot

import com.stal111.forbidden_arcanus.core.init.ModEntities
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.loot.EntityLootSubProvider
import net.minecraft.world.entity.EntityType
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import java.util.stream.Stream

class ModEntityLoot(
    lookupProvider: HolderLookup.Provider
) : EntityLootSubProvider(FeatureFlags.DEFAULT_FLAGS, lookupProvider) {

    override fun generate() {
        add(
            ModEntities.LOST_SOUL.get(),
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).add(LootItem.lootTableItem(ModItems.SOUL))
            )
        )
        add(
            ModEntities.CORRUPT_LOST_SOUL.get(),
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.CORRUPT_SOUL))
            )
        )
        add(
            ModEntities.ENCHANTED_LOST_SOUL.get(),
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.ENCHANTED_SOUL))
            )
        )
    }

    override fun getKnownEntityTypes(): Stream<EntityType<*>> {
        return Stream.of(
            ModEntities.LOST_SOUL.get(),
            ModEntities.CORRUPT_LOST_SOUL.get(),
            ModEntities.ENCHANTED_LOST_SOUL.get()
        )
    }
}