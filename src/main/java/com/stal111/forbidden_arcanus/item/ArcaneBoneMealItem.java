package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.valhelsia.valhelsia_core.util.ItemStackUtils;

import javax.annotation.Nonnull;

/**
 * Arcane Bone Meal Item <br>
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
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();

        if (state.getBlock() == Blocks.FARMLAND) {
            world.setBlockState(pos, ModBlocks.MAGICAL_FARMLAND.getBlock().getDefaultState().with(BlockStateProperties.MOISTURE_0_7, state.get(BlockStateProperties.MOISTURE_0_7)));
            world.playEvent(player, 2001, pos, Block.getStateId(state));

            ItemStackUtils.shrinkStack(player, context.getItem());

            return ActionResultType.func_233537_a_(world.isRemote);
        } else if (ArcaneBoneMealItem.applyBoneMeal(context.getItem(), world, pos, player)) {
            if (!world.isRemote) {
                world.playEvent(2005, pos, 0);
            }

            return ActionResultType.func_233537_a_(world.isRemote);
        } else {
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
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, world, pos, world.getBlockState(pos), stack);
        if (hook != 0) {
            return hook > 0;
        }

        if (canGrow(world, pos)) {
            grow(world, pos);

            stack.shrink(1);

            return true;
        }

        return false;
    }

    private static boolean canGrow(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof IGrowable) {
            return ((IGrowable) state.getBlock()).canGrow(world, pos, state, world.isRemote());
        }
        return false;
    }

    private static void grow(World world, BlockPos pos) {
        if (world.isRemote()) {
            return;
        }
        for (int i = 0; i < 1000; i++) {
            if (canGrow(world, pos) && !world.isRemote()) {
                ((IGrowable) world.getBlockState(pos).getBlock()).grow((ServerWorld) world, world.rand, pos, world.getBlockState(pos));
            } else {
                return;
            }
        }
    }
}
