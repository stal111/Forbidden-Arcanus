package com.stal111.forbidden_arcanus.client.gui.screen;

import com.stal111.forbidden_arcanus.client.gui.components.tab.AbstractTab;
import com.stal111.forbidden_arcanus.client.gui.components.tab.ContainerTabButton;
import com.stal111.forbidden_arcanus.client.gui.components.tab.ScreenAccess;
import com.stal111.forbidden_arcanus.common.inventory.tab.ContainerTab;
import com.stal111.forbidden_arcanus.common.inventory.tab.TabbedContainerMenu;
import com.stal111.forbidden_arcanus.common.network.serverbound.ChangeTabPayload;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class TabbedContainerScreen<T extends TabbedContainerMenu> extends AbstractContainerScreen<T> {

    private final Map<ContainerTab, Supplier<AbstractTab>> tabs = new LinkedHashMap<>();
    private final Map<ContainerTab, TabButtonProperties> tabButtonProperties = new LinkedHashMap<>();
    private final Map<ContainerTab, ContainerTabButton> tabButtons = new LinkedHashMap<>();

    private ContainerTab previousContainerTab = this.menu.getActiveTab();
    private AbstractTab activeTab;

    private final ScreenAccess screenAccess = new ScreenAccess() {
        @Override
        public int getGuiLeft() {
            return TabbedContainerScreen.this.getGuiLeft();
        }

        @Override
        public int getGuiTop() {
            return TabbedContainerScreen.this.getGuiTop();
        }

        @Override
        public int getWidth() {
            return TabbedContainerScreen.this.getXSize();
        }

        @Override
        public int getHeight() {
            return TabbedContainerScreen.this.getYSize();
        }

        @Override
        public <W extends GuiEventListener & Renderable & NarratableEntry> W addRenderableWidget(W widget) {
            return TabbedContainerScreen.this.addRenderableWidget(widget);
        }

        @Override
        public <W extends Renderable> W addRenderableOnly(W renderable) {
            return TabbedContainerScreen.this.addRenderableOnly(renderable);
        }
    };

    public TabbedContainerScreen(T menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

//        this.tabManager = new ContainerTabManager(this.getTabConfiguration().tabs().getFirst().create(), tab -> this.rebuildWidgets());
    }

    public TabbedContainerScreen(T menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
        super(menu, inventory, title, imageWidth, imageHeight);
    }

    public void addTabFactory(ContainerTab tab, ContainerTabButton.Type type, Identifier icon, Supplier<AbstractTab> tabSupplier) {
        this.tabs.put(tab, tabSupplier);
        this.tabButtonProperties.put(tab, new TabButtonProperties(type, icon));
    }

    private void setTab(ContainerTab tab) {
        this.previousContainerTab = tab;
        this.activeTab = this.tabs.get(tab).get();

        this.rebuildWidgets();

        this.tabButtons.get(tab).setSelected(true);
    }

    @Override
    protected void init() {
        super.init();

        int yOffset = 0;

        ScreenPosition tabButtonPosition = this.getTabButtonPosition();

        for (Map.Entry<ContainerTab, TabButtonProperties> entry : this.tabButtonProperties.entrySet()) {
            ContainerTabButton button = new ContainerTabButton(tabButtonPosition.x(), tabButtonPosition.y() + yOffset, 35, 27, entry.getValue().type(), entry.getValue().icon(), () -> {
                this.setTab(entry.getKey());

                ClientPacketDistributor.sendToServer(new ChangeTabPayload(entry.getKey()));
            });

            this.addRenderableWidget(button);
            this.tabButtons.put(entry.getKey(), button);

            yOffset += 28;
        }

        if (this.activeTab == null) {
            this.setTab(this.menu.getActiveTab());
        }

        this.activeTab.init(this.screenAccess);
    }

    @Override
    protected void containerTick() {
        if (this.previousContainerTab != this.menu.getActiveTab()) {
            this.setTab(this.previousContainerTab);
        }

        this.activeTab.tick();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        this.activeTab.renderBg(this.screenAccess, graphics, partialTicks, mouseX, mouseY);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractContents(graphics, mouseX, mouseY, a);

        this.activeTab.extractRenderState(graphics, mouseX, mouseY, a);
    }

    public abstract ScreenPosition getTabButtonPosition();

    public record TabButtonProperties(ContainerTabButton.Type type, Identifier icon) {
    }
}
