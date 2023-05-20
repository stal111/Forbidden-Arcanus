package com.stal111.forbidden_arcanus.common.integration;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.CreateItemResult;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import it.unimi.dsi.fastutil.ints.IntIntPair;
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
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
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
 * @since 2021-09-14
 */
public class HephaestusSmithingCategory implements IRecipeCategory<Ritual> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/container/hephaestus_forge_jei.png");

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
    private static final IntIntPair OUTPUT_POSITION = IntIntPair.of(121, 36);
    private static final IntIntPair FORGE_TIER_POSITION = IntIntPair.of(123, 85);
    private static final IntIntPair ENHANCER_POSITION = IntIntPair.of(10, 12);
    private static final int ENHANCER_Y_OFFSET = 21;

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
        this.addInputs(builder, ritual.inputs(), ritual.mainIngredient());

        if (ritual.requirements() != null) {
            this.addEnhancers(builder, ritual.requirements().enhancers());
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_POSITION.firstInt(), OUTPUT_POSITION.secondInt())
                .addItemStack(((CreateItemResult) ritual.result()).getResult());

        int requiredTier = ritual.requirements() == null ? 1 : ritual.requirements().tier();

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, FORGE_TIER_POSITION.firstInt(), FORGE_TIER_POSITION.secondInt())
                .addItemStack(HephaestusForgeBlock.setTierOnStack(new ItemStack(ModBlocks.HEPHAESTUS_FORGE.get()), requiredTier))
                .addTooltipCallback((recipeSlotView, tooltip) -> {
                    tooltip.clear();
                    tooltip.add(Component.translatable("jei.forbidden_arcanus.hephaestusSmithing.required_tier").append(": " + requiredTier));
                });
    }

    private void addInputs(@Nonnull IRecipeLayoutBuilder builder, List<RitualInput> inputs, Ingredient mainIngredient) {
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

    private void addEnhancers(@Nonnull IRecipeLayoutBuilder builder, List<Holder<EnhancerDefinition>> enhancers) {
        for (int i = 0; i < enhancers.size(); i++) {
            Holder<EnhancerDefinition> enhancer = enhancers.get(i);

            builder.addSlot(RecipeIngredientRole.CATALYST, ENHANCER_POSITION.firstInt(), ENHANCER_POSITION.secondInt() + i * ENHANCER_Y_OFFSET)
                    .addItemStack(enhancer.get().item().getDefaultInstance());
        }
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
