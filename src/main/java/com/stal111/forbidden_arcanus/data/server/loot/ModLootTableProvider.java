package com.stal111.forbidden_arcanus.data.server.loot;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.data.server.loot.ModBlockLootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Mod Loot Table Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.loot.ModLootTableProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-12
 */
public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((resourceLocation, lootTable) -> LootTableManager.validateLootTable(validationtracker, resourceLocation, lootTable));

    }
}
