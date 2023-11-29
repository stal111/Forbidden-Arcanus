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
            FrameType.CODEC.fieldOf("frame").forGetter(info -> {
                return info.frame;
            }),
            IconProvider.CODEC.fieldOf("icon").forGetter(info -> {
                return info.icon;
            })
    ).apply(instance, DisplayInfo::new));

    private final Component title;
    private final FrameType frame;
    private final IconProvider icon;

    public DisplayInfo(Component title, FrameType frame, IconProvider icon) {
        this.title = title;
        this.frame = frame;
        this.icon = icon;
    }

    public Component getTitle() {
        return this.title;
    }

    public FrameType getFrame() {
        return this.frame;
    }

    public IconProvider getIcon() {
        return this.icon;
    }
}
