package com.stal111.forbidden_arcanus.common.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.init.world.ModStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.valhelsia.valhelsia_core.common.world.SimpleValhelsiaStructure;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Nipa Structure <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.structure.NipaStructure
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-04-07
 */
public class NipaStructure extends SimpleValhelsiaStructure {

    public static final Codec<NipaStructure> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(settingsCodec(instance), Codec.BOOL.fieldOf("floating_probability").forGetter((structure) -> {
            return structure.floating;
        })).apply(instance, NipaStructure::new);
    });
    private final boolean floating;

    public NipaStructure(Structure.StructureSettings settings, boolean floating) {
        super(settings, "nipa");
        this.floating = floating;
        // super(codec, PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), NipaStructure::generatePieces), "nipa");
    }

    private void generatePieces(StructurePiecesBuilder piecesBuilder, Structure.GenerationContext context) {
        BlockPos pos = new BlockPos(context.chunkPos().getWorldPosition().getX(), 90, context.chunkPos().getWorldPosition().getZ());
        RandomSource random = context.random();

        piecesBuilder.addPiece(new NipaPieces.Piece(context.structureTemplateManager(), this.floating ? NipaPieces.NIPA_FLOATING : NipaPieces.NIPA, pos, Rotation.getRandom(random), floating));
    }

    @Nonnull
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Nonnull
    @Override
    public Optional<GenerationStub> findGenerationPoint(@Nonnull GenerationContext generationContext) {
        return onTopOfChunkCenter(generationContext, Heightmap.Types.WORLD_SURFACE_WG, (piecesBuilder) -> {
            this.generatePieces(piecesBuilder, generationContext);
        });
    }

    @Nonnull
    @Override
    public StructureType<?> type() {
        return ModStructureTypes.NIPA.get();
    }
}
