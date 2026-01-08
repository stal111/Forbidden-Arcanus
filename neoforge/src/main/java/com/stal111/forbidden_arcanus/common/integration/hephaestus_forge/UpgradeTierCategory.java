package com.stal111.forbidden_arcanus.common.integration.hephaestus_forge;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualRequirements;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result.UpgradeTierResult;
import com.stal111.forbidden_arcanus.common.integration.ForbiddenArcanusJEIPlugin;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-06-05
 */
public class UpgradeTierCategory extends HephaestusForgeCategory<UpgradeTierResult> {

    private static final String NAME = "hephaestus_forge_upgrading";
    private static final Identifier TEXTURE = ForbiddenArcanus.identifier("textures/gui/jei/hephaestus_forge/forge_upgrading.png");

    private static final IntIntPair REQUIRED_TIER_POSITION = IntIntPair.of(7, 35);
    private static final IntIntPair UPGRADED_TIER_POSITION = IntIntPair.of(123, 36);

    public UpgradeTierCategory(IGuiHelper guiHelper) {
        super(NAME, guiHelper, TEXTURE, 41, 79);
    }

    @Override
    public @NotNull IRecipeType<Ritual> getRecipeType() {
        return ForbiddenArcanusJEIPlugin.HEPHAESTUS_FORGE_UPGRADING;
    }

    @Override
    protected void buildRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull RitualRequirements requirements, @NotNull UpgradeTierResult result) {
        int tier = requirements.tier().tier();

        builder.addSlot(RecipeIngredientRole.INPUT, REQUIRED_TIER_POSITION.firstInt(), REQUIRED_TIER_POSITION.secondInt())
                .add(HephaestusForgeBlock.createItem(HephaestusForgeLevel.getFromIndex(tier)));

        builder.addSlot(RecipeIngredientRole.OUTPUT, UPGRADED_TIER_POSITION.firstInt(), UPGRADED_TIER_POSITION.secondInt())
                .add(HephaestusForgeBlock.createItem(result.resultTier()));
    }

    @Override
    protected boolean displayEnhancers() {
         return false;
    }
}
