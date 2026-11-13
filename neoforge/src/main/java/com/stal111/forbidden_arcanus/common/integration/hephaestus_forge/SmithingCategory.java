package com.stal111.forbidden_arcanus.common.integration.hephaestus_forge;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.TierPredicate;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.RitualResult;
import com.stal111.forbidden_arcanus.common.integration.ForbiddenArcanusJEIPlugin;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * @author stal111
 * @since 2023-06-05
 */
public class SmithingCategory extends HephaestusForgeCategory<RitualResult> {

    private static final String NAME = "hephaestus_smithing";
    private static final Identifier TEXTURE = ForbiddenArcanus.identifier("textures/gui/jei/hephaestus_forge/smithing.png");

    private static final IntIntPair FORGE_TIER_POSITION = IntIntPair.of(122, 85);
    private static final IntIntPair OUTPUT_POSITION = IntIntPair.of(122, 35);

    public SmithingCategory(IGuiHelper guiHelper) {
        super(NAME, guiHelper, TEXTURE, 41, 83);
    }

    @Override
    public @NotNull IRecipeType<Ritual> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.HEPHAESTUS_SMITHING;
    }

    @Override
    protected void buildRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Ritual ritual, @NotNull RitualResult result) {
        TierPredicate tierPredicate = ritual.requirements().tier();
        ContextMap contextMap = Optional.ofNullable(Minecraft.getInstance().level)
                .map(SlotDisplayContext::fromLevel)
                .orElse(ContextMap.EMPTY);
        List<ItemStack> mainInputs = ritual.mainIngredient().display().resolveForStacks(contextMap);

        if (mainInputs.isEmpty()) {
            mainInputs = List.of(ItemStack.EMPTY);
        }

        IRecipeSlotBuilder outputSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_POSITION.firstInt(), OUTPUT_POSITION.secondInt());

        for (ItemStack mainInput : mainInputs) {
            outputSlot.add(result.getResultItem(mainInput));
        }

        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, FORGE_TIER_POSITION.firstInt(), FORGE_TIER_POSITION.secondInt())
                .add(HephaestusForgeBlock.createItem(tierPredicate.tier()))
                .addRichTooltipCallback((_, tooltip) -> tooltip.add(tierPredicate.asComponent()));
    }
}
