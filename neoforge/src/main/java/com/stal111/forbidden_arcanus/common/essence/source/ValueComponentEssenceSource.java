package com.stal111.forbidden_arcanus.common.essence.source;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ValueComponentEssenceSource implements EssenceSource {

    public static final ValueComponentEssenceSource INSTANCE = new ValueComponentEssenceSource();
    public static final MapCodec<ValueComponentEssenceSource> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, ValueComponentEssenceSource> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public @Nullable EssenceValue getEssenceValue(ItemStack stack, RandomSource random) {
        return EssenceHelper.getEssenceValue(stack).orElse(null);
    }

    @Override
    public EssenceSourceType<?> getType() {
        return EssenceSourceTypes.FROM_VALUE_COMPONENT.get();
    }
}
