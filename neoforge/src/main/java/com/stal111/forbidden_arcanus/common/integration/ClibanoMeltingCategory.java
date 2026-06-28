package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.gui.components.clibano.MaterialSlot;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoMeltingRecipe;
import com.stal111.forbidden_arcanus.common.item.crafting.display.ClibanoRecipeDisplay;
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
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * @author stal111
 * @since 2022-08-14
 */
public class ClibanoMeltingCategory implements IRecipeCategory<ClibanoMeltingRecipe> {

    private static final Identifier TEXTURE = ForbiddenArcanus.identifier("textures/gui/jei/clibano_melting.png");
    private static final Component TITLE = Component.translatable(Util.makeDescriptionId("jei", ForbiddenArcanus.identifier("category.clibano_melting")));

    private static final int WIDTH = 128;
    private static final int HEIGHT = 92;

    private final IDrawable background;
    private final IDrawable icon;

    private final IDrawableAnimated animatedFlame;
    private final Function<Integer, IDrawableAnimated> animatedArrow;

    public ClibanoMeltingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CLIBANO_CORE.get()));

        IDrawableStatic staticFlame = guiHelper.createDrawable(TEXTURE, 128, 5, 20, 19);
        this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic staticArrow = guiHelper.createDrawable(TEXTURE, 128, 24, 10, 13);
        this.animatedArrow = (duration) -> guiHelper.createAnimatedDrawable(staticArrow, duration, IDrawableAnimated.StartDirection.TOP, false);
    }

    @Override
    public IRecipeType<ClibanoMeltingRecipe> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.CLIBANO_MELTING;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return TITLE;
    }

    @NotNull
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ClibanoMeltingRecipe recipe, @NotNull IFocusGroup focuses) {
        if (recipe.display().getFirst() instanceof ClibanoRecipeDisplay display) {
            builder.addInputSlot(56, 14).add(display.ingredient());

            builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 56, 63).add(display.fuel());

            builder.addOutputSlot(96, 38).add(display.result()).addRichTooltipCallback((_, tooltip) -> {
                tooltip.clear();

                tooltip.add(MaterialSlot.getAmountComponent(display.resultMaterial()));
            });
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, ClibanoMeltingRecipe recipe, IFocusGroup focuses) {
        builder.addDrawable(this.animatedFlame, 54, 37);

        if (recipe.display().getFirst() instanceof ClibanoRecipeDisplay display) {
            builder.addDrawable(this.animatedArrow.apply(display.duration()), 99, 20);
        }
    }

    @Override
    public void draw(@NotNull ClibanoMeltingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);

        this.drawCookTime(recipe.getCookingTime(ClibanoFireType.FIRE), guiGraphics, 79);
    }

    protected void drawCookTime(int cookingTime, GuiGraphicsExtractor guiGraphics, int y) {
        if (cookingTime > 0) {
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookingTime / 20);
            Font font = Minecraft.getInstance().font;
            int stringWidth = font.width(timeString);

            guiGraphics.text(font, timeString, this.background.getWidth() - stringWidth - 2, y, 0xFF808080, false);
        }
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
