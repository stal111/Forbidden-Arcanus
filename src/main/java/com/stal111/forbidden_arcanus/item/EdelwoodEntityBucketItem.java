package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.valhelsia.valhelsia_core.util.ItemStackUtils;

import java.util.Random;

public class EdelwoodEntityBucketItem extends Item {

    private final EntityType<?> entityType;

    public EdelwoodEntityBucketItem(EntityType<?> entityType, Properties properties) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (!EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.PERMAFROST.get())) {
            if (entityType == EntityType.MAGMA_CUBE) {
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    BlockPos pos = new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ());
                    if (!player.abilities.isCreativeMode) {
                        if (new Random().nextDouble() < 0.008) {
                            this.placeEntity((ServerWorld) world, stack, pos);
                            if (!world.isRemote()) {
                                player.inventory.removeStackFromSlot(slot);
                                player.inventory.add(slot, new ItemStack(Items.CHARCOAL));
                                player.setFire(3);
                            }
                        }
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, isSelected);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        RayTraceResult raytraceresult = rayTrace(world, player, RayTraceContext.FluidMode.NONE);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, world, stack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(stack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.resultPass(stack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            Direction direction = blockraytraceresult.getFace();
            BlockPos pos = blockpos.offset(direction);
            if (!world.isRemote()) {
                this.placeEntity((ServerWorld) world, stack, pos);
            }
            return ActionResult.resultSuccess(emptyBucket(stack, player));
        }
    }

    protected ItemStack emptyBucket(ItemStack stack, PlayerEntity player) {
        if (!player.abilities.isCreativeMode) {
            return ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_BUCKET.get()));
        }
        return stack;
    }

    private void placeEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        this.entityType.spawn(world, stack, null, pos, SpawnReason.BUCKET, true, false);
    }
}
