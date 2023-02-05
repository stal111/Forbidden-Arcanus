package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.core.init.ModRitualResults;
import net.minecraft.world.item.ItemStack;

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

    public static final RitualResultType.NetworkSerializer<CreateItemResult> SERIALIZER = (buffer, result) -> {
        buffer.writeItemStack(result.result, false);
    };

    public static final RitualResultType.NetworkDeserializer<CreateItemResult> DESERIALIZER = buffer -> {
        return new CreateItemResult(buffer.readItem());
    };

    private final ItemStack result;

    public CreateItemResult(ItemStack result) {
        this.result = result;
    }

    @Override
    public void apply(RitualManager.MainIngredientAccessor accessor) {
        accessor.set(this.result);
    }

    @Override
    public RitualResultType<? extends RitualResult> getType() {
        return ModRitualResults.CREATE_ITEM.get();
    }
}
