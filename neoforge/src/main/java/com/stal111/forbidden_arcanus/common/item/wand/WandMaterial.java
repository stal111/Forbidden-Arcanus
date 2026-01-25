package com.stal111.forbidden_arcanus.common.item.wand;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record WandMaterial(
        WandPart wandPart,
        Identifier texture
) implements TooltipProvider {

    public static final Codec<WandMaterial> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            WandPart.CODEC.fieldOf("part").forGetter(WandMaterial::wandPart),
            Identifier.CODEC.fieldOf("texture").forGetter(WandMaterial::texture)
    ).apply(instance, WandMaterial::new));

    public static final Codec<Holder<WandMaterial>> CODEC = RegistryFileCodec.create(FARegistries.WAND_MATERIAL, DIRECT_CODEC);

    private static final String INFO_KEY = Util.makeDescriptionId("item", ForbiddenArcanus.identifier("wand_material.info"));

    public static Codec<Holder<WandMaterial>> validatedCodec(WandPart part) {
        return CODEC.validate(material -> material.value().wandPart() == part ? DataResult.success(material) : DataResult.error(() -> "Material not applicable to wand part: " + part));
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        consumer.accept(Component.translatable(INFO_KEY, this.wandPart().getSerializedName()).withStyle(ChatFormatting.BLUE));
    }
}
