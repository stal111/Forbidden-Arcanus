package com.stal111.forbidden_arcanus.common.world.structure;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.world.ModStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Random;

/**
 * Nipa Pieces
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.structure.NipaPieces
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-11
 */
public class NipaPieces {

    //TODO

    public static final ResourceLocation NIPA = new ResourceLocation(ForbiddenArcanus.MOD_ID, "nipa");
    public static final ResourceLocation NIPA_FLOATING = new ResourceLocation(ForbiddenArcanus.MOD_ID, "nipa_floating");

    public static class Piece extends TemplateStructurePiece {

        public Piece(StructureManager templateManager, ResourceLocation templateName, BlockPos templatePosition, Rotation rotation, boolean floating) {
            super(ModStructurePieces.NIPA, 0, templateManager, templateName, templatePosition.toString(), makeSettings(rotation, templateName), templatePosition);
            this.templatePosition = templatePosition;

        }

        public Piece(ServerLevel p_162441_, CompoundTag p_162442_) {
            super(ModStructurePieces.NIPA, p_162442_, p_162441_, (p_162451_) -> {
                return makeSettings(Rotation.valueOf(p_162442_.getString("Rot")), p_162451_);
            });
        }

        private static StructurePlaceSettings makeSettings(Rotation p_162447_, ResourceLocation p_162448_) {
            return  (new StructurePlaceSettings()).setRotation(p_162447_).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }


        @Override
        protected void addAdditionalSaveData(ServerLevel level, CompoundTag tagCompound) {
            super.addAdditionalSaveData(level, tagCompound);
            tagCompound.putString("Template", this.templateName.toString());

        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, Random rand, BoundingBox sbb) {

        }

        @Override
        public boolean postProcess(WorldGenLevel seedReader, StructureFeatureManager structureManager, ChunkGenerator generator, Random random, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            int i = seedReader.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
            //this.templatePosition = new BlockPos(this.templatePosition.getX(), floating ? i + 60 : i - 3, this.templatePosition.getZ());
            this.templatePosition = new BlockPos(this.templatePosition.getX(), i + 60, this.templatePosition.getZ());

            return super.postProcess(seedReader, structureManager, generator, random, boundingBox, chunkPos, pos);
        }
    }
}
