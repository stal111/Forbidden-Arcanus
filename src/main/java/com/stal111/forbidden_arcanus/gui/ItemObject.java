package com.stal111.forbidden_arcanus.gui;

import com.stal111.forbidden_arcanus.gui.GuiObject;
import net.minecraft.item.ItemStack;

public class ItemObject extends GuiObject {

    private final ItemStack stack;

    public ItemObject(ItemStack stack, int posX, int posY) {
        super(posX, posY);
        this.stack = stack;
    }

    @Override
    public void render(int x, int y) {
        getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI(stack, getPosX(), getPosY());
    }

    @Override
    public void renderHoverEffect(int x, int y) {
        if (x >= getPosX() && y >= getPosY() && x < getPosX() + 16 && y < getPosY() + 16) {
            getMinecraft().currentScreen.renderTooltip(getMinecraft().currentScreen.getTooltipFromItem(stack), x, y);
        }
    }
}
