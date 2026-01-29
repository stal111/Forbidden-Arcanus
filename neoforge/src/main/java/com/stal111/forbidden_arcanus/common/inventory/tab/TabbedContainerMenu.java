package com.stal111.forbidden_arcanus.common.inventory.tab;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import org.jspecify.annotations.Nullable;

import java.util.List;

public abstract class TabbedContainerMenu extends AbstractContainerMenu {

    private final List<ContainerTab> tabs;

    private final DataSlot activeTab = DataSlot.standalone();

    protected TabbedContainerMenu(@Nullable MenuType<?> menuType, int containerId, ContainerTab... tabs) {
        super(menuType, containerId);
        this.tabs = List.of(tabs);

        this.addDataSlot(this.activeTab);
        this.activeTab.set(0);
    }

    public void setActiveTab(ContainerTab tab) {
        this.activeTab.set(this.tabs.indexOf(tab));
    }

    public ContainerTab getActiveTab() {
        return this.tabs.get(this.activeTab.get());
    }
}
