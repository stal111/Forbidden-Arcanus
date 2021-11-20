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
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaBlockLootTables;

/**
 * Mod Block Loot Tables
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
        take(this::registerSilkTouch, NewModBlocks.ARCANE_GLASS, NewModBlocks.ARCANE_GLASS_PANE);
        take(block -> add(block, droppingWithSilkTouch(block, LootItem.lootTableItem(ModItems.ARCANE_GOLD_NUGGET.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItems.ARCANE_GOLDEN_PICKAXE.get()))).otherwise(LootItem.lootTableItem(block)))), NewModBlocks.ARCANE_GILDED_DARKSTONE);
        take(block -> add(block, LootTable.lootTable()), NewModBlocks.BLACK_HOLE);
        take(block -> add(block, droppingWithSilkTouch(block, LootItem.lootTableItem(NewModBlocks.FUNGYSS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntRange.lowerBound(0))).apply(ApplyExplosionDecay.explosionDecay()))), NewModBlocks.FUNGYSS_BLOCK);
        take(block -> add(block, droppingWhen(block, ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER)), NewModBlocks.ARCANE_CRYSTAL_OBELISK);
        take(block -> add(block, createSingleItemTableWithSilkTouch(block, NewModItems.STELLARITE_PIECE.get())), NewModBlocks.STELLA_ARCANUM);
        take(block -> add(block, createOreDrop(block, ModItems.XPETRIFIED_ORB.get())), NewModBlocks.XPETRIFIED_ORE);
        take(block -> add(block, createOreDrop(block, ModItems.ARCANE_CRYSTAL.get())), NewModBlocks.ARCANE_CRYSTAL_ORE, NewModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE);
        take(block -> add(block, createSingleItemTableWithSilkTouch(block, ModItems.RUNE.get())), NewModBlocks.RUNIC_STONE, NewModBlocks.RUNIC_DEEPSLATE, NewModBlocks.RUNIC_DARKSTONE);
        take(block -> add(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).append(applyExplosionCondition(block, LootItem.lootTableItem(ModItems.STRANGE_ROOT.get())).when(LootItemRandomChanceCondition.randomChance(0.1F))))), NewModBlocks.PETRIFIED_ROOT);

        forEach(block -> block instanceof FlowerPotBlock, this::registerFlowerPot);
        forEach(block -> block instanceof SlabBlock, block -> add(block, ValhelsiaBlockLootTables::droppingSlab));
        forEach(block -> block instanceof DoorBlock, ValhelsiaBlockLootTables::registerDoor);

        forEach(this::registerDropSelfLootTable);
    }
}
