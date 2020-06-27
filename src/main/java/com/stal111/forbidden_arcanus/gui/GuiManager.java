package com.stal111.forbidden_arcanus.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.gui.element.GuiElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager {

    private static Map<String, List<GuiElement>> objects = new HashMap<>();

    public List<GuiElement> getElements(String identifier) {
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

    public void clear() {
        for (Map.Entry<String, List<GuiElement>> entry : objects.entrySet()) {
            entry.getValue().clear();
        }
        objects.clear();
    }

    public void renderAll(MatrixStack matrixStack, int x, int y) {
        objects.forEach((s, guiElements) -> guiElements.forEach(element -> element.render(matrixStack, x, y)));
    }

    public void renderHoverEffectAll(MatrixStack matrixStack, int x, int y) {
        objects.forEach((s, guiElements) -> guiElements.forEach(element -> element.renderHoverEffect(matrixStack, x, y)));

    }

    public void onClicked(double x, double y) {
        for (Map.Entry<String, List<GuiElement>> entry : objects.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                entry.getValue().get(i).onClicked(x, y);
            }
        }
    }
}