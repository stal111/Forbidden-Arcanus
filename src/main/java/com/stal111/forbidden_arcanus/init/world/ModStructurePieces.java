package com.stal111.forbidden_arcanus.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.world.structure.NipaPieces;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;

/**
 * Mod Structure Pieces
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructurePieces
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-04-12
 */
public class ModStructurePieces {

    public static final StructurePieceType NIPA = register("nipa", NipaPieces.Piece::new);

    static StructurePieceType register(String name, StructurePieceType type) {
        return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), type);
    }
}
