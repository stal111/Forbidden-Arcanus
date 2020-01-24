package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class EdelwoodBucketItem extends Item implements ICapacityBucket {

    private final Fluid containedBlock;
    private int capacity;

    public EdelwoodBucketItem(Fluid containedFluidIn, int capacity, Item.Properties builder) {
        super(builder);
        this.containedBlock = containedFluidIn;
        this.fluidSupplier = containedFluidIn.delegate;
        this.capacity = capacity;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (!EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.PERMAFROST.getEnchantment())) {
            if (FluidTags.LAVA.contains(containedBlock)) {
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    BlockPos pos = player.getPosition();
                    if (!player.abilities.isCreativeMode) {
                        if (new Random().nextDouble() < 0.005) {
                            if (!world.isRemote()) {
                                player.inventory.removeStackFromSlot(slot);
                                player.inventory.add(slot, Items.CHARCOAL.getDefaultInstance());
                                world.setBlockState(pos, containedBlock.getDefaultState().getBlockState());
                            }
                        }
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, isSelected);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, this.containedBlock == Fluids.EMPTY || (ICapacityBucket.getFullness(stack) != this.getCapacity() && !playerIn.abilities.isCreativeMode) ? RayTraceContext.FluidMode.SOURCE_ONLY : RayTraceContext.FluidMode.NONE);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, stack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.func_226250_c_(stack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.func_226250_c_(stack);
        } else {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            Direction direction = blockraytraceresult.getFace();
            BlockPos blockpos1 = blockpos.offset(direction);
            if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos1, direction, stack)) {
                BlockState blockstate1 = worldIn.getBlockState(blockpos);
                if (this.containedBlock == Fluids.EMPTY || this.containedBlock == blockstate1.getFluidState().getFluid()) {
                    if (blockstate1.getBlock() instanceof IBucketPickupHandler) {
                        Fluid fluid = ((IBucketPickupHandler)blockstate1.getBlock()).pickupFluid(worldIn, blockpos, blockstate1);
                        if (fluid != Fluids.EMPTY) {
                            if (stack.getItem() instanceof EdelwoodFishBucketItem) { ;
                                if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos, blockraytraceresult)) {
                                    this.onLiquidPlaced(worldIn, stack, blockpos);
                                    if (playerIn instanceof ServerPlayerEntity) {
                                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerIn, blockpos, stack);
                                    }
                                    playerIn.addStat(Stats.ITEM_USED.get(this));
                                    return ActionResult.func_226248_a_(this.emptyBucket(stack, playerIn));
                                } else {
                                    return ActionResult.func_226251_d_(stack);
                                }
                            } else {
                                playerIn.addStat(Stats.ITEM_USED.get(this));

                                SoundEvent soundevent = this.containedBlock.getAttributes().getEmptySound();
                                if(soundevent == null) soundevent = fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL;
                                playerIn.playSound(soundevent, 1.0F, 1.0F);
                                ItemStack itemstack1 = fluid == Fluids.WATER ? this.fillBucket(stack, playerIn, ModItems.EDELWOOD_WATER_BUCKET.getItem()) : this.fillBucket(stack, playerIn, ModItems.EDELWOOD_LAVA_BUCKET.getItem());
                                if (!worldIn.isRemote) {
                                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)playerIn, new ItemStack(fluid.getFilledBucket()));
                                }
                                return ActionResult.func_226248_a_(itemstack1);
                            }
                        }
                    }
                    return ActionResult.func_226251_d_(stack);
                } else {
                    BlockPos blockpos2 = blockstate1.getBlock() instanceof ILiquidContainer && this.containedBlock == Fluids.WATER ? blockpos : blockpos1;
                    if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos2, blockraytraceresult)) {
                        this.onLiquidPlaced(worldIn, stack, blockpos2);
                        if (playerIn instanceof ServerPlayerEntity) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerIn, blockpos2, stack);
                        }
                        playerIn.addStat(Stats.ITEM_USED.get(this));
                        return ActionResult.func_226248_a_(this.emptyBucket(stack, playerIn));
                    } else {
                        return ActionResult.func_226251_d_(stack);
                    }
                }
            } else {
                return ActionResult.func_226251_d_(stack);
            }
        }
    }

    protected ItemStack emptyBucket(ItemStack stack, PlayerEntity player) {
        if (!player.abilities.isCreativeMode) {
            int fullness = ICapacityBucket.getFullness(stack);
            if ((fullness - 1) > 0) {
                return ICapacityBucket.setFullness(stack, fullness - 1);
            }
            return ItemStackUtils.transferEnchantments(stack, ModItems.EDELWOOD_BUCKET.getStack());
        }
        return stack;
    }

    public void onLiquidPlaced(World world, ItemStack stack, BlockPos pos) {
    }

    private ItemStack fillBucket(ItemStack emptyBucket, PlayerEntity player, Item fullBucket) {
        if (player.abilities.isCreativeMode) {
            return emptyBucket;
        } else {
            if (emptyBucket.getItem() == fullBucket) {
                return ICapacityBucket.setFullness(emptyBucket, ICapacityBucket.getFullness(emptyBucket) + 1);
            } else {
                ItemStack stack = ItemStackUtils.transferEnchantments(emptyBucket, fullBucket.getDefaultInstance());
                emptyBucket.shrink(1);
                if (emptyBucket.isEmpty()) {
                    return stack;
                } else {
                    if (!player.inventory.addItemStackToInventory(stack)) {
                        player.dropItem(stack, false);
                    }
                    return emptyBucket;
                }
            }
        }
    }

    public boolean tryPlaceContainedLiquid(@Nullable PlayerEntity player, World worldIn, BlockPos posIn, @Nullable BlockRayTraceResult p_180616_4_) {
        if (!(this.containedBlock instanceof FlowingFluid)) {
            return false;
        } else {
            BlockState blockstate = worldIn.getBlockState(posIn);
            Material material = blockstate.getMaterial();
            boolean flag = blockstate.isReplaceable(this.containedBlock);
            if (blockstate.isAir() || flag || blockstate.getBlock() instanceof ILiquidContainer && ((ILiquidContainer)blockstate.getBlock()).canContainFluid(worldIn, posIn, blockstate, this.containedBlock)) {
                if (worldIn.dimension.doesWaterVaporize() && this.containedBlock.isIn(FluidTags.WATER)) {
                    int i = posIn.getX();
                    int j = posIn.getY();
                    int k = posIn.getZ();
                    worldIn.playSound(player, posIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

                    for(int l = 0; l < 8; ++l) {
                        worldIn.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                    }
                } else if (blockstate.getBlock() instanceof ILiquidContainer && this.containedBlock == Fluids.WATER) {
                    if (((ILiquidContainer)blockstate.getBlock()).receiveFluid(worldIn, posIn, blockstate, ((FlowingFluid)this.containedBlock).getStillFluidState(false))) {
                        this.playEmptySound(player, worldIn, posIn);
                    }
                } else {
                    if (!worldIn.isRemote && flag && !material.isLiquid()) {
                        worldIn.destroyBlock(posIn, true);
                    }

                    this.playEmptySound(player, worldIn, posIn);
                    worldIn.setBlockState(posIn, this.containedBlock.getDefaultState().getBlockState(), 11);
                }

                return true;
            } else {
                return p_180616_4_ != null && this.tryPlaceContainedLiquid(player, worldIn, p_180616_4_.getPos().offset(p_180616_4_.getFace()), null);
            }
        }
    }

    protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
        SoundEvent soundevent = this.containedBlock.getAttributes().getEmptySound();
        if(soundevent == null) soundevent = this.containedBlock.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
        worldIn.playSound(player, pos, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (stack.getItem() == ModItems.EDELWOOD_WATER_BUCKET.getItem() || stack.getItem() == ModItems.EDELWOOD_LAVA_BUCKET.getItem()) {
            tooltip.add(new StringTextComponent(" "));
            tooltip.add(new StringTextComponent(" "));
        }
        super.addInformation(stack, world, tooltip, flag);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundNBT nbt) {
        if (this.getClass() == EdelwoodBucketItem.class)
            return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
        else
            return super.initCapabilities(stack, nbt);
    }

    private final java.util.function.Supplier<? extends Fluid> fluidSupplier;

    public Fluid getFluid() { return fluidSupplier.get(); }

    @Override
    public int getCapacity() {
        return capacity;
    }
}
