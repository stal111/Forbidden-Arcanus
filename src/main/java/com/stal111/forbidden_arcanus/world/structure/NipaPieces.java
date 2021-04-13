package com.stal111.forbidden_arcanus.world.structure;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.world.ModStructurePieces;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.*;

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

    public static final ResourceLocation NIPA = new ResourceLocation(ForbiddenArcanus.MOD_ID, "nipa");
    public static final ResourceLocation NIPA_FLOATING = new ResourceLocation(ForbiddenArcanus.MOD_ID, "nipa_floating");

    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation templateName;
        private final Rotation rotation;
        private final boolean floating;

        public Piece(TemplateManager templateManager, ResourceLocation templateName, BlockPos templatePosition, Rotation rotation, boolean floating) {
            super(ModStructurePieces.NIPA, 0);
            this.templateName = templateName;
            this.templatePosition = templatePosition;
            this.rotation = rotation;
            this.floating = floating;
            this.setup(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compound) {
            super(ModStructurePieces.NIPA, compound);
            this.templateName = new ResourceLocation(compound.getString("Template"));
            this.rotation = Rotation.valueOf(compound.getString("Rot"));
            this.floating = compound.getBoolean("Floating");
            this.setup(templateManager);
        }

        private void setup(TemplateManager templateManagerIn) {
            Template template = templateManagerIn.getTemplateDefaulted(this.templateName);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.templateName.toString());
            tagCompound.putString("Rot", this.rotation.name());
            tagCompound.putBoolean("Floating", this.floating);
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {

        }

        @Override
        public boolean func_230383_a_(ISeedReader seedReader, StructureManager structureManager, ChunkGenerator generator, Random random, MutableBoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            int i = seedReader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
            this.templatePosition = new BlockPos(this.templatePosition.getX(), floating ? i + 60 : i - 3, this.templatePosition.getZ());
            return super.func_230383_a_(seedReader, structureManager, generator, random, boundingBox, chunkPos, pos);
        }
    }
}
