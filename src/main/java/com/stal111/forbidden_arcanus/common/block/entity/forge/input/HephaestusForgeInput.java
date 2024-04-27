package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.api.common.util.DeferredCodec;

/**
 * @author stal111
 * @since 2023-05-24
 */
public abstract class HephaestusForgeInput {

    public static final Codec<HephaestusForgeInput> DIRECT_CODEC = new DeferredCodec<>(() -> FARegistries.FORGE_INPUT_TYPE_REGISTRY.byNameCodec().dispatch(HephaestusForgeInput::type, HephaestusForgeInputType::codec));

    private final EssenceType essenceType;

    protected HephaestusForgeInput(EssenceType essenceType) {
        this.essenceType = essenceType;
    }

    /**
     * Checks whether this input is valid for the given essence type. <br>
     * If you want to do additional checks override {@link HephaestusForgeInput#canInputStack(ItemStack)}.
     */
    public final boolean canInput(EssenceType type, ItemStack stack) {
        return this.essenceType == type && this.canInputStack(stack);
    }

    public boolean canInputStack(ItemStack stack) {
        return true;
    }

    public abstract int getInputValue(EssenceType type, ItemStack stack, RandomSource random);
    public abstract void finishInput(EssenceType type, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue);

    public EssenceType getEssenceType() {
        return this.essenceType;
    }

    public abstract HephaestusForgeInputType<?> type();
}
