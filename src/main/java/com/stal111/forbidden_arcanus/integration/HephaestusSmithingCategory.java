package com.stal111.forbidden_arcanus.integration;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Hephaestus Smithing Category <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.integration.HephaestusSmithingCategory
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-09-14
 */
public class HephaestusSmithingCategory implements IRecipeCategory<Ritual> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/hephaestus_forge_jei.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final List<EssenceInfo> essences = new ArrayList<>();

    private final List<Pair<Integer, Integer>> inputPositions = Arrays.asList(
            new Pair<>(62, 11),
            new Pair<>(84, 23),
            new Pair<>(84, 47),
            new Pair<>(62, 57),
            new Pair<>(40, 47),
            new Pair<>(40, 23)
    );

    private final Pair<Integer, Integer> hephaestusForgeItemPosition = new Pair<>(62, 34);
    private final Pair<Integer, Integer> outputPosition = new Pair<>(120, 35);
    private final Pair<Integer, Integer> pedestalTypePosition = new Pair<>(122, 84);

    public HephaestusSmithingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 147, 107);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()));
        this.essences.addAll(Arrays.asList(
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 161, 1, 10, 10), "Aureal", 42, 79),
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 173, 1, 10, 10), "Souls", 58, 79),
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 185, 1, 10, 10), "Blood", 74, 79),
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 197, 1, 10, 10), "Experience", 90, 79)
        ));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "hephaestus_smithing");
    }

    @Nonnull
    @Override
    public Class<? extends Ritual> getRecipeClass() {
        return Ritual.class;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return new TranslatableComponent("jei.forbidden_arcanus.category.hephaestusSmithing");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(@Nonnull Ritual recipe, @Nonnull IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getInputs());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResult());
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull Ritual recipe, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

        itemStacks.init(0, true, this.hephaestusForgeItemPosition.getFirst(), this.hephaestusForgeItemPosition.getSecond());
        itemStacks.set(0, recipe.getHephaestusForgeItem());

        int index = 1;

        for (int i = 0; i < 6; i++) {
            Ingredient ingredient = recipe.getInput(i);
            if (ingredient != null) {
                itemStacks.init(index, true, this.inputPositions.get(i).getFirst(), this.inputPositions.get(i).getSecond());
                itemStacks.set(index, Arrays.asList(ingredient.getItems()));

                index++;
            }
        }

        itemStacks.init(index, false, this.outputPosition.getFirst(), this.outputPosition.getSecond());
        itemStacks.set(index, recipe.getResult());

        index++;
        itemStacks.init(index, false, this.pedestalTypePosition.getFirst(), this.pedestalTypePosition.getSecond());
        itemStacks.set(index, new ItemStack(recipe.getPedestalType().getBlock()));

        int pedestalIndex = index++;
        itemStacks.addTooltipCallback((slot, input, ingredient, tooltip) -> {
            if (slot == pedestalIndex) {
                tooltip.clear();
                tooltip.add(new TranslatableComponent("jei.forbidden_arcanus.hephaestusSmithing.requiredPedestal").append(": ").append(recipe.getPedestalType().getBlock().getName()));
                tooltip.add(new TranslatableComponent("jei.forbidden_arcanus.hephaestusSmithing.requiredLevel").append(": 1"));
            }
        });
    }

    @Override
    public void draw(@Nonnull Ritual recipe, @Nonnull PoseStack poseStack, double mouseX, double mouseY) {
        this.essences.forEach(essenceInfo -> essenceInfo.drawable().draw(poseStack, essenceInfo.posX(), essenceInfo.posY()));
    }

    @Nonnull
    @Override
    public List<Component> getTooltipStrings(@Nonnull Ritual recipe, double mouseX, double mouseY) {
        for (EssenceInfo essenceInfo : this.essences) {
            int posX = essenceInfo.posX();
            int posY = essenceInfo.posY();

            if (mouseX >= posX && mouseY >= posY && mouseX <= posX + 10 && mouseY <= posY + 10) {
                return Collections.singletonList(new TranslatableComponent("jei.forbidden_arcanus.hephaestusSmithing.required" + essenceInfo.name()).append(": " + recipe.getEssences().getFromName(essenceInfo.name())));
            }
        }

        return Collections.emptyList();
    }

    private record EssenceInfo(IDrawableStatic drawable, String name, int posX, int posY) {}
}
