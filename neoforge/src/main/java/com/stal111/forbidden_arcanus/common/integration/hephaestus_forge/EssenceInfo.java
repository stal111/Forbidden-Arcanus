package com.stal111.forbidden_arcanus.common.integration.hephaestus_forge;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.Arrays;
import java.util.List;

/**
 * @author stal111
 * @since 2023-06-05
 */
public record EssenceInfo(IDrawableStatic drawable, EssenceType type, int posX, int posY) {

    private static final Identifier TEXTURE = ForbiddenArcanus.identifier("textures/gui/jei/hephaestus_forge/essences.png");

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
        return mouseX >= this.posX && mouseY >= this.posY && mouseX <= this.posX + SIZE && mouseY <= this.posY + SIZE;
    }

    public Component getTooltip(EssenceSet essenceSet) {
        return Component.translatable("jei.forbidden_arcanus.hephaestus_smithing.required_essence", type.getComponent(), essenceSet.get(this.type));
    }
}
