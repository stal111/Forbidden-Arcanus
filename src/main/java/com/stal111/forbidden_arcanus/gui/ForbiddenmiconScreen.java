package com.stal111.forbidden_arcanus.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ForbiddenmiconScreen extends Screen {

    private int currPage = 3;

    private ForbiddenmiconChangePageButton buttonNextPage;
    private ForbiddenmiconChangePageButton buttonPreviousPage;

    protected static final ResourceLocation FORBIDDENMICON_GUI_TEXTURES = ModUtils.location("textures/gui/forbiddenmicon.png");

    public ForbiddenmiconScreen() {
        super(new TranslationTextComponent("forbiddenmicon.title"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        this.addDoneButton();
        this.addChangePageButtons();
    }

    protected void addDoneButton() {
        this.addButton(new Button(this.width / 2 - 100, 196 + 50, 200, 20, new TranslationTextComponent("gui.done").getFormattedText(), (p_214161_1_) -> {
            this.minecraft.displayGuiScreen(null);
        }));
    }

    private void addChangePageButtons() {
        int i = (this.width - 192) / 2;
        this.buttonNextPage = this.addButton(new ForbiddenmiconChangePageButton(i + 330, 230, true, (p_214159_1_) -> {
            System.out.println("PRESSED NEXT BUTTON");
        }, false));
        this.buttonPreviousPage = this.addButton(new ForbiddenmiconChangePageButton(i + 10, 230, false, (p_214158_1_) -> {
            System.out.println("PRESSED BACK BUTTON");
        }, false));
        this.updateButtons();
    }

    private void updateButtons() {
        this.buttonNextPage.visible = this.currPage < 6 - 1;
        this.buttonPreviousPage.visible = this.currPage > 0;
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        RenderSystem.pushMatrix();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.scalef(1.25F, 1.25F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(FORBIDDENMICON_GUI_TEXTURES);
        int i = (this.width - 263) / 3;
        blit(i, 2, this.getBlitOffset(), 0, 0, 263, 181, 256, 512);
        RenderSystem.popMatrix();
        super.render(p_render_1_, p_render_2_ , p_render_3_);
    }
}
