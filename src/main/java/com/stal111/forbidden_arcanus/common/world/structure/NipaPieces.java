package com.stal111.forbidden_arcanus.common.world.structure;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.world.ModStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import javax.annotation.Nonnull;

/**
 * Nipa Pieces <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.structure.NipaPieces
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-04-11
 */
public class NipaPieces {

    public static final ResourceLocation NIPA = new ResourceLocation(ForbiddenArcanus.MOD_ID, "nipa");
    public static final ResourceLocation NIPA_FLOATING = new ResourceLocation(ForbiddenArcanus.MOD_ID, "nipa_floating");

    public static class Piece extends TemplateStructurePiece {

        private final boolean floating;

        public Piece(StructureTemplateManager structureManager, ResourceLocation templateName, BlockPos templatePosition, Rotation rotation, boolean floating) {
            super(ModStructurePieces.NIPA.get(), 0, structureManager, templateName, templateName.toString(), makeSettings(rotation), templatePosition);
            this.floating = floating;
        }

        public Piece(StructurePieceSerializationContext context, CompoundTag tag) {
            super(ModStructurePieces.NIPA.get(), tag, context.structureTemplateManager(), (resourceLocation) -> {
                return makeSettings(Rotation.valueOf(tag.getString("Rot")));
            });
            this.floating = tag.getBoolean("Floating");
        }


        private static StructurePlaceSettings makeSettings(Rotation rotation) {
            return new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        @Override
        protected void addAdditionalSaveData(@Nonnull StructurePieceSerializationContext context, @Nonnull CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putString("Rot", this.placeSettings.getRotation().name());
            tag.putBoolean("Floating", this.floating);
        }

        @Override
        protected void handleDataMarker(@Nonnull String marker, @Nonnull BlockPos pos, @Nonnull ServerLevelAccessor level, @Nonnull RandomSource random, @Nonnull BoundingBox box) {
        }

        @Override
        public void postProcess(WorldGenLevel level, @Nonnull StructureManager structureManager, @Nonnull ChunkGenerator generator, @Nonnull RandomSource random, @Nonnull BoundingBox boundingBox, @Nonnull ChunkPos chunkPos, BlockPos pos) {
            int i = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
            this.templatePosition = new BlockPos(this.templatePosition.getX(), this.floating ? i + 60 : i - 3, this.templatePosition.getZ());

            super.postProcess(level, structureManager, generator, random, boundingBox, chunkPos, pos);
        }
    }
}
