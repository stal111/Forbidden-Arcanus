package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.config.BlockConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * Block Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.StellaArcanumBlock
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 */
public class StellaArcanumBlock extends Block {

    public static boolean explode = false;
    public static Level world;

    public StellaArcanumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void destroy(LevelAccessor level, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        if (!level.isClientSide()) {
            if (explode && world != null) {
                world.explode(null, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, BlockConfig.STELLA_ARCANUM_EXPLOSION_RADIUS.get(), BlockConfig.STELLA_ARCANUM_BLOCK_DAMAGE.get() ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
            }
            explode = false;
        }
        super.destroy(level, pos, state);
    }
}
