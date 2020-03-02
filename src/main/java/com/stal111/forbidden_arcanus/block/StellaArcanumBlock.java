package com.stal111.forbidden_arcanus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;

public class StellaArcanumBlock extends Block {

    public StellaArcanumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlayerDestroy(IWorld world, BlockPos pos, BlockState state) {
        if (!world.isRemote()) {
            world.getWorld().createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3, Explosion.Mode.BREAK);
        }
        super.onPlayerDestroy(world, pos, state);
    }
}
