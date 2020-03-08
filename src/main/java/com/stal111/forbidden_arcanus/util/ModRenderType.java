package com.stal111.forbidden_arcanus.util;

import net.minecraft.client.renderer.RenderType;

public enum ModRenderType {
    SOLID,
    CUTOUT_MIPPED,
    CUTOUT,
    TRANSLUCENT,
    TRANSLUCENT_NO_CRUMBLING;

    public RenderType getRenderType() {
        switch (this) {
            case CUTOUT_MIPPED: return RenderType.getCutoutMipped();
            case CUTOUT: return RenderType.getCutout();
            case TRANSLUCENT: return RenderType.getTranslucent();
            case TRANSLUCENT_NO_CRUMBLING: return RenderType.getTranslucentNoCrumbling();
            default: return RenderType.getSolid();
        }
    }
}
