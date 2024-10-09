package com.stal111.forbidden_arcanus.common.block.entity.clibano.residue;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2022-06-29
 */
public record ResidueType(Component name, CombineInfo combineInfo) {

    public static final Codec<ResidueType> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.fieldOf("name").forGetter(info -> {
                return info.name;
            }),
            CombineInfo.CODEC.fieldOf("combine_info").forGetter(info -> {
                return info.combineInfo;
            })
    ).apply(instance, ResidueType::new));

    public static final Codec<Holder<ResidueType>> CODEC = RegistryFileCodec.create(FARegistries.RESIDUE_TYPE, DIRECT_CODEC);

    public static ResidueType withDefaultKey(String name, int requiredAmount, ItemStack result) {
        return new ResidueType(Component.translatable("residue." + ForbiddenArcanus.MOD_ID + "." + name), new CombineInfo(requiredAmount, result));
    }

    public record CombineInfo(int requiredAmount, ItemStack result) {

        public static final Codec<CombineInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("required_amount").forGetter(info -> {
                    return info.requiredAmount;
                }),
                ItemStack.CODEC.fieldOf("result").forGetter(info -> {
                    return info.result;
                })
        ).apply(instance, CombineInfo::new));
    }
}
