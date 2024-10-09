package com.stal111.forbidden_arcanus.common.item.enhancer;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-02-21
 */
public enum EnhancerTarget implements StringRepresentable {
    HEPHAESTUS_FORGE("hephaestus_forge", "item.forbidden_arcanus.enhancer.hephaestus_forge_effect"),
    CLIBANO("clibano", "item.forbidden_arcanus.enhancer.clibano_effect");

    public static final StringRepresentable.EnumCodec<EnhancerTarget> CODEC = StringRepresentable.fromEnum(EnhancerTarget::values);

    public static final StreamCodec<FriendlyByteBuf, EnhancerTarget> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(EnhancerTarget.class);

    private final String name;
    private final Component title;

    EnhancerTarget(String name, String title) {
        this.name = name;
        this.title = Component.translatable(title).withStyle(ChatFormatting.GRAY);
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public Component getTitle() {
        return this.title;
    }
}
