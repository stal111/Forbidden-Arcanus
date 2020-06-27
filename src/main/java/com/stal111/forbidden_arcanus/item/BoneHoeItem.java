package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BoneHoeItem extends ModHoeItem {

    public BoneHoeItem(IItemTier tier, int something, float attackSpeedIn, Item.Properties builder) {
        super(tier, something, attackSpeedIn, builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockPos blockpos1 = blockpos.offset(context.getFace());
        PlayerEntity playerEntity = context.getPlayer();
        boolean flag = false;
        for (Block block : HoeItem.HOE_LOOKUP.keySet()) {
            if (world.getBlockState(blockpos).getBlock() == block) {
                flag = true;
            }
        }
        if (flag) {
            return super.onItemUse(context);
        } else {
            if (ModUtils.applyBonemeal(context.getItem(), (ServerWorld) world, blockpos, context.getPlayer())) {
                if (!world.isRemote) {
                    world.playEvent(2005, blockpos, 0);
                    if (playerEntity != null) {
                        context.getItem().damageItem(1, playerEntity, (p_220041_1_) -> {
                            p_220041_1_.sendBreakAnimation(context.getHand());
                        });
                    }
                }

                return ActionResultType.SUCCESS;
            } else {
                BlockState blockstate = world.getBlockState(blockpos);
                boolean flag1 = blockstate.isSolidSide(world, blockpos, context.getFace());
                if (flag1 && BoneMealItem.growSeagrass(context.getItem(), world, blockpos1, context.getFace())) {
                    if (!world.isRemote) {
                        world.playEvent(2005, blockpos1, 0);
                        if (playerEntity != null) {
                            context.getItem().damageItem(1, playerEntity, (p_220041_1_) -> {
                                p_220041_1_.sendBreakAnimation(context.getHand());
                            });
                        }
                    }

                    return ActionResultType.SUCCESS;
                } else {
                    return super.onItemUse(context);
                }
            }
        }
    }
}
