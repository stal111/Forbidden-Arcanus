package com.stal111.forbidden_arcanus.common.research.icon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * @author stal111
 * @since 25.11.2023
 */
public final class TextureIcon implements IconProvider {

    public static final Codec<TextureIcon> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(info -> {
                return info.texture;
            })
    ).apply(instance, TextureIcon::new));

    private final ResourceLocation texture;

    public TextureIcon(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public void renderIcon(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(this.texture, x, y, 0, 0, 16, 16, 16, 16);
    }
}
