package com.stal111.forbidden_arcanus.data.model;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * @author stal111
 * @since 28.10.2023
 */
public class ModModelTemplates {

    public static final ModelTemplate FORBIDDENOMICON = create("template_forbiddenomicon", TextureSlot.FRONT, TextureSlot.BACK, TextureSlot.INSIDE, TextureSlot.SIDE);

    public static ModelTemplate create(String name, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/" + name)), Optional.empty(), textureSlots);
    }
}
