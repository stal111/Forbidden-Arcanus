package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.common.util.DeferredCodec;

import java.util.List;

/**
 * @author stal111
 * @since 2023-05-24
 */
public abstract class HephaestusForgeInput {

    public static final Codec<HephaestusForgeInput> DIRECT_CODEC = new DeferredCodec<>(() -> FARegistries.FORGE_INPUT_TYPE_REGISTRY.get().getCodec().dispatch(HephaestusForgeInput::type, HephaestusForgeInputType::codec));

    protected final List<EssenceType> essenceTypes;

    protected HephaestusForgeInput(EssenceType essenceType) {
        this(List.of(essenceType));
    }

    protected HephaestusForgeInput(List<EssenceType> essenceTypes) {
        this.essenceTypes = essenceTypes;
    }

    public static <T extends HephaestusForgeInput> RecordCodecBuilder<T, List<EssenceType>> essenTypeCodec() {
        return EssenceType.ONE_OR_MULTIPLE_CODEC.fieldOf("essence_type").forGetter(input -> {
            return input.essenceTypes;
        });
    }

    /**
     * Checks whether this input is valid for the given essence type. <br>
     * If you want to do additional checks override {@link HephaestusForgeInput#additionalInputChecks(ItemStack)}.
     */
    public final boolean canInput(EssenceType type, ItemStack stack) {
        return this.essenceTypes.contains(type) && this.additionalInputChecks(stack);
    }

    public boolean additionalInputChecks(ItemStack stack) {
        return true;
    }

    public abstract int getInputValue(EssenceType type, ItemStack stack, RandomSource random);
    public abstract void finishInput(EssenceType type, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue);

    public abstract HephaestusForgeInputType<?> type();
}
