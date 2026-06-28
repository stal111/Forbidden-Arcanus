package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.clibano.MaterialSlot;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoAlloyingRecipe;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class ClibanoAlloyingCategory implements IRecipeCategory<ClibanoAlloyingRecipe> {

    private static final Identifier TEXTURE = ForbiddenArcanus.identifier("textures/gui/jei/clibano_alloying.png");
    private static final Component TITLE = Component.translatable(Util.makeDescriptionId("jei", ForbiddenArcanus.identifier("category.clibano_alloying")));

    private static final int WIDTH = 150;
    private static final int HEIGHT = 118;

    private final IDrawable background;
    private final IDrawable icon;

    private final IDrawableAnimated animatedArrow;

    public ClibanoAlloyingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CLIBANO_CORE.get()));

        IDrawableStatic staticArrow = guiHelper.createDrawable(TEXTURE, 150, 5, 10, 13);
        this.animatedArrow = guiHelper.createAnimatedDrawable(staticArrow, 20, IDrawableAnimated.StartDirection.TOP, false);
    }

    @Override
    public IRecipeType<ClibanoAlloyingRecipe> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.CLIBANO_ALLOYING;
    }

    @Override
    public void draw(@NotNull ClibanoAlloyingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ClibanoAlloyingRecipe recipe, IFocusGroup focuses) {
        for (int i = 0; i < recipe.requiredMaterials().size(); i++) {
            MoltenMaterial material = recipe.requiredMaterials().get(i);

            builder.addInputSlot(10 + 26 * (i % 4), 12 + 38 * (i / 4)).add(material.type().value().result()).addRichTooltipCallback((_, tooltip) -> {
                tooltip.clear();

                tooltip.add(MaterialSlot.getAmountComponent(material));
            });
        }

        builder.addOutputSlot(121, 51).add(recipe.result());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, ClibanoAlloyingRecipe recipe, IFocusGroup focuses) {
        builder.addDrawable(this.animatedArrow, 124, 33);
    }
}
