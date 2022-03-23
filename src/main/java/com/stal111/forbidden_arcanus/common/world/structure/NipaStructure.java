package com.stal111.forbidden_arcanus.common.world.structure;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.world.structure.config.NipaConfig;
import com.stal111.forbidden_arcanus.core.init.world.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.valhelsia.valhelsia_core.common.world.SimpleValhelsiaStructure;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Nipa Structure <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.structure.NipaStructure
 *
 * @author stal111
 * @version 1.18.1 - 2.0.0
 * @since 2021-04-07
 */
public class NipaStructure extends SimpleValhelsiaStructure<NipaConfig> {

    public NipaStructure(Codec<NipaConfig> codec) {
        super(codec, PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), NipaStructure::generatePieces), "nipa");
    }

    private static void generatePieces(StructurePiecesBuilder piecesBuilder, PieceGenerator.Context<NipaConfig> context) {
        BlockPos pos = new BlockPos(context.chunkPos().getWorldPosition().getX(), 90, context.chunkPos().getWorldPosition().getZ());
        Random random = context.random();
        boolean floating = random.nextFloat() <= context.config().getFloatingProbability();

        piecesBuilder.addPiece(new NipaPieces.Piece(context.structureManager(), floating ? NipaPieces.NIPA_FLOATING : NipaPieces.NIPA, pos, Rotation.getRandom(random), floating));
    }

    @Nonnull
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

//    @Override
//    public StructureFeatureConfiguration getFeatureConfiguration() {
//        return ModStructures.SeparationSettings.NIPA;
//    }

//    public static class Start extends StructureStart<NipaConfig> {
//
//        public Start(StructureFeature<NipaConfig> p_163595_, ChunkPos p_163596_, int p_163597_, long p_163598_) {
//            super(p_163595_, p_163596_, p_163597_, p_163598_);
//        }
//
//        @Override
//        public void generatePieces(RegistryAccess registryAccess, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, NipaConfig config, LevelHeightAccessor level) {
//            BlockPos pos = new BlockPos(chunkPos.getWorldPosition().getX(), 90, chunkPos.getWorldPosition().getZ());
//
//            boolean floating = random.nextFloat() <= config.getFloatingProbability();
//            this.pieces.add(new NipaPieces.Piece(structureManager, floating ? NipaPieces.NIPA_FLOATING : NipaPieces.NIPA, pos, Rotation.getRandom(this.random), floating));
//
//            this.getBoundingBox();
//        }
//    }
}
