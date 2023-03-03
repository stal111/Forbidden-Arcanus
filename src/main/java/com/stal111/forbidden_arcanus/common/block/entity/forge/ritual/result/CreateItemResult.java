package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.core.init.ModRitualResultTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author stal111
 * @since 2023-02-05
 */
public class CreateItemResult extends RitualResult {

    public static final Codec<CreateItemResult> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ItemStack.CODEC.fieldOf("result_item").forGetter(result -> {
                return result.result;
            })
    ).apply(instance, CreateItemResult::new));

    private final ItemStack result;

    public CreateItemResult(ItemStack result) {
        this.result = result;
    }

    @Override
    public void apply(RitualManager.MainIngredientAccessor accessor, Level level, BlockPos pos) {
        accessor.set(this.result.copy());
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public RitualResultType<? extends RitualResult> getType() {
        return ModRitualResultTypes.CREATE_ITEM.get();
    }
}
