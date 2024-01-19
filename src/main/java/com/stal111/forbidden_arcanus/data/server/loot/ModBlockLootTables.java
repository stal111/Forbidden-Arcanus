package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.valhelsia.valhelsia_core.datagen.ValhelsiaBlockLootTables;

import java.util.Set;

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
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, ForbiddenArcanus.REGISTRY_MANAGER);
    }

    @Override
    protected void generate() {
        //TODO
//        take(block -> add(block, droppingWithFunction(block, builder -> builder.apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Fluid", "BlockEntityTag.Fluid")))), ModBlocks.UTREM_JAR);
//        take(block -> add(block, LootTable.lootTable()), ModBlocks.BLACK_HOLE, ModBlocks.CLIBANO_SIDE_HORIZONTAL, ModBlocks.CLIBANO_SIDE_VERTICAL, ModBlocks.CLIBANO_CORNER, ModBlocks.CLIBANO_CENTER, ModBlocks.CLIBANO_MAIN_PART);
//        take(block -> add(block, createSilkTouchDispatchTable(block, LootItem.lootTableItem(ModBlocks.FUNGYSS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntRange.lowerBound(0))).apply(ApplyExplosionDecay.explosionDecay()))), ModBlocks.FUNGYSS_BLOCK);
//        take(block -> add(block, droppingWhen(block, ArcaneCrystalObeliskBlock.PART, ObeliskPart.LOWER)), ModBlocks.ARCANE_CRYSTAL_OBELISK, ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK);
//        take(block -> add(block, createSingleItemTableWithSilkTouch(block, ModItems.STELLARITE_PIECE.get())), ModBlocks.STELLA_ARCANUM);
//        take(block -> add(block, createOreDrop(block, ModItems.ARCANE_CRYSTAL.get())), ModBlocks.ARCANE_CRYSTAL_ORE, ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE);
//        take(block -> add(block, createSingleItemTableWithSilkTouch(block, ModItems.RUNE.get())), ModBlocks.RUNIC_STONE, ModBlocks.RUNIC_DEEPSLATE, ModBlocks.RUNIC_DARKSTONE);
//        take(block -> add(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).append(applyExplosionCondition(block, LootItem.lootTableItem(ModItems.STRANGE_ROOT.get())).when(LootItemRandomChanceCondition.randomChance(0.1F))))), ModBlocks.PETRIFIED_ROOT);
//        take(block -> add(block, createLeavesDrops(block, ModBlocks.CHERRY_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(block, LootItem.lootTableItem(ModItems.CHERRY_PEACH.get())).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))))), ModBlocks.CHERRY_LEAVES);
//        take(block -> add(block, createLeavesDrops(block, ModBlocks.AURUM_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F)), ModBlocks.AURUM_LEAVES);
//        take(block -> add(block, createLeavesDrops(block, ModBlocks.AURUM_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(applyExplosionCondition(block, LootItem.lootTableItem(Items.GOLD_NUGGET)).when(LootItemRandomChanceCondition.randomChance(0.1F))))), ModBlocks.NUGGETY_AURUM_LEAVES);
//        take(block -> {
//            LootItemCondition.Builder builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.AGE_6, 6));
//            add(block, applyExplosionDecay(block, createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(ModItems.GOLDEN_ORCHID_SEEDS.get()))).withPool(LootPool.lootPool().when(HAS_NO_SILK_TOUCH).when(builder).add(LootItem.lootTableItem(ModItems.DEORUM_NUGGET.get()).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))))));
//        }, ModBlocks.GOLDEN_ORCHID);
//        take(block -> {
//            LootItemCondition.Builder builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3));
//            add(block, createSingleItemTable(ModItems.STRANGE_ROOT.get()).withPool(LootPool.lootPool().when(builder).add(LootItem.lootTableItem(ModItems.STRANGE_ROOT.get()).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
//        }, ModBlocks.STRANGE_ROOT);
//        take(block -> add(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(ModItems.EDELWOOD_STICK.get())))), ModBlocks.EDELWOOD_BRANCH);
//        take(block -> add(block, ModBlockLootTables::createSilkTouchOrShearsOnlyTable),
//                ModBlocks.CHERRY_LEAF_CARPET,
//                ModBlocks.CHERRY_FLOWER_VINES,
//                ModBlocks.CHERRY_FLOWER_VINES_PLANT
//        );
//
//        forEach(block -> block instanceof IronBarsBlock, this::dropWhenSilkTouch);
//        forEach(block -> block instanceof FlowerPotBlock, this::registerFlowerPot);
//        forEach(block -> block instanceof SlabBlock, block -> add(block, ValhelsiaBlockLootTables::droppingSlab));
//        forEach(block -> block instanceof DoorBlock, block -> add(block, block1 -> this.createSinglePropConditionTable(block1, DoorBlock.HALF, DoubleBlockHalf.LOWER)));
//
//        forEach(this::registerDropSelfLootTable);
    }

    protected static LootTable.Builder createSilkTouchOrShearsOnlyTable(ItemLike pItem) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(HAS_SHEARS_OR_SILK_TOUCH).setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(pItem)));
    }
}
