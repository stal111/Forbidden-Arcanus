package com.stal111.forbidden_arcanus.client.gui.screen.research;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.screen.research.tab.AbstractTab;
import com.stal111.forbidden_arcanus.client.gui.screen.research.tab.EmptyTab;
import com.stal111.forbidden_arcanus.client.gui.screen.research.tab.ResearchTab;
import com.stal111.forbidden_arcanus.client.gui.screen.research.tab.ResearchTabButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

/**
 * @author stal111
 * @since 15.11.2023
 */
public enum ResearchCategory {
    RESEARCH("research", ResearchTabButton.PURPLE_SPRITES, ResearchTab::new),
    DOCUMENTATION("documentation", ResearchTabButton.PURPLE_SPRITES, EmptyTab::new),
    MAGIC_WAND("magic_wand", ResearchTabButton.RED_SPRITES, EmptyTab::new),
    SPELL_SEAL("spell_seal", ResearchTabButton.RED_SPRITES, EmptyTab::new);

    private final WidgetSprites sprites;

    private final ResourceLocation iconLocation;
    private final TabFactory tabFactory;

    ResearchCategory(String name, WidgetSprites sprites, TabFactory tabFactory) {
        this.sprites = sprites;
        this.iconLocation = ForbiddenArcanus.location("textures/gui/research/icon/" + name + ".png");
        this.tabFactory = tabFactory;
    }

    public WidgetSprites getTabSprites() {
        return this.sprites;
    }

    public ResourceLocation getIconLocation() {
        return this.iconLocation;
    }

    public AbstractTab createTab(int width, int height) {
        return this.tabFactory.create(width, height);
    }

    @FunctionalInterface
    public interface TabFactory {
        AbstractTab create(int width, int height);
    }
}
