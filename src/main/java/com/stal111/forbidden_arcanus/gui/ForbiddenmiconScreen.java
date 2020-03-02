package com.stal111.forbidden_arcanus.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.RecipePreviewObject;
import com.stal111.forbidden_arcanus.item.ForbiddenmiconItem;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.*;

public class ForbiddenmiconScreen extends Screen {

    public static final ResourceLocation FORBIDDENMICON_GUI_TEXTURES = ModUtils.location("textures/gui/forbiddenmicon.png");

    private int currentIndex = 0;

    public ForbiddenmiconEntry entry;

    private List<ITextComponent> cachedPageLines = Collections.emptyList();

    private ForbiddenmiconChangePageButton buttonNextPage;
    private ForbiddenmiconChangePageButton buttonPreviousPage;

    private RecipePreviewObject recipePreview;

    public static ForbiddenmiconScreen INSTANCE;

    private final ItemStack stack;

    public ForbiddenmiconScreen(ItemStack stack) {
        super(new TranslationTextComponent("forbiddenmicon.title"));
        INSTANCE = this;
        this.stack = stack;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        this.initEntry();
        this.addDoneButton();
        this.addChangePageButtons();
       // this.addCraftingPreviewButtons();
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_mouseClicked_5_) {
        if (entry != null) {
            for (int i = 0; i < GuiObject.getObjects().size(); i++) {
                GuiObject.getObjects().get(i).onClicked(x, y);
            }
        }
        return super.mouseClicked(x, y, p_mouseClicked_5_);
    }

    private void initEntry() {
        this.entry = (ForbiddenmiconEntry) Main.PAGE_LOADER.getEntries().toArray()[currentIndex];
        this.cachedPageLines = RenderComponentsUtil.splitText(new StringTextComponent(entry.getDescription()), 130, this.font, true, true);
        this.initRecipePreview();
    }

    private void initRecipePreview() {
        this.recipePreview = new RecipePreviewObject(this.width / 2 - 116, 125, this.getBlitOffset());
        this.recipePreview.init();
    }

    protected void addDoneButton() {
        this.addButton(new Button(this.width / 2 - 100, 230, 200, 20, new TranslationTextComponent("gui.done").getFormattedText(), (p_214161_1_) -> {
            this.minecraft.displayGuiScreen(null);
        }));
    }

    private void addChangePageButtons() {
        int i = this.width / 2;
        this.buttonNextPage = this.addButton(new ForbiddenmiconChangePageButton(i + 105, 197, true, (p_214159_1_) -> {
            if (Main.PAGE_LOADER.getEntries().toArray().length > currentIndex + 1) {
                this.currentIndex++;
                this.initEntry();
                if (entry.hasRecipe()) {
                    if (entry.getRecipes() != null && entry.getSmeltingRecipes() != null) {
                        this.recipePreview.craftingButton.setActivated(true);
                        this.recipePreview.smeltingButton.setActivated(false);
                    } else if (entry.getRecipes() == null & entry.getSmeltingRecipes() != null) {
                        this.recipePreview.smeltingButton.setActivated(true);
                    } else {
                        this.recipePreview.craftingButton.setActivated(true);
                    }
                    recipePreview.initRecipe();
                    recipePreview.initChangeRecipeButtons();
                }
                this.recipePreview.currentRecipe = 0;
            }
        }, false));
        this.buttonPreviousPage = this.addButton(new ForbiddenmiconChangePageButton(i - 118, 197, false, (p_214158_1_) -> {
            if (currentIndex - 1 >= 0) {
                this.currentIndex--;
                this.initEntry();
                if (entry.hasRecipe()) {
                    if (entry.getRecipes() != null && entry.getSmeltingRecipes() != null) {
                        this.recipePreview.craftingButton.setActivated(true);
                        this.recipePreview.smeltingButton.setActivated(false);
                    } else if (entry.getRecipes() == null & entry.getSmeltingRecipes() != null) {
                        this.recipePreview.smeltingButton.setActivated(true);
                    } else {
                        this.recipePreview.craftingButton.setActivated(true);
                    }
                    recipePreview.initRecipe();
                    recipePreview.initChangeRecipeButtons();
                }
                this.recipePreview.currentRecipe = 0;
            }
        }, false));
    }

    public void addTitle() {
        int i = this.width / 2;
        if (entry.hasTitle()) {
            blit(i - 120, 42, this.getBlitOffset(), 272, 7, 116, 18, 256, 512);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(0.77F, 0.77F, 1.0F);
            this.font.drawString(entry.getTitle(), ((((i - 60) * 1.29F)) - this.font.getStringWidth(entry.getTitle()) / 2F), 48 * 1.27F, 0xFFFFFF);
            RenderSystem.popMatrix();
        }
    }

    public void addImage() {
        int i = (int) (this.width / 3.15);
        if (entry.hasTitle()) {
            blit(i, 60, this.getBlitOffset(), 274, 28, 113, 65, 256, 512);
        }
    }

    public void addText() {
        int i = this.width / 2;
        if (entry.hasTitle()) {
            blit(i + 10, 62, this.getBlitOffset(), 394, 8, 108, 131, 256, 512);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(0.7F, 0.7F, 1.0F);
            if (entry.hasDescription()) {
                int k = Math.min(128 / 9, this.cachedPageLines.size());
                for(int l = 0; l < k; ++l) {
                    ITextComponent itextcomponent1 = this.cachedPageLines.get(l);
                    this.font.drawString(itextcomponent1.applyTextStyle(TextFormatting.WHITE).getFormattedText(), (i + 14) * 1.428F, (64 * 1.45F) + l * 9, 0);
                }
            } else {
                this.font.drawString("Lorem ipsum dolor", (i + 14) * 1.428F, 64 * 1.45F, 0xFFFFFF);
            }
            RenderSystem.popMatrix();
        }
    }

    @Override
    public void render(int x, int y, float p_render_3_) {
        this.renderBackground();
        RenderSystem.pushMatrix();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.minecraft.getTextureManager().bindTexture(FORBIDDENMICON_GUI_TEXTURES);
        int i = this.width / 2 - 132;
        blit(i, 32, this.getBlitOffset(), 0, 0, 264, 186, 256, 512);

        addTitle();
        RenderSystem.popMatrix();
        this.minecraft.getTextureManager().bindTexture(FORBIDDENMICON_GUI_TEXTURES);

        addImage();
        addText();

        if (recipePreview != null) {
            recipePreview.render(x, y);
        }

        GuiObject.getObjects().forEach(guiObject -> guiObject.render(x, y));
        GuiObject.getObjects().forEach(guiObject -> guiObject.renderHoverEffect(x, y));
        super.render(x, y , p_render_3_);
    }

    @Override
    public void onClose() {
        if (stack.getItem() instanceof ForbiddenmiconItem) {
            ForbiddenmiconItem.setOpen(stack, false);
        }
        super.onClose();
    }
}
