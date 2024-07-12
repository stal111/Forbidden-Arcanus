package com.stal111.forbidden_arcanus.common.integration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueChance;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoRecipe;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerHelper;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import static mezz.jei.api.recipe.RecipeIngredientRole.*;

/**
 * @author stal111
 * @since 2022-08-14
 */
public class ClibanoCombustionCategory implements IRecipeCategory<ClibanoRecipe> {

    private static final ResourceLocation TEXTURE = ForbiddenArcanus.location("textures/gui/jei/clibano_combustion.png");
    private static final Component TITLE = Component.translatable("jei.forbidden_arcanus.category.clibanoCombustion");

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
        this.animatedFlames.put(ClibanoFireType.SOUL_FIRE, guiHelper.createAnimatedDrawable(this.staticBlueFlame, 300, IDrawableAnimated.StartDirection.TOP, true));

        this.staticPurpleFlame = guiHelper.createDrawable(TEXTURE, 189, 1, 12, 15);
        this.animatedFlames.put(ClibanoFireType.ENCHANTED_FIRE, guiHelper.createAnimatedDrawable(this.staticPurpleFlame, 300, IDrawableAnimated.StartDirection.TOP, true));

        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @NotNull
                    @Override
                    public IDrawableAnimated load(@NotNull Integer cookTime) {
                        return guiHelper.drawableBuilder(TEXTURE, 148, 32, 13, 12)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    @NotNull
    @Override
    public RecipeType<ClibanoRecipe> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.CLIBANO_COMBUSTION;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return TITLE;
    }

    @NotNull
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @NotNull
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ClibanoRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(INPUT, 37, 24).addIngredients(recipe.getIngredients().get(0));

        if (recipe.isDoubleRecipe()) {
            builder.addSlot(INPUT, 55, 24).addIngredients(recipe.getIngredients().get(1));
        }

        TagKey<Item> tagKey = recipe.getRequiredFireType().getTagKey();

        if (tagKey != null) {
            builder.addSlot(RENDER_ONLY, 12, 60).addIngredients(Ingredient.of(tagKey));
        }

        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
        Holder<EnhancerDefinition> enhancer = recipe.getRequiredEnhancer();

        if (enhancer != null) {
            builder.addSlot(CATALYST, 12, 24).addItemStack(EnhancerHelper.createEnhancer(registryAccess, enhancer.value().displayItem().value(), enhancer));
        }

        builder.addSlot(OUTPUT, 97, 35).addItemStack(recipe.getResultItem(registryAccess));
    }

    private IDrawableAnimated getArrow(ClibanoRecipe recipe) {
        int cookTime = recipe.getCookingTime(recipe.getRequiredFireType());
        if (cookTime <= 0) {
            cookTime = ClibanoRecipe.DEFAULT_COOKING_TIME;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }

    @Override
    public void draw(@NotNull ClibanoRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.animatedFlames.get(recipe.getRequiredFireType()).draw(guiGraphics, 48, 43);

        if (!recipe.isDoubleRecipe()) {
            guiGraphics.blit(TEXTURE, 54, 23, 224, 0, 18, 18);
        }

        IDrawableAnimated arrow = this.getArrow(recipe);
        arrow.draw(guiGraphics, 74, 43);

        this.drawExperience(recipe, guiGraphics, 12);
        this.drawCookTime(recipe, guiGraphics, 79);
    }

    protected void drawExperience(ClibanoRecipe recipe, GuiGraphics guiGraphics, int y) {
        float experience = recipe.getExperience();
        if (experience > 0) {
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            Minecraft minecraft = Minecraft.getInstance();
            Font font = minecraft.font;
            int stringWidth = font.width(experienceString);

            guiGraphics.drawString(font, experienceString, this.background.getWidth() - stringWidth, y, 0xFF808080, false);
        }
    }

    protected void drawCookTime(ClibanoRecipe recipe, GuiGraphics guiGraphics, int y) {
        int cookTime = recipe.getCookingTime(recipe.getRequiredFireType());
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font font = minecraft.font;
            int stringWidth = font.width(timeString);

            guiGraphics.drawString(font, timeString, this.background.getWidth() - stringWidth, y, 0xFF808080, false);
        }
    }

    @NotNull
    @Override
    public List<Component> getTooltipStrings(@NotNull ClibanoRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 92 && mouseY >= 59 && mouseX <= 117 && mouseY <= 65) {
            ResidueChance chance = recipe.getResidueChance();

            if (chance == null) {
                return List.of();
            }

            return List.of(chance.type().value().name().copy()
                    .append(" ")
                    .append(Component.translatable("jei.forbidden_arcanus.clibanoCombustion.residue"))
                    .append(" (" + chance.chance() * 100 +"%)")
            );
        }

        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }
}
