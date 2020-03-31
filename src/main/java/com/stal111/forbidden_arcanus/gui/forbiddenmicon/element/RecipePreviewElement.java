package com.stal111.forbidden_arcanus.gui.forbiddenmicon.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.gui.*;
import com.stal111.forbidden_arcanus.gui.element.GuiElement;
import com.stal111.forbidden_arcanus.gui.element.ItemElement;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconEntry;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconScreen;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button.ChangeFurnaceTypeButton;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button.ChangeRecipeButton;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button.ChangeRecipeTypeButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.*;

public class RecipePreviewElement extends GuiElement {

    private ForbiddenmiconScreen forbiddenmiconScreen;
    private ForbiddenmiconEntry entry;
    private GuiManager manager;

    private final int blitOffset;

    private final FontRenderer font;

    private int currentRecipe = 0;

    private RecipeCategory activeRecipeCategory;
    private ChangeFurnaceTypeButton.FurnaceType activeFurnaceType;

    public List<ChangeRecipeTypeButton> changeRecipeTypeButtons = new ArrayList<>();

    private ChangeRecipeButton nextRecipeButton;
    private ChangeRecipeButton previousRecipeButton;

    public RecipePreviewElement(ForbiddenmiconScreen forbiddenmiconScreen, int posX, int posY, int blitOffset, FontRenderer font) {
        super(posX, posY, 111, 68);
        this.forbiddenmiconScreen = forbiddenmiconScreen;
        this.manager = forbiddenmiconScreen.manager;
        this.blitOffset = blitOffset;
        this.font = font;

        for (RecipeCategory category : RecipeCategory.values()) {
            changeRecipeTypeButtons.add(new ChangeRecipeTypeButton(category, blitOffset, category.getStartX(), category.getStartY(), buttonObject -> {
                buttonObject.setActivated(true);
                changeRecipeTypeButtons.forEach(button -> {
                    if (button.getRecipeCategory() != ((ChangeRecipeTypeButton) buttonObject).getRecipeCategory()) {
                        button.setActivated(false);
                    }
                });
                activeRecipeCategory = ((ChangeRecipeTypeButton) buttonObject).getRecipeCategory();
                manager.getObjects().removeIf(guiObject -> guiObject instanceof ItemElement);
                loadRecipe(activeRecipeCategory.getRecipeSerializers());
            }, category.getHoverText()));
        }

        nextRecipeButton = new ChangeRecipeButton(getPosX() + 93, 177, blitOffset, 171, 204, buttonObject -> {
            if (currentRecipe + 1 < entry.getRecipes(Arrays.asList(IRecipeSerializer.CRAFTING_SHAPED, IRecipeSerializer.CRAFTING_SHAPELESS)).size()) {
                currentRecipe++;
                manager.getObjects().removeIf(guiObject -> guiObject instanceof ItemElement);
                loadRecipe(activeRecipeCategory.getRecipeSerializers());
                if (!(currentRecipe + 1 < entry.getRecipes(Arrays.asList(IRecipeSerializer.CRAFTING_SHAPED, IRecipeSerializer.CRAFTING_SHAPELESS)).size())) {
                    nextRecipeButton.setPressable(false);
                }
                previousRecipeButton.setPressable(true);
            }
        });

        previousRecipeButton = new ChangeRecipeButton(getPosX() + 81, 177, blitOffset, 158, 204, buttonObject -> {
            if (currentRecipe - 1 >= 0) {
                currentRecipe--;
                manager.getObjects().removeIf(guiObject -> guiObject instanceof ItemElement);
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
            blit(blitOffset, 275, 97, 111, 68, 256, 512);

            int i = 0;
            int j = 88;

            for (RecipeCategory category : RecipeCategory.values()) {
                if (entry.getRecipes(category.getRecipeSerializers()) != null) {
                    i++;
                }
            }

            while (i > 0) {
                blit(getPosX() + j, 192, blitOffset, 379, 169, 17, 7, 256, 512);
                j -= 18;
                i--;
            }

            if (activeRecipeCategory == RecipeCategory.CRAFTING) {
                blit(getPosX() + 6, 131, blitOffset, 221, 195, 98, 56, 256, 512);
            } else if (activeRecipeCategory == RecipeCategory.SMELTING) {
                blit(getPosX() + 26, 143, blitOffset, 330, 206, 61, 35, 256, 512);
                RenderSystem.scalef(0.77F, 0.77F, 1.0F);
                font.drawString(new TranslationTextComponent("forbiddenmicon.recipe.smelting." + activeFurnaceType.toString().toLowerCase()).getFormattedText(), (getPosX() +  8) * 1.29F, (getPosY() + 5) * 1.29F, 0xFFFFFF);
            }
        }
    }

    public void setEntry(ForbiddenmiconEntry entry) {
        this.entry = entry;
        manager.getObjects().removeIf(guiObject -> guiObject instanceof ChangeRecipeTypeButton || guiObject instanceof ItemElement || guiObject instanceof ChangeRecipeButton);
        this.activeRecipeCategory = null;
        this.activeFurnaceType = null;
        this.currentRecipe = 0;
        int i = 90;
        for (RecipeCategory category : RecipeCategory.values()) {
            if (entry.getRecipes(category.getRecipeSerializers()) != null) {
                for (ChangeRecipeTypeButton changeRecipeTypeButton : changeRecipeTypeButtons) {
                    if (changeRecipeTypeButton.getRecipeCategory() == category) {
                        changeRecipeTypeButton.setPos(getPosX() + i, 192);
                        changeRecipeTypeButton.setActivated(false);
                        manager.addGuiObject(changeRecipeTypeButton);
                        i -= 18;
                    }
                }
            }
        }
        for (GuiElement object : manager.getObjects()) {
            if (object instanceof ChangeRecipeTypeButton) {
                this.activeRecipeCategory = ((ChangeRecipeTypeButton) object).getRecipeCategory();
                break;
            }
        }
        if (activeRecipeCategory != null) {
            changeRecipeTypeButtons.forEach(button -> {
                if (button.getRecipeCategory() == activeRecipeCategory) {
                    button.setActivated(true);
                }
            });
            loadRecipe(activeRecipeCategory.getRecipeSerializers());

            if (entry.getRecipes(activeRecipeCategory.getRecipeSerializers()).size() >= 2 && activeRecipeCategory == RecipeCategory.CRAFTING) {
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
        this.manager.getObjects().removeIf(guiObject -> guiObject instanceof ItemElement || guiObject instanceof ChangeRecipeTypeButton);
    }

    public void loadRecipe(Collection<IRecipeSerializer<?>> recipeSerializers) {
        manager.getObjects().removeIf(guiObject -> guiObject instanceof FlameElement || guiObject instanceof ChangeFurnaceTypeButton);
        Collection<IRecipe<?>> recipes = entry.getRecipes(recipeSerializers);
        if (recipes != null) {
            IRecipe<?> recipe = (IRecipe<?>) recipes.toArray()[currentRecipe];
            Ingredient ingredient = (Ingredient) recipe.getIngredients().toArray()[0];
            addItems(recipes);
            if (activeRecipeCategory == RecipeCategory.SMELTING) {
                int i = getPosY() + 2;
                boolean flag = false;
                for (ChangeFurnaceTypeButton.FurnaceType furnaceType : ChangeFurnaceTypeButton.FurnaceType.values()) {
                    for (IRecipeSerializer<?> recipeSerializer : recipeSerializers) {
                        if (recipeSerializer == furnaceType.getRecipeSerializer()) {
                            if (entry.getRecipes(Collections.singletonList(recipeSerializer)) != null) {
                                ChangeFurnaceTypeButton changeFurnaceTypeButton = new ChangeFurnaceTypeButton(getEndX() - 18, i, blitOffset, furnaceType, buttonObject -> {
                                    for (GuiElement guiObject : manager.getObjects()) {
                                        if (guiObject instanceof ChangeFurnaceTypeButton) {
                                            if (((ChangeFurnaceTypeButton) guiObject).getFurnaceType() != furnaceType) {
                                                ((ChangeFurnaceTypeButton) guiObject).setActivated(false);
                                            }
                                        }
                                    }
                                    buttonObject.setActivated(true);
                                    this.activeFurnaceType = furnaceType;
                                    manager.getObjects().removeIf(guiObject -> guiObject instanceof ItemElement);
                                    addItems(entry.getRecipes(Collections.singletonList(activeFurnaceType.getRecipeSerializer())));
                                    Optional<AbstractCookingRecipe> optional = Minecraft.getInstance().world.getRecipeManager().getRecipe(activeFurnaceType.getRecipeType(), new Inventory(ingredient.getMatchingStacks()[0]), Minecraft.getInstance().world);
                                    manager.getObjects().forEach(guiObject -> {
                                        if (guiObject instanceof FlameElement) {
                                            ((FlameElement) guiObject).setCookingTime(optional.map(AbstractCookingRecipe::getCookTime).orElse(200));
                                            ((FlameElement) guiObject).setExperience(optional.map(AbstractCookingRecipe::getExperience).orElse(0.0F));
                                        }
                                    });
                                });
                                if (!flag) {
                                    changeFurnaceTypeButton.setActivated(true);
                                    this.activeFurnaceType = furnaceType;
                                }
                                manager.addGuiObject(changeFurnaceTypeButton);
                                i += 15;
                                flag = true;
                            }
                        }
                    }
                }
                Optional<AbstractCookingRecipe> optional = Minecraft.getInstance().world.getRecipeManager().getRecipe(activeFurnaceType.getRecipeType(), new Inventory(ingredient.getMatchingStacks()[0]), Minecraft.getInstance().world);
                manager.addGuiObject(new FlameElement(getPosX() + 31, 162, optional.map(AbstractCookingRecipe::getCookTime).orElse(200), optional.map(AbstractCookingRecipe::getExperience).orElse(0.0F), FlameElement.FireType.FIRE));
            }
        }
    }

    private void addItems(Collection<IRecipe<?>> recipes) {
        for (int j = 0; j < getRecipeIngredients(recipes).size(); j++) {
            if (activeRecipeCategory == RecipeCategory.CRAFTING) {
                if (!getRecipeIngredients(recipes).get(j).isEmpty()) {
                    if (j == 0 || j == 3 || j == 6) {
                        manager.addGuiObject(new ItemElement(getRecipeIngredients(recipes).get(j), getPosX() + 8, j == 0 ? 132 : j == 3 ? 151 : 170));
                    } else if (j == 1 || j == 4 || j == 7) {
                        manager.addGuiObject(new ItemElement(getRecipeIngredients(recipes).get(j), getPosX() + 27, j == 1 ? 132 : j == 4 ? 151 : 170));
                    } else {
                        manager.addGuiObject(new ItemElement(getRecipeIngredients(recipes).get(j), getPosX() + 46, j == 2 ? 132 : j == 5 ? 151 : 170));
                    }
                }
                if (!getRecipeOutput(recipes).isEmpty()) {
                    manager.addGuiObject(new ItemElement(getRecipeOutput(recipes), getPosX() + 83, 151));
                }
            } else if (activeRecipeCategory == RecipeCategory.SMELTING) {
                manager.addGuiObject(new ItemElement(getRecipeIngredients(recipes).get(0), getPosX() + 28,  144));
                manager.addGuiObject(new ItemElement(getRecipeOutput(recipes), getPosX() + 66, 152));
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
        CRAFTING(327, 168, "crafting", IRecipeSerializer.CRAFTING_SHAPELESS, IRecipeSerializer.CRAFTING_SHAPED),
        SMELTING(363, 168, "smelting", IRecipeSerializer.SMELTING, IRecipeSerializer.BLASTING, IRecipeSerializer.SMOKING);

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
