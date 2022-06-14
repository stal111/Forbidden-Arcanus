package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.structure.NipaPieces;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.RegistryHelper;

/**
 * Mod Structure Pieces
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructurePieces
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-04-12
 */
public class ModStructurePieces implements RegistryClass {

    public static final RegistryHelper<StructurePieceType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registry.STRUCTURE_PIECE_REGISTRY);


    public static final RegistryObject<StructurePieceType> NIPA = HELPER.register("nipa", () -> NipaPieces.Piece::new);
}
