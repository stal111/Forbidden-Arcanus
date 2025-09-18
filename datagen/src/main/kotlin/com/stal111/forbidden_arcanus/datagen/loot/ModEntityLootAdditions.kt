package com.stal111.forbidden_arcanus.datagen.loot

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.core.init.ModItems
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.LootTableSubProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import java.util.function.BiConsumer

class ModEntityLootAdditions(private val registries: HolderLookup.Provider) : LootTableSubProvider {
    override fun generate(consumer: BiConsumer<ResourceKey<LootTable>, LootTable.Builder>) {
        consumer.accept(
            BAT_WING_ADDITION,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).add(LootItem.lootTableItem(ModItems.BAT_WING))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))).apply(
                    EnchantedCountIncreaseFunction.lootingMultiplier(
                        this.registries,
                        UniformGenerator.between(0.0f, 1.0f)
                    )
                )
            )
        )
        consumer.accept(
            TENTACLE_ADDITION,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).add(LootItem.lootTableItem(ModItems.TENTACLE))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 1.0f))).apply(
                    EnchantedCountIncreaseFunction.lootingMultiplier(
                        this.registries,
                        UniformGenerator.between(0.0f, 1.0f)
                    )
                )
            )
        )
        consumer.accept(
            ENDER_PEARL_FRAGMENT_ADDITION,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.ENDER_PEARL_FRAGMENT))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f))).apply(
                    EnchantedCountIncreaseFunction.lootingMultiplier(
                        this.registries,
                        UniformGenerator.between(0.0f, 2.0f)
                    )
                )
            )
        )
        consumer.accept(
            DRAGON_SCALE_ADDITION,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                    .add(LootItem.lootTableItem(ModItems.DRAGON_SCALE))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))).apply(
                    EnchantedCountIncreaseFunction.lootingMultiplier(
                        this.registries,
                        UniformGenerator.between(0.0f, 2.0f)
                    )
                )
            )
        )
    }

    companion object {
        val BAT_WING_ADDITION = register("entities/additions/bat_wing_addition")
        val TENTACLE_ADDITION = register("entities/additions/tentacle_addition")
        val ENDER_PEARL_FRAGMENT_ADDITION = register("entities/additions/ender_pearl_fragment_addition")
        val DRAGON_SCALE_ADDITION = register("entities/additions/dragon_scale_addition")

        private fun register(name: String): ResourceKey<LootTable> {
            return ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location(name))
        }
    }
}
