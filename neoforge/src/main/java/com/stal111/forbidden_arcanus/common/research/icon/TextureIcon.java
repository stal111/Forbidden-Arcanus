package com.stal111.forbidden_arcanus.common.research.icon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

/**
 * @author stal111
 * @since 25.11.2023
 */
public final class TextureIcon implements IconProvider {

    public static final Codec<TextureIcon> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("texture").forGetter(info -> {
                return info.texture;
            })
    ).apply(instance, TextureIcon::new));

    private final Identifier texture;

    public TextureIcon(Identifier texture) {
        this.texture = texture;
    }

    @Override
    public void renderIcon(GuiGraphicsExtractor guiGraphics, int x, int y) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.texture, x, y, 0, 0, 16, 16, 16, 16);
    }
}
