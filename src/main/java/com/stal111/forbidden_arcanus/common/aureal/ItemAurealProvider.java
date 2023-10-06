package com.stal111.forbidden_arcanus.common.aureal;

import com.stal111.forbidden_arcanus.core.init.ModEnchantments;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
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

    public static final Capability<ItemAurealProvider> AUREAL = CapabilityManager.get(new CapabilityToken<>(){});

    private final LazyOptional<ItemAurealProvider> holder = LazyOptional.of(() -> this);

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
        int limit = this.getTrueAurealLimit();
        int aurealReservoirLevel = this.stack.getEnchantmentLevel(ModEnchantments.AUREAL_RESERVOIR.get());

        if (aurealReservoirLevel == 0) {
            return limit;
        }

        return (int) (limit * (1 + aurealReservoirLevel / 10.0D));
    }

    public int getTrueAurealLimit() {
        CompoundTag tag = this.stack.getTag();

        return tag == null || !tag.contains(AUREAL_LIMIT_KEY) ? this.defaultLimit : tag.getInt(AUREAL_LIMIT_KEY);
    }

    @Override
    public void setAurealLimit(int limit) {
        CompoundTag tag = this.stack.getOrCreateTag();

        tag.putInt(AUREAL_LIMIT_KEY, limit);
    }

    public ProviderInfo getSnapshot() {
        return new ProviderInfo(this.getAureal(), this.getTrueAurealLimit());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
        return AUREAL.orEmpty(capability, this.holder);
    }

    public record ProviderInfo(int aureal, int limit) {

        public static final ProviderInfo EMPTY = new ProviderInfo(0, 0);

        public ProviderInfo combine(ProviderInfo info) {
            return new ProviderInfo(this.aureal() + info.aureal(), this.limit() + info.limit());
        }
    }
}
