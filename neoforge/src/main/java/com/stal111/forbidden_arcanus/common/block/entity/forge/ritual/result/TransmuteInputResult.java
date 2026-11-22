package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.init.ModRitualResultTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.TransmuteRecipe;

public record TransmuteInputResult(ItemStackTemplate result) implements RitualResult {

    public static final MapCodec<TransmuteInputResult> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStackTemplate.CODEC.fieldOf("result_item").forGetter(TransmuteInputResult::result)
    ).apply(instance, TransmuteInputResult::new));

    @Override
    public ItemStack getResultItem(ItemStack mainInput) {
        return TransmuteRecipe.createWithOriginalComponents(this.result, mainInput);
    }

    @Override
    public RitualResultType<? extends RitualResult> getType() {
        return ModRitualResultTypes.TRANSMUTE_INPUT.get();
    }
}
