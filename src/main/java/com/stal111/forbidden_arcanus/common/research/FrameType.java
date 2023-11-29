package com.stal111.forbidden_arcanus.common.research;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 23.11.2023
 */
public enum FrameType implements StringRepresentable {
    DEFAULT("default"),
    ADVANCED("advanced"),
    MASTER("master");

    public static final Codec<FrameType> CODEC = StringRepresentable.fromEnum(FrameType::values);

    private final String name;
    private final ResourceLocation frameTexture;

    FrameType(String name) {
        this.name = name;
        this.frameTexture = new ResourceLocation(ForbiddenArcanus.MOD_ID, "research/frame/" + name);
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public ResourceLocation getFrameTexture() {
        return this.frameTexture;
    }
}
