package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;

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

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (stack.is(ModItems.SOUL.get()) && level.mayInteract(player, pos)) {
            ItemStackUtils.shrinkStack(player, stack);

            level.setBlockAndUpdate(pos, Blocks.SOUL_SAND.defaultBlockState());
            level.levelEvent(player, 2001, pos, Block.getId(state));
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useItemOn(stack, state, level, pos, player, hand, result);
    }
}
