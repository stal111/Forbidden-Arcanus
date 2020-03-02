package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.stal111.forbidden_arcanus.gui.ForbiddenmiconScreen;
import com.stal111.forbidden_arcanus.gui.GuiObject;
import com.stal111.forbidden_arcanus.gui.ItemObject;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class RecipePreviewObject extends GuiObject {

    private final int blitOffset;

    public int currentRecipe = 0;

    public ButtonObject craftingButton;
    public ButtonObject smeltingButton;

    public ButtonObject nextRecipeButton;
    public ButtonObject previousRecipeButton;

    public RecipePreviewObject(int posX, int posY, int blitOffset) {
        super(posX, posY);
        this.blitOffset = blitOffset;
    }

    @Override
    public void init() {
        getObjects().clear();
        if (ForbiddenmiconScreen.INSTANCE.entry.hasRecipe()) {
            if (ForbiddenmiconScreen.INSTANCE.entry.getRecipes() != null) {
                craftingButton = new SwitchRecipeTypeButton(ForbiddenmiconScreen.INSTANCE.width / 2 - 44, 192, blitOffset, 329, 175, buttonObject -> {
                    buttonObject.setActivated(true);
                    getObjects().forEach(guiObject -> {
                        if (guiObject instanceof ButtonObject && buttonObject != guiObject) {
                            ((ButtonObject) guiObject).setActivated(false);
                        }
                    });
                    initRecipe();
                    initChangeRecipeButtons();
                }, "crafting").setActivated(true);
                getObjects().add(craftingButton);
            }
            if (ForbiddenmiconScreen.INSTANCE.entry.getSmeltingRecipes() != null) {
                smeltingButton = new SwitchRecipeTypeButton(ForbiddenmiconScreen.INSTANCE.width / 2 - 26, 192, blitOffset, 365, 175, buttonObject -> {
                    buttonObject.setActivated(true);
                    getObjects().forEach(guiObject -> {
                        if (guiObject instanceof ButtonObject && buttonObject != guiObject) {
                            ((ButtonObject) guiObject).setActivated(false);
                        }
                    });
                    initRecipe();
                    initChangeRecipeButtons();
                }, "smelting");
                getObjects().add(smeltingButton);
            }
            initChangeRecipeButtons();
            initRecipe();
        }
    }

    public void initChangeRecipeButtons() {
        getObjects().removeIf(guiObject -> guiObject instanceof SwitchRecipeButton);
        if (ForbiddenmiconScreen.INSTANCE.entry.hasRecipe()) {
            if ((craftingButton != null && craftingButton.isActivated() && ForbiddenmiconScreen.INSTANCE.entry.getRecipes().size() >= 2) || (smeltingButton != null && smeltingButton.isActivated() && ForbiddenmiconScreen.INSTANCE.entry.getSmeltingRecipes().size() >= 2)) {
                nextRecipeButton = new SwitchRecipeButton(ForbiddenmiconScreen.INSTANCE.width / 2 - 23, 177, blitOffset, 171, 204, buttonObject -> {
                    if (currentRecipe + 1 < ForbiddenmiconScreen.INSTANCE.entry.getRecipes().size()) {
                        currentRecipe++;
                        initRecipe();
                        if (!(currentRecipe + 1 < ForbiddenmiconScreen.INSTANCE.entry.getRecipes().size())) {
                            ((SwitchRecipeButton) nextRecipeButton).setPressable(false);
                        }
                        ((SwitchRecipeButton) previousRecipeButton).setPressable(true);
                    }
                });
                previousRecipeButton = new SwitchRecipeButton(ForbiddenmiconScreen.INSTANCE.width / 2 - 35, 177, blitOffset, 158, 204, buttonObject -> {
                    if (currentRecipe - 1 >= 0) {
                        currentRecipe--;
                        initRecipe();
                        if (!(currentRecipe - 1 >= 0)) {
                            ((SwitchRecipeButton) previousRecipeButton).setPressable(false);
                        }
                        ((SwitchRecipeButton) nextRecipeButton).setPressable(true);
                    }
                });

                ((SwitchRecipeButton) previousRecipeButton).setPressable(false);

                getObjects().add(nextRecipeButton);
                getObjects().add(previousRecipeButton);
            }
        }
    }

    public void initRecipe() {
        getObjects().removeIf(guiObject -> guiObject instanceof ItemObject);
        int i = ForbiddenmiconScreen.INSTANCE.width / 2;
        if (craftingButton != null && craftingButton.isActivated()) {
            for (int j = 0; j < getCraftingIngredients().size(); j++) {
                if (!getCraftingIngredients().get(j).isEmpty()) {
                    if (j == 0 || j == 3 || j == 6) {
                        getObjects().add(new ItemObject(getCraftingIngredients().get(j), i - 108, j == 0 ? 132 : j == 3 ? 151 : 170));
                    } else if (j == 1 || j == 4 || j == 7) {
                        getObjects().add(new ItemObject(getCraftingIngredients().get(j), i - 89, j == 1 ? 132 : j == 4 ? 151 : 170));
                    } else {
                        getObjects().add(new ItemObject(getCraftingIngredients().get(j), i - 70, j == 2 ? 132 : j == 5 ? 151 : 170));
                    }
                }
                if (!getCraftingResult().isEmpty()) {
                    getObjects().add(new ItemObject(getCraftingResult(), i - 33, 151));
                }
            }
        } else if (smeltingButton != null && smeltingButton.isActivated()) {
            getObjects().add(new ItemObject(getCurrentSmeltingRecipe().getIngredients().get(0).getMatchingStacks()[0], i - 88,  144));
            getObjects().add(new ItemObject(getCurrentSmeltingRecipe().getRecipeOutput().getStack(), i - 50, 152));
        }
    }

    @Override
    public void render(int x, int y) {
        if (ForbiddenmiconScreen.INSTANCE.entry.hasRecipe()) {
            getMinecraft().getTextureManager().bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
            AbstractGui.blit(getPosX(), getPosY(), blitOffset, 275, 96, 111, 75, 256, 512);
            int i = ForbiddenmiconScreen.INSTANCE.width / 2;
            if (craftingButton != null && craftingButton.isActivated()) {
                blit(i - 110, 131, blitOffset, 221, 195, 98, 56, 256, 512);
            } else if (smeltingButton != null && smeltingButton.isActivated()) {
                blit(i - 90, 143, blitOffset, 330, 206, 60, 34, 256, 512);
            }
        }
    }

    private List<ItemStack> getCraftingIngredients() {
        NonNullList<Ingredient> list = getCurrentCraftingRecipe().getIngredients();
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            stacks.add(i, list.get(i) == Ingredient.EMPTY ? ItemStack.EMPTY : list.get(i).getMatchingStacks()[0]);
        }
        return stacks;
    }

    private ItemStack getCraftingResult() {
        return getCurrentCraftingRecipe().getRecipeOutput();
    }

    private IRecipe<?> getCurrentCraftingRecipe() {
        return (IRecipe<?>) ForbiddenmiconScreen.INSTANCE.entry.getRecipes().toArray()[currentRecipe];
    }

    private AbstractCookingRecipe getCurrentSmeltingRecipe() {
        return (AbstractCookingRecipe) ForbiddenmiconScreen.INSTANCE.entry.getSmeltingRecipes().toArray()[currentRecipe];
    }
}
