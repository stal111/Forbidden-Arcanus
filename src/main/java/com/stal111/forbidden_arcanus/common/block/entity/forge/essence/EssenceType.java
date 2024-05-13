package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
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
    AUREAL("aureal", 7),
    SOULS("souls", 0),
    BLOOD("blood", 0),
    EXPERIENCE("experience", 13);

    public static final StringRepresentable.EnumCodec<EssenceType> CODEC = StringRepresentable.fromEnum(EssenceType::values);

    public static final Codec<List<EssenceType>> ONE_OR_MULTIPLE_CODEC = Codec.either(EssenceType.CODEC, EssenceType.CODEC.listOf()).flatComapMap(either -> {
        return either.map(List::of, Function.identity());
    }, list -> {
        return list.size() == 1 ? DataResult.success(Either.left(list.get(0))) : DataResult.error(() -> "List size must be 1");
    });

    public static final StreamCodec<FriendlyByteBuf, EssenceType> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(EssenceType.class);

    private final String name;
    private final int lightEmission;
    private final Component component;
    private final ResourceLocation spriteLocation;

    EssenceType(String name, int lightEmission) {
        this.name = name;
        this.lightEmission = lightEmission;
        this.component = Component.translatable("forbidden_arcanus.essence." + name);
        this.spriteLocation = new ResourceLocation(ForbiddenArcanus.MOD_ID, "icon/" + name);
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public Component getComponent() {
        return this.component;
    }

    public int getLightEmission() {
        return this.lightEmission;
    }

    public ResourceLocation getSpriteLocation() {
        return this.spriteLocation;
    }
}
