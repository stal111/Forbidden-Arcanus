package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModParticles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SoulExtractorItem extends Item {

    public SoulExtractorItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        HitResult rayTraceResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.SOURCE_ONLY);

        //if (!player.isSneaking()) {
            if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockRayTraceResult = (BlockHitResult) rayTraceResult;
                BlockPos pos = blockRayTraceResult.getBlockPos();
                if (world.mayInteract(player, pos)) {
                    Block block = world.getBlockState(pos).getBlock();
                    if (block == Blocks.SOUL_SAND) {
                        player.startUsingItem(hand);
                        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
                    }
                }
            }
        //}
        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            HitResult rayTraceResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.SOURCE_ONLY);
         //   if (!player.isSneaking()) {
                if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockRayTraceResult = (BlockHitResult) rayTraceResult;
                    BlockPos pos = blockRayTraceResult.getBlockPos();
                    if (world.mayInteract(player, pos)) {
                        if (world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND) {
                            stack.hurtAndBreak(1, player, (playerEntity) -> {
                                playerEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                            });
                            for (int i = 0; i < 4; i++) {
                                world.addParticle((ParticleOptions) ModParticles.SOUL.get(), pos.getX() + player.getRandom().nextFloat(), pos.getY() + 1, pos.getZ() + player.getRandom().nextFloat(), 1, 1, 1);
                            }
                            player.awardStat(Stats.ITEM_USED.get(this));
                            if (!world.isClientSide) {
                                world.setBlockAndUpdate(pos, ModBlocks.SOULLESS_SAND.get().defaultBlockState());
                                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1F, pos.getZ(), new ItemStack(ModItems.SOUL.get())));
                            }
                        }
                    }
               // }
            }
        }
        return stack;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity livingEntity, int count) {
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            Level world = player.getCommandSenderWorld();
            HitResult rayTraceResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.SOURCE_ONLY);

          //  if (!player.isSneaking()) {
                if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockRayTraceResult = (BlockHitResult) rayTraceResult;
                    BlockPos pos = blockRayTraceResult.getBlockPos();
                    if (world.mayInteract(player, pos)) {
                        Block block = world.getBlockState(pos).getBlock();
                        if (block == Blocks.SOUL_SAND) {
                            if (new Random().nextInt(6) == 1) {
                                world.levelEvent(player, 2001, pos, Block.getId(world.getBlockState(pos)));
                            }
                        }
                    }
                //}
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 35;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player player) {
        return !player.getAbilities().instabuild;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {
        list.add(new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".soul_extractor").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, world, list, flag);
    }
}
