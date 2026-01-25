package com.stal111.forbidden_arcanus.client.gui.screen.wand;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.screen.animation.ScreenAnimation;
import com.stal111.forbidden_arcanus.common.inventory.wand.WandDeskMenu;
import com.stal111.forbidden_arcanus.common.network.serverbound.CraftWandPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class WandDeskScreen extends AbstractContainerScreen<WandDeskMenu> {

    public static final Identifier BACKGROUND = ForbiddenArcanus.identifier("textures/gui/container/wand_desk_edit.png");
    private static final WidgetSprites CREATE_SPRITES = new WidgetSprites(ForbiddenArcanus.identifier("container/wand_desk/create_button"), ForbiddenArcanus.identifier("container/wand_desk/create_button_disabled"), ForbiddenArcanus.identifier("container/wand_desk/create_button_highlighted"));

    private WandDeskMenu menu;
    private ImageButton createButton;
    private ScreenAnimation createAnimation;

    public WandDeskScreen(WandDeskMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 231);
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();

        this.createButton = new ImageButton(this.getGuiLeft() + 146, this.getGuiTop() + 106, 20, 20, CREATE_SPRITES, button -> {
            ClientPacketDistributor.sendToServer(CraftWandPayload.INSTANCE);

            this.createAnimation.start();
        }, CommonComponents.GUI_YES);

        this.createButton.active = this.menu.canCraftWand();

        this.createAnimation = new ScreenAnimation(this.getGuiLeft() + 24, this.getGuiTop() + 12, 128, 128, ForbiddenArcanus.identifier("container/wand_desk/animation/wand_creation"), 12, 1.7F);

        this.addRenderableWidget(this.createButton);
        this.addRenderableOnly(this.createAnimation);
    }

    @Override
    protected void containerTick() {
        this.createButton.active = this.menu.canCraftWand();
        this.createAnimation.tick();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float a, int xm, int ym) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.getXSize(), this.getYSize(), 256, 256);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        if (this.hoveredSlot instanceof WandDeskMenu.InputSlot inputSlot && !inputSlot.hasItem()) {
            guiGraphics.setTooltipForNextFrame(this.font, this.font.split(inputSlot.getOnboardingTooltip(), 115), mouseX, mouseY);
        }
    }
}
