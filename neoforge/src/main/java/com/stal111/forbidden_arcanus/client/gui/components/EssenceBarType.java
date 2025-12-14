package com.stal111.forbidden_arcanus.client.gui.components;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.Identifier;

public record EssenceBarType(int width, int height, Identifier texture, boolean vertical) {

    public static EssenceBarType createVertical(int width, int height, String texture) {
        return new EssenceBarType(width, height, ForbiddenArcanus.identifier(texture), true);
    }

    public static EssenceBarType createHorizontal(int width, int height, String texture) {
        return new EssenceBarType(width, height, ForbiddenArcanus.identifier(texture), false);
    }

    public static final EssenceBarType HEPHAESTUS_FORGE_AUREAL = createVertical(6, 32, "container/hephaestus_forge/aureal_bar");
    public static final EssenceBarType HEPHAESTUS_FORGE_ECTOPLASM = createVertical(6, 32, "container/hephaestus_forge/ectoplasm_bar");
    public static final EssenceBarType HEPHAESTUS_FORGE_BLOOD = createVertical(6, 32, "container/hephaestus_forge/blood_bar");
    public static final EssenceBarType HEPHAESTUS_FORGE_EXPERIENCE = createVertical(6, 32, "container/hephaestus_forge/experience_bar");
    public static final EssenceBarType CLIBANO_ECTOPLASM = createVertical(5, 18, "container/clibano/ectoplasm_bar");
    public static final EssenceBarType PLAYER_AUREAL = createHorizontal(75, 7, "hud/aureal_bar");

}
