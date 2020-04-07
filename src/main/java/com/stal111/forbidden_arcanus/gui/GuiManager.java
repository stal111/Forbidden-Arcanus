package com.stal111.forbidden_arcanus.gui;

import com.stal111.forbidden_arcanus.gui.element.GuiElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager {

    private static Map<String, List<GuiElement>> objects = new HashMap<>();

    public List<GuiElement> getObjects(String identifier) {
        objects.computeIfAbsent(identifier, k -> new ArrayList<>());

        return objects.get(identifier);
    }

    public void addGuiObject(String identifier, GuiElement guiElement) {
        objects.computeIfAbsent(identifier, k -> new ArrayList<>());

        objects.get(identifier).add(guiElement);
    }

    public void removeGuiObject(String identifier, GuiElement guiElement) {
        objects.computeIfAbsent(identifier, k -> new ArrayList<>());

        objects.get(identifier).remove(guiElement);
        if (objects.get(identifier).size() == 0) {
            objects.remove(identifier);
        }
    }

    public void renderAll(int x, int y) {
        objects.forEach((s, guiElements) -> guiElements.forEach(element -> element.render(x, y)));
    }

    public void renderHoverEffectAll(int x, int y) {
        objects.forEach((s, guiElements) -> guiElements.forEach(element -> element.renderHoverEffect(x, y)));

    }

    public void onClicked(double x, double y) {
        objects.forEach((s, guiElements) -> {
            for (int i = 0; i < getObjects(s).size(); i++) {
                getObjects(s).get(i).onClicked(x, y);
            }
        });
    }
}