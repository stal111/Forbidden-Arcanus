package com.stal111.forbidden_arcanus.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.gui.element.GuiElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public abstract class ModScreen extends Screen {

    public final GuiManager manager = new GuiManager();

    protected ModScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public void func_230430_a_(MatrixStack matrixStack, int x, int y, float p_render_3_) {
        manager.renderAll(matrixStack, x, y);
        manager.renderHoverEffectAll(matrixStack, x, y);
        super.func_230430_a_(matrixStack, x, y, p_render_3_);
    }

    @Override
    public boolean func_231044_a_(double x, double y, int p_mouseClicked_5_) {
        manager.onClicked(x, y);
        return super.func_231044_a_(x, y, p_mouseClicked_5_);
    }

    public abstract String getName();

    public List<GuiElement> getElements() {
        return manager.getElements(getName());
    }

    public void addElement(GuiElement element) {
        manager.addGuiObject(getName(), element);
    }

    @Override
    public void func_231175_as__() {
        manager.clear();
        super.func_231175_as__();
    }
}
