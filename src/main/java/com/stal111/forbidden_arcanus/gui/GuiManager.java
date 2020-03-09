package com.stal111.forbidden_arcanus.gui;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    private static List<GuiObject> objects = new ArrayList<>();

    public List<GuiObject> getObjects() {
        return objects;
    }

    public void addGuiObject(GuiObject guiObject) {
        objects.add(guiObject);
    }

    public void removeGuiObject(GuiObject guiObject) {
        objects.remove(guiObject);
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
