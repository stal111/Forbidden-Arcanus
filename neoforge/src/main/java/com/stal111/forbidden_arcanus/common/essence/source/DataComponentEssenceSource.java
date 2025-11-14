package com.stal111.forbidden_arcanus.common.essence.source;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.essence.EssenceProvider;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public record DataComponentEssenceSource(DataComponentType<?> dataComponentType) implements EssenceSource {

    public static final Codec<DataComponentEssenceSource> CODEC = DataComponentType.CODEC.xmap(DataComponentEssenceSource::new, DataComponentEssenceSource::dataComponentType);
    public static final StreamCodec<RegistryFriendlyByteBuf, DataComponentEssenceSource> STREAM_CODEC = DataComponentType.STREAM_CODEC.map(DataComponentEssenceSource::new, DataComponentEssenceSource::dataComponentType);

    @Override
    public EssenceValue getEssenceValue(ItemStack stack, RandomSource random) {
        if (stack.get(this.dataComponentType) instanceof EssenceProvider essenceProvider) {
            return essenceProvider.getEssenceValue();
        }

        return null;
    }

    @Override
    public EssenceSourceType<?> getType() {
        return EssenceSourceTypes.FROM_COMPONENT.get();
    }
}
