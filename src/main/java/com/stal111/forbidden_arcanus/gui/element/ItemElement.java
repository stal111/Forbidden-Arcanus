package com.stal111.forbidden_arcanus.gui.element;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

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
    public void render(MatrixStack matrixStack, int x, int y) {
        getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI(stack, getPosX(), getPosY());
        getMinecraft().getItemRenderer().renderItemOverlays(getMinecraft().fontRenderer, stack, getPosX(), getPosY());
    }

    @Override
    public void renderHoverEffect(MatrixStack matrixStack, int x, int y) {
        if (isMouseOver(x, y)) {
            renderFancyTooltip(matrixStack, Lists.transform(getCurrentScreen().getTooltipFromItem(stack), ITextComponent::func_241878_f), x, y);
        }
    }
}
