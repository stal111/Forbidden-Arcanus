package com.stal111.forbidden_arcanus.client.gui.components;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.Identifier;

public record EssenceBarType(int width, int height, Identifier texture, FillOrigin origin) {

    public static EssenceBarType create(int width, int height, String texture, FillOrigin origin) {
        return new EssenceBarType(width, height, ForbiddenArcanus.identifier(texture), origin);
    }

    public static final EssenceBarType HEPHAESTUS_FORGE_AUREAL = create(6, 32, "container/hephaestus_forge/aureal_bar", FillOrigin.BOTTOM);
    public static final EssenceBarType HEPHAESTUS_FORGE_ECTOPLASM = create(6, 32, "container/hephaestus_forge/ectoplasm_bar", FillOrigin.BOTTOM);
    public static final EssenceBarType HEPHAESTUS_FORGE_BLOOD = create(6, 32, "container/hephaestus_forge/blood_bar", FillOrigin.BOTTOM);
    public static final EssenceBarType HEPHAESTUS_FORGE_EXPERIENCE = create(6, 32, "container/hephaestus_forge/experience_bar", FillOrigin.BOTTOM);
    public static final EssenceBarType CLIBANO_ECTOPLASM = create(28, 5, "container/clibano/ectoplasm_bar", FillOrigin.LEFT);
    public static final EssenceBarType PLAYER_AUREAL = create(75, 7, "hud/aureal_bar", FillOrigin.RIGHT);

    public enum FillOrigin {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
}
