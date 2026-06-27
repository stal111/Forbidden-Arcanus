package com.stal111.forbidden_arcanus.common.item.crafting.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record ClibanoRecipeDisplay(SlotDisplay ingredient,
                                   SlotDisplay fuel,
                                   MoltenMaterial resultMaterial,
                                   SlotDisplay craftingStation,
                                   int duration) implements RecipeDisplay {

    public static final MapCodec<ClibanoRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            SlotDisplay.CODEC.fieldOf("ingredient").forGetter(ClibanoRecipeDisplay::ingredient),
            SlotDisplay.CODEC.fieldOf("fuel").forGetter(ClibanoRecipeDisplay::fuel),
            MoltenMaterial.CODEC.fieldOf("result").forGetter(ClibanoRecipeDisplay::resultMaterial),
            SlotDisplay.CODEC.fieldOf("craftingStation").forGetter(ClibanoRecipeDisplay::craftingStation),
            Codec.INT.fieldOf("duration").forGetter(ClibanoRecipeDisplay::duration)
    ).apply(instance, ClibanoRecipeDisplay::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClibanoRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC,
            ClibanoRecipeDisplay::ingredient,
            SlotDisplay.STREAM_CODEC,
            ClibanoRecipeDisplay::fuel,
            MoltenMaterial.STREAM_CODEC,
            ClibanoRecipeDisplay::resultMaterial,
            SlotDisplay.STREAM_CODEC,
            ClibanoRecipeDisplay::craftingStation,
            ByteBufCodecs.VAR_INT,
            ClibanoRecipeDisplay::duration,
            ClibanoRecipeDisplay::new
    );

    @Override
    public SlotDisplay result() {
        return new SlotDisplay.ItemStackSlotDisplay(this.resultMaterial.type().value().result());
    }

    @Override
    public SlotDisplay craftingStation() {
        return this.craftingStation;
    }

    @Override
    public Type<? extends RecipeDisplay> type() {
        return FARecipeDisplays.CLIBANO.get();
    }
}
