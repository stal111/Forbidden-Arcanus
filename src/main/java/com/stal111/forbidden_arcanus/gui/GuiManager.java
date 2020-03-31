package com.stal111.forbidden_arcanus.gui;

import com.stal111.forbidden_arcanus.gui.element.GuiElement;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    private static List<GuiElement> objects = new ArrayList<>();

    public List<GuiElement> getObjects() {
        return objects;
    }

    public void addGuiObject(GuiElement guiElement) {
        objects.add(guiElement);
    }

    public void removeGuiObject(GuiElement guiElement) {
        objects.remove(guiElement);
    }

    public void renderAll(int x, int y) {
        objects.forEach(guiObject -> guiObject.render(x, y));
    }

    public void renderHoverEffectAll(int x, int y) {
        objects.forEach(guiObject -> guiObject.renderHoverEffect(x, y));
    }

    public void onClicked(double x, double y) {
        for (int i = 0; i < getObjects().size(); i++) {
            getObjects().get(i).onClicked(x, y);
        }
    }
}
