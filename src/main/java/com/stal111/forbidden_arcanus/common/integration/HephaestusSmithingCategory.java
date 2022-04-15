package com.stal111.forbidden_arcanus.common.integration;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Hephaestus Smithing Category <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.integration.HephaestusSmithingCategory
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2021-09-14
 */
public class HephaestusSmithingCategory implements IRecipeCategory<Ritual> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/hephaestus_forge_jei.png");

    private static final List<Pair<Integer, Integer>> INPUT_POSITIONS = List.of(
            new Pair<>(63, 12),
            new Pair<>(85, 24),
            new Pair<>(85, 48),
            new Pair<>(63, 58),
            new Pair<>(41, 48),
            new Pair<>(41, 24)
    );

    private static final Pair<Integer, Integer> FORGE_ITEM_POSITION = new Pair<>(63, 35);
    private static final Pair<Integer, Integer> OUTPUT_POSITION = new Pair<>(121, 36);
    private static final Pair<Integer, Integer> PEDESTAL_TYPE_POSITION = new Pair<>(123, 85);

    private final IDrawable background;
    private final IDrawable icon;
    private final List<EssenceInfo> essences;

    public HephaestusSmithingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 147, 107);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()));
        this.essences = List.of(
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 161, 1, 10, 10), "Aureal", 42, 79),
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 173, 1, 10, 10), "Souls", 58, 79),
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 185, 1, 10, 10), "Blood", 74, 79),
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 197, 1, 10, 10), "Experience", 90, 79)
        );
    }

    @Nonnull
    @Override
    public RecipeType<Ritual> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.HEPHAESTUS_SMITHING;
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull Ritual recipe, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, FORGE_ITEM_POSITION.getFirst(), FORGE_ITEM_POSITION.getSecond())
                .addItemStack(recipe.getHephaestusForgeItem());

        for (int i = 0; i < 6; i++) {
            Ingredient ingredient = recipe.getInput(i);

            if (ingredient != null) {
                builder.addSlot(RecipeIngredientRole.INPUT, INPUT_POSITIONS.get(i).getFirst(), INPUT_POSITIONS.get(i).getSecond())
                        .addIngredients(ingredient);
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_POSITION.getFirst(), OUTPUT_POSITION.getSecond())
                .addItemStack(recipe.getResult());

        builder.addSlot(RecipeIngredientRole.OUTPUT, PEDESTAL_TYPE_POSITION.getFirst(), PEDESTAL_TYPE_POSITION.getSecond())
                .addItemStack(new ItemStack(recipe.getPedestalType().getBlock()))
                .addTooltipCallback((recipeSlotView, tooltip) -> {
                    tooltip.clear();
                    tooltip.add(new TranslatableComponent("jei.forbidden_arcanus.hephaestusSmithing.requiredPedestal").append(": ").append(recipe.getPedestalType().getBlock().getName()));
                    tooltip.add(new TranslatableComponent("jei.forbidden_arcanus.hephaestusSmithing.requiredLevel").append(": 1"));
                });
    }

    @Override
    public void draw(@Nonnull Ritual recipe, @Nonnull IRecipeSlotsView slotsView, @Nonnull PoseStack poseStack, double mouseX, double mouseY) {
        this.essences.forEach(essenceInfo -> essenceInfo.drawable().draw(poseStack, essenceInfo.posX(), essenceInfo.posY()));
    }

    @Nonnull
    @Override
    public List<Component> getTooltipStrings(@Nonnull Ritual recipe, @Nonnull IRecipeSlotsView slotsView, double mouseX, double mouseY) {
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
