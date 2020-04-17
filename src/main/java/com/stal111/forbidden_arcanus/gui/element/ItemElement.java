package com.stal111.forbidden_arcanus.gui.element;

import net.minecraft.item.ItemStack;

public class ItemElement extends GuiElement {

    private final ItemStack stack;

    public ItemElement(ItemStack stack, int posX, int posY) {
        super(posX, posY, 16, 16);
        this.stack = stack;
    }

    @Override
    public String getName() {
        return "item";
    }

    @Override
    public void render(int x, int y) {
        getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI(stack, getPosX(), getPosY());
        getMinecraft().getItemRenderer().renderItemOverlays(getMinecraft().fontRenderer, stack, getPosX(), getPosY());
    }

    @Override
    public void renderHoverEffect(int x, int y) {
        if (isMouseOver(x, y)) {
            renderFancyTooltip(getCurrentScreen().getTooltipFromItem(stack), x, y);
        }
    }
}
