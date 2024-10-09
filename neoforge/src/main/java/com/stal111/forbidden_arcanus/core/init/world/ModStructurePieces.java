package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.structure.NipaPieces;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * Mod Structure Pieces
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructurePieces
 *
 * @author stal111
 * @since 2021-04-12
 */
public class ModStructurePieces implements RegistryClass {

    public static final MappedRegistryHelper<StructurePieceType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.STRUCTURE_PIECE);


    public static final RegistryEntry<StructurePieceType, StructurePieceType> NIPA = HELPER.register("nipa", () -> NipaPieces.Piece::new);
}
