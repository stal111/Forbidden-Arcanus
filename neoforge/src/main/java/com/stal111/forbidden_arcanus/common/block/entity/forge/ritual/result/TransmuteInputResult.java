package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.init.ModRitualResultTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record TransmuteInputResult(Holder<Item> result) implements RitualResult {

    public static final MapCodec<TransmuteInputResult> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemStack.ITEM_NON_AIR_CODEC.fieldOf("result_item").forGetter(TransmuteInputResult::result)
    ).apply(instance, TransmuteInputResult::new));

    @Override
    public ItemStack getResultItem(ItemStack mainInput) {
        return mainInput.transmuteCopy(this.result.value());
    }

    @Override
    public RitualResultType<? extends RitualResult> getType() {
        return ModRitualResultTypes.TRANSMUTE_INPUT.get();
    }
}
