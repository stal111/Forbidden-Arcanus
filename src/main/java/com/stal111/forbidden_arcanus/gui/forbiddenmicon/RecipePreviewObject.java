package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.stal111.forbidden_arcanus.gui.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RecipePreviewObject extends GuiObject {

    private ForbiddenmiconScreen forbiddenmiconScreen;
    private ForbiddenmiconEntry entry;
    private GuiManager manager;

    private final int blitOffset;

    private int currentRecipe = 0;

    private RecipeCategory activeRecipeCategory;

    public List<SwitchRecipeTypeButton> switchRecipeTypeButtons = new ArrayList<>();

    private SwitchRecipeButton nextRecipeButton;
    private SwitchRecipeButton previousRecipeButton;

    public RecipePreviewObject(ForbiddenmiconScreen forbiddenmiconScreen, int posX, int posY, int blitOffset) {
        super(posX, posY, 111, 75);
        this.forbiddenmiconScreen = forbiddenmiconScreen;
        this.manager = forbiddenmiconScreen.manager;
        this.blitOffset = blitOffset;

        for (RecipeCategory category : RecipeCategory.values()) {
            switchRecipeTypeButtons.add(new SwitchRecipeTypeButton(category, blitOffset, category.getStartX(), category.getStartY(), buttonObject -> {
                buttonObject.setActivated(true);
                switchRecipeTypeButtons.forEach(button -> {
                    if (button.getRecipeCategory() != ((SwitchRecipeTypeButton) buttonObject).getRecipeCategory()) {
                        button.setActivated(false);
                    }
                });
                activeRecipeCategory = ((SwitchRecipeTypeButton) buttonObject).getRecipeCategory();
                manager.getObjects().removeIf(guiObject -> guiObject instanceof ItemObject);
                loadRecipe(activeRecipeCategory.getRecipeSerializers());
            }, category.getHoverText()));
        }

        nextRecipeButton = new SwitchRecipeButton(getPosX() + 93, 177, blitOffset, 171, 204, buttonObject -> {
            if (currentRecipe + 1 < ForbiddenmiconScreen.INSTANCE.entry.getRecipes(Arrays.asList(IRecipeSerializer.CRAFTING_SHAPED, IRecipeSerializer.CRAFTING_SHAPELESS)).size()) {
                currentRecipe++;
                manager.getObjects().removeIf(guiObject -> guiObject instanceof ItemObject);
                loadRecipe(activeRecipeCategory.getRecipeSerializers());
                if (!(currentRecipe + 1 < ForbiddenmiconScreen.INSTANCE.entry.getRecipes(Arrays.asList(IRecipeSerializer.CRAFTING_SHAPED, IRecipeSerializer.CRAFTING_SHAPELESS)).size())) {
                    nextRecipeButton.setPressable(false);
                }
                previousRecipeButton.setPressable(true);
            }
        });

        previousRecipeButton = new SwitchRecipeButton(getPosX() + 81, 177, blitOffset, 158, 204, buttonObject -> {
            if (currentRecipe - 1 >= 0) {
                currentRecipe--;
                manager.getObjects().removeIf(guiObject -> guiObject instanceof ItemObject);
                loadRecipe(activeRecipeCategory.getRecipeSerializers());
                if (!(currentRecipe - 1 >= 0)) {
                    previousRecipeButton.setPressable(false);
                }
                nextRecipeButton.setPressable(true);
            }
        });
    }

    @Override
    public void render(int x, int y) {
        if (activeRecipeCategory != null) {
            bindTexture(ForbiddenmiconScreen.FORBIDDENMICON_GUI_TEXTURES);
            blit(blitOffset, 275, 96, 111, 75, 256, 512);
            if (activeRecipeCategory == RecipeCategory.CRAFTING) {
                blit(getPosX() + 6, 131, blitOffset, 221, 195, 98, 56, 256, 512);
            } else if (activeRecipeCategory == RecipeCategory.SMELTING) {
                blit(getPosX() + 26, 143, blitOffset, 330, 206, 60, 34, 256, 512);
            }
        }
    }

    public void setEntry(ForbiddenmiconEntry entry) {
        this.entry = entry;
        manager.getObjects().removeIf(guiObject -> guiObject instanceof SwitchRecipeTypeButton || guiObject instanceof ItemObject || guiObject instanceof SwitchRecipeButton);
        this.activeRecipeCategory = null;
        this.currentRecipe = 0;
        int i = 90;
        for (RecipeCategory category : RecipeCategory.values()) {
            if (entry.getRecipes(category.getRecipeSerializers()) != null) {
                for (SwitchRecipeTypeButton switchRecipeTypeButton : switchRecipeTypeButtons) {
                    if (switchRecipeTypeButton.getRecipeCategory() == category) {
                        switchRecipeTypeButton.setPos(getPosX() + i, 192);
                        switchRecipeTypeButton.setActivated(false);
                        manager.addGuiObject(switchRecipeTypeButton);
                        i -= 18;
                    }
                }
            }
        }
        for (GuiObject object : manager.getObjects()) {
            if (object instanceof SwitchRecipeTypeButton) {
                this.activeRecipeCategory = ((SwitchRecipeTypeButton) object).getRecipeCategory();
                break;
            }
        }
        if (activeRecipeCategory != null) {
            switchRecipeTypeButtons.forEach(button -> {
                if (button.getRecipeCategory() == activeRecipeCategory) {
                    button.setActivated(true);
                }
            });
            loadRecipe(activeRecipeCategory.getRecipeSerializers());

            if (entry.getRecipes(activeRecipeCategory.getRecipeSerializers()).size() >= 2) {
                previousRecipeButton.setPressable(false);
                nextRecipeButton.setPressable(true);
                manager.getObjects().addAll(Arrays.asList(nextRecipeButton, previousRecipeButton));
            }
        }
    }

    public boolean entryLoaded() {
        return entry != null;
    }

    public void clearEntry() {
        this.entry = null;
    }

    public void loadRecipe(Collection<IRecipeSerializer<?>> recipeSerializers) {
        Collection<IRecipe<?>> recipes = entry.getRecipes(recipeSerializers);
        if (recipes != null) {
            for (int j = 0; j < getRecipeIngredients(recipes).size(); j++) {
                if (activeRecipeCategory == RecipeCategory.CRAFTING) {
                    if (!getRecipeIngredients(recipes).get(j).isEmpty()) {
                        if (j == 0 || j == 3 || j == 6) {
                            manager.addGuiObject(new ItemObject(getRecipeIngredients(recipes).get(j), getPosX() + 8, j == 0 ? 132 : j == 3 ? 151 : 170));
                        } else if (j == 1 || j == 4 || j == 7) {
                            manager.addGuiObject(new ItemObject(getRecipeIngredients(recipes).get(j), getPosX() + 27, j == 1 ? 132 : j == 4 ? 151 : 170));
                        } else {
                            manager.addGuiObject(new ItemObject(getRecipeIngredients(recipes).get(j), getPosX() + 46, j == 2 ? 132 : j == 5 ? 151 : 170));
                        }
                    }
                    if (!getRecipeOutput(recipes).isEmpty()) {
                        manager.addGuiObject(new ItemObject(getRecipeOutput(recipes), getPosX() + 83, 151));
                    }
                } else if (activeRecipeCategory == RecipeCategory.SMELTING) {
                    manager.addGuiObject(new ItemObject(getRecipeIngredients(recipes).get(0), getPosX() + 28,  144));
                    manager.addGuiObject(new ItemObject(getRecipeOutput(recipes), getPosX() + 66, 152));
                }
            }
        }
    }

    public List<ItemStack> getRecipeIngredients(Collection<IRecipe<?>> recipes) {
        IRecipe<?> recipe = (IRecipe<?>) recipes.toArray()[currentRecipe];
        List<ItemStack> list = new ArrayList<>();
        recipe.getIngredients().forEach(ingredient -> {
            if (ingredient == Ingredient.EMPTY) {
                list.add(ItemStack.EMPTY);
            } else {
                list.add(ingredient.getMatchingStacks()[0]);
            }
        });
        return list;
    }

    public ItemStack getRecipeOutput(Collection<IRecipe<?>> recipes) {
        IRecipe<?> recipe = (IRecipe<?>) recipes.toArray()[currentRecipe];
        return recipe.getRecipeOutput();
    }

    public enum RecipeCategory {
        CRAFTING(329, 175, "crafting", IRecipeSerializer.CRAFTING_SHAPELESS, IRecipeSerializer.CRAFTING_SHAPED),
        SMELTING(365, 175, "smelting", IRecipeSerializer.SMELTING);

        private final int startX;
        private final int startY;

        private final String hoverText;

        private final Collection<IRecipeSerializer<?>> recipeSerializers;

        RecipeCategory(int startX, int startY, String hoverText, IRecipeSerializer<?>... recipeSerializer) {
            this.startX = startX;
            this.startY = startY;
            this.hoverText = hoverText;
            this.recipeSerializers = Arrays.asList(recipeSerializer);
        }

        public int getStartX() {
            return startX;
        }

        public int getStartY() {
            return startY;
        }

        public String getHoverText() {
            return hoverText;
        }

        public Collection<IRecipeSerializer<?>> getRecipeSerializers() {
            return recipeSerializers;
        }
    }
}
