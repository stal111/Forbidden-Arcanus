package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.stal111.forbidden_arcanus.gui.element.GuiElement;

import java.util.Arrays;
import java.util.List;

public enum ForbiddenmiconCategory {
    MAIN(8, 187),
    ITEMS(8, 196),
    BLOCKS(8, 204);

    private final int startX;
    private final int startY;
    private final List<GuiElement> elements;

    ForbiddenmiconCategory(int startX, int startY, GuiElement... elements) {
        this.startX = startX;
        this.startY = startY;
        this.elements = Arrays.asList(elements);
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public List<GuiElement> getElements() {
        return elements;
    }
}
