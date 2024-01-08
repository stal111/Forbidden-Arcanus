package com.stal111.forbidden_arcanus.common.item.bucket;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 15.12.2023
 */
public class CapacityBucketFluidHandler implements IFluidHandlerItem, ICapabilityProvider {

    private final LazyOptional<IFluidHandlerItem> holder = LazyOptional.of(() -> this);

    private final CapacityFluidBucket bucket;
    private ItemStack container;

    public CapacityBucketFluidHandler(CapacityFluidBucket bucket, ItemStack container) {
        this.bucket = bucket;
        this.container = container;
    }

    private int getFullness() {
        return this.bucket.getFullness(this.container);
    }

    private void setFluid(@NotNull FluidStack fluidStack) {
        Item item = this.bucket.getFilledBucket(fluidStack.getFluid());

        if (item != null) {
            this.container = new ItemStack(item);
        }
    }

    @Override
    public @NotNull ItemStack getContainer() {
        return this.container;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return new FluidStack(this.bucket.getFluid(), FluidType.BUCKET_VOLUME * this.getFullness());
    }

    @Override
    public int getTankCapacity(int tank) {
        return this.bucket.getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        FluidStack fluidStack = this.getFluidInTank(tank);

        if (fluidStack.isEmpty()) {
            return this.bucket.getFilledBucket(stack.getFluid()) != null;
        }

        return stack.isFluidEqual(fluidStack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (this.container.getCount() != 1 || resource.getAmount() < FluidType.BUCKET_VOLUME || this.bucket.isFull(this.container)) {
            return 0;
        }

        if (action.execute()) {
            int fullness = this.getFullness();

            if (this.container.is(this.bucket.getEmptyBucket())) {
                this.setFluid(resource);

                return FluidType.BUCKET_VOLUME;
            }

            int fillAmount = Math.min(this.bucket.getCapacity() - fullness, resource.getAmount() / FluidType.BUCKET_VOLUME);

            this.bucket.setFullness(this.container, fullness + fillAmount);

            return fillAmount * FluidType.BUCKET_VOLUME;
        }

        return 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        if (this.container.getCount() != 1 || resource.getAmount() < FluidType.BUCKET_VOLUME || !resource.isFluidEqual(this.getFluidInTank(0))) {
            return FluidStack.EMPTY;
        }

        return this.drain(resource.getAmount(), action);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        if (this.container.getCount() != 1 || maxDrain < FluidType.BUCKET_VOLUME) {
            return FluidStack.EMPTY;
        }

        int fullness = this.getFullness();

        if (action.execute() && fullness != 0) {
            int drainAmount = Math.min(fullness, maxDrain / FluidType.BUCKET_VOLUME);

            this.bucket.setFullness(this.container, fullness - drainAmount);

            if (fullness - drainAmount <= 0) {
                this.setFluid(FluidStack.EMPTY);
            }

            return new FluidStack(this.bucket.getFluid(), drainAmount * FluidType.BUCKET_VOLUME);
        }

        return FluidStack.EMPTY;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        return ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(capability, this.holder);
    }
}
