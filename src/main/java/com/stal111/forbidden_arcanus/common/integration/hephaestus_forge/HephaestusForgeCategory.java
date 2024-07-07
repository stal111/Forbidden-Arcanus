package com.stal111.forbidden_arcanus.common.integration.hephaestus_forge;

import com.google.common.collect.ImmutableList;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualInput;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author stal111
 * @since 2023-06-05
 */
public abstract class HephaestusForgeCategory implements IRecipeCategory<Ritual> {

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

    private final String name;

    private final IDrawable background;
    private final IDrawable icon;
    private final List<EssenceInfo> essences;

    public HephaestusForgeCategory(String name, IGuiHelper guiHelper, ResourceLocation texture, int essencesStartX, int essencesStartY) {
        this.name = name;
        this.background = guiHelper.createDrawable(texture, 0, 0, 148, 108);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.HEPHAESTUS_FORGE_TIER_1.get()));
        this.essences = EssenceInfo.create(guiHelper, essencesStartX, essencesStartY);
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.forbidden_arcanus.category." + name);
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Ritual ritual, @NotNull IFocusGroup focusGroup) {
        this.addInputs(builder, ritual.inputs(), ritual.mainIngredient());

        if (ritual.requirements() != null && this.displayEnhancers()) {
            ritual.requirements().enhancers().ifPresent(holders -> {
                this.addEnhancers(builder, holders);
            });
        }

        this.buildRecipe(builder, ritual);
    }

    protected abstract void buildRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Ritual ritual);

    protected boolean displayEnhancers() {
        return true;
    }

    private void addInputs(@NotNull IRecipeLayoutBuilder builder, List<RitualInput> inputs, Ingredient mainIngredient) {
        builder.addSlot(RecipeIngredientRole.INPUT, FORGE_ITEM_POSITION.firstInt(), FORGE_ITEM_POSITION.secondInt())
                .addIngredients(mainIngredient);

        int index = 0;

        for (RitualInput input : inputs) {
            for (int j = 0; j < input.amount(); j++) {
                builder.addSlot(RecipeIngredientRole.INPUT, INPUT_POSITIONS.get(index).firstInt(), INPUT_POSITIONS.get(index).secondInt())
                        .addIngredients(input.ingredient());

                index++;
            }
        }
    }

    private void addEnhancers(@NotNull IRecipeLayoutBuilder builder, HolderSet<EnhancerDefinition> enhancers) {
        for (int i = 0; i < enhancers.size(); i++) {
            Holder<EnhancerDefinition> enhancer = enhancers.get(i);

            //TODO
//            builder.addSlot(RecipeIngredientRole.CATALYST, ENHANCER_POSITION.firstInt(), ENHANCER_POSITION.secondInt() + i * ENHANCER_Y_OFFSET)
//                    .addItemStack(enhancer.value().item().getDefaultInstance());
        }
    }

    @Override
    public void draw(@NotNull Ritual recipe, @NotNull IRecipeSlotsView slotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.essences.forEach(essenceInfo -> essenceInfo.drawable().draw(guiGraphics, essenceInfo.posX(), essenceInfo.posY()));
    }

    @NotNull
    @Override
    public List<Component> getTooltipStrings(@NotNull Ritual recipe, @NotNull IRecipeSlotsView slotsView, double mouseX, double mouseY) {
        for (EssenceInfo essenceInfo : this.essences) {
            if (essenceInfo.shouldDisplayTooltip(mouseX, mouseY)) {
                return Collections.singletonList(essenceInfo.getTooltip(recipe.requirements().essences()));
            }
        }

        return Collections.emptyList();
    }

    public static ItemStack getForgeItem(int tier) {
        return HephaestusForgeLevel.values()[tier].getBlock().asItem().getDefaultInstance();
    }
}
