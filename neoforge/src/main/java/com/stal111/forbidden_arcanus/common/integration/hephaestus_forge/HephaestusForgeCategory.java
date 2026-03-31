package com.stal111.forbidden_arcanus.common.integration.hephaestus_forge;

import com.google.common.collect.ImmutableList;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualRequirements;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author stal111
 * @since 2023-06-05
 */
public abstract class HephaestusForgeCategory<T extends RitualResult> implements IRecipeCategory<Ritual> {

    private static final List<IntIntPair> INPUT_POSITIONS = ImmutableList.of(
            IntIntPair.of(63, 13),
            IntIntPair.of(82, 16),
            IntIntPair.of(85, 35),
            IntIntPair.of(82, 54),
            IntIntPair.of(63, 57),
            IntIntPair.of(44, 54),
            IntIntPair.of(41, 35),
            IntIntPair.of(44, 16)
    );

    private static final IntIntPair FORGE_ITEM_POSITION = IntIntPair.of(63, 35);
    private static final IntIntPair ENHANCER_POSITION = IntIntPair.of(10, 12);
    private static final int ENHANCER_Y_OFFSET = 21;

    private static final int WIDTH = 148;
    private static final int HEIGHT = 108;

    private final String name;

    private final IDrawable background;
    private final IDrawable icon;
    private final List<EssenceInfo> essences;

    public HephaestusForgeCategory(String name, IGuiHelper guiHelper, Identifier texture, int essencesStartX, int essencesStartY) {
        this.name = name;
        this.background = guiHelper.createDrawable(texture, 0, 0, WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()));
        this.essences = EssenceInfo.create(guiHelper, essencesStartX, essencesStartY);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.forbidden_arcanus.category." + name);
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Ritual ritual, @NotNull IFocusGroup focusGroup) {
        this.addInputs(builder, ritual.inputs(), ritual.mainIngredient());

        if (this.displayEnhancers()) {
            this.addEnhancers(builder, ritual.requirements().enhancers());
        }

        this.buildRecipe(builder, ritual.requirements(), (T) ritual.result());
    }

    protected abstract void buildRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull RitualRequirements requirements, @NotNull T result);

    protected boolean displayEnhancers() {
        return true;
    }

    private void addInputs(@NotNull IRecipeLayoutBuilder builder, List<RitualInput> inputs, Ingredient mainIngredient) {
        builder.addSlot(RecipeIngredientRole.INPUT, FORGE_ITEM_POSITION.firstInt(), FORGE_ITEM_POSITION.secondInt())
                .add(mainIngredient);

        int index = 0;

        for (RitualInput input : inputs) {
            for (int j = 0; j < input.amount(); j++) {
                builder.addSlot(RecipeIngredientRole.INPUT, INPUT_POSITIONS.get(index).firstInt(), INPUT_POSITIONS.get(index).secondInt())
                        .add(input.ingredient());

                index++;
            }
        }
    }

    private void addEnhancers(@NotNull IRecipeLayoutBuilder builder, HolderSet<EnhancerDefinition> enhancers) {
        for (int i = 0; i < enhancers.size(); i++) {
            Holder<EnhancerDefinition> enhancer = enhancers.get(i);

            builder.addSlot(RecipeIngredientRole.CRAFTING_STATION, ENHANCER_POSITION.firstInt(), ENHANCER_POSITION.secondInt() + i * ENHANCER_Y_OFFSET)
                    .add(enhancer.value().displayItem().value().getDefaultInstance());
        }
    }

    @Override
    public void draw(@NotNull Ritual recipe, @NotNull IRecipeSlotsView slotsView, @NotNull GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics);
        this.essences.forEach(essenceInfo -> essenceInfo.drawable().draw(guiGraphics, essenceInfo.posX(), essenceInfo.posY()));
    }

    @Override
    public void getTooltip(@NotNull ITooltipBuilder tooltip, @NotNull Ritual recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        for (EssenceInfo essenceInfo : this.essences) {
            if (essenceInfo.shouldDisplayTooltip(mouseX, mouseY)) {
                tooltip.add(essenceInfo.getTooltip(recipe.requirements().essences()));
            }
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
