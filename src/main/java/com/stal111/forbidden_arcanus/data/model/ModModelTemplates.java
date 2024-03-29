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
    public static final ModelTemplate DESK = create("template_desk", TextureSlot.FRONT, TextureSlot.BACK, TextureSlot.INSIDE, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModelTemplate PEDESTAL = create("template_pedestal", TextureSlot.SIDE, TextureSlot.TOP);
    public static final ModelTemplate CLIBANO_CENTER = create("clibano_center", TextureSlot.TEXTURE);
    public static final ModelTemplate CLIBANO_SIDE_HORIZONTAL = create("clibano_side_horizontal", TextureSlot.SIDE);
    public static final ModelTemplate CLIBANO_SIDE_VERTICAL = create("clibano_side_vertical", TextureSlot.SIDE);
    public static final ModelTemplate HEPHAESTUS_FORGE = create("template_hephaestus_forge", TextureSlot.TOP, ModTextureSlots.TOP_LAYER, TextureSlot.SIDE, ModTextureSlots.SIDE_LAYER, ModTextureSlots.CLOTH_SIDE, TextureSlot.BOTTOM, ModTextureSlots.BLOCK);

    public static ModelTemplate create(String name, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/" + name)), Optional.empty(), textureSlots);
    }
}
