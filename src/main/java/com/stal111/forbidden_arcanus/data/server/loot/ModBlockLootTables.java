package com.stal111.forbidden_arcanus.data.server.loot;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
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
        take(block -> registerLootTable(block, droppingWithFunction(block, builder -> builder.apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Fluid", "BlockEntityTag.Fluid")))), NewModBlocks.UTREM_JAR);
        forEach(block -> block instanceof SlabBlock, block -> registerLootTable(block, ValhelsiaBlockLootTables::droppingSlab));
        forEach(block -> block instanceof DoorBlock, ValhelsiaBlockLootTables::registerDoor);
        take(this::registerSilkTouch, NewModBlocks.ARCANE_GLASS, NewModBlocks.ARCANE_GLASS_PANE);
        take(block -> registerLootTable(block, droppingWithSilkTouch(block, LootItem.lootTableItem(ModItems.ARCANE_GOLD_NUGGET.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItems.ARCANE_GOLDEN_PICKAXE.get()))).otherwise(LootItem.lootTableItem(block)))), NewModBlocks.ARCANE_GILDED_DARKSTONE);
        take(block -> registerLootTable(block, LootTable.lootTable()), NewModBlocks.BLACK_HOLE);
        take(block -> registerLootTable(block, droppingWithSilkTouch(block, LootItem.lootTableItem(NewModBlocks.FUNGYSS.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntRange.lowerBound(0))).apply(ApplyExplosionDecay.explosionDecay()))), NewModBlocks.FUNGYSS_BLOCK);
        take(block -> registerLootTable(block, droppingWhen(block, ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER)), NewModBlocks.ARCANE_CRYSTAL_OBELISK);
        take(block -> registerLootTable(block, droppingWithSilkTouch(block, ModItems.ARCANE_CRYSTAL.get()).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))), NewModBlocks.ARCANE_CRYSTAL_ORE);
        take(block -> registerLootTable(block, droppingWithSilkTouch(block, ModItems.RUNE.get())), NewModBlocks.RUNIC_STONE, NewModBlocks.RUNIC_DEEPSLATE, NewModBlocks.RUNIC_DARKSTONE);
        forEach(block -> block instanceof FlowerPotBlock, this::registerFlowerPot);

        forEach(this::registerDropSelfLootTable);
    }
}
