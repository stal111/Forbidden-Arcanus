package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

/**
 * @author stal111
 * @since 2023-01-04
 */
public enum EssenceType implements StringRepresentable {
    AUREAL("aureal"),
    SOULS("souls"),
    BLOOD("blood"),
    EXPERIENCE("experience");

    public static final StringRepresentable.EnumCodec<EssenceType> CODEC = StringRepresentable.fromEnum(EssenceType::values);

    public static final Codec<List<EssenceType>> ONE_OR_MULTIPLE_CODEC = Codec.either(EssenceType.CODEC, EssenceType.CODEC.listOf()).flatComapMap(either -> {
        return either.map(List::of, Function.identity());
    }, list -> {
        return list.size() == 1 ? DataResult.success(Either.left(list.get(0))) : DataResult.error(() -> "List size must be 1");
    });

    public static final StreamCodec<FriendlyByteBuf, EssenceType> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(EssenceType.class);

    private final String name;
    private final Component component;

    EssenceType(String name) {
        this.name = name;
        this.component = Component.translatable("forbidden_arcanus.essence." + name);
    }

    public Component getComponent() {
        return this.component;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
