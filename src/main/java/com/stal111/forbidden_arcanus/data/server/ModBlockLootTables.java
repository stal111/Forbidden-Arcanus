package com.stal111.forbidden_arcanus.data.server;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.NewerModBlocks;
import net.minecraft.loot.functions.CopyNbt;
import net.valhelsia.valhelsia_core.data.ValhelsiaBlockLootTables;

/**
 * Mod Block Loot Tables
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.ModBlockLootTables
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-12
 */
public class ModBlockLootTables extends ValhelsiaBlockLootTables {

    public ModBlockLootTables() {
        super(ForbiddenArcanus.REGISTRY_MANAGER);
    }

    @Override
    public void addTables() {
        take(block -> registerLootTable(block, block1 -> droppingWithFunction(block1, builder -> builder
                .acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Fluid", "BlockEntityTag.Fluid")))),
                NewerModBlocks.UTREM_JAR);

        forEach(this::registerDropSelfLootTable);
    }
}
