package com.stal111.forbidden_arcanus.common.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.research.icon.IconProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ExtraCodecs;

/**
 * @author stal111
 * @since 23.11.2023
 */
public class DisplayInfo {

    public static final Codec<DisplayInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.COMPONENT.fieldOf("title").forGetter(info -> {
                return info.title;
            }),
            ExtraCodecs.COMPONENT.fieldOf("description").forGetter(info -> {
                return info.description;
            }),
            FrameType.CODEC.fieldOf("frame").forGetter(info -> {
                return info.frame;
            }),
            IconProvider.CODEC.fieldOf("icon").forGetter(info -> {
                return info.icon;
            }),
            Codec.INT.fieldOf("relative_x").forGetter(info -> {
                return info.relativeX;
            }),
            Codec.INT.fieldOf("relative_y").forGetter(info -> {
                return info.relativeY;
            })
    ).apply(instance, DisplayInfo::new));

    private final Component title;
    private final Component description;
    private final FrameType frame;
    private final IconProvider icon;
    private final int relativeX;
    private final int relativeY;

    public DisplayInfo(Component title, Component description, FrameType frame, IconProvider icon, int relativeX, int relativeY) {
        this.title = title;
        this.description = description;
        this.frame = frame;
        this.icon = icon;
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    public Component getTitle() {
        return this.title;
    }

    public Component getDescription() {
        return this.description;
    }

    public FrameType getFrame() {
        return this.frame;
    }

    public IconProvider getIcon() {
        return this.icon;
    }

    public int getRelativeX() {
        return this.relativeX;
    }

    public int getRelativeY() {
        return this.relativeY;
    }
}
