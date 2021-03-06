package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.gui.ModScreen;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.ImageElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.RecipePreviewElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button.ChangeCategoryButton;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button.ChangePageButton;
import com.stal111.forbidden_arcanus.item.ForbiddenmiconItem;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;
import java.util.List;

public class ForbiddenmiconScreen extends ModScreen {

    public static final ResourceLocation FORBIDDENMICON_GUI_TEXTURES = ModUtils.location("textures/gui/forbiddenmicon.png");

    private int currentIndex = 0;

    public ForbiddenmiconEntry entry;

    private List<IReorderingProcessor> cachedPageLines = Collections.emptyList();

    private RecipePreviewElement recipePreview;

    public static ForbiddenmiconScreen INSTANCE;

    private final ItemStack stack;

    private ForbiddenmiconCategory activeCategory;

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
        this.recipePreview = new RecipePreviewElement(manager, this.width / 2 - 116, 126, this.getBlitOffset(), this.font);
        if (entry != null) {
            this.recipePreview.setEntry(entry);
        }
        int i = 56;
        int leftSideCount = 0;
        getElements().removeIf(guiObject -> guiObject instanceof ChangeCategoryButton);
        for (ForbiddenmiconCategory category : ForbiddenmiconCategory.values()) {
            addElement(new ChangeCategoryButton(this.width / 2 - 140, i, getBlitOffset(), category, leftSideCount <= 4, buttonObject -> {
                getElements().forEach(guiObject -> {
                    if (guiObject instanceof ChangeCategoryButton) {
                        if (((ChangeCategoryButton) guiObject).getCategory() != category) {
                            ((ChangeCategoryButton) guiObject).setActivated(false);
                        }
                    }
                });

                buttonObject.setActivated(true);
                activeCategory = category;
                currentIndex = 0;
                initEntry();
                recipePreview.clearEntry();
                if (entry != null) {
                    recipePreview.setEntry(entry);
                }
            }));
            i += 17;
            leftSideCount++;
        }
        getElements().forEach(guiObject -> {
            if (guiObject instanceof ChangeCategoryButton) {
                if (((ChangeCategoryButton) guiObject).getCategory() == ForbiddenmiconCategory.MAIN) {
                    ((ChangeCategoryButton) guiObject).setActivated(true);
                }
            }
        });
    }

    private void initEntry() {
        getElements().removeIf(guiObject -> guiObject instanceof ImageElement);

        if (ForbiddenArcanus.PAGE_LOADER.getEntries(activeCategory == null ? ForbiddenmiconCategory.MAIN : activeCategory).size() != 0) {
            this.entry = (ForbiddenmiconEntry) ForbiddenArcanus.PAGE_LOADER.getEntries(activeCategory == null ? ForbiddenmiconCategory.MAIN : activeCategory).toArray()[currentIndex];
           // this.cachedPageLines = RenderComponentsUtil.splitText(new StringTextComponent(entry.getDescription()), 130, this.field_230712_o_, true, true);

            addElement(new ImageElement(this.width / 2 - 116, 61));
        } else {
            this.entry = null;
        }
    }

    protected void addDoneButton() {
        this.addButton(new Button(this.width / 2 - 100, 230, 200, 20, new TranslationTextComponent("gui.done"), (p_214161_1_) -> {
            this.onClose();
        }));
    }

    private void addChangePageButtons() {
        int i = this.width / 2;
        addElement(new ChangePageButton(i + 105, 197, getBlitOffset(), 80, 240, buttonObject -> {
            if (ForbiddenArcanus.PAGE_LOADER.getEntries(activeCategory).toArray().length > currentIndex + 1) {
                this.currentIndex++;
                this.initEntry();
                this.recipePreview.setEntry(entry);
            }
        }));

        addElement(new ChangePageButton(i - 117, 197, getBlitOffset(), 59, 240, buttonObject -> {
            if (currentIndex - 1 >= 0) {
                this.currentIndex--;
                this.initEntry();
                this.recipePreview.setEntry(entry);
            }
        }));
    }

    public void addTitle(MatrixStack matrixStack) {
        int i = this.width / 2;
        if (entry.hasTitle()) {
            RenderSystem.pushMatrix();
            blit(matrixStack, i - 120, 42, this.getBlitOffset(), 272, 7, 116, 18, 256, 512);
            RenderSystem.scalef(0.77F, 0.77F, 1.0F);
            this.font.drawString(matrixStack, entry.getTitle().getString(), ((((i - 60) * 1.29F)) - this.font.getStringWidth(entry.getTitle().getString()) / 2F), 48 * 1.27F, 0xFFFFFF);
            RenderSystem.popMatrix();
        }
    }

    public void addImage(MatrixStack matrixStack) {
        int i = (int) (this.width / 3.15);
        if (entry.hasTitle()) {
            RenderSystem.pushMatrix();
            blit(matrixStack, i, 60, this.getBlitOffset(), 274, 28, 113, 65, 256, 512);
            RenderSystem.popMatrix();
        }
    }

    public void addText(MatrixStack matrixStack) {
        int i = this.width / 2;
        if (entry.hasTitle()) {
            RenderSystem.pushMatrix();
            blit(matrixStack, i + 10, 62, this.getBlitOffset(), 394, 8, 108, 131, 256, 512);
            RenderSystem.scalef(0.7F, 0.7F, 1.0F);
            if (entry.hasDescription()) {
                int k = Math.min(128 / 9, this.cachedPageLines.size());
                for(int l = 0; l < k; ++l) {
                    IReorderingProcessor iTextProperties = this.cachedPageLines.get(l);
                    this.font.func_238422_b_(matrixStack, iTextProperties, (i + 14) * 1.428F, (64 * 1.45F) + l * 9, 0);
                }
            } else {
                this.font.drawString(matrixStack, "Lorem ipsum dolor", (i + 14) * 1.428F, 64 * 1.45F, 0xFFFFFF);
            }
            RenderSystem.popMatrix();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float p_render_3_) {
        this.renderBackground(matrixStack);
        RenderSystem.pushMatrix();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.getMinecraft().getTextureManager().bindTexture(FORBIDDENMICON_GUI_TEXTURES);
        int i = this.width / 2 - 132;
        blit(matrixStack, i, 32, this.getBlitOffset(), 0, 0, 264, 186, 256, 512);

        RenderSystem.popMatrix();

        if (entry != null) {
            addTitle(matrixStack);

            RenderSystem.pushMatrix();
            this.getMinecraft().getTextureManager().bindTexture(FORBIDDENMICON_GUI_TEXTURES);
            addText(matrixStack);

            if (recipePreview != null) {
                recipePreview.render(matrixStack, x, y);
            }
            RenderSystem.popMatrix();
        }
        super.render(matrixStack, x, y , p_render_3_);
    }

    @Override
    public String getName() {
        return "forbiddenmicon";
    }

    @Override
    public void onClose() {
        if (stack.getItem() instanceof ForbiddenmiconItem) {
            ForbiddenmiconItem.setOpen(stack, false);
        }
        super.onClose();
    }
}
