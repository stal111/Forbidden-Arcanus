package com.stal111.forbidden_arcanus.common.integration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoRecipe;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * @author stal111
 * @since 2022-08-14
 */
public class ClibanoCombustionCategory implements IRecipeCategory<ClibanoRecipe> {

    private static final Identifier TEXTURE = ForbiddenArcanus.identifier("textures/gui/jei/clibano_combustion.png");
    private static final Component TITLE = Component.translatable("jei.forbidden_arcanus.category.clibanoCombustion");

    private static final int WIDTH = 147;
    private static final int HEIGHT = 97;

    private final IDrawable background;
    private final IDrawable icon;

    protected final IDrawableStatic staticFlame;
    protected final IDrawableStatic staticBlueFlame;
    protected final IDrawableStatic staticPurpleFlame;

    private final HashMap<ClibanoFireType, IDrawableAnimated> animatedFlames = new HashMap<>();

    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;


    public ClibanoCombustionCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
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

    @Override
    public IRecipeType<ClibanoRecipe> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.CLIBANO_COMBUSTION;
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ClibanoRecipe recipe, @NotNull IFocusGroup focuses) {
//        builder.addSlot(INPUT, 37, 24).add(recipe.getIngredients().get(0));
//
//        if (recipe.isDoubleRecipe()) {
//            builder.addSlot(INPUT, 55, 24).add(recipe.getIngredients().get(1));
//        }
//
//        TagKey<Item> tagKey = recipe.requiredFireType().getTagKey();
//
//        if (tagKey != null) {
//            builder.addSlot(RENDER_ONLY, 12, 60).addIngredients(Ingredient.of(tagKey));
//        }
//
//        recipe.requiredEnhancer().ifPresent(enhancer -> {
//            builder.addSlot(CATALYST, 12, 24).addItemStack(enhancer.value().displayItem().value().getDefaultInstance());
//        });
//
//        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
//
//        builder.addSlot(OUTPUT, 97, 35).addItemStack(recipe.getResultItem(registryAccess));
    }

    private IDrawableAnimated getArrow(int cookingTime) {
        return this.cachedArrows.getUnchecked(cookingTime);
    }

    @Override
    public void draw(@NotNull ClibanoRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        this.animatedFlames.get(recipe.requiredFireType()).draw(guiGraphics, 48, 43);

        if (!recipe.isDoubleRecipe()) {
//            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, 54, 23, 224, 0, 18, 18);
        }

        IDrawableAnimated arrow = this.getArrow(recipe.getDefaultCookingTime());
        arrow.draw(guiGraphics, 74, 43);

        this.drawExperience(recipe.getExperience(), guiGraphics, 12);
        this.drawCookTime(recipe.getDefaultCookingTime(), guiGraphics, 79);
    }

    protected void drawExperience(float experience, GuiGraphics guiGraphics, int y) {
        if (experience > 0) {
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            Minecraft minecraft = Minecraft.getInstance();
            Font font = minecraft.font;
            int stringWidth = font.width(experienceString);

            guiGraphics.drawString(font, experienceString, this.background.getWidth() - stringWidth, y, 0xFF808080, false);
        }
    }

    protected void drawCookTime(int cookingTime, GuiGraphics guiGraphics, int y) {
        if (cookingTime > 0) {
            int cookTimeSeconds = cookingTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font font = minecraft.font;
            int stringWidth = font.width(timeString);

            guiGraphics.drawString(font, timeString, this.background.getWidth() - stringWidth, y, 0xFF808080, false);
        }
    }

    @Override
    public void getTooltip(@NotNull ITooltipBuilder tooltip, @NotNull ClibanoRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (mouseX >= 92 && mouseY >= 59 && mouseX <= 117 && mouseY <= 65) {
            recipe.residueChance().ifPresent(chance -> {
                tooltip.add(chance.type().value().name().copy()
                        .append(" ")
                        .append(Component.translatable("jei.forbidden_arcanus.clibanoCombustion.residue"))
                        .append(" (" + chance.chance() * 100 +"%)")
                );
            });
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
