package com.stal111.forbidden_arcanus.common.integration;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Hephaestus Smithing Category <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.integration.HephaestusSmithingCategory
 *
 * @author stal111
 * @since 2021-09-14
 */
public class HephaestusSmithingCategory implements IRecipeCategory<Ritual> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/hephaestus_forge_jei.png");

    private static final List<Pair<Integer, Integer>> INPUT_POSITIONS = ImmutableList.of(
            Pair.of(63, 13),
            Pair.of(82, 16),
            Pair.of(85, 35),
            Pair.of(82, 54),
            Pair.of(63, 57),
            Pair.of(44, 54),
            Pair.of(41, 35),
            Pair.of(44, 16)
    );

    private static final Pair<Integer, Integer> FORGE_ITEM_POSITION = new Pair<>(63, 35);
    private static final Pair<Integer, Integer> OUTPUT_POSITION = new Pair<>(121, 36);
    private static final Pair<Integer, Integer> PEDESTAL_TYPE_POSITION = new Pair<>(123, 85);

    private final IDrawable background;
    private final IDrawable icon;
    private final List<EssenceInfo> essences;

    public HephaestusSmithingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 147, 107);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()));
        this.essences = ImmutableList.of(
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 161, 1, 10, 10), EssenceType.AUREAL, 42, 79),
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 173, 1, 10, 10), EssenceType.SOULS, 58, 79),
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 185, 1, 10, 10), EssenceType.BLOOD, 74, 79),
                new EssenceInfo(guiHelper.createDrawable(TEXTURE, 197, 1, 10, 10), EssenceType.EXPERIENCE, 90, 79)
        );
    }

    @Nonnull
    @Override
    public RecipeType<Ritual> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.HEPHAESTUS_SMITHING;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return Component.translatable("jei.forbidden_arcanus.category.hephaestusSmithing");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull Ritual ritual, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, FORGE_ITEM_POSITION.getFirst(), FORGE_ITEM_POSITION.getSecond())
                .addIngredients(ritual.mainIngredient());

        int index = 0;

        for (RitualInput input : ritual.inputs()) {
            for (int j = 0; j < input.amount(); j++) {
                builder.addSlot(RecipeIngredientRole.INPUT, INPUT_POSITIONS.get(index).getFirst(), INPUT_POSITIONS.get(index).getSecond())
                        .addIngredients(input.ingredient());

                index++;
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_POSITION.getFirst(), OUTPUT_POSITION.getSecond())
                .addItemStack(((CreateItemResult) ritual.result()).getResult());

        int requiredTier = ritual.requirements() == null ? 1 : ritual.requirements().tier();

        builder.addSlot(RecipeIngredientRole.OUTPUT, PEDESTAL_TYPE_POSITION.getFirst(), PEDESTAL_TYPE_POSITION.getSecond())
                .addItemStack(HephaestusForgeBlock.setTierOnStack(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()), requiredTier))
                .addTooltipCallback((recipeSlotView, tooltip) -> {
                    tooltip.clear();
                    tooltip.add(Component.translatable("jei.forbidden_arcanus.hephaestusSmithing.required_tier").append(": " + requiredTier));
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
                return Collections.singletonList(Component.translatable("jei.forbidden_arcanus.hephaestusSmithing.required_" + essenceInfo.type().getSerializedName()).append(": " + recipe.essences().get(essenceInfo.type())));
            }
        }

        return Collections.emptyList();
    }

    private record EssenceInfo(IDrawableStatic drawable, EssenceType type, int posX, int posY) {
    }
}
