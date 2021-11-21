package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;

/**
 * Soulless Sand Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.SoullessSandBlock
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-20
 */
public class SoullessSandBlock extends SoulSandBlock {

    public SoullessSandBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() == ModItems.SOUL.get() && level.mayInteract(player, pos)) {
            ItemStackUtils.shrinkStack(player, stack);

            level.setBlockAndUpdate(pos, Blocks.SOUL_SAND.defaultBlockState());
            level.levelEvent(player, 2001, pos, Block.getId(state));
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
