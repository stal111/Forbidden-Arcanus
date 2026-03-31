package com.stal111.forbidden_arcanus.client.gui.screen.wand;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.tab.AbstractTab;
import com.stal111.forbidden_arcanus.client.gui.components.tab.ScreenAccess;
import com.stal111.forbidden_arcanus.client.gui.screen.animation.ScreenAnimation;
import com.stal111.forbidden_arcanus.common.inventory.wand.WandDeskMenu;
import com.stal111.forbidden_arcanus.common.network.serverbound.CraftWandPayload;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class EditWandTab extends AbstractTab {

    public static final Identifier BACKGROUND = ForbiddenArcanus.identifier("textures/gui/container/wand_desk_edit.png");

    private static final WidgetSprites CREATE_SPRITES = new WidgetSprites(ForbiddenArcanus.identifier("container/wand_desk/create_button"), ForbiddenArcanus.identifier("container/wand_desk/create_button_disabled"), ForbiddenArcanus.identifier("container/wand_desk/create_button_highlighted"));
    private static final Component CREATE_COMPONENT = Component.translatable("container.forbidden_arcanus.wand_desk.create");

    private final WandDeskMenu menu;

    private ImageButton createButton;
    private ScreenAnimation createAnimation;

    protected EditWandTab(WandDeskMenu menu) {
        this.menu = menu;
    }

    @Override
    public void init(ScreenAccess screen) {
        this.createButton = new ImageButton(screen.getGuiLeft() + 146, screen.getGuiTop() + 106, 20, 20, CREATE_SPRITES, _ -> {
            ClientPacketDistributor.sendToServer(CraftWandPayload.INSTANCE);

            this.createAnimation.start();
        }, CREATE_COMPONENT);
        this.createButton.setTooltip(Tooltip.create(CREATE_COMPONENT));

        this.createButton.active = this.menu.canCraftWand();

        this.createAnimation = new ScreenAnimation(screen.getGuiLeft() + 24, screen.getGuiTop() + 12, 128, 128, ForbiddenArcanus.identifier("container/wand_desk/animation/wand_creation"), 11, 1.7F);

        screen.addRenderableWidget(this.createButton);
    }

    @Override
    public void tick() {
        this.createButton.active = this.menu.canCraftWand();
        this.createAnimation.tick();
    }

    @Override
    public void renderBg(ScreenAccess screen, GuiGraphicsExtractor guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, screen.getGuiLeft(), screen.getGuiTop(), 0, 0, screen.getWidth(), screen.getHeight(), 256, 256);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double mouseX, double mouseY) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return false;
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
        return false;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        this.createAnimation.extractRenderState(graphics, mouseX, mouseY, partialTicks);
    }
}
