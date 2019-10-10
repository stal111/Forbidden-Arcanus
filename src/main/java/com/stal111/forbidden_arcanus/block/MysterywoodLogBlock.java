package com.stal111.forbidden_arcanus.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class MysterywoodLogBlock extends LogBlock {

    public MysterywoodLogBlock(Properties properties) {
        super(MaterialColor.ADOBE, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        double d0 = pos.getX() + random.nextFloat();
        double d2 = pos.getY() + random.nextFloat();
        double d3 = pos.getZ() + random.nextFloat();
        double d4 = (random.nextFloat() - 0.5) * 0.3;
        double d5 = (random.nextFloat() - 0.5) * 0.3;
        double d6 = (random.nextFloat() - 0.5) * 0.3;
        world.addParticle(ParticleTypes.END_ROD, d0, d2, d3, d4, d5, d6);
    }
}
