package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.IParticleData;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SoulExtractorItem extends Item {

    public SoulExtractorItem() {
        super(ModItems.properties().maxDamage(128));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        RayTraceResult rayTraceResult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);

        //if (!player.isSneaking()) {
            if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) rayTraceResult;
                BlockPos pos = blockRayTraceResult.getPos();
                if (world.isBlockModifiable(player, pos)) {
                    Block block = world.getBlockState(pos).getBlock();
                    if (block == Blocks.SOUL_SAND) {
                        player.setActiveHand(hand);
                        return new ActionResult<>(ActionResultType.SUCCESS, stack);
                    }
                }
            }
        //}
        return new ActionResult<>(ActionResultType.FAIL, stack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            RayTraceResult rayTraceResult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
         //   if (!player.isSneaking()) {
                if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
                    BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) rayTraceResult;
                    BlockPos pos = blockRayTraceResult.getPos();
                    if (world.isBlockModifiable(player, pos)) {
                        if (world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND) {
                            stack.damageItem(1, player, (playerEntity) -> {
                                playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                            });
                            for (int i = 0; i < 4; i++) {
                                world.addParticle((IParticleData) ModParticles.SOUL.get(), pos.getX() + random.nextFloat(), pos.getY() + 1, pos.getZ() + random.nextFloat(), 1, 1, 1);
                            }
                            player.addStat(Stats.ITEM_USED.get(this));
                            if (!world.isRemote) {
                                world.setBlockState(pos, ModBlocks.SOULLESS_SAND.getState());
                                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1F, pos.getZ(), new ItemStack(NewModItems.SOUL.get())));
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
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            World world = player.getEntityWorld();
            RayTraceResult rayTraceResult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);

          //  if (!player.isSneaking()) {
                if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
                    BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult) rayTraceResult;
                    BlockPos pos = blockRayTraceResult.getPos();
                    if (world.isBlockModifiable(player, pos)) {
                        Block block = world.getBlockState(pos).getBlock();
                        if (block == Blocks.SOUL_SAND) {
                            if (new Random().nextInt(6) == 1) {
                                world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
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
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        return !player.abilities.isCreativeMode;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        list.add(new TranslationTextComponent("tooltip." + Main.MOD_ID + ".soul_extractor").applyTextStyle(TextFormatting.GRAY));
        super.addInformation(stack, world, list, flag);
    }
}
