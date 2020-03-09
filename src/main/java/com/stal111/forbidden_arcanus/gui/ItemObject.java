package com.stal111.forbidden_arcanus.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemObject extends GuiObject {

    private final ItemStack stack;

    public ItemObject(ItemStack stack, int posX, int posY) {
        super(posX, posY, 16, 16);
        this.stack = stack;
    }

    @Override
    public void render(int x, int y) {
        getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI(stack, getPosX(), getPosY());
        getMinecraft().getItemRenderer().renderItemOverlays(getMinecraft().fontRenderer, stack, getPosX(), getPosY());
    }

    @Override
    public void renderHoverEffect(int x, int y) {
        if (isMouseOver(x, y)) {
            renderFancyTooltip(getMinecraft().currentScreen.getTooltipFromItem(stack), x, y);
        }
    }
}
