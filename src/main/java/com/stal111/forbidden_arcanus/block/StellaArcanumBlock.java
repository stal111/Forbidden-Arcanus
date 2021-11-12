package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.config.BlockConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class StellaArcanumBlock extends Block {

    public static boolean explode = false;
    public static Level world;

    public StellaArcanumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void destroy(LevelAccessor iWorld, BlockPos pos, BlockState state) {
        if (!iWorld.isClientSide()) {
            if (explode) {
                if (world != null) {
                    world.explode(null, pos.getX(), pos.getY(), pos.getZ(), BlockConfig.STELLA_ARCANUM_EXPLOSION_RADIUS.get(), BlockConfig.STELLA_ARCANUM_BLOCK_DAMAGE.get() ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
                }
                explode = false;
            }
        }
        super.destroy(iWorld, pos, state);
    }
}
