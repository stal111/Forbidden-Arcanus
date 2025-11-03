package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EctoplasmExtractorItem extends Item {

    private static final int USE_DURATION = 35;

    public EctoplasmExtractorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player == null) {
            return super.useOn(context);
        }

        HitResult hitResult = this.calculateHitResult(player);

        if (hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult blockHitResult && this.isValidBlock(context.getLevel(), blockHitResult.getBlockPos(), player)) {
            player.startUsingItem(context.getHand());
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        HitResult hitResult = this.calculateHitResult(livingEntity);

        if (hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult blockHitResult && this.isValidBlock(level, blockHitResult.getBlockPos(), livingEntity)) {
            int i = this.getUseDuration(stack, livingEntity) - remainingUseDuration + 1;

            if (i % 10 == 5) {
                level.levelEvent(livingEntity, 2001, blockHitResult.getBlockPos(), Block.getId(level.getBlockState(blockHitResult.getBlockPos())));
            }
        } else {
            livingEntity.stopUsingItem();
        }

        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        HitResult hitResult = this.calculateHitResult(livingEntity);

        if (hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult blockHitResult && this.isValidBlock(level, blockHitResult.getBlockPos(), livingEntity)) {
            BlockPos pos = blockHitResult.getBlockPos();

            for (int i = 0; i < 4; i++) {
                level.addParticle(ModParticles.SOUL.get(), pos.getX() + level.getRandom().nextFloat(), pos.getY() + 1, pos.getZ() + level.getRandom().nextFloat(), 1, 1, 1);
            }

            EssenceHelper.addEssence(stack, 1);

            if (!level.isClientSide() && livingEntity instanceof Player player) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            level.setBlockAndUpdate(pos, ModBlocks.SOULLESS_SAND.get().defaultBlockState());
        }

        return super.finishUsingItem(stack, level, livingEntity);
    }

    private HitResult calculateHitResult(LivingEntity livingEntity) {
        return ProjectileUtil.getHitResultOnViewVector(livingEntity, EntitySelector.CAN_BE_PICKED, livingEntity.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE));
    }

    private boolean isValidBlock(Level level, BlockPos pos, LivingEntity livingEntity) {
        return level.mayInteract(livingEntity, pos) && level.getBlockState(pos).is(Blocks.SOUL_SAND);
    }
}
