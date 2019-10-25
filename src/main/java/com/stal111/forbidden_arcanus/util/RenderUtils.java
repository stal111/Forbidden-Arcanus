package com.stal111.forbidden_arcanus.util;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.common.ForgeMod;

public class RenderUtils {

    private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

    public static boolean isLightMapDisabled() {
        return !ForgeMod.forgeLightPipelineEnabled;
    }

    public static VertexFormat getFormatWithLightMap(VertexFormat format) {
        if (isLightMapDisabled()) {
            return format;
        }

        if (format == DefaultVertexFormats.BLOCK) {
            return DefaultVertexFormats.BLOCK;
        } else if (format == DefaultVertexFormats.ITEM) {
            return ITEM_FORMAT_WITH_LIGHTMAP;
        } else if (!format.hasUv(1)) {
            VertexFormat result = new VertexFormat(format);

            result.addElement(DefaultVertexFormats.TEX_2S);

            return result;
        }
        return format;
    }
}
