package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class EdelwoodEntityBucketItem extends Item {

    private final EntityType<?> entityType;

    public EdelwoodEntityBucketItem(EntityType<?> entityType, Properties properties) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (!EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.PERMAFROST.getEnchantment())) {
            if (entityType == EntityType.MAGMA_CUBE) {
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    BlockPos pos = player.getPosition();
                    if (!player.abilities.isCreativeMode) {
                        if (new Random().nextDouble() < 0.008) {
                            this.placeEntity(world, stack, pos);
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
            return ActionResult.func_226250_c_(stack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.func_226250_c_(stack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            Direction direction = blockraytraceresult.getFace();
            BlockPos pos = blockpos.offset(direction);
            if (!world.isRemote()) {
                this.placeEntity(world, stack, pos);
            }
            return ActionResult.func_226248_a_(emptyBucket(stack, player));
        }
    }

    protected ItemStack emptyBucket(ItemStack stack, PlayerEntity player) {
        if (!player.abilities.isCreativeMode) {
            return ItemStackUtils.transferEnchantments(stack, ModItems.EDELWOOD_BUCKET.getStack());
        }
        return stack;
    }

    private void placeEntity(World world, ItemStack stack, BlockPos pos) {
        this.entityType.spawn(world, stack, null, pos, SpawnReason.BUCKET, true, false);
    }
}
