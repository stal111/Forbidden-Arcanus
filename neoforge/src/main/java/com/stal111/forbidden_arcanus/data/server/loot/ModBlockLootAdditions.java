package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class ModBlockLootAdditions implements LootTableSubProvider {

    public static final ResourceKey<LootTable> SPAWNER_SCRAP_ADDITION = register("blocks/additions/spawner_scrap_addition");

    public ModBlockLootAdditions(HolderLookup.Provider lookupProvider) {
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(SPAWNER_SCRAP_ADDITION, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.SPAWNER_SCRAP))));
    }

    private static ResourceKey<LootTable> register(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location(name));
    }
}
