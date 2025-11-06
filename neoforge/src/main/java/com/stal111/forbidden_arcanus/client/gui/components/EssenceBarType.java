package com.stal111.forbidden_arcanus.client.gui.components;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;

public record EssenceBarType(int width, int height, ResourceLocation texture) {

    public static EssenceBarType create(int width, int height, String texture) {
        return new EssenceBarType(width, height, ForbiddenArcanus.location(texture));
    }

    public static final EssenceBarType HEPHAESTUS_FORGE_AUREAL = EssenceBarType.create(6, 32, "container/hephaestus_forge/aureal_bar");
    public static final EssenceBarType HEPHAESTUS_FORGE_ECTOPLASM = EssenceBarType.create(6, 32, "container/hephaestus_forge/ectoplasm_bar");
    public static final EssenceBarType HEPHAESTUS_FORGE_BLOOD = EssenceBarType.create(6, 32, "container/hephaestus_forge/blood_bar");
    public static final EssenceBarType HEPHAESTUS_FORGE_EXPERIENCE = EssenceBarType.create(6, 32, "container/hephaestus_forge/experience_bar");
    public static final EssenceBarType CLIBANO_ECTOPLASM = EssenceBarType.create(5, 18, "container/clibano/ectoplasm_bar");

}
