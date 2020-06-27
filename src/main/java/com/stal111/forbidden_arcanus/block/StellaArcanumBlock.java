package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.config.BlockConfig;
import com.stal111.forbidden_arcanus.config.ItemConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;

public class StellaArcanumBlock extends Block {

    public static boolean explode = false;

    public StellaArcanumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlayerDestroy(IWorld world, BlockPos pos, BlockState state) {
        if (!world.isRemote()) {
            if (explode) {
                world.getWorld().createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), BlockConfig.STELLA_ARCANUM_EXPLOSION_RADIUS.get(), BlockConfig.STELLA_ARCANUM_BLOCK_DAMAGE.get() ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
                explode = false;
            }
        }
        super.onPlayerDestroy(world, pos, state);
    }
}
