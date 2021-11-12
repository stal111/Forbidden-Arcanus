package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import java.util.Random;

public class EdelwoodEntityBucketItem extends Item {

    private final EntityType<?> entityType;

    public EdelwoodEntityBucketItem(EntityType<?> entityType, Properties properties) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.PERMAFROST.get())) {
            if (entityType == EntityType.MAGMA_CUBE) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
                    if (!player.getAbilities().instabuild) {
                        if (new Random().nextDouble() < 0.008) {
                            this.placeEntity((ServerLevel) world, stack, pos);
                            if (!world.isClientSide()) {
                                player.getInventory().removeItemNoUpdate(slot);
                                player.getInventory().add(slot, new ItemStack(Items.CHARCOAL));
                                player.setSecondsOnFire(3);
                            }
                        }
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, isSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        HitResult raytraceresult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, world, stack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(stack);
        } else if (raytraceresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        } else {
            BlockHitResult blockraytraceresult = (BlockHitResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getBlockPos();
            Direction direction = blockraytraceresult.getDirection();
            BlockPos pos = blockpos.relative(direction);
            if (!world.isClientSide()) {
                this.placeEntity((ServerLevel) world, stack, pos);
            }
            return InteractionResultHolder.success(emptyBucket(stack, player));
        }
    }

    protected ItemStack emptyBucket(ItemStack stack, Player player) {
        if (!player.getAbilities().instabuild) {
            return ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_BUCKET.get()));
        }
        return stack;
    }

    private void placeEntity(ServerLevel world, ItemStack stack, BlockPos pos) {
        this.entityType.spawn(world, stack, null, pos, MobSpawnType.BUCKET, true, false);
    }
}
