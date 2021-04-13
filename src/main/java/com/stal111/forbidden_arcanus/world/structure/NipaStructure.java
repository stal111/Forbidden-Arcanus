package com.stal111.forbidden_arcanus.world.structure;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.world.structure.config.NipaConfig;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

/**
 * Nipa Structure
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.structure.NipaStructure
 *
 * @author stal111
 * @version 1.0
 * @since 2021-04-07
 */
public class NipaStructure extends Structure<NipaConfig> {

    public NipaStructure(Codec<NipaConfig> codec) {
        super(codec);
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public IStartFactory<NipaConfig> getStartFactory() {
        return NipaStructure.Start::new;
    }

    public static class Start extends StructureStart<NipaConfig> {

        public Start(Structure<NipaConfig> structure, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int p_i225876_5_, long p_i225876_6_) {
            super(structure, chunkX, chunkZ, boundingBox, p_i225876_5_, p_i225876_6_);
        }

        @Override
        public void func_230364_a_(DynamicRegistries registries, ChunkGenerator generator, TemplateManager manager, int chunkX, int chunkZ, Biome biome, NipaConfig config) {
            int x = (chunkX << 4) + 8;
            int z = (chunkZ << 4) + 8;
            BlockPos pos = new BlockPos(x, 90, z);

            boolean floating = rand.nextFloat() <= config.getFloatingProbability();
            this.components.add(new NipaPieces.Piece(manager, floating ? NipaPieces.NIPA_FLOATING : NipaPieces.NIPA, pos, Rotation.randomRotation(this.rand), floating));

            this.recalculateStructureSize();
        }
    }
}
