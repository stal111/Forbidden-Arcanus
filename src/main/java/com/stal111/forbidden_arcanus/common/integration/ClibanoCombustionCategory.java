package com.stal111.forbidden_arcanus.common.integration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;

import static mezz.jei.api.recipe.RecipeIngredientRole.*;

/**
 * @author stal111
 * @since 2022-08-14
 */
public class ClibanoCombustionCategory implements IRecipeCategory<ClibanoRecipe> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/clibano_combustion_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    protected final IDrawableStatic staticFlame;
    protected final IDrawableStatic staticBlueFlame;
    protected final IDrawableStatic staticPurpleFlame;

    private final HashMap<ClibanoFireType, IDrawableAnimated> animatedFlames = new HashMap<>();

    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;


    public ClibanoCombustionCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 147, 97);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CLIBANO_CORE.get()));

        this.staticFlame = guiHelper.createDrawable(TEXTURE, 151, 1, 12, 15);
        this.animatedFlames.put(ClibanoFireType.FIRE, guiHelper.createAnimatedDrawable(this.staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true));

        this.staticBlueFlame = guiHelper.createDrawable(TEXTURE, 170, 1, 12, 15);
        this.animatedFlames.put(ClibanoFireType.BLUE_FIRE, guiHelper.createAnimatedDrawable(this.staticBlueFlame, 300, IDrawableAnimated.StartDirection.TOP, true));

        this.staticPurpleFlame = guiHelper.createDrawable(TEXTURE, 189, 1, 12, 15);
        this.animatedFlames.put(ClibanoFireType.PURPLE_FIRE, guiHelper.createAnimatedDrawable(this.staticBlueFlame, 300, IDrawableAnimated.StartDirection.TOP, true));

        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Nonnull
                    @Override
                    public IDrawableAnimated load(@Nonnull Integer cookTime) {
                        return guiHelper.drawableBuilder(TEXTURE, 148, 32, 13, 12)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    @Nonnull
    @Override
    public RecipeType<ClibanoRecipe> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.CLIBANO_COMBUSTION;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return Component.translatable("jei.forbidden_arcanus.category.clibanoCombustion");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull ClibanoRecipe recipe, @Nonnull IFocusGroup focuses) {
        builder.addSlot(INPUT, 55, 24)
                .addIngredients(recipe.getIngredients().get(0));

        if (recipe.getRequiredFireType() == ClibanoFireType.BLUE_FIRE) {
            builder.addSlot(RENDER_ONLY, 12, 60)
                    .addIngredients(Ingredient.of(ModTags.Items.CLIBANO_CREATES_BLUE_FIRE));
        }

        builder.addSlot(OUTPUT, 97, 35)
                .addItemStack(recipe.getResultItem());
    }

    private IDrawableAnimated getArrow(ClibanoRecipe recipe) {
        int cookTime = recipe.getCookingTime();
        if (cookTime <= 0) {
            cookTime = ClibanoRecipe.DEFAULT_COOKING_TIME;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }

    @Override
    public void draw(@Nonnull ClibanoRecipe recipe, @Nonnull IRecipeSlotsView recipeSlotsView, @Nonnull PoseStack stack, double mouseX, double mouseY) {
        this.animatedFlames.get(recipe.getRequiredFireType()).draw(stack, 48, 43);

        IDrawableAnimated arrow = this.getArrow(recipe);
        arrow.draw(stack, 74, 43);

        this.drawExperience(recipe, stack, 12);
        this.drawCookTime(recipe, stack, 79);
    }

    protected void drawExperience(ClibanoRecipe recipe, PoseStack poseStack, int y) {
        float experience = recipe.getExperience();
        if (experience > 0) {
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(experienceString);
            fontRenderer.draw(poseStack, experienceString, this.background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    protected void drawCookTime(ClibanoRecipe recipe, PoseStack poseStack, int y) {
        int cookTime = recipe.getCookingTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            fontRenderer.draw(poseStack, timeString, this.background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    @Nonnull
    @Override
    public List<Component> getTooltipStrings(@Nonnull ClibanoRecipe recipe, @Nonnull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 92 && mouseY >= 59 && mouseX <= 117 && mouseY <= 65) {
            ClibanoRecipe.ResidueInfo residueInfo = recipe.getResidueInfo();

            if (residueInfo == ClibanoRecipe.ResidueInfo.NONE) {
                return List.of();
            }

            return List.of(residueInfo.getType().getComponent()
                    .append(" ")
                    .append(Component.translatable("jei.forbidden_arcanus.clibanoCombustion.residue"))
                    .append(" (" + residueInfo.chance() * 100 +"%)")
            );
        }

        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }
}
