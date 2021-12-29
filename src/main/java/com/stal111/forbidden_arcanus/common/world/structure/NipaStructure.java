package com.stal111.forbidden_arcanus.common.world.structure;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.core.init.world.ModStructures;
import com.stal111.forbidden_arcanus.common.world.structure.config.NipaConfig;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.valhelsia.valhelsia_core.common.world.SimpleValhelsiaStructure;

import javax.annotation.Nonnull;

/**
 * Nipa Structure
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.structure.NipaStructure
 *
 * @author stal111
 * @version 1.0
 * @since 2021-04-07
 */
public class NipaStructure extends SimpleValhelsiaStructure<NipaConfig> {

    //TODO

    public NipaStructure(Codec<NipaConfig> codec) {
        super(codec, p_197348_ -> {
            return null;
        }, "nipa");
    }

    @Nonnull
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

//    @Nonnull
//    @Override
//    public StructureStartFactory<NipaConfig> getStartFactory() {
//        return NipaStructure.Start::new;
//    }

    @Override
    public StructureFeatureConfiguration getFeatureConfiguration() {
        return ModStructures.SeparationSettings.NIPA;
    }

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
