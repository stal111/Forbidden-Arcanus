package com.stal111.forbidden_arcanus.common.aureal;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 17.09.2023
 */
public class ItemAurealProvider implements AurealProvider, ICapabilityProvider {

    private static final String AUREAL_KEY = "Aureal";
    private static final String AUREAL_LIMIT_KEY = "AurealLimit";

    public static final Capability<AurealProvider> AUREAL = CapabilityManager.get(new CapabilityToken<>(){});

    private final LazyOptional<AurealProvider> holder = LazyOptional.of(() -> this);

    @NotNull
    private final ItemStack stack;

    private final int defaultLimit;

    public ItemAurealProvider(@NotNull ItemStack stack, int defaultLimit) {
        this.stack = stack;
        this.defaultLimit = defaultLimit;
    }

    @Override
    public int getAureal() {
        CompoundTag tag = this.stack.getTag();

        if (tag == null) {
            return 0;
        }

        return tag.getInt(AUREAL_KEY);
    }

    @Override
    public void setAureal(int aureal) {
        CompoundTag tag = this.stack.getOrCreateTag();

        tag.putInt(AUREAL_KEY, aureal);
    }

    @Override
    public int getAurealLimit() {
        CompoundTag tag = this.stack.getTag();

        if (tag == null) {
            return this.defaultLimit;
        }

        return tag.getInt(AUREAL_LIMIT_KEY);
    }

    @Override
    public void setAurealLimit(int limit) {
        CompoundTag tag = this.stack.getOrCreateTag();

        tag.putInt(AUREAL_LIMIT_KEY, limit);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        return AUREAL.orEmpty(capability, this.holder);
    }
}
