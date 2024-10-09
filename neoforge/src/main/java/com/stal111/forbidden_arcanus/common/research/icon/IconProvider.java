package com.stal111.forbidden_arcanus.common.research.icon;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.client.gui.GuiGraphics;

import java.util.function.Function;

/**
 * @author stal111
 * @since 25.11.2023
 */
public sealed interface IconProvider permits ItemIcon, TextureIcon {

    Codec<IconProvider> CODEC = Codec.either(ItemIcon.CODEC, TextureIcon.CODEC).xmap(icon -> {
        return icon.map(Function.identity(), Function.identity());
    }, iconProvider -> {
        if (iconProvider instanceof ItemIcon itemIcon) {
            return Either.left(itemIcon);
        }

        return Either.right((TextureIcon) iconProvider);
    });

    void renderIcon(GuiGraphics guiGraphics, int x, int y);
}
