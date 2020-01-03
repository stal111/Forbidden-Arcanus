package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BoneShovelItem extends ShovelItem {

    public BoneShovelItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder.addToolType(net.minecraftforge.common.ToolType.SHOVEL, tier.getHarvestLevel()));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockPos blockpos1 = blockpos.offset(context.getFace());
        PlayerEntity playerEntity = context.getPlayer();
        if (world.getBlockState(blockpos).getBlock() == Blocks.GRASS_BLOCK) {
            return super.onItemUse(context);
        } else {
            if (ModUtils.applyBonemeal(context.getItem(), (ServerWorld) world, blockpos, playerEntity)) {
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
                boolean flag = blockstate.func_224755_d(world, blockpos, context.getFace());
                if (flag && ModUtils.growSeagrass(context.getItem(), (ServerWorld) world, blockpos1, context.getFace())) {
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
                    return ActionResultType.PASS;
                }
            }
        }
    }
}
