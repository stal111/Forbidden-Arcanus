package com.stal111.forbidden_arcanus.init.world;

import com.stal111.forbidden_arcanus.world.structure.NipaPieces;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

import java.util.Locale;

/**
 * Mod Structure Pieces
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructurePieces
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-12
 */
public class ModStructurePieces {

    public static final IStructurePieceType NIPA = register(NipaPieces.Piece::new, "Nipa");

    static IStructurePieceType register(IStructurePieceType type, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), type);
    }
}
