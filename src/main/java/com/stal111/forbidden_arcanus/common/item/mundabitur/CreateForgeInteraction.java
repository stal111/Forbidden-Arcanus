package com.stal111.forbidden_arcanus.common.item.mundabitur;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

import java.util.function.Predicate;

/**
 * @author stal111
 * @since 05.11.2023
 */
public class CreateForgeInteraction extends TransformPatternInteraction {

    public CreateForgeInteraction(Predicate<BlockState> predicate, BlockPattern pattern) {
        super(predicate, pattern);
    }

    @Override
    public void interact(TransformPatternContext context) {
        Level level = context.level();
        BlockPos pos = context.pos();

        this.placeBlock(level, pos, ModBlocks.HEPHAESTUS_FORGE.get().defaultBlockState().setValue(ModBlockStateProperties.ACTIVATED, true));

        CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), level);
        entity.setPos(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
        entity.setVisualOnly(true);

        level.addFreshEntity(entity);
    }
}
