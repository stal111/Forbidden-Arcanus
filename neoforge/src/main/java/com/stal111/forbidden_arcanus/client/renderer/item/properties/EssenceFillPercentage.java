package com.stal111.forbidden_arcanus.client.renderer.item.properties;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EssenceFillPercentage implements RangeSelectItemModelProperty {

    public static final MapCodec<EssenceFillPercentage> MAP_CODEC = MapCodec.unit(new EssenceFillPercentage());

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        return EssenceHelper.getEssenceStorage(stack).map(EssenceStorage::getFillPercentage).orElse(0.0F);
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return MAP_CODEC;
    }
}
