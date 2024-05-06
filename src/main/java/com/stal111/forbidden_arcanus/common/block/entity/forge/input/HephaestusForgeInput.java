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

    public abstract boolean canInput(EssenceType type, ItemStack stack);

    public abstract int getInputValue(EssenceType type, ItemStack stack, RandomSource random);
    public abstract void finishInput(EssenceType type, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue);
}
