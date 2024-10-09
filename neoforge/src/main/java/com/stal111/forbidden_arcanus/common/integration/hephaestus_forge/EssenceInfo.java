package com.stal111.forbidden_arcanus.common.integration.hephaestus_forge;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;

/**
 * @author stal111
 * @since 2023-06-05
 */
public record EssenceInfo(IDrawableStatic drawable, EssenceType type, int posX, int posY) {

    private static final ResourceLocation TEXTURE = ForbiddenArcanus.location("textures/gui/jei/hephaestus_forge/essences.png");

    private static final int SIZE = 12;

    public static List<EssenceInfo> create(IGuiHelper helper, int startX, int startY) {
        return Arrays.stream(EssenceType.values())
                .map(type -> {
                    int index = type.ordinal();
                    IDrawableStatic drawable = helper.drawableBuilder(TEXTURE, index * 13, 0, SIZE, SIZE).setTextureSize(64, 16).build();

                    return new EssenceInfo(drawable, type, startX + (16 * index), startY);
                }).toList();
    }

    public boolean shouldDisplayTooltip(double mouseX, double mouseY) {
        if (mouseX >= this.posX && mouseY >= this.posY && mouseX <=this. posX + SIZE && mouseY <= this.posY + SIZE) {
            return true;
        }

        return false;
    }

    public Component getTooltip(EssencesDefinition definition) {
        return Component.translatable("jei.forbidden_arcanus.hephaestusSmithing.required_" + this.type().getSerializedName()).append(": " + definition.get(this.type));
    }
}
