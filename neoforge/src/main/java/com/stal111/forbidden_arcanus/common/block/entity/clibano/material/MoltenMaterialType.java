package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStackTemplate;

public record MoltenMaterialType(ItemStackTemplate result, int maxAmount) {

    public static final Codec<MoltenMaterialType> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(MoltenMaterialType::result),
            ExtraCodecs.POSITIVE_INT.fieldOf("max_amount").forGetter(MoltenMaterialType::maxAmount)
    ).apply(instance, MoltenMaterialType::new));

    public static final Codec<Holder<MoltenMaterialType>> CODEC = RegistryFileCodec.create(FARegistries.MOLTEN_MATERIAL_TYPE, DIRECT_CODEC);

    public static StreamCodec<RegistryFriendlyByteBuf, Holder<MoltenMaterialType>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FARegistries.MOLTEN_MATERIAL_TYPE);
}
