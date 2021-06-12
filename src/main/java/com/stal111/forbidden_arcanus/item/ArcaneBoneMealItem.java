package com.stal111.forbidden_arcanus.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;

/**
 * Arcane Bone Meal Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.ArcaneBoneMealItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-12
 */
public class ArcaneBoneMealItem extends BoneMealItem {

    public ArcaneBoneMealItem(Properties builder) {
        super(builder);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockPos offsetPos = pos.offset(context.getFace());

        if (ArcaneBoneMealItem.applyBoneMeal(context.getItem(), world, pos, context.getPlayer())) {
            if (!world.isRemote) {
                world.playEvent(2005, pos, 0);
            }

            return ActionResultType.func_233537_a_(world.isRemote);
        } else {
            BlockState state = world.getBlockState(pos);
            boolean flag = state.isSolidSide(world, pos, context.getFace());

            if (flag && growSeagrass(context.getItem(), world, offsetPos, context.getFace())) {
                if (!world.isRemote) {
                    world.playEvent(2005, offsetPos, 0);
                }

                return ActionResultType.func_233537_a_(world.isRemote);
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    public static boolean applyBoneMeal(ItemStack stack, World world, BlockPos pos, PlayerEntity player) {
        BlockState state = world.getBlockState(pos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, world, pos, state, stack);
        if (hook != 0) {
            return hook > 0;
        }

        if (!isGrowable(state)) {
            return false;
        }

        IGrowable growable = (IGrowable) state.getBlock();

        if (!growable.canGrow(world, pos, state, world.isRemote)) {
            return false;
        }

        if (world instanceof ServerWorld) {
            while (isGrowable(state) && growable.canGrow(world, pos, state, world.isRemote)) {
                growable.grow((ServerWorld) world, world.rand, pos, state);
                state = world.getBlockState(pos);
            }
        }

        stack.shrink(1);

        return true;
    }

    private static boolean isGrowable(BlockState state) {
        return state.getBlock() instanceof IGrowable;
    }
}
