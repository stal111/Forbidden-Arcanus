package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

/**
 * @author stal111
 * @since 14.05.2024
 */
public class ModChestLoot implements LootTableSubProvider {

    public static final ResourceKey<LootTable> ARTISAN_RELIC_ADDITION = register("chests/additions/artisan_relic_addition");
    public static final ResourceKey<LootTable> CRIMSON_STONE_ADDITION = register("chests/additions/crimson_stone_addition");
    public static final ResourceKey<LootTable> ELEMENTARIUM_ADDITION = register("chests/additions/elementarium_addition");
    public static final ResourceKey<LootTable> MALEDICTUS_PACT_ADDITION = register("chests/additions/maledictus_pact_addition");

    private final HolderLookup.Provider lookupProvider;

    public ModChestLoot(HolderLookup.Provider lookupProvider) {
        this.lookupProvider = lookupProvider;
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        HolderLookup.RegistryLookup<EnhancerDefinition> lookup = this.lookupProvider.lookup(FARegistries.ENHANCER_DEFINITION).orElseThrow();
        consumer.accept(ARTISAN_RELIC_ADDITION, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.ARTISAN_RELIC)).when(LootItemRandomChanceCondition.randomChance(0.18F))));
        consumer.accept(CRIMSON_STONE_ADDITION, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.CRIMSON_STONE)).when(LootItemRandomChanceCondition.randomChance(0.18F))));
        consumer.accept(ELEMENTARIUM_ADDITION, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.ELEMENTARIUM)).when(LootItemRandomChanceCondition.randomChance(0.21F))));
        consumer.accept(MALEDICTUS_PACT_ADDITION, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.MALEDICTUS_PACT)).when(LootItemRandomChanceCondition.randomChance(0.18F))));
    }

    private static ResourceKey<LootTable> register(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location(name));
    }
}
