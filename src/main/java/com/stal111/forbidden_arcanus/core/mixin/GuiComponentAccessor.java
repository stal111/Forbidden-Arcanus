package com.stal111.forbidden_arcanus.core.mixin;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.gui.GuiComponent;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiComponent.class)
public interface GuiComponentAccessor {
    @Invoker
    static void callFillGradient(Matrix4f matrix4f, BufferBuilder bufferBuilder, int x1, int y1, int x2, int y2, int blitOffset, int colorFrom, int colorTo) {

    }
}
