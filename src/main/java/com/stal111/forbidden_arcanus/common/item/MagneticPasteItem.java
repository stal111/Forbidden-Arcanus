package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2022-12-30
 */
public class MagneticPasteItem extends Item {

    public MagneticPasteItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack stack = context.getItemInHand();

        if (state.is(ModBlocks.DARKSTONE_PEDESTAL.get())) {
            Player player = context.getPlayer();
            state = ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get().defaultBlockState();

            stack.shrink(1);
            level.setBlock(pos, state, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
            //level.levelEvent(player, 3003, pos, 0);

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useOn(context);
    }
}
