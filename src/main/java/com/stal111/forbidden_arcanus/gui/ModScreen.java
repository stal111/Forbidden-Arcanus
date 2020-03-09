package com.stal111.forbidden_arcanus.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class ModScreen extends Screen {

    public final GuiManager manager = new GuiManager();

    protected ModScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public void render(int x, int y, float p_render_3_) {
        manager.renderAll(x, y);
        manager.renderHoverEffectAll(x, y);
        super.render(x, y, p_render_3_);
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_mouseClicked_5_) {
        manager.onClicked(x, y);
        return super.mouseClicked(x, y, p_mouseClicked_5_);
    }
}
