package com.stal111.forbidden_arcanus.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MysterywoodLogBlock extends RotatedPillarBlock {

    public MysterywoodLogBlock(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        double d0 = pos.getX() + random.nextFloat();
        double d2 = pos.getY() + random.nextFloat();
        double d3 = pos.getZ() + random.nextFloat();
        double d4 = (random.nextFloat() - 0.5) * 0.3;
        double d5 = (random.nextFloat() - 0.5) * 0.3;
        double d6 = (random.nextFloat() - 0.5) * 0.3;
        world.addParticle(ParticleTypes.END_ROD, d0, d2, d3, d4, d5, d6);
    }
}
