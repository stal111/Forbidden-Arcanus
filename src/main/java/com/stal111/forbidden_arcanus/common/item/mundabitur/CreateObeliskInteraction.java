package com.stal111.forbidden_arcanus.common.item.mundabitur;

import com.stal111.forbidden_arcanus.common.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

import java.util.function.Predicate;

/**
 * @author stal111
 * @since 05.11.2023
 */
public class CreateObeliskInteraction extends TransformPatternInteraction {

    public CreateObeliskInteraction(Predicate<BlockState> predicate, BlockPattern pattern) {
        super(predicate, pattern);
    }

    @Override
    public void interact(TransformPatternContext context) {
        BlockPos pos = context.pos();
        Level level = context.level();

        while (level.getBlockState(pos).is(ModBlocks.ARCANE_CRYSTAL_BLOCK.get())) {
            pos = pos.below();
        }

        BlockState obelisk = ModBlocks.ARCANE_CRYSTAL_OBELISK.get().defaultBlockState();

        this.placeBlock(level, pos, obelisk.setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.LOWER).setValue(ModBlockStateProperties.RITUAL, ArcaneCrystalObeliskBlock.isArcaneChiseledPolishedDarkstoneBelow(level, pos)));
        this.placeBlock(level, pos.above(), obelisk.setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.MIDDLE));
        this.placeBlock(level, pos.above(2), obelisk.setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.UPPER));
    }
}
