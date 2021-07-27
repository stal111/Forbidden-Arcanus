package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.IntClamper;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.*;
import net.valhelsia.valhelsia_core.data.ValhelsiaBlockLootTables;

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
        take(block -> registerLootTable(block, droppingWithFunction(block, builder -> builder.acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Fluid", "BlockEntityTag.Fluid")))), NewModBlocks.UTREM_JAR);
        forEach(block -> block instanceof SlabBlock, block -> registerLootTable(block, ValhelsiaBlockLootTables::droppingSlab));
        forEach(block -> block instanceof DoorBlock, ValhelsiaBlockLootTables::registerDoor);
        take(this::registerSilkTouch, NewModBlocks.ARCANE_GLASS, NewModBlocks.ARCANE_GLASS_PANE);
        take(block -> registerLootTable(block, droppingWithSilkTouch(block, ItemLootEntry.builder(ModItems.ARCANE_GOLD_NUGGET.get()).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 4.0F))).acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().item(ModItems.ARCANE_GOLDEN_PICKAXE.get()))).alternatively(ItemLootEntry.builder(block)))), NewModBlocks.ARCANE_GILDED_DARKSTONE);
        take(block -> registerLootTable(block, LootTable.builder()), NewModBlocks.BLACK_HOLE);
        take(block -> registerLootTable(block, droppingWithSilkTouch(block, ItemLootEntry.builder(NewModBlocks.FUNGYSS.get()).acceptFunction(SetCount.builder(RandomValueRange.of(-6.0F, 2.0F))).acceptFunction(LimitCount.func_215911_a(IntClamper.func_215848_a(0))).acceptFunction(ExplosionDecay.builder()))), NewModBlocks.FUNGYSS_BLOCK);
        take(block -> registerLootTable(block, droppingWhen(block, ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER)), NewModBlocks.ARCANE_CRYSTAL_OBELISK);
        take(block -> registerLootTable(block, droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(ModItems.ARCANE_CRYSTAL.get()).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))))), NewModBlocks.ARCANE_CRYSTAL_ORE);

        forEach(this::registerDropSelfLootTable);
    }
}
