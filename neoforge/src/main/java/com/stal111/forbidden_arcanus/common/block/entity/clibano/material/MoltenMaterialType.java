package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.item.ItemStackTemplate;

public record MoltenMaterialType(ItemStackTemplate display) {

    public static Codec<MoltenMaterialType> DIRECT_CODEC = ItemStackTemplate.CODEC.xmap(MoltenMaterialType::new, MoltenMaterialType::display);

    public static final Codec<Holder<MoltenMaterialType>> CODEC = RegistryFileCodec.create(FARegistries.MOLTEN_MATERIAL_TYPE, DIRECT_CODEC);

    public static StreamCodec<RegistryFriendlyByteBuf, Holder<MoltenMaterialType>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FARegistries.MOLTEN_MATERIAL_TYPE);
}
