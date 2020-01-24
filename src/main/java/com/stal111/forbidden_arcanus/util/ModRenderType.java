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
            case CUTOUT_MIPPED: return RenderType.cutoutMipped();
            case CUTOUT: return RenderType.cutout();
            case TRANSLUCENT: return RenderType.translucent();
            case TRANSLUCENT_NO_CRUMBLING: return RenderType.translucentNoCrumbling();
            default: return RenderType.solid();
        }
    }
}
