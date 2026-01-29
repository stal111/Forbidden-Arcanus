package com.stal111.forbidden_arcanus.client.gui.screen.wand;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.tab.ContainerTabButton;
import com.stal111.forbidden_arcanus.client.gui.screen.TabbedContainerScreen;
import com.stal111.forbidden_arcanus.common.inventory.wand.WandDeskMenu;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class WandDeskScreen extends TabbedContainerScreen<WandDeskMenu> {

    public static final Identifier SPRITE_SLOT_HIGHLIGHT = ForbiddenArcanus.identifier("container/slot/slot_highlight");

    public WandDeskScreen(WandDeskMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 231);
        this.addTabFactory(WandDeskMenu.EDIT_WAND, ContainerTabButton.Type.PURPLE, ForbiddenArcanus.identifier("icon/magic_wand"), () -> new EditWandTab(this.getMenu()));
        this.addTabFactory(WandDeskMenu.EDIT_SPELLS, ContainerTabButton.Type.PURPLE, ForbiddenArcanus.identifier("icon/spell_seal"), EditSpellsTab::new);
    }

    @Override
    public ScreenPosition getTabButtonPosition() {
        return new ScreenPosition(this.getGuiLeft() + 175, this.getGuiTop() + 20);
    }

    @Override
    protected void handleSlotStateChanged(int slotId, int containerId, boolean newState) {
        super.handleSlotStateChanged(slotId, containerId, newState);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        if (this.hoveredSlot instanceof WandDeskMenu.InputSlot inputSlot && !inputSlot.hasItem()) {
            guiGraphics.setTooltipForNextFrame(this.font, this.font.split(inputSlot.getOnboardingTooltip(), 115), mouseX, mouseY);
        }
    }

    @Override
    protected void renderSlot(GuiGraphics graphics, Slot slot, int mouseX, int mouseY) {
        if (slot.getItem().has(ModDataComponents.PROVIDES_WAND_MATERIAL)) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SPRITE_SLOT_HIGHLIGHT, slot.x, slot.y, 16, 16);
        }

        super.renderSlot(graphics, slot, mouseX, mouseY);
    }
}
