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
            case CUTOUT_MIPPED: return RenderType.func_228641_d_();
            case CUTOUT: return RenderType.func_228643_e_();
            case TRANSLUCENT: return RenderType.func_228645_f_();
            case TRANSLUCENT_NO_CRUMBLING: return RenderType.func_228647_g_();
            default: return RenderType.func_228639_c_();
        }
    }
}
