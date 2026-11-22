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
import org.jspecify.annotations.Nullable;

public class EctoplasmExtractorItem extends Item {

    public static final int USE_DURATION = 35;
    private static final int ECTOPLASM_AMOUNT = 21;

    public EctoplasmExtractorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player == null || !this.canExtract(context.getItemInHand())) {
            return InteractionResult.PASS;
        }

        if (this.isValidBlock(context.getLevel(), context.getClickedPos(), player)) {
            player.startUsingItem(context.getHand());

            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        BlockPos pos = this.getTargetedValidBlock(level, livingEntity);

        if (pos == null || !this.canExtract(stack)) {
            livingEntity.stopUsingItem();

            return;
        }

        int elapsedTicks = USE_DURATION - remainingUseDuration + 1;

        if (elapsedTicks % 10 == 5) {
            level.levelEvent(livingEntity, 2001, pos, Block.getId(Blocks.SOUL_SAND.defaultBlockState()));
        }

        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        BlockPos pos = this.getTargetedValidBlock(level, livingEntity);

        if (pos != null && this.canExtract(stack)) {
            for (int i = 0; i < 4; i++) {
                level.addParticle(ModParticles.SOUL.get(), pos.getX() + level.getRandom().nextFloat(), pos.getY() + 1, pos.getZ() + level.getRandom().nextFloat(), 1, 1, 1);
            }

            EssenceHelper.addEssence(stack, ECTOPLASM_AMOUNT);

            if (!level.isClientSide()) {
                level.setBlockAndUpdate(pos, ModBlocks.SOULLESS_SAND.get().defaultBlockState());

                if (livingEntity instanceof Player player) {
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }

        return super.finishUsingItem(stack, level, livingEntity);
    }

    private boolean canExtract(ItemStack stack) {
        return EssenceHelper.getEssenceStorage(stack)
                .map(storage -> !storage.isFull())
                .orElse(false);
    }

    private @Nullable BlockPos getTargetedValidBlock(Level level, LivingEntity livingEntity) {
        HitResult hitResult = ProjectileUtil.getHitResultOnViewVector(livingEntity, EntitySelector.CAN_BE_PICKED, livingEntity.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE));

        if (hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();

            if (this.isValidBlock(level, pos, livingEntity)) {
                return pos;
            }
        }

        return null;
    }

    private boolean isValidBlock(Level level, BlockPos pos, LivingEntity livingEntity) {
        return level.mayInteract(livingEntity, pos) && level.getBlockState(pos).is(Blocks.SOUL_SAND);
    }
}
