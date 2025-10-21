package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record MoltenMaterialType(ItemStack display) {

    public static Codec<MoltenMaterialType> CODEC = ItemStack.CODEC.xmap(MoltenMaterialType::new, MoltenMaterialType::display);

    public static StreamCodec<RegistryFriendlyByteBuf, MoltenMaterialType> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            MoltenMaterialType::display,
            MoltenMaterialType::new
    );
}
