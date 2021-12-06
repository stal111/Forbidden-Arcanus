package com.stal111.forbidden_arcanus.item;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;

import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.wrappers.BucketPickupHandlerWrapper;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

public class EdelwoodBucketItem extends Item implements ICapacityBucket {

    private final Fluid containedBlock;

    public EdelwoodBucketItem(Fluid containedFluidIn, Item.Properties properties) {
        super(properties);
        this.containedBlock = containedFluidIn;
        this.fluidSupplier = containedFluidIn.delegate;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.PERMAFROST.get())) {
            if (FluidTags.LAVA.contains(containedBlock)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
                    if (!player.getAbilities().instabuild) {
                        if (new Random().nextDouble() < 0.005) {
                            if (!world.isClientSide()) {
                                player.getInventory().removeItemNoUpdate(slot);
                                player.getInventory().add(slot, new ItemStack(Items.CHARCOAL));
                                world.setBlockAndUpdate(pos, containedBlock.defaultFluidState().createLegacyBlock());
                            }
                        }
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, isSelected);
    }
    
    private boolean isValidFluid(Fluid fluid) {
        return fluid.isSame(Fluids.LAVA) || fluid.isSame(Fluids.WATER) || (ForgeMod.MILK.isPresent() && fluid.isSame(ForgeMod.MILK.get()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, this.containedBlock == Fluids.EMPTY || (ICapacityBucket.getFullness(stack) != this.getCapacity() && !playerIn.getAbilities().instabuild) ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, stack, raytraceresult);
        if (ret != null) return ret;
        if (raytraceresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(stack);
        } else if (raytraceresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        } else {
            BlockHitResult blockraytraceresult = (BlockHitResult)raytraceresult;
            BlockPos blockpos = blockraytraceresult.getBlockPos();
            Direction direction = blockraytraceresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos1, direction, stack)) {
                BlockState blockstate1 = worldIn.getBlockState(blockpos);
                if (this.containedBlock == Fluids.EMPTY || this.containedBlock == blockstate1.getFluidState().getType()) {
                    IFluidHandler fluidHandler = null;
                    BlockEntity te = worldIn.getBlockEntity(blockpos);
                    if (te != null)
                        fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
                    if (fluidHandler == null && blockstate1.getBlock() instanceof BucketPickup)
                        fluidHandler = new BucketPickupHandlerWrapper((BucketPickup) blockstate1.getBlock(), worldIn, blockpos);
                    Fluid fluid = Fluids.EMPTY;
                    if (fluidHandler != null) {
                        FluidStack fluidStack = fluidHandler.drain(FluidAttributes.BUCKET_VOLUME, FluidAction.SIMULATE);
                        if (isValidFluid(fluidStack.getFluid()) && fluidStack.getAmount() >= FluidAttributes.BUCKET_VOLUME) {
                            fluidStack = fluidHandler.drain(FluidAttributes.BUCKET_VOLUME, FluidAction.EXECUTE);
                            fluid = fluidStack.getFluid();
                        }
                    }
                    
                    if (fluid != Fluids.EMPTY) {
                        if (stack.getItem() instanceof EdelwoodFishBucketItem) { ;
                            if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos, blockraytraceresult)) {
                                this.onLiquidPlaced(worldIn, stack, blockpos);
                                if (playerIn instanceof ServerPlayer) {
                                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerIn, blockpos, stack);
                                }
                                playerIn.awardStat(Stats.ITEM_USED.get(this));
                                return InteractionResultHolder.success(this.emptyBucket(stack, playerIn));
                            } else {
                                return InteractionResultHolder.fail(stack);
                            }
                    } else {
                        playerIn.awardStat(Stats.ITEM_USED.get(this));

                        SoundEvent soundevent = this.containedBlock.getAttributes().getEmptySound();
                        if(soundevent == null) soundevent = fluid.is(FluidTags.LAVA) ? SoundEvents.BUCKET_FILL_LAVA : SoundEvents.BUCKET_FILL;
                        playerIn.playSound(soundevent, 1.0F, 1.0F);
                        ItemStack filled = ItemStack.EMPTY;
                        if (fluid.isSame(Fluids.WATER))
                            filled = this.fillBucket(stack, playerIn, NewModItems.EDELWOOD_WATER_BUCKET.get());
                        else if (fluid.isSame(Fluids.LAVA))
                            filled = this.fillBucket(stack, playerIn, NewModItems.EDELWOOD_LAVA_BUCKET.get());
                        else if (ForgeMod.MILK.isPresent() && fluid.isSame(ForgeMod.MILK.get()))
                            filled = this.fillBucket(stack, playerIn, ModItems.EDELWOOD_MILK_BUCKET.get());

                        if (!filled.isEmpty()) {
                            if (!worldIn.isClientSide) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)playerIn, new ItemStack(fluid.getBucket()));
                            }
                            return InteractionResultHolder.success(filled);
                        }
                        return InteractionResultHolder.fail(stack);
                    }
                }
                return InteractionResultHolder.fail(stack);
                } else {
                    BlockPos blockpos2 = blockstate1.getBlock() instanceof LiquidBlockContainer && this.containedBlock == Fluids.WATER ? blockpos : blockpos1;
                    if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos2, blockraytraceresult)) {
                        this.onLiquidPlaced(worldIn, stack, blockpos2);
                        if (playerIn instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)playerIn, blockpos2, stack);
                        }
                        playerIn.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.success(this.emptyBucket(stack, playerIn));
                    } else {
                        return InteractionResultHolder.fail(stack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(stack);
            }
        }
    }

    protected ItemStack emptyBucket(ItemStack stack, Player player) {
        if (!player.getAbilities().instabuild) {
            int fullness = ICapacityBucket.getFullness(stack);
            if ((fullness - 1) > 0) {
                return ICapacityBucket.setFullness(stack, fullness - 1);
            }
            return ItemStackUtils.transferEnchantments(stack, new ItemStack(NewModItems.EDELWOOD_BUCKET.get()));
        }
        return stack;
    }

    public void onLiquidPlaced(Level world, ItemStack stack, BlockPos pos) {
    }

    private ItemStack fillBucket(ItemStack emptyBucket, Player player, Item fullBucket) {
        if (player.getAbilities().instabuild) {
            return emptyBucket;
        } else {
            if (emptyBucket.getItem() == fullBucket) {
                return ICapacityBucket.setFullness(emptyBucket, ICapacityBucket.getFullness(emptyBucket) + 1);
            } else {
                ItemStack stack = ItemStackUtils.transferEnchantments(emptyBucket, new ItemStack(fullBucket));
                emptyBucket.shrink(1);
                if (emptyBucket.isEmpty()) {
                    return stack;
                } else {
                    if (!player.getInventory().add(stack)) {
                        player.drop(stack, false);
                    }
                    return emptyBucket;
                }
            }
        }
    }

    public boolean tryPlaceContainedLiquid(@Nullable Player player, Level worldIn, BlockPos posIn, @Nullable BlockHitResult p_180616_4_) {
        if (!(this.containedBlock instanceof FlowingFluid)) {
            return false;
        } else {
            BlockState blockstate = worldIn.getBlockState(posIn);
            Material material = blockstate.getMaterial();
            boolean flag = blockstate.canBeReplaced(this.containedBlock);
            if (blockstate.isAir() || flag || blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.containedBlock)) {
                if (worldIn.dimensionType().ultraWarm() && this.containedBlock.is(FluidTags.WATER)) {
                    int i = posIn.getX();
                    int j = posIn.getY();
                    int k = posIn.getZ();
                    worldIn.playSound(player, posIn, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.8F);

                    for(int l = 0; l < 8; ++l) {
                        worldIn.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                    }
                } else if (blockstate.getBlock() instanceof LiquidBlockContainer && this.containedBlock == Fluids.WATER) {
                    if (((LiquidBlockContainer)blockstate.getBlock()).placeLiquid(worldIn, posIn, blockstate, ((FlowingFluid)this.containedBlock).getSource(false))) {
                        this.playEmptySound(player, worldIn, posIn);
                    }
                } else {
                    if (!worldIn.isClientSide && flag && !material.isLiquid()) {
                        worldIn.destroyBlock(posIn, true);
                    }

                    this.playEmptySound(player, worldIn, posIn);
                    worldIn.setBlock(posIn, this.containedBlock.defaultFluidState().createLegacyBlock(), 11);
                }

                return true;
            } else {
                return p_180616_4_ != null && this.tryPlaceContainedLiquid(player, worldIn, p_180616_4_.getBlockPos().relative(p_180616_4_.getDirection()), null);
            }
        }
    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor worldIn, BlockPos pos) {
        SoundEvent soundevent = this.containedBlock.getAttributes().getEmptySound();
        if(soundevent == null) soundevent = this.containedBlock.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        worldIn.playSound(player, pos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        if (stack.getItem() == NewModItems.EDELWOOD_WATER_BUCKET.get() || stack.getItem() == NewModItems.EDELWOOD_LAVA_BUCKET.get()) {
            tooltip.add(new TextComponent(" "));
            tooltip.add(new TextComponent(" "));
        }
        super.appendHoverText(stack, world, tooltip, flag);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        if (this.getClass() == EdelwoodBucketItem.class)
            return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
        else
            return super.initCapabilities(stack, nbt);
    }

    private final java.util.function.Supplier<? extends Fluid> fluidSupplier;

    public Fluid getFluid() { return fluidSupplier.get(); }

    @Override
    public int getCapacity() {
        //return this == NewModItems.EDELWOOD_BUCKET.get() ? 0 : this == ModItems.EDELWOOD_WATER_BUCKET.get() ? ItemConfig.EDELWOOD_WATER_BUCKET_CAPACITY.get() : ItemConfig.EDELWOOD_LAVA_BUCKET_CAPACITY.get();
        return 3;
    }
}
