package com.stal111.forbidden_arcanus.common.item.wand;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;

public record WandMaterial(
        WandPart wandPart,
        Identifier texture
) {

    public static final Codec<WandMaterial> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            WandPart.CODEC.fieldOf("part").forGetter(WandMaterial::wandPart),
            Identifier.CODEC.fieldOf("texture").forGetter(WandMaterial::texture)
    ).apply(instance, WandMaterial::new));

    public static final Codec<Holder<WandMaterial>> CODEC = RegistryFileCodec.create(FARegistries.WAND_MATERIAL, DIRECT_CODEC);

    public static Codec<Holder<WandMaterial>> validatedCodec(WandPart part) {
        return CODEC.validate(material -> material.value().wandPart() == part ? DataResult.success(material) : DataResult.error(() -> "Material not applicable to wand part: " + part));
    }
}
