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
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class ModEntityLootAdditions implements LootTableSubProvider {

    public static final ResourceKey<LootTable> BAT_WING_ADDITION = register("entities/additions/bat_wing_addition");
    public static final ResourceKey<LootTable> TENTACLE_ADDITION = register("entities/additions/tentacle_addition");
    public static final ResourceKey<LootTable> ENDER_PEARL_FRAGMENT_ADDITION = register("entities/additions/ender_pearl_fragment_addition");
    public static final ResourceKey<LootTable> DRAGON_SCALE_ADDITION = register("entities/additions/dragon_scale_addition");

    private final HolderLookup.Provider registries;

    public ModEntityLootAdditions(HolderLookup.Provider lookupProvider) {
        this.registries = lookupProvider;
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(BAT_WING_ADDITION, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.BAT_WING)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))));
        consumer.accept(TENTACLE_ADDITION, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.TENTACLE)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))));
        consumer.accept(ENDER_PEARL_FRAGMENT_ADDITION, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.ENDER_PEARL_FRAGMENT)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 2.0F)))));
        consumer.accept(DRAGON_SCALE_ADDITION, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(ModItems.DRAGON_SCALE)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 2.0F)))));
    }

    private static ResourceKey<LootTable> register(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, ForbiddenArcanus.location(name));
    }
}
