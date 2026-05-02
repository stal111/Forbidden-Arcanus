package com.stal111.forbidden_arcanus.common.integration.hephaestus_forge;

import com.stal111.forbidden_arcanus.client.gui.screen.HephaestusForgeScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

import java.util.List;

public class HephaestusForgeGuiContainerHandler implements IGuiContainerHandler<HephaestusForgeScreen> {

    @Override
    public List<Rect2i> getGuiExtraAreas(HephaestusForgeScreen containerScreen) {
        return List.of(
                new Rect2i(containerScreen.getLeftPos() - 26, containerScreen.getTopPos() + 16, 29, 51),
                new Rect2i(containerScreen.getLeftPos() + 172, containerScreen.getTopPos() + 16, 29, 51)
        );
    }
}
