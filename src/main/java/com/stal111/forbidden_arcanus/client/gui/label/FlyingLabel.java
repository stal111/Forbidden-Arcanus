package com.stal111.forbidden_arcanus.client.gui.label;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

/**
 * @author stal111
 * @since 08.05.2024
 */
public interface FlyingLabel<R extends HitResult> {

    void render(GuiGraphics guiGraphics, ItemStack stack, float partialTick, int centerX, int centerY, R result);
}
