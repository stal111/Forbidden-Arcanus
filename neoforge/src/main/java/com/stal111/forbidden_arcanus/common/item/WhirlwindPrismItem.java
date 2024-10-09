package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-06-08
 */
public class WhirlwindPrismItem extends Item {

    public WhirlwindPrismItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();

        BlockPos pos = context.getClickedPos();

        if (!level.getBlockState(pos).canBeReplaced()) {
            pos = pos.relative(context.getClickedFace());
        }

        level.setBlockAndUpdate(pos, ModBlocks.WHIRLWIND.get().defaultBlockState());

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
