package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.crafting.EssenceTransformRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.Blocks;

public class EssenceTransformCategory extends AbstractRecipeCategory<EssenceTransformRecipe> {

    private static final Component TITLE = Component.translatable(Util.makeDescriptionId("jei", ForbiddenArcanus.identifier("category.essence_transform")));

    private static final int WIDTH = 82;
    private static final int HEIGHT = 36;

    public EssenceTransformCategory(IGuiHelper guiHelper) {
        super(ForbiddenArcanusJEIPlugin.ESSENCE_TRANSFORM, TITLE, guiHelper.createDrawableItemLike(Blocks.CAULDRON), WIDTH, HEIGHT);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EssenceTransformRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(1, 14)
                .setStandardSlotBackground()
                .add(recipe.input());

        builder.addOutputSlot(61, 14)
                .setOutputSlotBackground()
                .add(recipe.result());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, EssenceTransformRecipe recipe, IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(26, 14);

        Component essenceText = recipe.essenceCost().value().asComponent(ChatFormatting.DARK_GRAY);
        builder.addText(essenceText, 60, 10)
                .setPosition(7, 1)
                .setTextAlignment(HorizontalAlignment.CENTER);
    }
}
