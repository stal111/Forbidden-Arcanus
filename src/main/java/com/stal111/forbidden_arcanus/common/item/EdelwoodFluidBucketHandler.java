package com.stal111.forbidden_arcanus.common.item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.stal111.forbidden_arcanus.core.init.ModItems;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackUtils;


public class EdelwoodFluidBucketHandler implements IFluidHandlerItem, ICapabilityProvider {

    private static EdelwoodBucketItem[] TANK_TO_BUCKET = null;

    @Nonnull
    ItemStack container;

    public void setupTankToBucketTable() {
        if (TANK_TO_BUCKET == null) {
            TANK_TO_BUCKET = new EdelwoodBucketItem[] {
                ModItems.EDELWOOD_WATER_BUCKET.get(), 
                ModItems.EDELWOOD_LAVA_BUCKET.get(), 
            };
        }
    }

    public EdelwoodFluidBucketHandler(ItemStack stack) {
        if (!(stack.getItem() instanceof EdelwoodBucketItem)) {
            throw new IllegalArgumentException("Stack must contain Edelwood bucket item");
        }
        setupTankToBucketTable();
        this.container = stack;
    }

    private EdelwoodBucketItem getBucket() {
        return (EdelwoodBucketItem) container.getItem();
    }

    private Fluid getFluid() {
        return getBucket().getFluid();
    }

    private boolean fluidIsSame(Fluid toCompare) {
        return getFluid().isSame(toCompare);
    }

    @Override
    public int getTanks() {
        return TANK_TO_BUCKET.length;
    }

    private boolean isTankActive(int tank) {
        if (tank < 0 || tank >= TANK_TO_BUCKET.length) return false;
        return fluidIsSame(Fluids.EMPTY) || fluidIsSame(TANK_TO_BUCKET[tank].getFluid());
    }

    private int getTankForFluid(Fluid fluid) {
        for (int i = 0; i < TANK_TO_BUCKET.length; i++) {
            if (TANK_TO_BUCKET[i].getFluid().isSame(fluid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public @Nonnull FluidStack getFluidInTank(int tank) {
        if (!isTankActive(tank)) return FluidStack.EMPTY;

        int fullness = getBucket().getFullness(container) * 1000; 
        if (fullness == 0) {
            return FluidStack.EMPTY;
        } else {
            return new FluidStack(getFluid(), fullness);
        }
    }

    @Override
    public int getTankCapacity(int tank) {
        if (!isTankActive(tank)) return 0;

        return TANK_TO_BUCKET[tank].getCapacity() * 1000;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        if (tank < 0 || tank >= TANK_TO_BUCKET.length) return false;
        
        return TANK_TO_BUCKET[tank].getFluid().isSame(stack.getFluid());
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        int tank = getTankForFluid(resource.getFluid());
        if (!isTankActive(tank)) return 0;

        // Round down to nearest bucket
        int fullness = getBucket().getFullness(container);
        int roomAvailable = (getTankCapacity(tank) / 1000) - fullness;
        int fluidSupply = resource.getAmount() / 1000;
        int toTransfer = Math.min(roomAvailable, fluidSupply);

        if (action.execute()) {
            if (fluidIsSame(Fluids.EMPTY)) {
                container = ItemStackUtils.transferEnchantments(container, new ItemStack(TANK_TO_BUCKET[tank]));
            }
            getBucket().setFullness(container, fullness + toTransfer);
        }

        return toTransfer * 1000;
    }

    @Override
    public @Nonnull FluidStack drain(FluidStack resource, FluidAction action) {
        int tank = getTankForFluid(resource.getFluid());
        if (!isTankActive(tank)) return FluidStack.EMPTY;
        return drain(resource.getAmount(), action);
    }

    @Override
    public @Nonnull FluidStack drain(int maxDrain, FluidAction action) {
        // Round down to nearest bucket
        int amountAvailable = getBucket().getFullness(container);
        int fluidDemand = maxDrain / 1000;
        int toTransfer = Math.min(amountAvailable, fluidDemand); 

        if (action.execute()) {
            getBucket().setFullness(container, amountAvailable - toTransfer);
            if (getBucket().getFullness(container) == 0) {
                container = ItemStackUtils.transferEnchantments(container, getBucket().getEmptyBucket());
            }
        }

        if (toTransfer == 0) {
            return FluidStack.EMPTY;
        } else {
            return new FluidStack(getFluid(), toTransfer * 1000);
        }
    }

    @Override
    public @Nonnull ItemStack getContainer() {
        return container;
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER_ITEM) {
            return LazyOptional.of(()->this).cast();
        }
        return LazyOptional.empty();
    }
}
