package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaBlockLootTables;

/**
 * Mod Block Loot Tables <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.loot.ModBlockLootTables
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-02-12
 */
public class ModBlockLootTables extends ValhelsiaBlockLootTables {

    public ModBlockLootTables() {
        super(ForbiddenArcanus.REGISTRY_MANAGER);
    }

    @Override
    public void addTables() {
        take(block -> add(block, droppingWithFunction(block, builder -> builder.apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Fluid", "BlockEntityTag.Fluid")))), NewModBlocks.UTREM_JAR);
        take(block -> add(block, createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModItems.ARCANE_GOLD_NUGGET.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItems.ARCANE_GOLDEN_PICKAXE.get()))).otherwise(LootItem.lootTableItem(block)))), NewModBlocks.ARCANE_GILDED_DARKSTONE);
        take(block -> add(block, LootTable.lootTable()), NewModBlocks.BLACK_HOLE);
        take(block -> add(block, createSilkTouchDispatchTable(block, LootItem.lootTableItem(NewModBlocks.FUNGYSS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntRange.lowerBound(0))).apply(ApplyExplosionDecay.explosionDecay()))), NewModBlocks.FUNGYSS_BLOCK);
        take(block -> add(block, droppingWhen(block, ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER)), NewModBlocks.ARCANE_CRYSTAL_OBELISK);
        take(block -> add(block, createSingleItemTableWithSilkTouch(block, NewModItems.STELLARITE_PIECE.get())), NewModBlocks.STELLA_ARCANUM);
        take(block -> add(block, createOreDrop(block, ModItems.XPETRIFIED_ORB.get())), NewModBlocks.XPETRIFIED_ORE);
        take(block -> add(block, createOreDrop(block, ModItems.ARCANE_CRYSTAL.get())), NewModBlocks.ARCANE_CRYSTAL_ORE, NewModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE);
        take(block -> add(block, createSingleItemTableWithSilkTouch(block, ModItems.RUNE.get())), NewModBlocks.RUNIC_STONE, NewModBlocks.RUNIC_DEEPSLATE, NewModBlocks.RUNIC_DARKSTONE);
        take(block -> add(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).append(applyExplosionCondition(block, LootItem.lootTableItem(ModItems.STRANGE_ROOT.get())).when(LootItemRandomChanceCondition.randomChance(0.1F))))), NewModBlocks.PETRIFIED_ROOT);
        take(block -> add(block, createLeavesDrops(block, NewModBlocks.CHERRYWOOD_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(block, LootItem.lootTableItem(ModItems.CHERRY_PEACH.get())).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))))), NewModBlocks.CHERRYWOOD_LEAVES);
        take(block -> add(block, createLeavesDrops(block, NewModBlocks.MYSTERYWOOD_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F)), NewModBlocks.MYSTERYWOOD_LEAVES);
        take(block -> add(block, createLeavesDrops(block, NewModBlocks.MYSTERYWOOD_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(block, LootItem.lootTableItem(Items.GOLD_NUGGET)).when(LootItemRandomChanceCondition.randomChance(0.1F))))), NewModBlocks.NUGGETY_MYSTERYWOOD_LEAVES);

        forEach(block -> block instanceof IronBarsBlock, this::dropWhenSilkTouch);
        forEach(block -> block instanceof FlowerPotBlock, this::registerFlowerPot);
        forEach(block -> block instanceof SlabBlock, block -> add(block, ValhelsiaBlockLootTables::droppingSlab));
        forEach(block -> block instanceof DoorBlock, ValhelsiaBlockLootTables::registerDoor);

        forEach(this::registerDropSelfLootTable);
    }
}
