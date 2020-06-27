package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.gui.ModScreen;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.ImageElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.RecipePreviewElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button.ChangeCategoryButton;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button.ChangePageButton;
import com.stal111.forbidden_arcanus.item.ForbiddenmiconItem;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;

import java.util.Collections;
import java.util.List;

public class ForbiddenmiconScreen extends ModScreen {

    public static final ResourceLocation FORBIDDENMICON_GUI_TEXTURES = ModUtils.location("textures/gui/forbiddenmicon.png");

    private int currentIndex = 0;

    public ForbiddenmiconEntry entry;

    private List<ITextComponent> cachedPageLines = Collections.emptyList();

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
    protected void func_231160_c_() {
        this.initEntry();
        this.addDoneButton();
        this.addChangePageButtons();
        this.recipePreview = new RecipePreviewElement(manager, this.field_230708_k_ / 2 - 116, 126, this.func_230927_p_(), this.field_230712_o_);
        if (entry != null) {
            this.recipePreview.setEntry(entry);
        }
        int i = 56;
        int leftSideCount = 0;
        getElements().removeIf(guiObject -> guiObject instanceof ChangeCategoryButton);
        for (ForbiddenmiconCategory category : ForbiddenmiconCategory.values()) {
            addElement(new ChangeCategoryButton(this.field_230708_k_ / 2 - 140, i, func_230927_p_(), category, leftSideCount <= 4, buttonObject -> {
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

        if (Main.PAGE_LOADER.getEntries(activeCategory == null ? ForbiddenmiconCategory.MAIN : activeCategory).size() != 0) {
            this.entry = (ForbiddenmiconEntry) Main.PAGE_LOADER.getEntries(activeCategory == null ? ForbiddenmiconCategory.MAIN : activeCategory).toArray()[currentIndex];
           // this.cachedPageLines = RenderComponentsUtil.splitText(new StringTextComponent(entry.getDescription()), 130, this.field_230712_o_, true, true);

            addElement(new ImageElement(this.field_230708_k_ / 2 - 116, 61));
        } else {
            this.entry = null;
        }
    }

    protected void addDoneButton() {
        this.func_230480_a_(new Button(this.field_230708_k_ / 2 - 100, 230, 200, 20, new TranslationTextComponent("gui.done"), (p_214161_1_) -> {
            this.func_231175_as__();
        }));
    }

    private void addChangePageButtons() {
        int i = this.field_230708_k_ / 2;
        addElement(new ChangePageButton(i + 105, 197, func_230927_p_(), 80, 240, buttonObject -> {
            if (Main.PAGE_LOADER.getEntries(activeCategory).toArray().length > currentIndex + 1) {
                this.currentIndex++;
                this.initEntry();
                this.recipePreview.setEntry(entry);
            }
        }));

        addElement(new ChangePageButton(i - 117, 197, func_230927_p_(), 59, 240, buttonObject -> {
            if (currentIndex - 1 >= 0) {
                this.currentIndex--;
                this.initEntry();
                this.recipePreview.setEntry(entry);
            }
        }));
    }

    public void addTitle(MatrixStack matrixStack) {
        int i = this.field_230708_k_ / 2;
        if (entry.hasTitle()) {
            RenderSystem.pushMatrix();
            func_238464_a_(matrixStack, i - 120, 42, this.func_230927_p_(), 272, 7, 116, 18, 256, 512);
            RenderSystem.scalef(0.77F, 0.77F, 1.0F);
            this.field_230712_o_.func_238421_b_(matrixStack, entry.getTitle().getString(), ((((i - 60) * 1.29F)) - this.field_230712_o_.getStringWidth(entry.getTitle().getString()) / 2F), 48 * 1.27F, 0xFFFFFF);
            RenderSystem.popMatrix();
        }
    }

    public void addImage(MatrixStack matrixStack) {
        int i = (int) (this.field_230708_k_ / 3.15);
        if (entry.hasTitle()) {
            RenderSystem.pushMatrix();
            func_238464_a_(matrixStack, i, 60, this.func_230927_p_(), 274, 28, 113, 65, 256, 512);
            RenderSystem.popMatrix();
        }
    }

    public void addText(MatrixStack matrixStack) {
        int i = this.field_230708_k_ / 2;
        if (entry.hasTitle()) {
            RenderSystem.pushMatrix();
            func_238464_a_(matrixStack, i + 10, 62, this.func_230927_p_(), 394, 8, 108, 131, 256, 512);
            RenderSystem.scalef(0.7F, 0.7F, 1.0F);
            if (entry.hasDescription()) {
                int k = Math.min(128 / 9, this.cachedPageLines.size());
                for(int l = 0; l < k; ++l) {
                    ITextProperties iTextProperties = this.cachedPageLines.get(l);
                    this.field_230712_o_.func_238422_b_(matrixStack, iTextProperties, (i + 14) * 1.428F, (64 * 1.45F) + l * 9, 0);
                }
            } else {
                this.field_230712_o_.func_238421_b_(matrixStack, "Lorem ipsum dolor", (i + 14) * 1.428F, 64 * 1.45F, 0xFFFFFF);
            }
            RenderSystem.popMatrix();
        }
    }

    @Override
    public void func_230430_a_(MatrixStack matrixStack, int x, int y, float p_render_3_) {
        this.func_230446_a_(matrixStack);
        RenderSystem.pushMatrix();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.field_230706_i_.getTextureManager().bindTexture(FORBIDDENMICON_GUI_TEXTURES);
        int i = this.field_230708_k_ / 2 - 132;
        func_238464_a_(matrixStack, i, 32, this.func_230927_p_(), 0, 0, 264, 186, 256, 512);

        RenderSystem.popMatrix();

        if (entry != null) {
            addTitle(matrixStack);

            RenderSystem.pushMatrix();
            this.field_230706_i_.getTextureManager().bindTexture(FORBIDDENMICON_GUI_TEXTURES);
            addText(matrixStack);

            if (recipePreview != null) {
                recipePreview.render(matrixStack, x, y);
            }
            RenderSystem.popMatrix();
        }
        super.func_230430_a_(matrixStack, x, y , p_render_3_);
    }

    @Override
    public String getName() {
        return "forbiddenmicon";
    }

    @Override
    public void func_231175_as__() {
        if (stack.getItem() instanceof ForbiddenmiconItem) {
            ForbiddenmiconItem.setOpen(stack, false);
        }
        super.func_231175_as__();
    }
}
