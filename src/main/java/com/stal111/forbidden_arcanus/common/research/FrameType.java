package com.stal111.forbidden_arcanus.common.research;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.gui.components.WidgetSprites;
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
    private final WidgetSprites sprites;

    FrameType(String name) {
        this.name = name;
        this.sprites = new WidgetSprites(ForbiddenArcanus.location("research/frame/" + name + "_locked"), ForbiddenArcanus.location("research/frame/" + name), ForbiddenArcanus.location("research/frame/" + name + "_locked_highlighted"));
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public ResourceLocation getFrameTexture(boolean locked, boolean highlighted) {
        return this.sprites.get(locked, highlighted);
    }
}
