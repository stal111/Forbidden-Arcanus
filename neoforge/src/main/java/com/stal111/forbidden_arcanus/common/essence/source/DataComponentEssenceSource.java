package com.stal111.forbidden_arcanus.common.essence.source;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.essence.EssenceProvider;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jetbrains.annotations.Nullable;

public record DataComponentEssenceSource(DataComponentType<?> dataComponentType) implements EssenceSource {

    public static final Codec<DataComponentEssenceSource> CODEC = DataComponentType.CODEC.xmap(DataComponentEssenceSource::new, DataComponentEssenceSource::dataComponentType);
    public static final StreamCodec<RegistryFriendlyByteBuf, DataComponentEssenceSource> STREAM_CODEC = DataComponentType.STREAM_CODEC.map(DataComponentEssenceSource::new, DataComponentEssenceSource::dataComponentType);

    @Override
    public @Nullable EssenceValue extractEssence(MutableDataComponentHolder componentHolder, boolean keepOriginal) {
        if (componentHolder.get(this.dataComponentType) instanceof EssenceProvider essenceProvider) {
            EssenceValue value = essenceProvider.getExtractableValue(false);

            if (!keepOriginal) {
                essenceProvider.extract(componentHolder, value.amount());
            }

            return value;
        }

        return null;
    }
}
